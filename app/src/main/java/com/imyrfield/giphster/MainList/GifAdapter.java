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

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.imyrfield.giphster.API.GiphyResponse.*;
import com.imyrfield.giphster.R;

import java.util.ArrayList;
import java.util.List;
import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;
import static com.bumptech.glide.request.RequestOptions.centerCropTransform;



/**
 * Created by imyrfield on 2017-06-20.
 */

public class GifAdapter extends RecyclerView.Adapter<GifViewHolder> {

    private List<Gif> list = new ArrayList<>();
    RequestOptions options = new RequestOptions()
            .placeholder(R.drawable.ic_image_placeholder)
            .error(R.drawable.ic_image_error)
            .apply(centerCropTransform());

    @Override
    public GifViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GifViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(GifViewHolder holder, int position) {


        Glide.with(holder.gif.getContext())
                .asGif()
                .load(list.get(position).getUrl())
                .apply(options)
                .into(holder.gif);

        // TODO: hide progress bar and show imageview
        holder.itemView.setOnClickListener(view -> loadDialog(view));
    }

    private void loadDialog(View view) {
    }

    @Override
    public void onViewRecycled(final GifViewHolder holder) {
        super.onViewRecycled(holder);
        Glide.with(holder.gif.getContext()).clear(holder.gif);
        holder.gif.setImageDrawable(null);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public List<Gif> getList(){
        return list;
    }
}
