package com.testmcp.simpletasks.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.EditText;
import android.widget.LinearLayout;

import com.testmcp.simpletasks.R;
import com.testmcp.simpletasks.interactor.OnTaskAddedOutputInteractor;
import com.testmcp.simpletasks.interactor.network.TasksAPI;
import com.testmcp.simpletasks.view.settings.AuthPref;
import com.testmcp.simpletasks.view.settings.SettingsActivity;

public class TasksList extends AppCompatActivity {

    private SwipeRefreshLayout swipeContainer;
    private TasksListFragment tasksListFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //NetworkGetter.setCookieManager(getApplicationContext());

        AuthPref.setSharedPreferences(getApplicationContext());
        setContentView(R.layout.activity_tasks_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tasksListFragment = (TasksListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_tasks_list);
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

                tasksListFragment.updateList();
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Actualizando lista", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
                TasksListFragment tasksListFragment = (TasksListFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.fragment_tasks_list);
                addTaskDialogShow();
                //tasksListFragment.updateList();
                //tasksListFragment.login();


            }
        });
    }

    private void addTaskDialogShow() {
        AlertDialog.Builder builderDialog = new AlertDialog.Builder(this);
        builderDialog.setTitle(getString(R.string.add_task_title));
        builderDialog.setMessage(getString(R.string.add_task_description));
        final EditText input = new EditText(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        builderDialog.setView(input);
        builderDialog.setPositiveButton("Aceptar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String descripcion = input.getText().toString();
                        if (descripcion.compareTo("") != 0) {
                            TasksAPI.addTask(descripcion, new OnTaskAddedOutputInteractor(tasksListFragment));
                        }
                    }
                });

        builderDialog.setNegativeButton("Cancelar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        builderDialog.show();
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
