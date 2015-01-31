package com.sheniff.googleimagesearch.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;

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

[NEXT] Advanced: Use the ActionBar SearchView or custom layout as the query box instead of an EditText

Advanced: Robust error handling, check if internet is available, handle error cases, network failures
Advanced: User can share an image to their friends or email it to themselves
Advanced: Improve the user interface and experiment with image assets and/or styling and coloring
Bonus: Use the StaggeredGridView to display improve the grid of image results
Bonus: User can zoom or pan images displayed in full-screen detail view
Reorganize the code the Jorge way xD
Add loader for pagination
*/

public class SearchActivity extends ActionBarActivity {
    private static int MAX_PAGES = 8;
    private EditText etQuery;
    private String query = "";
    private GridView gvResults;
    private ArrayList<ImageResult> imageResults;
    private ImageResultsAdapter aImageResults;
    private ImageSearchSettings searchSettings;

    private EndlessScrollListener endlessScrollListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setupViews();
        imageResults = new ArrayList<>();
        aImageResults = new ImageResultsAdapter(this, imageResults);
//        aImageResults.setListMaxSize(MAX_PAGES * GoogleImages.PER_PAGE);
        gvResults.setAdapter(aImageResults);
        // init settings object
        searchSettings = new ImageSearchSettings();
        // set up infinite scroll
        endlessScrollListener = new EndlessScrollListener(MAX_PAGES) {
            @Override
            protected void onLoadMore(int page, int totalItemCount) {
                Log.d("ONLOADMORE", "Loading page..." + Integer.toString(page) + " " + Integer.toString(totalItemCount) + " " + etQuery.getText().toString());
                queryImages(query, page);
            }
        };
        gvResults.setOnScrollListener(endlessScrollListener);
    }

    private void setupViews() {
        etQuery = (EditText) findViewById(R.id.etQuery);
        gvResults = (GridView) findViewById(R.id.gvResults);
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

    public void onImageSearch(View view) {
        query = etQuery.getText().toString();

        if(imageResults.size() > 0) {
            imageResults.clear();
            aImageResults.notifyDataSetChanged();
        }

        queryImages(query, 0);

        // hide keyboard
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etQuery.getWindowToken(), 0);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    public void showSettings(MenuItem item) {
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        SettingsFragment settingsDialog = SettingsFragment.newInstance(searchSettings);
        settingsDialog.show(fm, "fragment_settings");
    }
}
