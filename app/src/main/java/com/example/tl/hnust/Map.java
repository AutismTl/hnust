package com.example.tl.hnust;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.radar.RadarNearbyResult;
import com.baidu.mapapi.radar.RadarNearbySearchOption;
import com.baidu.mapapi.radar.RadarSearchError;
import com.baidu.mapapi.radar.RadarSearchListener;
import com.baidu.mapapi.radar.RadarSearchManager;
import com.baidu.mapapi.radar.RadarUploadInfo;
import com.baidu.mapapi.radar.RadarUploadInfoCallback;


public class Map extends Activity implements RadarSearchListener,RadarUploadInfoCallback,View.OnClickListener{

    public LocationClient mLocationClient;
    private MapView mapView;
    private BaiduMap baiduMap;
    private Marker markerA;
    private boolean isFirstLocate = true;
    private RadarSearchManager mManager;
    private LatLng pt = null;
    private Button zdsc;
    private Button qxzd;
    private Button sc;
    private Button delete;
    private Button gx;
    private Button wx;
    private Button blank;
    private Button jt;
    private int flag=0;
    private int flag1=0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
        mLocationClient.registerLocationListener(new MyLocationListener());    //注册监听函数
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.map);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        mapView = (MapView) findViewById(R.id.bmapView);
        delete=(Button)findViewById(R.id.delete);
        zdsc=(Button)findViewById(R.id.zdsc);
        sc=(Button)findViewById(R.id.sc);
        qxzd=(Button)findViewById(R.id.qxzd);
        blank=(Button)findViewById(R.id.blank);
        gx=(Button)findViewById(R.id.gx);
        wx=(Button)findViewById(R.id.wx);
        jt=(Button)findViewById(R.id.jt);
        baiduMap = mapView.getMap();
        baiduMap.setMyLocationEnabled(true);
        mManager = RadarSearchManager.getInstance();
        //周边雷达设置监听
        mManager.addNearbyInfoListener(this);
        //周边雷达设置用户身份标识，id为空默认是设备标识
        mManager.setUserID(null);
        requestLocation();
        // 点击marker
        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                marker.remove();
                return true;
            }
    });
        delete.setOnClickListener(this);
        zdsc.setOnClickListener(this);
        sc.setOnClickListener(this);
        qxzd.setOnClickListener(this);
        blank.setOnClickListener(this);
        gx.setOnClickListener(this);
        wx.setOnClickListener(this);
        jt.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
            switch (v.getId()){
                case R.id.qxzd: stopUploadClick();
                    break;
                case R.id.sc:   uploadOnceClick();
                    break;
                case R.id.delete:  clearInfoClick();
                    break;
                case R.id.zdsc:   uploadContinueClick();
                    break;
                case R.id.blank:    if (flag==0){
                    baiduMap.setMapType(BaiduMap.MAP_TYPE_NONE);
                    flag=1;flag1=0;
                }else{
                    baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                    flag= 0;
                }
                    break;
                case R.id.gx:   RadarLocationSearch();
                    break;
                case R.id.wx:    if (flag1==0){
                    baiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                   flag1=1;flag=0;
                }else{
                    baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                    flag1=0;
                }
                    break;
                case R.id.jt:   if (baiduMap.isTrafficEnabled()) {
                    baiduMap.setTrafficEnabled(false);
                } else {
                    baiduMap.setTrafficEnabled(true);
                }
                    break;
            }
    }


    /**
     * 上传一次位置
     *
     * @param
     */
    public void uploadOnceClick() {
        if (pt == null) {
            Toast.makeText(Map.this, "未获取到位置", Toast.LENGTH_LONG).show();
            return;
        }else {
            RadarUploadInfo info = new RadarUploadInfo();
            SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(this);
            info.comments = prefs.getString("id","NULL");
            info.pt = pt;
            mManager.uploadInfoRequest(info);
        }
    }

    /**
     * 开始自动上传
     *
     * @param
     */
    public void uploadContinueClick() {
        if (pt == null) {
            Toast.makeText(Map.this, "未获取到位置", Toast.LENGTH_LONG).show();
            return;
        } else {
            mManager.startUploadAuto(this, 10000);
            Toast.makeText(Map.this, "开始自动上传位置", Toast.LENGTH_LONG).show();
        }
    }
    /**
     * 实现上传callback，自动上传
     */
    @Override
    public RadarUploadInfo onUploadInfoCallback() {
        RadarUploadInfo info = new RadarUploadInfo();
        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(this);
        info.comments = prefs.getString("id","NULL");
        info.pt = pt;
        return info;
    }
    /**
     * 停止自动上传
     *
     * @param
     */
    public void stopUploadClick() {
        RadarSearchManager.getInstance().stopUploadAuto();
        Toast.makeText(Map.this, "已停止自动上传", Toast.LENGTH_LONG).show();
    }

    /**
     * 清除自己当前的信息
     *
     * @param
     */
    public void clearInfoClick() {
        RadarSearchManager.getInstance().clearUserInfo();
        Toast.makeText(Map.this, "已清除当前位置信息", Toast.LENGTH_LONG).show();
    }

    /**
     * 雷达周边位置检索
     */
    private void RadarLocationSearch() {
        // 自己的位置
        RadarNearbySearchOption option = new RadarNearbySearchOption()
                .centerPt(pt)
                .pageNum(0)
                .radius(2 * 1000);
        // 发起查询请求
        mManager.nearbyInfoRequest(option);
    }

    @Override
    public void onGetNearbyInfoList(RadarNearbyResult radarNearbyResult, RadarSearchError radarSearchError) {
        if (radarSearchError == RadarSearchError.RADAR_NO_ERROR) {
            Toast.makeText(this, "查询周边成功", Toast.LENGTH_LONG).show();
            Log.i("ttttt", radarNearbyResult.infoList.get(0).userID + "\n" +
                    radarNearbyResult.infoList.get(0).distance + "\n" +
                    radarNearbyResult.infoList.get(0).pt + "\n" +
                    radarNearbyResult.infoList.get(0).timeStamp);
            baiduMap.clear();
            BitmapDescriptor ff3 = BitmapDescriptorFactory.fromResource(R.drawable.icon_gcoding);
            if (radarNearbyResult != null && radarNearbyResult.infoList != null && radarNearbyResult.infoList.size() > 0) {
                for (int i = 0; i < radarNearbyResult.infoList.size(); i++) {
                    MarkerOptions option = new MarkerOptions().icon(ff3).position(radarNearbyResult.infoList.get(i).pt);
                    OverlayOptions textOption = new TextOptions()
                            .bgColor(00000000)
                            .fontSize(25)
                            .fontColor(0xFFFF00FF)
                            .text(radarNearbyResult.infoList.get(i).comments)
                            .position(radarNearbyResult.infoList.get(i).pt);

                    Bundle des = new Bundle();
                    if (radarNearbyResult.infoList.get(i).comments == null || radarNearbyResult.infoList.get(i).comments.equals("")) {
                        des.putString("des", "没有备注");
                    } else {
                        des.putString("des", radarNearbyResult.infoList.get(i).comments);
                    }

                    option.extraInfo(des);
                    //在地图上添加该文字对象并显示
                    baiduMap.addOverlay(textOption);
                    baiduMap.addOverlay(option);

                }
            }
        } else {
            Toast.makeText(this, "查询周边失败", Toast.LENGTH_LONG).show();
            Log.i("ttttttt", "查询错误：" + radarSearchError.toString());
        }
    }

    @Override
    public void onGetUploadState(RadarSearchError radarSearchError) {
        if (radarSearchError == RadarSearchError.RADAR_NO_ERROR) {
            //上传成功
            Toast.makeText(this, "单次上传位置成功", Toast.LENGTH_LONG).show();
        } else {
            //上传失败
            Toast.makeText(this, "单次上传位置失败", Toast.LENGTH_LONG).show();
            Log.i("ttttttttt", "上传错误：" + radarSearchError.toString());
        }
    }

    @Override
    public void onGetClearInfoState(RadarSearchError radarSearchError) {
        if (radarSearchError == RadarSearchError.RADAR_NO_ERROR) {
            //清除成功
            Toast.makeText(this, "清除位置成功", Toast.LENGTH_LONG).show();
        } else {
            //清除失败
            Toast.makeText(this, "清除位置失败", Toast.LENGTH_LONG).show();
        }
    }


    private void requestLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setScanSpan(5000);
        option.setIsNeedAddress(true);
        option.setCoorType("bd09ll"); // 设置坐标类型
        mLocationClient.setLocOption(option);
        mLocationClient.start();
    }

    private void navigateTo(BDLocation location) {
        if (isFirstLocate) {
            pt = new LatLng(location.getLatitude(), location.getLongitude());
            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(pt);
            baiduMap.animateMapStatus(update);
            update = MapStatusUpdateFactory.zoomTo(16f);
            baiduMap.animateMapStatus(update);
        }
        MyLocationData.Builder locationBuilder = new MyLocationData.Builder();
        locationBuilder.latitude(location.getLatitude());
        locationBuilder.longitude(location.getLongitude());
        MyLocationData locationData = locationBuilder.build();
        baiduMap.setMyLocationData(locationData);
        if (isFirstLocate) {
            pt = new LatLng(location.getLatitude(), location.getLongitude());
            float f = baiduMap.getMaxZoomLevel();//19.0 最小比例尺
            MapStatusUpdate update = MapStatusUpdateFactory.newLatLngZoom(pt, f - 5);//设置到100米的大小
            baiduMap.animateMapStatus(update);
            isFirstLocate = false;
            uploadOnceClick();
            RadarLocationSearch();
        }
    }



    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location.getLocType() == BDLocation.TypeGpsLocation || location.getLocType() == BDLocation.TypeNetWorkLocation) {
                navigateTo(location);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
        mapView.onDestroy();
        baiduMap.setMyLocationEnabled(false);
        if (mManager != null) {
            //移除监听
            mManager.removeNearbyInfoListener(this);
            //清除用户信息
//        mManager.clearUserInfo();
            //释放资源
            mManager.destroy();
            mManager = null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }


}
