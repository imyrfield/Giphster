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

package com.imyrfield.giphster.Favorites

import android.net.Uri
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by imyrfield on 2017-06-25.
 */

open class FavoritesModel(
        @PrimaryKey var urlString: String = "",
        var createdAt : Long = 0, // Used to sort Favorites
        var fileId : Long = 0, //Maps to DownloadManager file ID
        var fileUri : String? = null // Uri to file in system
) : RealmObject(){}