package com.sheniff.googleimagesearch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.sheniff.googleimagesearch.GoogleImages;
import com.sheniff.googleimagesearch.R;
import com.sheniff.googleimagesearch.SettingsFragment;
import com.sheniff.googleimagesearch.adapters.ImageResultsAdapter;
import com.sheniff.googleimagesearch.models.ImageResult;
import com.sheniff.googleimagesearch.models.ImageSearchSettings;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/*
* The following user stories must be completed:

[NEXT] User can click on "settings" which allows selection of advanced search options to filter results
[NEXT] Advanced: Replace Filter ImageSearchSettings Activity with a lightweight modal overlay
[NEXT] User can configure advanced search filters such as:
    Size (small, medium, large, extra-large)
    Color filter (black, blue, brown, gray, green, etc...)
    Type (faces, photo, clip art, line art)
    Site (espn.com)
[NEXT] Subsequent searches will have any filters applied to the search results

User can scroll down “infinitely” to continue loading more image results (up to 8 pages)

Advanced: Robust error handling, check if internet is available, handle error cases, network failures
Advanced: Use the ActionBar SearchView or custom layout as the query box instead of an EditText
Advanced: User can share an image to their friends or email it to themselves
Advanced: Improve the user interface and experiment with image assets and/or styling and coloring
Bonus: Use the StaggeredGridView to display improve the grid of image results
Bonus: User can zoom or pan images displayed in full-screen detail view
*/

public class SearchActivity extends ActionBarActivity {
    private EditText etQuery;
    private GridView gvResults;
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
        gvResults.setAdapter(aImageResults);
        // init settings object
        searchSettings = new ImageSearchSettings();
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
        String query = etQuery.getText().toString();
        GoogleImages.query(query, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray imageResultsJSON;
                try {
                    imageResultsJSON = response.getJSONObject("responseData").getJSONArray("results");
                    imageResults.clear();
                    // When you make changes to the adapter, it does modify the underlying data
                    aImageResults.addAll(ImageResult.fromJSONArray(imageResultsJSON));
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
