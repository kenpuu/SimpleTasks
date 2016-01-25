package com.testmcp.simpletasks.view.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.Checkable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.testmcp.simpletasks.R;

/**
 * Created by mario on 25/01/2016.
 */
public class UserCheckableLayout extends LinearLayout{
    TextView usernameTextView;
    LinearLayout linearLayout;
    Boolean checked;
    public UserCheckableLayout(Context context) {
        super(context);
        checked = false;
        inflate(context, R.layout.user_checkable_layout, this);
        usernameTextView = (TextView) findViewById(R.id.checkable_username);
        linearLayout = (LinearLayout) findViewById(R.id.checkable_username_linearlayout);
    }

    public void setChecked(boolean checked) {
        if (checked) {
            //linearLayout.setBackgroundColor(0xFF00FF00);
            usernameTextView.setTypeface(null, Typeface.BOLD);
        } else {
            //linearLayout.setBackgroundColor(0x00FFFFFF);
            usernameTextView.setTypeface(null, Typeface.NORMAL);
        }
        this.checked = checked;
    }

    public boolean isChecked() {
        return this.checked;
    }

    public void toggle() {
        setChecked(!isChecked());
    }

    public void setUsername(String username) {
        usernameTextView.setText(username);
    }
}
