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

package com.imyrfield.giphster

import com.google.gson.annotations.SerializedName

/**
 * Created by imyrfield on 2017-06-19.
 */
data class GiphyResponse(val data : List<GiphyData>, val pagination : Pagination, val meta : Meta) {

    data class GiphyData(val id: String, val images : ImgFormats)

    data class ImgFormats(@SerializedName("fixed_width") val fixedWidth : Gif,
                          @SerializedName("fixed_width_small") val fixedWidthSmall : Gif,
                          @SerializedName("fixed_width_still") val fixedWidthStill : Gif)

    data class Gif(val url : String, val width : Int, val height : Int)

    data class Meta(val status : Int)

    data class Pagination(@SerializedName("total_count") val totalCount : Int, val count : Int, val offset : Int )

}