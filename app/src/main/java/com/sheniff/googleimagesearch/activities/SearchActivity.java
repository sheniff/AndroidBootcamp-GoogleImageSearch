package com.sheniff.googleimagesearch.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;

import com.etsy.android.grid.StaggeredGridView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.sheniff.googleimagesearch.EndlessScrollListener;
import com.sheniff.googleimagesearch.GoogleImages;
import com.sheniff.googleimagesearch.R;
import com.sheniff.googleimagesearch.adapters.ImageResultsAdapter;
import com.sheniff.googleimagesearch.fragments.SettingsFragment;
import com.sheniff.googleimagesearch.models.ImageResult;
import com.sheniff.googleimagesearch.models.ImageSearchSettings;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/*
* The following user stories must be completed:

[NEXT] Advanced: Robust error handling, check if internet is available, handle error cases, network failures

Add loader for pagination (fix it!!)
Advanced: Improve the user interface and experiment with image assets and/or styling and coloring
Reorganize the code the Jorge way xD

*/

public class SearchActivity extends ActionBarActivity {
    private static int MAX_PAGES = 8;
    private String query = "";
    private StaggeredGridView gvResults;
    private ArrayList<ImageResult> imageResults;
    private ImageResultsAdapter aImageResults;
    private ImageSearchSettings searchSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setupViews();
        imageResults = new ArrayList<>();
        aImageResults = new ImageResultsAdapter(this, imageResults);
        aImageResults.setListMaxSize(MAX_PAGES * GoogleImages.PER_PAGE);
        gvResults.setAdapter(aImageResults);
        // init settings object
        searchSettings = new ImageSearchSettings();
        // set up infinite scroll
        EndlessScrollListener endlessScrollListener = new EndlessScrollListener(MAX_PAGES) {
            @Override
            protected void onLoadMore(int page, int totalItemCount) {
                queryImages(query, page);
            }
        };
        gvResults.setOnScrollListener(endlessScrollListener);
    }

    private void setupViews() {
        gvResults = (StaggeredGridView) findViewById(R.id.gvResults);
        gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Launch the image display activity
                Intent i = new Intent(SearchActivity.this, ImageDisplayActivity.class);
                ImageResult result = imageResults.get(position);
                i.putExtra("result", result);
                startActivity(i);
            }
        });
    }

    public void queryImages(String query, final int page) {
        GoogleImages.query(this, query, searchSettings, page, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray imageResultsJSON;
                try {
                    imageResultsJSON = response.getJSONObject("responseData").getJSONArray("results");
                    // When you make changes to the adapter, it does modify the underlying data
                    imageResults.addAll(ImageResult.fromJSONArray(imageResultsJSON));
                    aImageResults.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String q) {
                query = q;
                if(imageResults.size() > 0) {
                    imageResults.clear();
                    aImageResults.notifyDataSetChanged();
                }
                queryImages(query, 0);
                // hide keyboard
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                if(getCurrentFocus() != null){
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    public void showSettings(MenuItem item) {
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        SettingsFragment settingsDialog = SettingsFragment.newInstance(searchSettings);
        settingsDialog.show(fm, "fragment_settings");
    }
}
