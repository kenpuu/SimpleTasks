package com.testmcp.simpletasks.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.testmcp.simpletasks.R;
import com.testmcp.simpletasks.interactor.OnAsignedUsersOutputInteractor;
import com.testmcp.simpletasks.interactor.OnLoadUsersOutputInteractor;
import com.testmcp.simpletasks.interactor.network.TasksAPI;
import com.testmcp.simpletasks.model.User;
import com.testmcp.simpletasks.view.adapter.UsersAdapter;

/**
 * Created by mario on 17/01/2016.
 */
public class ShareTaskDialog{
    TaskDetailFragment mFragment;
    AlertDialog.Builder mBuilder;
    EditText usernameSearchText;
    ImageButton imageButton;

    ListView listView;
    UsersAdapter usersAdapter;

    private String getUserText() {
        return usernameSearchText.getText().toString();
    }

    public void setUsers(User[] users) {
        usersAdapter.setUsernames(users);
    }

    private void getUserList() {
        TasksAPI.getUserlist(mFragment.getTask(), usernameSearchText.getText().toString(), new OnLoadUsersOutputInteractor(this));
    }

    public ShareTaskDialog(TaskDetailFragment tasksListFragment) {
        mFragment = tasksListFragment;

        mBuilder = new AlertDialog.Builder(mFragment.getActivity());
        LayoutInflater inflater = mFragment.getActivity().getLayoutInflater();
        LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.asign_task_layout, null);
        mBuilder.setView(linearLayout)
            .setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    TasksAPI.asingUsers(mFragment.getTask(), usersAdapter.getSelectedUserIDs(), new OnAsignedUsersOutputInteractor(mFragment));
                }
            })
            .setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    //LoginDialogFragment.this.getDialog().cancel();
                }
            });
        mBuilder.setTitle(R.string.asign_user_title).setMessage(R.string.asign_user_description);
        listView = (ListView) linearLayout.findViewById(R.id.list_view_users);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        usersAdapter = new UsersAdapter(mFragment.getTask());
        listView.setAdapter(usersAdapter);
        listView.setItemsCanFocus(false);
        listView.setOnItemClickListener(usersAdapter);
        usernameSearchText = (EditText) linearLayout.findViewById(R.id.search_user_edit_text);
        imageButton = (ImageButton) linearLayout.findViewById(R.id.imageButton_search_user);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUserList();
            }
        });
        disableSearch();
        TextWatcher textWatcher = new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if( getUserText().length() >= 2) {
                    enableSearh();
                } else {
                    disableSearch();
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        };
        usernameSearchText.addTextChangedListener(textWatcher);
    }

    private void disableSearch() {
        imageButton.setEnabled(false);
    }

    private void enableSearh() {
        imageButton.setEnabled(true);
    }

    public void show(){
        AlertDialog a = mBuilder.create();
        mBuilder.show();
        getUserList();
    }

}
