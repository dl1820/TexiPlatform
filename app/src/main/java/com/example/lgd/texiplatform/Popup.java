package com.example.lgd.texiplatform;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by LGD on 2017-07-21.
 */

public class Popup extends Activity {
    LocalService service;
    boolean mBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_popup);

        Button choice = (Button)findViewById(R.id.popbtn1);
        Button back = (Button)findViewById(R.id.popbtn2);

        final Intent intent = getIntent();

        choice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("asdffg", "위도 : " + intent.getDoubleExtra("latitude", -1) + " 경도 : " + intent.getDoubleExtra("longitude", -1));
                service.getData(intent.getDoubleExtra("latitude", -1) , intent.getDoubleExtra("longitude", -1));
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    @Override
    protected void onStart(){
        super.onStart();
        Intent intent = new Intent(this, LocalService.class);
        bindService(intent, mConnection , Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop(){
        super.onStop();
        if(mBound){
            unbindService(mConnection);
            mBound = false;
        }
    }

    private ServiceConnection mConnection = new ServiceConnection() {

        public void onServiceConnected(ComponentName name, IBinder mService) {
            service = (LocalService)((LocalService.LocalBinder)mService).getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBound = false;
        }
    };
}
