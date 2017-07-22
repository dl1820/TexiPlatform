package com.example.lgd.texiplatform;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by LGD on 2017-07-12.
 */

public class ListViewAdapter extends BaseAdapter{
    Context con;
    LayoutInflater inflater;
    ArrayList<ListViewItem> lvi;
    int layout;

    public ListViewAdapter(Context context, int alayout, ArrayList<ListViewItem> lvii){
        con = context;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        lvi = lvii;
        layout = alayout;
    }

    @Override
    public int getCount() {
        return lvi.size();
    }

    @Override
    public Object getItem(int position) {
        return lvi.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final int pos = position;
        if (convertView == null){
            convertView = inflater.inflate(layout, parent, false);
        }

        // activity_list 레이아웃을 inflate하여 convertView 참조 획득
        TextView loction = (TextView)convertView.findViewById(R.id.location);
        TextView waitcar = (TextView)convertView.findViewById(R.id.waitcar);
        TextView waittime = (TextView)convertView.findViewById(R.id.waittime);
        TextView arrivallocation = (TextView)convertView.findViewById(R.id.arrivallocation);
        TextView arrivaltime = (TextView)convertView.findViewById(R.id.arrivaltime);


        // 데이터 반영
        loction.setText(lvi.get(position).location);
        waitcar.setText(lvi.get(position).waitcar);
        waittime.setText(lvi.get(position).waittime);
        arrivallocation.setText(lvi.get(position).arrivallocation);
        arrivaltime.setText(lvi.get(position).arrivaltime);

        if (layout == R.layout.activity_listitem){
            Button button = (Button)convertView.findViewById(R.id.button3);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.v("asd", "" + position);
                    //send send = new send(position);
                }
            });
        }

        return convertView;
    }

    class send extends AppCompatActivity{
        public send(int position){
            Intent intent = new Intent(getApplicationContext(), Turn.class);
            intent.putExtra("num", position);
            startActivity(intent);
        }
    }
}

class ListViewItem{
    String location;
    String waitcar;
    String waittime;
    String arrivallocation;
    String arrivaltime;
    ListViewItem(String l, String wc, String wt, String al, String at){
        location = l;
        waitcar = wc;
        waittime = wt;
        arrivallocation = al;
        arrivaltime = at;
    }

}