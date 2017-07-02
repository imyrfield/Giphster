/****************************************************************************************************
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file        *
 * except in compliance with the License. You may obtain a copy of the License at:                  *
 *                                                                                                  *
 * http://www.apache.org/licenses/LICENSE-2.0                                                       *
 *                                                                                                  *
 * Unless required by applicable law or agreed to in writing, software distributed under the        *
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY              *
 * KIND, either express or implied. See the License for the specific language governing             *
 * permissions and limitations under the License.                                                   *
 ****************************************************************************************************/

package com.imyrfield.giphster.MainList;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.imyrfield.giphster.API.GiphyResponseModel;
import com.imyrfield.giphster.API.GiphyService;
import com.imyrfield.giphster.Util.PaginationScrollListener;
import com.imyrfield.giphster.R;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by imyrfield on 2017-06-20.
 */

public class MainFragment extends Fragment {

    static final String TAG = MainFragment.class.getSimpleName();
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final int NUM_COLS = 2;
    private RecyclerView recyclerView;
    private GridLayoutManager layoutManager;
    private GifAdapter gifAdapter;
    private CompositeDisposable disposables;
    private TextView emptyView;
    private PaginationScrollListener scrollListener;
    private boolean isSearching = false;
    private String searchQuery;

    public MainFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static MainFragment newInstance(int sectionNumber) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutManager = new GridLayoutManager(getActivity(), NUM_COLS);
        gifAdapter = new GifAdapter();
        disposables = new CompositeDisposable();
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_list, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.mainlist);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(gifAdapter);
        recyclerView.addOnScrollListener(getScrollListener());

        emptyView = (TextView) rootView.findViewById(android.R.id.empty);
        toggleEmptyView();

        // Default list shows trending Gifs
        displayTrending(0);

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_main_list, menu);
        MenuItem item = menu.findItem(R.id.menu_search);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(getTextListener(searchView));

        // Had to rollback to supportLibrary 25.3.0 because 26.0.0-alpha1 has a bug with this method
        MenuItemCompat.setOnActionExpandListener(item, new MenuItemCompat.OnActionExpandListener(){

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                isSearching = false;
                gifAdapter.clear();
                scrollListener.reset();
                displayTrending(0);
                return true;
            }
        });
    }

    private void displayTrending(int offSet) {
        displayGifs(GiphyService.getInstance().getTrendingGifs(offSet));
    }

    public void displaySearchResults(String search, int offSet) {
        displayGifs(GiphyService.getInstance().getSearchResults(search, offSet));
    }

    private void displayGifs(Observable<GiphyResponseModel> observable) {

        disposables.add(observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(response -> response.getData())
                .flatMap(Observable::fromIterable)
                .subscribe(response -> {
                    gifAdapter.add(response.component2().getFixedWidth());
                    toggleEmptyView();
                }));
    }

    private void toggleEmptyView(){
        emptyView.setText(R.string.mainlist_loading_error);
        emptyView.setVisibility(gifAdapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
        recyclerView.setVisibility(gifAdapter.getItemCount() == 0 ? View.GONE: View.VISIBLE);
    }

    private SearchView.OnQueryTextListener getTextListener(SearchView view){

        return new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                searchQuery = query;
                isSearching = true;
                gifAdapter.clear();
                scrollListener.reset();

                displaySearchResults(query, 0);

                //Hides keyboard after submitting
                view.clearFocus();
                try {
                    InputMethodManager input = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                    input.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        };
    }

    /*
     * Pagination Scroll Listener
     */
    private PaginationScrollListener getScrollListener(){
        return new PaginationScrollListener(layoutManager) {
            @Override
            public void loadMoreData(int page, int totalItems, RecyclerView rv) {

                if (isSearching) {
                    displaySearchResults(searchQuery, totalItems);
                } else {
                    displayTrending(totalItems);
                }
            }
        };
    }

    @Override
    public void onStop() {
        super.onStop();
        disposables.clear();
    }
}
