package com.testmcp.simpletasks.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.testmcp.simpletasks.R;
import com.testmcp.simpletasks.interactor.OnImageAddedOutputInteractor;
import com.testmcp.simpletasks.interactor.OnLoadTaskOutputInteractor;
import com.testmcp.simpletasks.interactor.OnCommentAddedOutputInteractor;
import com.testmcp.simpletasks.interactor.network.TasksAPI;
import com.testmcp.simpletasks.model.TaskEvent;
import com.testmcp.simpletasks.model.Task;
import com.testmcp.simpletasks.view.adapter.EventListAdapter;
import com.testmcp.simpletasks.view.settings.AuthPref;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link TaskDetailFragment#} factory method to
 * create an instance of this fragment.
 */
public class TaskDetailFragment extends Fragment {
    private Task task;
    private MenuItem mMenuItem;

    private static final int REQUEST_CODE = 1;
    public int TAKE_PICTURE = 1;

    Bitmap bitmap;

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public TaskDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setHasOptionsMenu(true);

    }

    public void updateTaskDetail(){
        TasksAPI.getTask(task.getId(), new OnLoadTaskOutputInteractor(this));
    }

    public void checkCreator(){
        if (AuthPref.getUsername().equals(task.getCreator())) {
            if (mMenuItem != null) mMenuItem.setVisible(true);
            //MenuItem item = (MenuItem) getActivity().findViewById(R.id.create_new);
            /*MenuItem item = mMenu.getItem(R.id.create_new);
            item.setVisible(true);
            getActivity().invalidateOptionsMenu();*/
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("TASK_DETAIL_FRAGMENT", "Entra en onCreateView");
        // Inflate the layout for this fragment
        Intent intent = getActivity().getIntent();
        final View rootView = inflater.inflate(R.layout.fragment_task_detail, container, false);
        if (intent != null && intent.hasExtra("Task")) {
            //String forecastStr = intent.getStringExtra(Intent.EXTRA_TEXT);
            task = (Task) intent.getSerializableExtra("Task");
            updateTaskDetail();
        }

        return rootView;
        //return inflater.inflate(R.layout.fragment_task_detail, container, false);
    }

    private void onAddTaskClickOK(View v){
        EditText editText = (EditText) getActivity().findViewById(R.id.comment_edit_text);
        String descripcion = editText.getText().toString();
        if (descripcion.length() > 0 )
            TasksAPI.addComment(getTask().getId(), descripcion, new OnCommentAddedOutputInteractor(this));
    }

    public void reset() {
        ((TextView) this.getActivity().findViewById(R.id.task_detail_desc))
                .setText(task.getDescripcion());
        ArrayList<TaskEvent> taskEvents = task.getTaskEvents();
        ListView listView = (ListView) getActivity().findViewById(R.id.event_list);
        EventListAdapter eventListAdapter = new EventListAdapter(taskEvents);
        listView.setAdapter(eventListAdapter);
        Button button = (Button) getActivity().findViewById(R.id.add_comment_button);
        EditText editText = (EditText) getActivity().findViewById(R.id.comment_edit_text);
        ImageButton imageButton = (ImageButton) getActivity().findViewById(R.id.image_task_button);
        imageButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivityForResult(intent, TAKE_PICTURE);

            }
        });
        editText.setText("");
        checkCreator();
        final TaskDetailFragment taskDetailFragment = this;

        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onAddTaskClickOK(v);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        mMenuItem = menu.findItem(R.id.share_task);
        //menu.clear();
        //inflater.inflate(R.menu.task_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.share_task) {
            new ShareTaskDialog(this).show();
            //http://www.androidpeople.com/android-listview-multiple-choice-example
        /*lView = (ListView) findViewById(R.id.ListView01);
//	Set option as Multiple Choice. So that user can able to select more the one option from list
        lView.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_multiple_choice, lv_items));
        lView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);*/
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // We need to recyle unused bitmaps
            if (bitmap != null) {
                bitmap.recycle();
            }
            //InputStream stream = getActivity().getContentResolver().openInputStream(data.getData());
            bitmap = (Bitmap) data.getExtras().get("data");

            TasksAPI.addImage(task.getId(), bitmap, new OnImageAddedOutputInteractor(this));
            //bitmap = BitmapFactory.decodeStream(stream);
            //stream.close();
            //imageView.setImageBitmap(bitmap);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
