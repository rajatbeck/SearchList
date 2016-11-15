package com.rajatbeck.searchlist;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.provider.Settings.Secure;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, SearchFragment.OnFragmentInteractionListener {

    private static final String TAG = "MAIN_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.search_icon);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        /*searchView.setSearchableInfo(searchManager.getSearchableInfo(
                new ComponentName(this, SearchableActivity.class)));*/
        searchView.setIconifiedByDefault(false);
        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {

            FragmentManager fragmentManager = getSupportFragmentManager();

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                Log.d(TAG, "search is clicked");
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.add(R.id.fragment_layout, new SearchFragment());
                ft.commit();
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_layout);
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.remove(fragment);
                ft.commit();
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "on pause called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "on stop called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "on destroy called");
    }
}
