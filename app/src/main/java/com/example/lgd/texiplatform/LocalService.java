package com.example.lgd.texiplatform;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;


public class LocalService extends Service {
    JSONObject jsonObject;
    String str;
    int cs;
    private IBinder iBinder;

    public LocalService(){
        iBinder = new LocalBinder();
    }

    public class LocalBinder extends Binder{
        public LocalService getService(){
            return LocalService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }

    public void getData(int i) {
        cs = i;
        switch (i) {
            case 1:
                ContentValues contentValues1 = new ContentValues();
                contentValues1.put("id", "admin@naver.cm");
                contentValues1.put("pwd", "keroro2424");
                httpC hC1 = new httpC(contentValues1, "https://firststep-2016.appspot.com/");
                hC1.execute();
                break;
        }
    }

    public void getData(double latitude, double longitude){
        ContentValues contentValues2 = new ContentValues();
        contentValues2.put("latitude", latitude);
        contentValues2.put("longitude", longitude);
        httpC hC2 = new httpC(contentValues2, "https://firststep-2016.appspot.com/favoStation");
        hC2.execute();
    }

    public class httpC extends AsyncTask<String, String, String> {
        ContentValues values;
        String url;
        public httpC(ContentValues values, String urll){
            this.values = values;
            this.url = urll;
        }

        @Override
        protected String doInBackground(String... strings) {
            Https requestHttpURLConnection = new Https();
            str = requestHttpURLConnection.request(url, values);
            return str;
        }

        @Override
        protected void onPostExecute(String message) {
            switch (cs){
                case 1:
                    Intent intent = new Intent(getApplicationContext(), EntireActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("str", str);
                    startActivity(intent);
                    break;
                case 2:
                    Toast.makeText(getApplicationContext(), "선택완료", Toast.LENGTH_LONG).show();
            }
        }
    }
}
