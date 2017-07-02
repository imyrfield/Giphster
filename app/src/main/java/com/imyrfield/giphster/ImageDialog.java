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

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.imyrfield.giphster.API.GiphyResponseModel.*;
import com.imyrfield.giphster.Favorites.FavoritesModel;
import com.imyrfield.giphster.Util.RealmHelper;

import io.realm.RealmResults;

import static com.bumptech.glide.request.RequestOptions.centerCropTransform;

/**
 * Created by imyrfield on 2017-06-22.
 */

public class ImageDialog extends AppCompatDialogFragment {

    private Gif gif;
    private ImageButton favorite;
    FaviconClickHandler mFaviconClickHandler;
    RealmHelper realmHelper;
    RealmResults<FavoritesModel> query;
    String url;
    long id = 0;

    private RequestOptions options = new RequestOptions()
            .error(R.drawable.ic_image_error)
            .apply(centerCropTransform());

    public interface FaviconClickHandler{
        void toggleFavorite(Gif gif, long id);
    }

    /*
     * Enforce interface implementation on hosting activity
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        realmHelper = RealmHelper.getInstance();
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

        ImageView image = (ImageView) root.findViewById(R.id.expanded_gif);
        favorite = (ImageButton) root.findViewById(R.id.favorite_icon);

        Glide.with(this)
                .asGif()
                .load(getImageUrl(gif))
                .apply(options)
                .into(image);

        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFaviconClickHandler.toggleFavorite(gif, id);
                dismiss();
            }
        });

        return root;
    }

    private String getImageUrl(Gif gif){

        url = gif.getUrl();
        id = realmHelper.isFavorite(url);

        if (id > 0) {
            // Already a favorite
            favorite.setImageResource(R.drawable.favorite_icon_checked);
            // Get uri to file on local system
            url = realmHelper.getFilePath(id);
        }
        return url;
    }

    public void setData(Gif gif){
        this.gif = gif;
    }
}
