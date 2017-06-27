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

package com.imyrfield.giphster; /****************************************************************************************************
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

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.imyrfield.giphster.API.GiphyResponseModel.*;
import com.imyrfield.giphster.R;

/**
 * Created by imyrfield on 2017-06-22.
 */

public class ImageDialog extends AppCompatDialogFragment {

    private ImageView image;
    private Gif gif;
    private ImageButton favorite;
    FaviconClickHandler mFaviconClickHandler;

    public interface FaviconClickHandler{
        void isFavorite(Gif gif);
    }

    @Override
    public void onAttach(Context context) {
        // Enforce interface implementation on hosting activity
        super.onAttach(context);
        try{
            mFaviconClickHandler = (FaviconClickHandler) getActivity();
        }catch (ClassCastException e){
            throw new ClassCastException(getActivity().toString() + " must implement FaviconClickHandler");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.image_dialog, container, false);
        root.setClickable(false);

        image = (ImageView) root.findViewById(R.id.expanded_gif);
        Glide.with(this)
                .asGif()
                .load(gif.getUrl()).into(image);

        favorite = (ImageButton) root.findViewById(R.id.favorite_icon);
        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFaviconClickHandler.isFavorite(gif);
            }
        });

        return root;
    }

    public void setData(Gif gif){
        this.gif = gif;
    }


}
