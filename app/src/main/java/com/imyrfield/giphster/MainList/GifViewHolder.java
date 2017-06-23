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

import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.imyrfield.giphster.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by imyrfield on 2017-06-21.
 */

class GifViewHolder extends RecyclerView.ViewHolder{

    ProgressBar pbar;
    @BindView(R.id.gif) ImageView gif;
    ImageView gifImage;

    public GifViewHolder(View itemView) {
        super(itemView);

        //ButterKnife.bind(this, itemView);
        gifImage = (ImageView) itemView.findViewById(R.id.gif);
        pbar = (ProgressBar) itemView.findViewById(R.id.progressbar);
    }
}
