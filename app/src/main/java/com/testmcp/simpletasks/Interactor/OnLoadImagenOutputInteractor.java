package com.testmcp.simpletasks.interactor;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.testmcp.simpletasks.interactor.network.TasksAPI;
import com.testmcp.simpletasks.model.Task;
import com.testmcp.simpletasks.view.TaskDetailFragment;
import com.testmcp.simpletasks.view.adapter.TaskListAdapter;


/**
 * Created by mario on 28/12/2015.
 */
public class OnLoadImagenOutputInteractor implements TasksAPI.OnLoadImagenInterface {
    ImageView imageView;

    public OnLoadImagenOutputInteractor(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    public void postDownloaded(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }

    @Override
    public void notAllowedHere() {

    }
}
