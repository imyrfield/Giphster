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

package com.imyrfield.giphster.API


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by imyrfield on 2017-06-19.
 */
interface GiphyKlient {
    @GET("trending")
    fun trendingGifs(@Query("api_key") apiKey : String,
                     @Query("limit") limit : Int,
                     @Query("offset") offset : Int) : Call<GiphyResponseModel>

    @GET("search")
    fun search(@Query("api_key") apiKey : String,
               @Query("q") query : String,
               @Query("limit") limit : Int,
               @Query("offset") offset : Int) : Call<GiphyResponseModel>
}
