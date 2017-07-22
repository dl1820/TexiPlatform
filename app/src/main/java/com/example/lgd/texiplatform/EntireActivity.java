package com.example.lgd.texiplatform;

/**
 * Created by LGD on 2017-07-13.
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PointF;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.skp.Tmap.TMapCircle;
import com.skp.Tmap.TMapGpsManager;
import com.skp.Tmap.TMapGpsManager.onLocationChangedCallback;
import com.skp.Tmap.TMapLabelInfo;
import com.skp.Tmap.TMapMarkerItem;
import com.skp.Tmap.TMapMarkerItem2;
import com.skp.Tmap.TMapPOIItem;
import com.skp.Tmap.TMapPoint;
import com.skp.Tmap.TMapPolyLine;
import com.skp.Tmap.TMapView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class EntireActivity extends BaseActivity implements onLocationChangedCallback{
    static boolean theme_guide_flage = true; // true  = select , false = current
    final String Tmap_Api_Key = "641c18d6-4c56-36a9-bc92-9a0cf17db051";
    private TMapView mMapView = null;
    private Context mContext;
    private FrameLayout contentView = null;
    TMapPoint tpoint, startPoint, endPoint;
    TMapMarkerItem startLocation = new TMapMarkerItem();
    TMapMarkerItem endLocation = new TMapMarkerItem();
    TMapPolyLine tMapPolyLine = new TMapPolyLine();

    boolean startbool = false, endbool = false;

    TMapGpsManager gps = null;

    JSONArray jsonArray;
    JSONObject jsonObject;
    String strr;
    LocalService service;

    private void initView(){
        mMapView.setOnApiKeyListener(new TMapView.OnApiKeyListenerCallback() {
            @Override
            public void SKPMapApikeySucceed() {
//                LogManager.printLog("MainActivity SKPMapApikeySucceed");
            }

            @Override
            public void SKPMapApikeyFailed(String errorMsg) {
//                LogManager.printLog("MainActivity SKPMapApikeyFailed " + errorMsg);
            }
        });

//		mMapView.setOnBizAppIdListener(new TMapView.OnBizAppIdListenerCallback() {
//			@Override
//			public void SKPMapBizAppIdSucceed() {
//				LogManager.printLog("MainActivity SKPMapBizAppIdSucceed");
//			}
//
//			@Override
//			public void SKPMapBizAppIdFailed(String errorMsg) {
//				LogManager.printLog("MainActivity SKPMapBizAppIdFailed " + errorMsg);
//			}
//		});


        mMapView.setOnEnableScrollWithZoomLevelListener(new TMapView.OnEnableScrollWithZoomLevelCallback() {
            @Override
            public void onEnableScrollWithZoomLevelEvent(float zoom, TMapPoint centerPoint) {
//                LogManager.printLog("MainActivity onEnableScrollWithZoomLevelEvent " + zoom + " " + centerPoint.getLatitude() + " " + centerPoint.getLongitude());
            }
        });

        mMapView.setOnDisableScrollWithZoomLevelListener(new TMapView.OnDisableScrollWithZoomLevelCallback() {
            @Override
            public void onDisableScrollWithZoomLevelEvent(float zoom, TMapPoint centerPoint) {
//                LogManager.printLog("MainActivity onDisableScrollWithZoomLevelEvent " + zoom + " " + centerPoint.getLatitude() + " " + centerPoint.getLongitude());
            }
        });

        mMapView.setOnClickListenerCallBack(new TMapView.OnClickListenerCallback() {
            @Override
            public boolean onPressUpEvent(ArrayList<TMapMarkerItem> markerlist, ArrayList<TMapPOIItem> poilist, TMapPoint point, PointF pointf) {
//                LogManager.printLog("MainActivity onPressUpEvent " + markerlist.size());
                return false;
            }

            @Override
            public boolean onPressEvent(ArrayList<TMapMarkerItem> markerlist,ArrayList<TMapPOIItem> poilist, TMapPoint point, PointF pointf) {
                if(startLocation.latitude !=0.0 && startLocation.longitude != 0.0){
                    endLocation.latitude = point.getLatitude();
                    endLocation.longitude = point.getLongitude();

                    endPoint = new TMapPoint(endLocation.latitude, endLocation.longitude);
                    MarkerOverlay endMarker = new MarkerOverlay(EntireActivity.this, mMapView);
                    String endID = String.format("end");
                    endMarker.setID(endID);
                    endMarker.setIcon(BitmapFactory.decodeResource(getResources(), R.drawable.end));
                    endMarker.setTMapPoint(endPoint);
                    mMapView.addMarkerItem2(endID, endMarker);
                    endbool = true;
                    System.out.print("NewstartLatitude = " + endLocation.latitude + "// NewstartLongitude = " + endLocation.longitude);
//                    TMapData tmapdata = new TMapData();
//                    if(startbool && endbool){
//                        try {new TMapPolyLine();
//                            tMapPolyLine1 = tmapdata.findPathDataWithType(TMapData.TMapPathType.CAR_PATH, startPoint, endPoint);	// 자동차 경로를 요청 <---- 여기서 오류남!
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        } catch (ParserConfigurationException e) {
//                            e.printStackTrace();
//                        } catch (SAXException e) {
//                            e.printStackTrace();
//                        }
//                        mMapView.addTMapPath(tMapPolyLine1);
//                    }
                }
                else{
                    startLocation.latitude = point.getLatitude();
                    startLocation.longitude = point.getLongitude();

//                    startPoint = new TMapPoint(startLocation.latitude, startLocation.longitude);
//                    MarkerOverlay startMarker = new MarkerOverlay(EntireActivity.this, mMapView);
//                    String startID = String.format("start");
//                    startMarker.setID(startID);
//                    startMarker.setIcon(BitmapFactory.decodeResource(getResources(), R.drawable.start));
//                    startMarker.setTMapPoint(startPoint);
//                    mMapView.addMarkerItem2(startID, startMarker);
                    System.out.print("NewstartLatitude = " + startLocation.latitude + "// NewstartLongitude = " + startLocation.longitude);
                    startbool = true;
                }


                return false;
            }
        });
        mMapView.setOnLongClickListenerCallback(new TMapView.OnLongClickListenerCallback() {
            @Override
            public void onLongPressEvent(ArrayList<TMapMarkerItem> markerlist, ArrayList<TMapPOIItem> poilist, TMapPoint point) {
//                service.getData(2);
//                Toast.makeText(getApplicationContext(), "위도 : " + point.getLatitude() + " 경도 : " + point.getLongitude(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), Popup.class);
                intent.putExtra("latitude", point.getLatitude());
                intent.putExtra("longitude", point.getLongitude());
                startActivity(intent);
//                if(startLocation.latitude !=0.0 && startLocation.longitude != 0.0){
//                    endLocation.latitude = point.getLatitude();
//                    endLocation.longitude = point.getLongitude();
//                    TMapPoint endPoint = new TMapPoint(endLocation.latitude, endLocation.longitude);
//                    MarkerOverlay endMarker = new MarkerOverlay(ThemeGuideActivity.this, mMapView);
//                    String endID = String.format("end");
//                    endMarker.setID(endID);
//                    endMarker.setIcon(BitmapFactory.decodeResource(getResources(), R.drawable.map_pin_red));
//                    endMarker.setTMapPoint(endPoint);
//                    mMapView.addMarkerItem2(endID, endMarker);
//
//                    LogManager.printLog("NewendLatitude = " + endLocation.latitude + "// NewendLongitude = " + endLocation.longitude);
//                }
//                else{
//                    startLocation.latitude = point.getLatitude();
//                    startLocation.longitude = point.getLongitude();
//                    TMapPoint startPoint = new TMapPoint(startLocation.latitude, startLocation.longitude);
//                    MarkerOverlay startMarker = new MarkerOverlay(ThemeGuideActivity.this, mMapView);
//                    String startID = String.format("start");
//                    startMarker.setID(startID);
//                    startMarker.setIcon(BitmapFactory.decodeResource(getResources(), R.drawable.map_pin_red));
//                    startMarker.setTMapPoint(startPoint);
//                    mMapView.addMarkerItem2(startID, startMarker);
//                    LogManager.printLog("NewstartLatitude = " + startLocation.latitude + "// NewstartLongitude = " + startLocation.longitude);
//                }
            }
        });

        mMapView.setOnClickReverseLabelListener(new TMapView.OnClickReverseLabelListenerCallback() {

            @Override
            public void onClickReverseLabelEvent(TMapLabelInfo findReverseLabel) {
//                if(findReverseLabel != null) {
//                   if(startLocation.latitude !=0.0 && startLocation.longitude != 0.0){
//                    endLocation.latitude = Double.parseDouble(findReverseLabel.labelLat);
//                    endLocation.longitude = Double.parseDouble(findReverseLabel.labelLon);
//                    TMapPoint endPoint = new TMapPoint(endLocation.latitude, endLocation.longitude);
//                    MarkerOverlay endMarker = new MarkerOverlay(ThemeGuideActivity.this, mMapView);
//                    String endID = String.format("end");
//                    endMarker.setID(endID);
//                    endMarker.setIcon(BitmapFactory.decodeResource(getResources(), R.drawable.map_pin_red));
//                    endMarker.setTMapPoint(endPoint);
//                    mMapView.addMarkerItem2(endID, endMarker);
//
//                    LogManager.printLog("LableEndLatitude = " + endLocation.latitude + "// LableEndLongitude = " + endLocation.longitude);
//                }
//                else{
//                    startLocation.latitude = Double.parseDouble(findReverseLabel.labelLat);
//                    startLocation.longitude = Double.parseDouble(findReverseLabel.labelLon);
//                    TMapPoint startPoint = new TMapPoint(startLocation.latitude, startLocation.longitude);
//                    MarkerOverlay startMarker = new MarkerOverlay(ThemeGuideActivity.this, mMapView);
//                    String startID = String.format("start");
//                    startMarker.setID(startID);
//                    startMarker.setIcon(BitmapFactory.decodeResource(getResources(), R.drawable.map_pin_red));
//                    startMarker.setTMapPoint(startPoint);
//                    mMapView.addMarkerItem2(startID, startMarker);
//                    LogManager.printLog("LableStartLatitude = " + startLocation.latitude + "// LableStartLongitude = " + startLocation.longitude);
//                }


            }


        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entire);
        Intent intent = getIntent();
        try {
            mContext = this;                                // 나중에 지워야 될 부분
            mMapView = new TMapView(this);                  // 역시나 지워야 될 부분
            configureMapView();

            addView(mMapView);
            //onLocationChange();
            initView();
            circleMap();

            mMapView.setCenterPoint(SplashActivity.getLong(), SplashActivity.getLat());

            if (SplashActivity.getLong() == 0)
                Toast.makeText(getApplicationContext(), "위치를 키고 다시 실행하세요", Toast.LENGTH_LONG).show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        strr = intent.getStringExtra("str");
        //makeMarker(36.981737807370244, 127.91557788848877, "롯마승강장");
        maker maker = new maker();
        maker.execute();

    }

    private void circleMap(){
        TMapCircle tcircle = new TMapCircle();
        TMapPoint tPoint = new TMapPoint(SplashActivity.getLat(), SplashActivity.getLong());
        tcircle.setRadius(1000);
        tcircle.setLineColor(Color.GRAY);
        tcircle.setAreaAlpha(50);
        tcircle.setCircleWidth((float)10);
        tcircle.setRadiusVisible(true);
        tcircle.setCenterPoint(tPoint);
        mMapView.addTMapCircle("circle", tcircle);
    }

    private void makeMarker(double latitude, double longtitude, String Name){
        TMapPoint tMapPoint = new TMapPoint(latitude, longtitude);
        TMapMarkerItem tItem = new TMapMarkerItem();

        tItem.setTMapPoint(tMapPoint);
        tItem.setName(Name);
        tItem.setVisible(TMapMarkerItem.VISIBLE);

        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.map_pin_red);
        mMapView.setIcon(bitmap);

        mMapView.addMarkerItem(Name, tItem);
    }

    private void configureMapView(){
        mMapView.setSKPMapApiKey(Tmap_Api_Key);
    }

    public void onLocationChange(){
        //mMapView.setLocationPoint(36.981197864771744, 127.9154920578003);
    }
    @Override
    public void onLocationChange(Location location) {
        mMapView.setLocationPoint(location.getLongitude(), location.getLatitude());
    }

    private class maker extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... params) {
            try {
                String str = "[{'latitude': 37.107734059476627, 'longitude': 127.75408015895491}, {'latitude': 36.984544468518735, 'longitude': 127.92739326739209}, {'latitude': 36.981236497103815, 'longitude': 127.93983196614579}, {'latitude': 36.978802146335227, 'longitude': 127.92849050225058}, {'latitude': 36.981375945654122, 'longitude': 127.91538661271196}, {'latitude': 36.981568945661593, 'longitude': 127.9153860861619}, {'latitude': 36.976127721237802, 'longitude': 127.90959369984}, {'latitude': 36.972722599182525, 'longitude': 127.9235698810309}, {'latitude': 36.969127303358796, 'longitude': 127.92313510674173}, {'latitude': 36.95202530354193, 'longitude': 127.90533062913454}, {'latitude': 36.973748491332898, 'longitude': 127.80124128478937}, {'latitude': 37.086222727411702, 'longitude': 127.9147243017205}, {'latitude': 37.014775145254376, 'longitude': 127.91693673404168}, {'latitude': 36.846295360208686, 'longitude': 127.99168626095613}, {'latitude': 36.990171299452022, 'longitude': 127.92592459152195}, {'latitude': 37.107734059476627, 'longitude': 127.75408015895491}, {'latitude': 36.987286064708833, 'longitude': 127.94304171618835}, {'latitude': 36.986573788376582, 'longitude': 127.92835973222101}]";
                jsonArray = new JSONArray(strr);
                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonObject = jsonArray.getJSONObject(i);
                    makeMarker(Double.parseDouble(jsonObject.getString("latitude")), Double.parseDouble(jsonObject.getString("longitude")), "" + i);
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }


}
