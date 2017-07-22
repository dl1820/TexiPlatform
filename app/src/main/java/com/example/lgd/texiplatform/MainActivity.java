package com.example.lgd.texiplatform;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    Button btnEntire, btnSystemSetting, btnFavorite, btnRecommendation, btnSpeed, btnEtc;
    LocalService service;
    boolean mBound = false;
    JSONObject jsonObject;
    String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startActivity(new Intent(MainActivity.this, SplashActivity.class));

        btnEntire = (Button)findViewById(R.id.Entire);
        btnSystemSetting = (Button)findViewById(R.id.SystemSetting);
        btnFavorite = (Button)findViewById(R.id.Favorite);
        btnRecommendation = (Button)findViewById(R.id.Recommendation);
        btnSpeed = (Button)findViewById(R.id.Speed);
        btnEtc = (Button)findViewById(R.id.etc);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.Entire:
                        Intent intent = new Intent(getApplicationContext(), LocalService.class);
                        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
                        if(mBound) {
                            service.getData(1);
                        }
                        break;
                    case R.id.SystemSetting:
                        break;
                    case R.id.Favorite:
                        break;
                    case R.id.Recommendation:
                        break;
                    case R.id.Speed:
                        break;
                    case R.id.etc:
                        break;
                }
            }
        };

        btnEntire.setOnClickListener(listener);
        btnSystemSetting.setOnClickListener(listener);
        btnFavorite.setOnClickListener(listener);
        btnRecommendation.setOnClickListener(listener);
        btnSpeed.setOnClickListener(listener);
        btnEtc.setOnClickListener(listener);
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
