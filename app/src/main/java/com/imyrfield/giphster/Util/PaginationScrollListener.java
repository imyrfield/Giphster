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

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by imyrfield on 2017-07-02.
 */

public abstract class PaginationScrollListener extends RecyclerView.OnScrollListener {

    private int currentPage = 0;
    private int prevTotalItems = 0;
    private boolean isLoading = true;
    private static final int INITIAL_INDEX = 0;
    private int visibleThreshold = 5;

    private final GridLayoutManager layoutManager;

    public PaginationScrollListener(GridLayoutManager manager){
        layoutManager =  manager;
        visibleThreshold = visibleThreshold * manager.getSpanCount();
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();;
        int totalItems = layoutManager.getItemCount();

        // If totalItems returned from layoutManager is less then prevTotalItems, then list must
        // have been cleared. So reset back to initial counts.
        if (totalItems < prevTotalItems){
            currentPage = INITIAL_INDEX;
            prevTotalItems = totalItems;
            if (prevTotalItems == 0) {
                isLoading = true;
            }
        }

        // While loading is true, check if total items returned from layoutmanager is greater then
        // prevTotalItems. If it is, then load must have finished, so set isLoading to false, and
        // update prevTotalItems count.
        if (isLoading && (totalItems > prevTotalItems)){
            isLoading = false;
            prevTotalItems = totalItems;
        }

        // If we've breached threshold, and aren't currently loading data, then loadMoreData()
        if (!isLoading && (lastVisibleItemPosition + visibleThreshold) > totalItems){
            currentPage++;
            loadMoreData(currentPage, totalItems, recyclerView);
            isLoading = true;
        }
    }

    // Reset before starting new search
    public void reset(){
        currentPage = INITIAL_INDEX;
        prevTotalItems = 0;
        isLoading = true;

    }

    // Implement to load more data
    public abstract void loadMoreData(int page, int totalItems, RecyclerView rv);
}
