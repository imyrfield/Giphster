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

package com.imyrfield.giphster.Favorites;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.imyrfield.giphster.API.GiphyResponseModel.*;
import com.imyrfield.giphster.MainList.GifAdapter;
import com.imyrfield.giphster.MainList.MainList;
import com.imyrfield.giphster.R;
import com.imyrfield.giphster.RealmHelper;

import java.util.Iterator;

import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;


/**
 * Created by imyrfield on 2017-06-20.
 */

public class FavoriteFragment extends Fragment{

    private static final int NUM_COLS = 2;
    private RealmHelper realmHelper;

    private RecyclerView recyclerView;
    private GifAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private TextView emptyView;

    public FavoriteFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        realmHelper = RealmHelper.getInstance();
        mAdapter = new GifAdapter();
        layoutManager = new GridLayoutManager(getActivity(), NUM_COLS);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_list, container, false);
        recyclerView = (RecyclerView) root.findViewById(R.id.mainlist);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);

        emptyView = (TextView) root.findViewById(android.R.id.empty);
        setupEmptyView();

        getFavorites();

        return root;
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    private void getFavorites(){

        realmHelper.getFavorites()
                .iterator()
                .forEachRemaining(favoritesModel ->
                        mAdapter.add(new Gif(favoritesModel.getUrlString(), 0, 0))
                );
        mAdapter.notifyDataSetChanged();
    }

    private void setupEmptyView() {
       // emptyView.setVisibility(mAdapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
        //recyclerView.setVisibility(mAdapter.getItemCount() == 0 ? View.GONE: View.VISIBLE);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){
            updateViews();
        }
    }

    public void updateViews(){
        mAdapter.clear();
        getFavorites();
        System.out.println("called updateViews()");
    }
}
