package com.android.pena.david.news4u.ui.save;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.android.pena.david.news4u.R;
import com.android.pena.david.news4u.model.SavedArticle;
import com.android.pena.david.news4u.ui.home.adapter.SavedArticlesAdapter;
import com.android.pena.david.news4u.ui.settings.SettingsActivity;
import com.android.pena.david.news4u.utils.NYTController;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;

public class SavedActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.refresh_saved_articles) SwipeRefreshLayout refreshArticles;
    @BindView(R.id.saved_articles_list) RecyclerView articlesList;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.drawer_layout) DrawerLayout drawer;
    @BindView(R.id.nav_view) NavigationView navigationView;

    private SavedArticlesAdapter savedArticlesAdapter;
    private NYTController nytController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        refreshArticles.setOnRefreshListener(this);
        setDrawer();

        nytController = new NYTController(this,getApplication());
        setSavedArticles();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.saved_articles, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_articles) {
            finish();
        }
        if(id == R.id.nav_settings){
            startActivity(new Intent(SavedActivity.this, SettingsActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onRefresh() {
        refreshArticles.setRefreshing(false);
    }

    private void setDrawer(){
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }


    private void setSavedArticles(){

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setAutoMeasureEnabled(false);
        articlesList.setLayoutManager(linearLayoutManager);
        RealmResults<SavedArticle> savedArticles = nytController.getSavedArticles();
        savedArticlesAdapter = new SavedArticlesAdapter(this,savedArticles);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(articlesList.getContext(),
                linearLayoutManager.getOrientation());
        articlesList.addItemDecoration(dividerItemDecoration);
        articlesList.setAdapter(savedArticlesAdapter);
    }


}
