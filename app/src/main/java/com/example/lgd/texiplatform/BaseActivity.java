package com.example.lgd.texiplatform;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

/**
 * Created by LGD on 2017-07-13.
 */

public class BaseActivity extends Activity implements View.OnClickListener {

    private FrameLayout contentView = null;
    private static Context mCtx = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_entire);
        mCtx = this;
        contentView  = (FrameLayout) findViewById(R.id.contentView);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void setContentView(int res)  {

        contentView.removeAllViews();

        LayoutInflater inflater;
        inflater = LayoutInflater.from(this);

        View item = inflater.inflate(res, null);
        contentView.addView(item, new FrameLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

    }

    @Override
    public void setContentView(View view) {

        contentView.removeAllViews();

        contentView.addView(view, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

    }


    public void addView(View v)
    {
        contentView.removeAllViews();
        contentView.addView(v, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    @Override
    public void onClick(View v) {

    }
}
