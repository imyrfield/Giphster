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
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.imyrfield.giphster.API.GiphyResponseModel;
import com.imyrfield.giphster.R;

import java.util.List;

import io.realm.Realm;

/**
 * Created by imyrfield on 2017-06-20.
 */

public class FavoriteFragment extends Fragment {


    private RecyclerView rv;
    private Realm realm;
    private List<GiphyResponseModel.Gif> favorites;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = Realm.getDefaultInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_list, container, false);
        rv = (RecyclerView) root.findViewById(R.id.mainlist);
        TextView empty = (TextView) root.findViewById(android.R.id.empty);
        rv.setVisibility(View.GONE);
        empty.setVisibility(View.VISIBLE);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        realm.close();
    }

//    // Listeners will be notified when data changes
//    puppies.addChangeListener(new OrderedRealmCollectionChangeListener<RealmResults<Dog>>() {
//        @Override
//        public void onChange(RealmResults<Dog> results, OrderedCollectionChangeSet changeSet) {
//            // Query results are updated in real time with fine grained notifications.
//            changeSet.getInsertions(); // => [0] is added.
//        }
//    });
//
//// Asynchronously update objects on a background thread
//   realm.executeTransactionAsync(new Realm.Transaction() {
//        @Override
//        public void execute(Realm bgRealm) {
//            Dog dog = bgRealm.where(Dog.class).equalTo("age", 1).findFirst();
//            dog.setAge(3);
//        }
//    }, new Realm.Transaction.OnSuccess() {
//        @Override
//        public void onSuccess() {
//            // Original queries and Realm objects are automatically updated.
//            puppies.size(); // => 0 because there are no more puppies younger than 2 years old
//            managedDog.getAge();   // => 3 the dogs age is updated
//        }
//    });

}
