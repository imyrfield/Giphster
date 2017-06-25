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

package com.imyrfield.giphster.API;

import com.imyrfield.giphster.Util.Utility;

import io.reactivex.Observable;

/**
 * Created by imyrfield on 2017-06-20.
 */

public final class GiphyService {

    private static int NUM_RESULTS = 24;
    private static String API_KEY = "dc6zaTOxFJmzC";

    private static IGiphyAPI giphyAPI;
    private static volatile GiphyService instance;

    private GiphyService(){
        giphyAPI = Utility.createService(IGiphyAPI.class);
    }

    public static GiphyService getInstance(){
        if (instance == null){
            synchronized (GiphyService.class){
                if (instance == null) {
                    instance = new GiphyService();
                }
            }
        }
        return instance;
    }

    public Observable<GiphyResponseModel> getTrendingGifs(){
        return giphyAPI.trending(API_KEY, NUM_RESULTS, 0);
    }

    public Observable<GiphyResponseModel> getSearchResults(String search){
        return giphyAPI.search(API_KEY, search, NUM_RESULTS, 0);
    }
}
