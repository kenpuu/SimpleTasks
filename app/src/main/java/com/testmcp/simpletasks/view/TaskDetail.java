package com.testmcp.simpletasks.view;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.testmcp.simpletasks.R;

public class TaskDetail extends ActionBarActivity {

    private MenuItem mMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.task_menu, menu);
        mMenuItem = menu.findItem(R.id.share_task);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }
}
