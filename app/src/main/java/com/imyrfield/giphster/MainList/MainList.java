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

import android.app.DownloadManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.imyrfield.giphster.API.GiphyResponseModel.*;
import com.imyrfield.giphster.Util.RealmHelper;
import com.imyrfield.giphster.Util.BusProvider;
import com.imyrfield.giphster.ImageDialog;
import com.imyrfield.giphster.R;
import com.squareup.otto.Subscribe;

import static android.os.Environment.DIRECTORY_PICTURES;

public class MainList extends AppCompatActivity implements ImageDialog.FaviconClickHandler {

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;
    TabLayout tabLayout;
    DownloadManager manager;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);

        // Set up the ViewPager with the sections adapter.
        mViewPager.setAdapter(mSectionsPagerAdapter);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        BusProvider.getInstance().register(this);
    }

    @Subscribe
    public void onDialogEvent(BusProvider.DialogEvent event){
        ImageDialog image = new ImageDialog();
        image.setData(event.getData());
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(image, "gif").addToBackStack(null).commit();
    }

    @Override
    public void toggleFavorite(Gif gif, long id) {

        RealmHelper realmHelper = RealmHelper.getInstance();

        if (id < 0){
            //Add to Favorites
            id = downloadGif(gif.getUrl());
            realmHelper.addToRealm(gif, id);
        } else {
            //Remove from favorites
            int removed = deleteGif(realmHelper.getFilePath(id));
            if (removed > 0) realmHelper.removeFromRealm(id);
        }
    }

    private long downloadGif(String url){

        manager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);
        request.setVisibleInDownloadsUi(false);
        request.setDestinationInExternalFilesDir(this, DIRECTORY_PICTURES, url);
        return manager.enqueue(request);
    }

    private int deleteGif(String uri){
        Uri fileUri = Uri.parse(uri);
        return getContentResolver().delete(fileUri, null, null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BusProvider.getInstance().unregister(this);
    }
}
