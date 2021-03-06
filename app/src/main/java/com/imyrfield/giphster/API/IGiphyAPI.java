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

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by imyrfield on 2017-06-20.
 */

public interface IGiphyAPI {

    @GET("trending")
    Observable<GiphyResponseModel> trending(
            @Query("api_key") String apiKey,
            @Query("limit") int limit,
            @Query("offset") int offset
    );

    @GET("search")
    Observable<GiphyResponseModel> search(
            @Query("api_key") String apiKey,
            @Query("q") String query,
            @Query("limit") int limit,
            @Query("offset") int offset
    );
}
