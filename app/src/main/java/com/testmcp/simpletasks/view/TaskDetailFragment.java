package com.testmcp.simpletasks.view;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.testmcp.simpletasks.R;
import com.testmcp.simpletasks.model.Event;
import com.testmcp.simpletasks.model.Task;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link TaskDetailFragment#} factory method to
 * create an instance of this fragment.
 */
public class TaskDetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private Task task;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    /*// TODO: Rename and change types and number of parameters
    public static TaskDetailFragment newInstance(String param1, String param2) {
        TaskDetailFragment fragment = new TaskDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }*/

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Intent intent = getActivity().getIntent();
        task = (Task) intent.getSerializableExtra("Task");
        View rootView = inflater.inflate(R.layout.fragment_task_detail, container, false);
        if (intent != null && intent.hasExtra("Task")) {
            //String forecastStr = intent.getStringExtra(Intent.EXTRA_TEXT);
            ((TextView) rootView.findViewById(R.id.task_detail_desc))
                    .setText(task.getDescripcion());
            ArrayList<Event> events = task.getEvents();
            //ListView listView = (ListView) getView().findViewById(R.id.event_list);
        }
        return rootView;
        //return inflater.inflate(R.layout.fragment_task_detail, container, false);
    }

    /*

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }*/


}
