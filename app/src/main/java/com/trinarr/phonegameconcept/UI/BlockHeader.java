package com.trinarr.phonegameconcept.UI;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.trinarr.phonegameconcept.R;

public class BlockHeader extends FrameLayout {
    Toolbar toolbar;
    private AppCompatActivity activity;
    private Class backOpenClass = null;

    public BlockHeader(AppCompatActivity activity) {
        super(activity);

        this.activity = activity;

        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.toolbar,this);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");

        activity.setSupportActionBar(toolbar);
    }

    public void setHeaderText(String header) {
        TextView toolbarHeader = toolbar.findViewById(R.id.toolbarHeader);
        toolbarHeader.setText(header);
    }

    public void hideAvatar() {
        findViewById(R.id.chatLogo).setVisibility(GONE);
    }

    public void setCloseClass(Class backOpenClass) {
        this.backOpenClass = backOpenClass;
    }

    public void setBackButton() {
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        Drawable drawable = toolbar.getNavigationIcon();
        if(drawable != null) {
            drawable.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogManager.log("activity "+activity, this.getClass());

                if(backOpenClass != null) {
                    Intent intent = new Intent(activity, backOpenClass);
                    activity.startActivity(intent);
                }

                activity.finish();
            }
        });
    }
}
