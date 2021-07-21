package com.schneewittchen.rosandroid.ui.fragments.map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
//import android.app.Fragment;
//import android.content.ComponentName;
//import android.content.Context;
//import android.content.Intent;
//import android.content.ServiceConnection;
//import android.location.LocationProvider;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.IBinder;
//import android.os.Looper;
//import android.util.Log;
//import android.util.TypedValue;
//import android.view.LayoutInflater;
//import android.view.TextureView;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.WindowManager;
//import android.widget.TextView;
//import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;

import com.liphy.navigation.IndoorLocation;
import com.liphy.navigation.LiphyLocationService;
import com.schneewittchen.rosandroid.R;
import com.schneewittchen.rosandroid.ui.activity.ICDCMapLayout;

import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
//import liphy.io.liphysdk.LightFlyCamera;

//import com.liphy.navigation.IndoorLocation;
//import com.liphy.navigation.LatLngTranslator;
//import com.liphy.navigation.LiphyLocationService;
//import com.liphy.navigation.LiphyState;
//import com.liphy.navigation.bluetooth.BluetoothInfo;
//import com.liphy.navigation.network.LiphyCloudManager;
//import com.parse.ParseException;
//import com.parse.ParseObject;
//import com.robotca.ControlApp.ControlApp;
//import com.robotca.ControlApp.Core.LocationProvider;
//import com.robotca.ControlApp.Core.RobotController;
//import com.robotca.ControlApp.R;
//import com.robotca.ControlApp.liphy;
//import org.osmdroid.bonuspack.overlays.GroundOverlay;
//import org.osmdroid.bonuspack.overlays.MapEventsOverlay;
//import org.osmdroid.bonuspack.overlays.MapEventsReceiver;
//import org.osmdroid.tixleprovider.tilesource.TileSourceFactory;
//import timber.log.Timber;

/**
 * Fragment containing the Map screen showing the real-world position of the Robot.
 * 包含显示机器人真实位置的地图屏幕的碎片。
 *
 */
public class
MapFragment extends Fragment implements MapEventsReceiver {


    //    private ConstraintLayout mapContainer;
//    private ImageView user_icon;

    private static boolean LIPHY_LOCATION_SERVICE_ENABLED = false;

    ICDCMapLayout icdcmap;
    private Handler handler = new Handler();
    double robotlocalization_x;
    double robotlocalization_y;
    int robot_x, robot_y;

    private TextView textViewLog;
    private TextView textViewvlcLoc;
    private String robotLocString = "";
    private String vlcMapLocString = "";
    private int countTest = 1;


    // liphy nav sdk
    private TextureView textureView;
    private LiphyLocationService liphyLocationService;
    private IndoorLocation lastLiphyLocation;
    private boolean isLiphyServiceBound = false;
    private static final float LIGHT_SIZE = 17.5f;
    //private LightFlyCamera lightFlyManager;
    // x,y coordinate translated by nav sdk
    private TextView xCoorText;
    private TextView yCoorText;

    //    private Pair<Double, Double> coordinateInCm;
    private double phone_x;
    private double phone_y;
    int phone_xx,phone_yy;
    private boolean lightIdInQueue = false;
    private String latestLightId = "";
    private String lastMappedId = "";

//    LightFlyCamera lightFlyManager;


    // Log tag String
    private static final String TAG = "MapFragment";
//    private RobotController controller;

//    private MyLocationNewOverlay myLocationOverlay;
//    private MyLocationNewOverlay secondMyLocationOverlay;
//    private MapView mapView;

    ArrayList<GeoPoint> waypoints = new ArrayList<>();

    private Timer timer = new Timer();

    /**
     * Default Constructor.
     */
    public MapFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.fragment_map, null);
//        mapView = (MapView) view.findViewById(R.id.mapview);
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        //l

        //textViewLog = (TextView) view.findViewById(R.id.textViewLog);
        //textViewvlcLoc = (TextView) view.findViewById(R.id.textViewvlcLocMap);
        //LocationProvider locationProvider = ((ControlApp) getActivity()).getRobotController().LOCATION_PROVIDER;//通过localization provider来接收当前的位置信息

        //***************************************** self define   ***************************************************************************


//        icdcmap=(ICDCMapLayout) view.findViewById(R.id.icdcmap);//地图
        textureView = (TextureView) view.findViewById(R.id.textureView123);//这个必须有，不然可能跳出程序
        //lightFlyManager = LightFlyCamera.getCameraInstance(getActivity());
       //lightFlyManager.setTextureView(textureView);
//        lightFlyManager.setLightTrackedCallback(this);
//        lightFlyManager.setDrawOnTextureView(false);
//        lightFlyManager.useFrontCamera(true);

        if(LIPHY_LOCATION_SERVICE_ENABLED){
            if (getActivity() != null) {
                Intent liphyLocationServiceIntent = new Intent(getActivity(), LiphyLocationService.class);
                //getActivity().bindService(liphyLocationServiceIntent, connection, Context.BIND_AUTO_CREATE);
            } else {
                Log.d("error", "getActivity() is null");
            }
        }


//        Intent liphyLocationServiceIntent = new Intent(getActivity(), LiphyLocationService.class);
//        getActivity().bindService(liphyLocationServiceIntent, connection, Context.BIND_AUTO_CREATE);


        // load cloud data
        /*LiphyCloudManager manager = LiphyCloudManager.getInstance();
        ArrayList<String> filters = new ArrayList<>();
        try {
            manager.fetchLightBeaconsFromCloud();
            manager.fetchBuildingsFromCloud(filters);
        } catch (ParseException e) {
            e.printStackTrace();

        }*/


//        setPositionOnMap(new android.graphics.Point(-150,-150),user_icon,mapContainer);

        timer.scheduleAtFixedRate(new TimerTask() {//每500ms.读取一次数据，确保定位数据更新
            @Override
            public void run() {
                //the result of slo-vlp
                try{
                    //Point robotlocalization=((ControlApp) getActivity()).getRobotController().getOdometry().getPose().getPose().getPosition();
//                    robotlocalization_x=robotlocalization.getX()*100;//the result of X cm
//                    robotlocalization_y=robotlocalization.getY()*100;//the result of y cm

                    robotlocalization_x=1*100;//the result of X cm
                    robotlocalization_y=1*100;//the result of y cm
                    robot_y=(int) robotlocalization_y;
                    robot_x=(int) robotlocalization_x;

                    //PDR的结果
                    phone_xx=(int) phone_x;
                    phone_yy=(int) phone_y;
                    //
//                    setPositionOnMap(new android.graphics.Point(robot_x,robot_y),user_icon,mapContainer);
//                    setPositionOnMap(new android.graphics.Point(600,100),user_icon,mapContainer);
//                    Log.v("robotl_currentThread: ",Thread.currentThread().getName());
//                    icdcmap.changeIcon(robot_x,robot_y);
                    robotLocString = String.valueOf((float)Math.round(robotlocalization_x*10)/10)+","+String.valueOf((float)Math.round(robotlocalization_y*10)/10)+"\n"+robotLocString;

                    Handler mainHandler = new Handler(Looper.getMainLooper());

                    Runnable myRunnable = new Runnable() {
                        @Override
                        public void run() {
                            icdcmap.updateRobotIcon(robot_x,-robot_y); // y coordinate is in opposite direction on phone
                            icdcmap.updateUserIcon(robot_y+20,-robot_x-20);
//                            if(lightIdInQueue){
//                                if(!lastMappedId.equals(latestLightId)){
//
//                                    for(int i = 0; i < icdcmap.lightMarkersList.size(); i++){
//                                        if(icdcmap.lightMarkersList.get(i).lightId.equals(latestLightId)){
//                                            if(!icdcmap.lightMarkersList.get(i).installed){ // if it is not installed
//                                                icdcmap.add_light_hotspot(latestLightId,robot_x,-robot_y);
//                                                lightIdInQueue = false;
//                                                lastMappedId = latestLightId;
//                                                vlcMapLocString = "ID:"+latestLightId+" @ "+String.valueOf((float)Math.round(robotlocalization_x*10)/10)+","+String.valueOf((float)Math.round(robotlocalization_y*10)/10)+"\n"+vlcMapLocString;
//                                                textViewvlcLoc.setText(vlcMapLocString);
//                                            }
//                                        }
//                                    }
//
//
//
//                                }else{
//                                    lightIdInQueue = false;
//                                }
//
//                            }
                            //countTest++;
                            //textViewLog.setText(robotLocString);
                            textViewLog.setText("Receiving Location");
                        } // This is your code
                    };
                    mainHandler.post(myRunnable);



                    //textViewLog.setText(textViewLog.getText()+"\nX = "+robot_x+" Y = "+robot_y);
                    //textViewLog.setText("\nX = "+robot_x+" Y = "+robot_y);


//                    icdcmap.updateRobotIcon(0,0);


//                    *****************************************************************
//                    icdcmap.changeIcon_phone(100,100);




                    //设置角度
//                    icdcmap.updateUserIcon.setRotation(lastRoateDegree);

//                    mapContainer.invalidate();
//                    mapContainer.requestLayout();


                    Log.v("robotlocalization_x",String.valueOf(robotlocalization_x));
                    Log.v("robotlocalization_y",String.valueOf(robotlocalization_y));
                    //Log.v("RobotoLocString",robotLocString);
                }
                catch (Exception e)
                {
//                    Toast.makeText(getActivity(), "Robot's position is not available", Toast.LENGTH_SHORT).show();
                    Log.v("robotlocalization","Robot's position is not available");
                }
            }
        }, 0, 500);

//        Toast.makeText(getActivity(), String.valueOf(phone_xx),Toast.LENGTH_SHORT).show();




        //在robotController.java中
        // 如果将此处的LOCATION_PROVIDER改为ekf输出，理论上可以以次作为定位结果（需要注意LocationProvider的格式）

        // Location overlay using the robot's GPS
//        myLocationOverlay = new MyLocationNewOverlay(locationProvider, mapView);//并且在mapview上显示
        // Location overlay using Android's GPS
//        secondMyLocationOverlay = new MyLocationNewOverlay(mapView);
//        MapEventsOverlay mapEventsOverlay = new MapEventsOverlay(mapView.getContext(), this);

        // Allow GPS updates
//        myLocationOverlay.enableMyLocation();
//        secondMyLocationOverlay.enableMyLocation();

        // Center on and follow the robot by default
//        myLocationOverlay.enableFollowLocation();

//        mapView.getOverlays().add(myLocationOverlay);
//        mapView.getOverlays().add(secondMyLocationOverlay);
//        mapView.getOverlays().add(0, mapEventsOverlay);

        // Set up the Center button
//        robotRecenterButton.setFocusable(false);
//        robotRecenterButton.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            // Center or recenter on Android with a long press
//            public boolean onLongClick(View v) {
//
////                mapView.postInvalidate();
////                myLocationOverlay.disableFollowLocation();
////                mapView.postInvalidate();
////                Toast.makeText(mapView.getContext(), "Centered on you", Toast.LENGTH_SHORT).show();
////                secondMyLocationOverlay.enableFollowLocation();
////                mapView.postInvalidate();
//                return true;
//
//            }
//        });
//        robotRecenterButton.setOnClickListener(new OnClickListener() {
//            @Override
//            // Center or recenter on robot with a tap
//            public void onClick(View v) {
////                secondMyLocationOverlay.disableFollowLocation();
////                mapView.postInvalidate();
////                Toast.makeText(mapView.getContext(), "Centered on the Robot", Toast.LENGTH_SHORT).show();
////                myLocationOverlay.enableFollowLocation();
////                mapView.postInvalidate();
//
//            }
//        });

//        IMapController mapViewController = mapView.getController();
//        mapViewController.setZoom(18);

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        //lightFlyManager.startTracking();
//        if(LIPHY_LOCATION_SERVICE_ENABLED){
//            if (isLiphyServiceBound) {
//                if (!liphyLocationService.isBluetoothRunning()) {
//                    liphyLocationService.startBluetoothSearch();
//                }
//            }
//        }

    }

    @Override
    public void onStop() {
        super.onStop();
        //lightFlyManager.stopTracking();

//        if(LIPHY_LOCATION_SERVICE_ENABLED){
//            if (isLiphyServiceBound) {
//                liphyLocationService.stopLocationProviders();
//            }
//        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();//防止内存泄漏
        timer=null;
//        getActivity().unbindService(connection);
    }

    /**
     * Tell the user the coordinates of a tapped point on the map.
     * @param geoPoint The tapped point
     * @return True
     */
    @Override
    public boolean singleTapConfirmedHelper(GeoPoint geoPoint) {
//        Toast.makeText(mapView.getContext(), "Tapped on (" + geoPoint.getLatitude() + "," +
//                geoPoint.getLongitude() + ")", Toast.LENGTH_LONG).show();

        return true;
    }

    /**
     * Place a marker using a long press.
     * @param geoPoint The point to place the marker
     * @return True
     */
//    @Override
     public boolean longPressHelper(GeoPoint geoPoint) {
//
//        GroundOverlay myGroundOverlay = new GroundOverlay(getActivity());
//        myGroundOverlay.setPosition(geoPoint);
//        try {
//            //noinspection ConstantConditions,deprecation
//            myGroundOverlay.setImage(getResources().getDrawable(R.drawable.ic_flag_black_24dp).mutate());
//        }
//        catch (NullPointerException e) {
//            Log.e(TAG, "", e);
//        }
//        myGroundOverlay.setDimensions(25.0f);
////        mapView.getOverlays().add(myGroundOverlay);
////        mapView.postInvalidate();
//
////        // keep storage of markers and current location
////        waypoints.add(myLocationOverlay.getMyLocation());
////        waypoints.add(geoPoint);
////
////        Toast.makeText(mapView.getContext(), "Marked on (" + geoPoint.getLatitude() + "," +
////                geoPoint.getLongitude() + ")", Toast.LENGTH_LONG).show();
//
        return true;
    }


    // Function to compute distance between 2 points as well as the angle (bearing) between them
    @SuppressWarnings("unused")
    private static void computeDistanceAndBearing(double lat1, double lon1,
                                                  double lat2, double lon2, float[] results) {
        // Based on http://www.ngs.noaa.gov/PUBS_LIB/inverse.pdf
        // using the "Inverse Formula" (section 4)

        int MAXITERS = 20;
        // Convert lat/long to radians
        lat1 *= Math.PI / 180.0;
        lat2 *= Math.PI / 180.0;
        lon1 *= Math.PI / 180.0;
        lon2 *= Math.PI / 180.0;

        double a = 6378137.0; // WGS84 major axis
        double b = 6356752.3142; // WGS84 semi-major axis
        double f = (a - b) / a;
        double aSqMinusBSqOverBSq = (a * a - b * b) / (b * b);

        double L = lon2 - lon1;
        double A = 0.0;
        double U1 = Math.atan((1.0 - f) * Math.tan(lat1));
        double U2 = Math.atan((1.0 - f) * Math.tan(lat2));

        double cosU1 = Math.cos(U1);
        double cosU2 = Math.cos(U2);
        double sinU1 = Math.sin(U1);
        double sinU2 = Math.sin(U2);
        double cosU1cosU2 = cosU1 * cosU2;
        double sinU1sinU2 = sinU1 * sinU2;

        double sigma = 0.0;
        double deltaSigma = 0.0;
        double cosSqAlpha;
        double cos2SM;
        double cosSigma;
        double sinSigma;
        double cosLambda = 0.0;
        double sinLambda = 0.0;

        double lambda = L; // initial guess
        for (int iter = 0; iter < MAXITERS; iter++) {
            double lambdaOrig = lambda;
            cosLambda = Math.cos(lambda);
            sinLambda = Math.sin(lambda);
            double t1 = cosU2 * sinLambda;
            double t2 = cosU1 * sinU2 - sinU1 * cosU2 * cosLambda;
            double sinSqSigma = t1 * t1 + t2 * t2; // (14)
            sinSigma = Math.sqrt(sinSqSigma);
            cosSigma = sinU1sinU2 + cosU1cosU2 * cosLambda; // (15)
            sigma = Math.atan2(sinSigma, cosSigma); // (16)
            double sinAlpha = (sinSigma == 0) ? 0.0 :
                    cosU1cosU2 * sinLambda / sinSigma; // (17)
            cosSqAlpha = 1.0 - sinAlpha * sinAlpha;
            cos2SM = (cosSqAlpha == 0) ? 0.0 :
                    cosSigma - 2.0 * sinU1sinU2 / cosSqAlpha; // (18)

            double uSquared = cosSqAlpha * aSqMinusBSqOverBSq; // defn
            A = 1 + (uSquared / 16384.0) * // (3)
                    (4096.0 + uSquared *
                            (-768 + uSquared * (320.0 - 175.0 * uSquared)));
            double B = (uSquared / 1024.0) * // (4)
                    (256.0 + uSquared *
                            (-128.0 + uSquared * (74.0 - 47.0 * uSquared)));
            double C = (f / 16.0) *
                    cosSqAlpha *
                    (4.0 + f * (4.0 - 3.0 * cosSqAlpha)); // (10)
            double cos2SMSq = cos2SM * cos2SM;
            deltaSigma = B * sinSigma * // (6)
                    (cos2SM + (B / 4.0) *
                            (cosSigma * (-1.0 + 2.0 * cos2SMSq) -
                                    (B / 6.0) * cos2SM *
                                            (-3.0 + 4.0 * sinSigma * sinSigma) *
                                            (-3.0 + 4.0 * cos2SMSq)));

            lambda = L +
                    (1.0 - C) * f * sinAlpha *
                            (sigma + C * sinSigma *
                                    (cos2SM + C * cosSigma *
                                            (-1.0 + 2.0 * cos2SM * cos2SM))); // (11)

            double delta = (lambda - lambdaOrig) / lambda;
            if (Math.abs(delta) < 1.0e-12) {
                break;
            }
        }

        float distance = (float) (b * A * (sigma - deltaSigma));
        results[0] = distance;
        if (results.length > 1) {
            float initialBearing = (float) Math.atan2(cosU2 * sinLambda,
                    cosU1 * sinU2 - sinU1 * cosU2 * cosLambda);
            initialBearing *= 180.0 / Math.PI;
            results[1] = initialBearing;
            if (results.length > 2) {
                float finalBearing = (float) Math.atan2(cosU1 * sinLambda,
                        -sinU1 * cosU2 + cosU1 * sinU2 * cosLambda);
                finalBearing *= 180.0 / Math.PI;
                results[2] = finalBearing;
            }
        }
    }


    /////******************self define function********************////
    public static float pxToDp(final Context context, final float px) {//pixel到dph的转换
        return px / context.getResources().getDisplayMetrics().density;
    }

    //在地图上显示定位结果
    private void setPositionOnMap(android.graphics.Point coordinate, View mapObject, ConstraintLayout mapContainer) {
        // View.getWidth() & .getHeight() returns value in pixel
        // i.e. convert to dp solved the problem

        float image_width_dp = pxToDp(getActivity(), mapObject.getWidth());
        float image_height_dp = pxToDp(getActivity(), mapObject.getHeight());
//        Log.v("mapsize","~~~~~");
//        Log.v("mapsize1",String.valueOf(image_width_dp));
//        Log.v("mapsize2",String.valueOf(image_height_dp));
        float image_width_dp1 = pxToDp(getActivity(), mapContainer.getWidth());
        float image_height_dp1 = pxToDp(getActivity(), mapContainer.getHeight());
//        Log.v("mapsize3",String.valueOf(image_width_dp1));
//        Log.v("mapsize4",String.valueOf(image_height_dp1));

        // this way the center of the image will shift to the desired coordinate
        //先算出dph跟物理距离的比例
        //由于采用了ConstraintLayout规定了dp
        int margin_start_with_offset = (int)((coordinate.x)/3.57142) - (int) (image_width_dp / 2)-(int)(image_width_dp1/2)+81;
        int margin_top_with_offset = (int)((coordinate.y)/3.5) - (int) (image_height_dp / 2)-(int)(image_height_dp1/2)+369;
//        Log.v("mapsize",String.valueOf(margin_start_with_offset));
//        Log.v("mapsize",String.valueOf(margin_top_with_offset));



        ConstraintSet set = new ConstraintSet();
        set.clone(mapContainer);
        //set.clear(mapObject.getId());
        set.connect(mapObject.getId(), ConstraintSet.START, mapContainer.getId(), ConstraintSet.START,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, margin_start_with_offset, getResources().getDisplayMetrics()));
        set.connect(mapObject.getId(), ConstraintSet.TOP, mapContainer.getId(), ConstraintSet.TOP,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, margin_top_with_offset, getResources().getDisplayMetrics()));
        set.applyTo(mapContainer);

//        Transition transition = new ChangeBounds();
//        transition.setDuration(10);
//        TransitionManager.beginDelayedTransition(mapContainer, transition);

//        mapContainer.requestLayout();
    }



    ////////////////////////////////////////****************************************************
    //@Override
//   public void onLatestLightIdReady(String s, ParseObject parseObject) {
//        /*latestLightId = s;
//        lightIdInQueue = true;
//        Log.v("LatestLightId",s);*/
//
//    }

//    @Override
//    public void onLatestLightInfoReady(LightFlyCamera.LEDKeyPoint ledKeyPoint, ParseObject parseObject) {
//        final double xLEDpos,yLEDpos;
//
//        xLEDpos = Math.round(ledKeyPoint.positionX*LIGHT_SIZE);//round
//        yLEDpos = Math.round(ledKeyPoint.positionY*LIGHT_SIZE);
//
//        final double distanceFromLight = Math.round(Math.sqrt(Math.pow(xLEDpos,2)+Math.pow(yLEDpos,2)));
//        // Find the azimuth angle of vector passing through the smartphone and led position
//        final double angleFromLight = (180 - Math.round(Math.toDegrees(Math.atan2(yLEDpos, xLEDpos))));
//
//        final String light = ledKeyPoint.lightId;
//
////        this.runOnUiThread(new Runnable() {
////            @Override
////            public void run() {
////                tvVLCPosition.setText("VLC ID from nav sdk is:" + light + "\n"
////                        +"X, Y = "+ xLEDpos + "," + yLEDpos + "\n"
////                        + "Distance: "+ distanceFromLight + "\n"
////                        + "Angle: " + angleFromLight);
////            }
////        });
//    }

    //@Override
    //public void onLatestBluetoothInfoLoaded(BluetoothInfo bluetoothInfo) {
//        Toast.makeText(getActivity(), bluetoothInfo.getBluetoothName(),Toast.LENGTH_LONG).show();
//    }

//    @Override
//    public void didTrackLight(String lightId){
//        Log.v("LightFlySDKLightID",lightId);
//        latestLightId = lightId;
//        lightIdInQueue = true;
//    }

   // @Override
    //public void onLocationUpdate(IndoorLocation location) {

//        if (location.getProvider().equals(getString(R.string.provider_bluetooth))) {
//            // enable pdr
//            LiphyState.setFirstSignalReceived(true);
////            Timber.tag("location").d("LIPHY & PDR ENABLED BY BLUETOOTH");
//            return;
//        } else if (location.getProvider().equals(getString(R.string.provider_liphy))) {
//            // first liphy location set as origin
//            if (!LiphyState.isFirstLiphySignalReceived()) {
////                coordinateInCm = new Pair<>(0.0, 0.0);
//                phone_x = 0;
//                phone_y = 0;
//                LiphyState.setFirstLiphySignalReceived(true);
//            }

            /*
            delete this after the hardcoded table is done
             */
//            phone_x = 0;
//            phone_y = 0;
//
//
//            lastLiphyLocation = new IndoorLocation(getString(R.string.provider_liphy), location.getLatitude(),
//                    location.getLongitude(), location.getFloor(), location.getBuildingId(),
//                    location.getBuildingName(), location.getBearing(), location.getTime());
//
//        } else if (location.getProvider().equals(getString(R.string.provider_pdr))) {
////            Timber.tag("location").d("bearing %f", location.getBearing());
////            Timber.tag("location").d("length %f", Math.sin(Math.toRadians(location.getBearing())));
//
//            phone_x += LatLngTranslator.DEFAULT_STEP_DIST * 100 * Math.sin(Math.toRadians(location.getBearing()));
//            phone_y += LatLngTranslator.DEFAULT_STEP_DIST * 100 * Math.cos(Math.toRadians(location.getBearing()));
//        }
//
//    }

    //@Override
    public void onOrientationUpdate(float v) {

    }

    //@Override
    public void onCompassAccuracyChanged(int i) {

    }

//    /**
//     * Defines callbacks for service binding, passed to bindService()
//     */
//    private final ServiceConnection connection = new ServiceConnection() {
//        @Override
//        public void onServiceConnected(ComponentName className, IBinder service) {
//            LiphyLocationService.LocalBinder binder = (LiphyLocationService.LocalBinder) service;
//            liphyLocationService = binder.getService();
//            liphyLocationService.getActivityAndTextureView(getActivity(), textureView);
//
//            // locaiton info callback
//            liphyLocationService.registerOnLiphyServiceListener(MapFragment.this);
//            // liphy & bluetooth callback
//            liphyLocationService.registerOnLiphyDeviceListener(MapFragment.this);
//
////            liphyLocationService.setAccessKeyForLiphySdk();
//
////            // start bluetooth (liphy will be started automatically)
////            if (EasyPermissions.hasPermissions(RobotChooser.this, Manifest.permission.CAMERA,
////            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)) {
//            if (LiphyState.isBluetoothSearch() && !liphyLocationService.isBluetoothRunning())
//                liphyLocationService.startBluetoothSearch();
////            }
//
////            Timber.tag("liphysdk").d("version: %s", liphyLocationService.liphySdkVersion());
////            Timber.tag("liphysdk").d("build no.: %s", liphyLocationService.liphySdkBuildNumebr());
////            Timber.tag("liphysdk").d("expiry date: %s", liphyLocationService.liphySdkExpiryDate());
//
//            isLiphyServiceBound = true;
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName arg0) {
//            // see on destroyed
//            liphyLocationService.unregisterOnLiphyServiceListener();
//            liphyLocationService = null;
//            isLiphyServiceBound = false;
//        }
//    };
}
