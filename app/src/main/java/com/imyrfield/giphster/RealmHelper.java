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

package com.imyrfield.giphster;

import com.imyrfield.giphster.API.GiphyResponseModel.*;
import com.imyrfield.giphster.Favorites.FavoriteFragment;
import com.imyrfield.giphster.Favorites.FavoritesModel;

import java.util.Date;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by imyrfield on 2017-06-30.
 */

public class RealmHelper {

    private static RealmHelper realmHelper;
    private Realm realm;
    private RealmResults results;

    private RealmHelper(){
        realm = Realm.getDefaultInstance();
    }

    public static RealmHelper getInstance(){
        if (realmHelper == null){
            synchronized (RealmHelper.class){
                if (realmHelper == null) {
                    realmHelper = new RealmHelper();
                }
            }
        }
        return realmHelper;
    }

    public long isFavorite(String url){
        RealmResults<FavoritesModel> query = realm.where(FavoritesModel.class)
                .equalTo("urlString", url)
                .findAll();
        return !query.isEmpty() ? query.first().getFileId() : -1;
    }

    public void addToRealm(Gif gif, long id){

        String uri = getFilePath(id);
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(new FavoritesModel(gif.getUrl(), new Date().getTime(), id, uri));
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {}
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
            }
        });
    }

    public void removeFromRealm(long id){
        realm.executeTransactionAsync(new Realm.Transaction() {
                                          @Override
                                          public void execute(Realm realm) {
                                             realm.where(FavoritesModel.class).equalTo("fileId", id).findAll().deleteAllFromRealm();
                                          }
                                      }, new Realm.Transaction.OnSuccess() {
                                          @Override
                                          public void onSuccess() {
                                          }
                                      }, new Realm.Transaction.OnError() {
                                          @Override
                                          public void onError(Throwable error) {
                                          }
                                      }
        );
    }

    public RealmResults<FavoritesModel> getFavorites() {

        return realm.where(FavoritesModel.class).findAllSortedAsync("createdAt");
    }

    public String getFilePath(long id){
        return "content://downloads/all_downloads/" + id;
    }

}
