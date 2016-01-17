package com.testmcp.simpletasks.view;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.testmcp.simpletasks.R;
import com.testmcp.simpletasks.interactor.LoadTaskOutputInteractor;
import com.testmcp.simpletasks.interactor.OnCommentAddedOutputInteractor;
import com.testmcp.simpletasks.interactor.network.TasksAPI;
import com.testmcp.simpletasks.model.TaskEvent;
import com.testmcp.simpletasks.model.Task;
import com.testmcp.simpletasks.view.adapter.EventListAdapter;

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
        if (getArguments() != null) {
        //    mParam1 = getArguments().getString(ARG_PARAM1);
        //    mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public void updateTaskDetail(){
        TasksAPI.getTask(task.getId(), new LoadTaskOutputInteractor(this));
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

    private void onClickOK(View v){
        EditText editText = (EditText) getActivity().findViewById(R.id.comment_edit_text);
        String contenido = editText.getText().toString();
        if (contenido.length() > 0 )
            TasksAPI.addComment(getTask().getId(), contenido, new OnCommentAddedOutputInteractor(this));
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
        editText.setText("");
        final TaskDetailFragment taskDetailFragment = this;

        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onClickOK(v);
            }
        });
    }

    /*

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }*/


}
