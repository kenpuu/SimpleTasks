package com.testmcp.simpletasks.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.preference.PreferenceManager;
import com.testmcp.simpletasks.R;
import com.testmcp.simpletasks.interactor.network.NetworkGetter;
import com.testmcp.simpletasks.interactor.network.TokenAuthPref;

public class TasksList extends AppCompatActivity {

    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //NetworkGetter.setCookieManager(getApplicationContext());
        TokenAuthPref.setSharedPreferences(getApplicationContext());
        setContentView(R.layout.activity_tasks_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipe_listview_taks);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                Snackbar.make(swipeContainer, "Actualizando lista", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                TasksListFragment tasksListFragment = (TasksListFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.fragment_tasks_list);
                tasksListFragment.updateList();
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Actualizando lista", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                TasksListFragment tasksListFragment = (TasksListFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.fragment_tasks_list);
                //tasksListFragment.updateList();
                tasksListFragment.login();



            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tasks_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
