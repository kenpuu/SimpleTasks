package com.testmcp.simpletasks.view.adapter;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CheckedTextView;

import com.testmcp.simpletasks.model.Task;
import com.testmcp.simpletasks.model.User;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mario on 25/01/2016.
 */
public class UsersAdapter extends BaseAdapter implements AdapterView.OnItemClickListener {
    User[] users;
    ArrayList<User> selectedUserIDs;

    private Task task;

    public User[] getUsers() {
        return users;
    }

    public UsersAdapter(Task task) {
        this.task = task;
        selectedUserIDs = new ArrayList<>();
    }

    public int[] getSelectedUserIDs() {
        int[] userIDs = new int[selectedUserIDs.size()];
        for (int i=0;i<selectedUserIDs.size();i++) userIDs[i] = selectedUserIDs.get(i).getId();
        return userIDs;
    }

    @Override
    public int getCount() {
        if (users == null)
            return 0;
        else
            return users.length;
    }

    public User getItemUser(int position) {
        if (users == null)
            return null;
        else
            return users[position];
    }

    @Override
    public String getItem(int position) {
        if (users == null)
            return null;
        else
            return users[position].getUsername();
    }

    @Override
    public long getItemId(int position) {
        if (users == null)
            return -1;
        else
            return users[position].getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        UserCheckableLayout userCheckableLayout;
        if (convertView == null) {
            userCheckableLayout = new UserCheckableLayout(parent.getContext());
            if (task.isAsigned((int) getItemId(position))) {
                addSelectedUser(getItemUser(position));
            } else {
                removeSelectedUser(getItemUser(position));
            }
        } else {
            userCheckableLayout = (UserCheckableLayout) convertView;
        }
        userCheckableLayout.setUsername(getItem(position));
        userCheckableLayout.setChecked(task.isAsigned((int) getItemId(position)));
            /*checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((CheckedTextView)v).toggle();
                }
            });*/
        return userCheckableLayout;
    }

    private void addSelectedUser(User user) {
        if (!selectedUserIDs.contains(user)) {
            selectedUserIDs.add(user);
        }
    }

    private void removeSelectedUser(User user) {
        if (selectedUserIDs.contains(user)) {
            selectedUserIDs.remove(user);
        }
    }

    public void setUsernames(User[] users) {
        this.users = users;
        notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        UserCheckableLayout userCheckableLayout = (UserCheckableLayout) view;
        if (!userCheckableLayout.isChecked()) {
            addSelectedUser(getItemUser(position));
        }
        else {
            removeSelectedUser(getItemUser(position));
        }
        userCheckableLayout.toggle();
    }
}


