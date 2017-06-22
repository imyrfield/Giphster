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

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.imyrfield.giphster.API.GiphyResponse;
import com.imyrfield.giphster.API.GiphyService;
import com.imyrfield.giphster.R;

import butterknife.BindView;
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
    @BindView(R.id.mainlist)
    RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private GifAdapter gifAdapter;
    private CompositeDisposable disposables;

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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main_list, container, false);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(gifAdapter);

        displayTrending();

        return rootView;
    }

    private void displayTrending() {
        displayGifs(GiphyService.getInstance().getTrendingGifs());
    }

    private void displaySearchResults(String search) {
        displayGifs(GiphyService.getInstance().getSearchResults(search));
    }

    private void displayGifs(Observable<GiphyResponse> observable) {
        disposables.add(observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(response -> response.getData())
                .flatMap(Observable::fromIterable)
                //Seperate into method to get URL
                .subscribe(response -> System.out.println(response.component2().getFixedWidth()))
        );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposables.dispose();
    }
}
