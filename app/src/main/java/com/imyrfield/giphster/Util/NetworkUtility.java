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

package com.imyrfield.giphster.Util;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by imyrfield on 2017-06-19.
 */

public class NetworkUtility {
    private static final String BASE_URL = "http://api.giphy.com/v1/gifs/";

    private static final Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create());

    private static final OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();

    private static final Retrofit retrofit = builder
            .client(okBuilder.build())
            .build();

    public static <S> S createService(Class<S> serviceClass){
        return  retrofit.create(serviceClass);
    }

}
