package com.schneewittchen.rosandroid.ui.fragments.map;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.annotation.RequiresApi;

//import com.robotca.ControlApp.R;

import com.schneewittchen.rosandroid.R;

import java.util.ArrayList;

import static android.view.Gravity.END;

public class ICDCMapLayout extends FrameLayout {

    private static final boolean USE_ROS_RESOLUTION = false; // Keep it false for now

//    // Parameters for Infineon Map
//    private static final float MAP_WIDTH_IN_METERS = 19.902431f; // Calculate by Hand
//    private static final float MAP_HEIGHT_IN_METERS = 24.0991f; // Calculate by Hand
//    private static final float ORIGIN_ON_ROS_X = -13.6f;
//    private static final float ORIGIN_ON_ROS_Y = -12.5f;
//    private static final float RESOLUTION_ON_ROS = 0.02574786f; // meters/pixel
//    private static final int MAP = R.drawable.infineon;

    // Parameters for Office Map
//    private static final float MAP_WIDTH_IN_METERS = 10.169f; // Not needed if using ROS Resolution
//    private static final float MAP_HEIGHT_IN_METERS = 7.40f; // Not needed if using ROS Resolution
//    private static final float ORIGIN_ON_ROS_X = -5f;
//    private static final float ORIGIN_ON_ROS_Y = -2.4f;
//    private static final float RESOLUTION_ON_ROS = 0.011556f; // meters/pixel
//    private static final int MAP = R.drawable.map_office;

     //Parameters for CYT Map
    private static final float MAP_WIDTH_IN_METERS = 21; // Calculate by Hand or measure
    private static final float MAP_HEIGHT_IN_METERS = 15; // Calculate by hand or measure
    private static final float ORIGIN_ON_ROS_X = -9.3f; // From YML file
    private static final float ORIGIN_ON_ROS_Y = -11.17f; // From YML file
    private static final float RESOLUTION_ON_ROS = 0.0179487f; // From YML file
    private static final int MAP = R.drawable.icdc_map;




    ImgView map;//一个自定义的类，用于存放地图的
    ImageView robot_icon;//用于存放用户图标(robot)
    ImageView user_icon;//用于存放用户图标(phone)

    ArrayList<LightIdMarker> lightMarkersList;


    //robot Position
    int robotPositionX=0;
    int robotPositionY=0;

    //user Position
    int userPositionX=0;
    int userPositionY=0;

    int map_x=0;
    int map_y=0; // at start Map will be at 0,0

    float view_width,view_height;

    private boolean flag=false;//定义一个标致位，用于测量控件的大小，只在一开始的时候做

    public ICDCMapLayout(@NonNull Context context) {
        super(context);
    }

    public ICDCMapLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
//
//    public ICDCMapLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//    }
//
//    public ICDCMapLayout(@NonNull Context context) {
//        super(context);
//    }
//
//    public ICDCMapLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
//        super(context, attrs);
//    }
//
//    public ICDCMapLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//    }


    //这个方法用来拦截TouchEvent
    //如果返回值是true，代表事件在当前的viewGroup中会被处理，向下传递之路被截断（所有子控件将没有机会参与Touch事件），
    // 同时把事件传递给当前的控件的onTouchEvent()继续进行传递或处理。
    //如果返回值是false，即不拦截当前传递来的事件，会继续向下传递，把事件交给子控件的onInterceptTouchEvent()。
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        getParent().requestDisallowInterceptTouchEvent(true);

        updateRobotIconLocationOnMap();
        updateUserIconLocationOnMap();
        updateLightMarkersLocationOnMap();


        /*RectF bounds = new RectF();
        Drawable drawable = map.getDrawable();
        map.getImageMatrix().mapRect(bounds, new RectF(drawable.getBounds()));

        Log.v("MapParameters","Width:"+String.valueOf(map.getWidth()));
        Log.v("MapParameters","Height:"+String.valueOf(map.getHeight()));
        // Bounds of the imageView
        Log.v("MapParameters","MapLeft:"+String.valueOf(map.getLeft()));
        Log.v("MapParameters","MapTop:"+String.valueOf(map.getTop()));
        Log.v("MapParameters","MapBottom:"+String.valueOf(map.getBottom()));
        Log.v("MapParameters","MapRight:"+String.valueOf(map.getRight()));

        //Bounds of the drawable inside image View

        Log.v("MapParameters","BoundsTop:"+String.valueOf(bounds.top));
        Log.v("MapParameters","BoundsBottom:"+String.valueOf(bounds.bottom));
        Log.v("MapParameters","BoundsLeft:"+String.valueOf(bounds.left));
        Log.v("MapParameters","BoundsRight:"+String.valueOf(bounds.right));


        Log.v("MapParameters","MapIntrinsicHeight:"+String.valueOf(map.getDrawable().getIntrinsicHeight()));
        Log.v("MapParameters","MapIntrinsicWidth:"+String.valueOf(map.getDrawable().getIntrinsicWidth()));


        */

        return false;
    }



    //****************************************
    //onMeasure方法的作用是测量控件的大小
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if(!flag){

            map=new ImgView(getContext());//创建自定义的ImgView类
            map.setImageResource(MAP);//放入图片
            //map.setImageResource(R.drawable.circle);

            //每个view都需要一个LayoutParams，告诉父容器的一些放入规则和方式
            LayoutParams lp_map=new LayoutParams(LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);//https://blog.csdn.net/u012810020/article/details/51970771
            lp_map.gravity= Gravity.BOTTOM;//通过此方法来控制该view的位置为居中。

            map.setX(0);//以像素为单位设置X的位置 （Sets the visual x position of this view）
            map.setY(0);

            //然后设置图片在View上显示时的样子
            map.setScaleType(ImgView.ScaleType.FIT_CENTER);//  不按比例缩放图片，目标是把图片塞满整个View
            // 如果设置为FIT_CENTER，则是把图片按比例扩大/缩小到View的宽度，居中显示

            addView(map,lp_map);

            addLightMakersOnMap(20); // add 20 light markers to the map


            //接下来定义定位的robot用户终端
            robot_icon=new ImageView(getContext());
            robot_icon.setImageResource(R.drawable.robot_pin);//设置图标
            LayoutParams lp_icon=new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            lp_icon.gravity= Gravity.BOTTOM|END;//一开始的位置，后面需要设置调
            lp_icon.height=66;
            lp_icon.width=66;
            robot_icon.setLayoutParams(lp_icon);
            //将起始点定位00
            robot_icon.setY(map.getY()+map_x);
            robot_icon.setX(map.getX()+map_y);


            addView(robot_icon);



            //接下来定义定位的phone用户终端
            user_icon=new ImageView(getContext());
            user_icon.setImageResource(R.drawable.gps);//设置图标
            LayoutParams lp_icon_phone=new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            lp_icon_phone.gravity= Gravity.BOTTOM|END;//一开始的位置，后面需要设置调
            lp_icon_phone.height=40;
            lp_icon_phone.width=40;
            user_icon.setLayoutParams(lp_icon_phone);
            //将起始点定位00

            user_icon.setY(0);
            user_icon.setX(0);

            addView(user_icon); //Not adding the user icon

            flag=true;

        }

        setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight());//在覆盖的onMeasure方法的末尾，调用此方法

    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        view_width=map.getWidth();
        view_height=map.getHeight();
        //Log.d("TAGggggg", String.valueOf(map.getWidth()));
    }

    public int translateXfromROSmapCoordinates(float x){

        RectF mapPixelBounds = new RectF();
        Drawable drawable = map.getDrawable();
        map.getImageMatrix().mapRect(mapPixelBounds, new RectF(drawable.getBounds()));
        // First covert the x scale from pixels into meters
        float xPixelsPerCentiMeters;
        if(USE_ROS_RESOLUTION){
            xPixelsPerCentiMeters = 1/(RESOLUTION_ON_ROS*100);
        }else{
            xPixelsPerCentiMeters = ((mapPixelBounds.right - mapPixelBounds.left)/(MAP_WIDTH_IN_METERS*100));
        }

        int displacement = (int) (x * xPixelsPerCentiMeters); // Displacement in pixels from origin
        int origin_x_pixels = (int) ((Math.abs(ORIGIN_ON_ROS_X)*100)*xPixelsPerCentiMeters);
        return origin_x_pixels + displacement + (int) mapPixelBounds.left + (int)map.getX();
    }

    public int translateYfromROSmapCoordinates(float y) {
        RectF mapPixelBounds = new RectF();



        Drawable drawable = map.getDrawable();
        map.getImageMatrix().mapRect(mapPixelBounds, new RectF(drawable.getBounds()));


            // First covert the y scale from pixels into meters
            // Here Maps Y axis direction is different between ROS and Android
            float yPixelsPerCentiMeters;
            if (USE_ROS_RESOLUTION) {
                yPixelsPerCentiMeters = 1 / (RESOLUTION_ON_ROS * 100);
            } else {
                yPixelsPerCentiMeters = ((mapPixelBounds.bottom - mapPixelBounds.top) / (MAP_HEIGHT_IN_METERS * 100));
            }

            int displacement = (int) (y * yPixelsPerCentiMeters); // Displacement in pixels from origin
            int origin_y_pixels = (int) (((MAP_HEIGHT_IN_METERS - Math.abs(ORIGIN_ON_ROS_Y)) * 100) * yPixelsPerCentiMeters);
            return origin_y_pixels + displacement + (int) mapPixelBounds.top + (int) map.getY();

    }




    public void add_light_hotspot(String lightId,int x, int y)
    {
        for (int i = 0; i< lightMarkersList.size(); i++){
            if(lightId.equals(lightMarkersList.get(i).lightId)){
                lightMarkersList.get(i).x = x;
                lightMarkersList.get(i).y = y;
                lightMarkersList.get(i).installed = true;
                lightMarkersList.get(i).marker.setY(translateYfromROSmapCoordinates(y));
                lightMarkersList.get(i).marker.setX(translateXfromROSmapCoordinates(x));
//                lightMarkersList.get(i).idTextView.setY(translateYfromROSmapCoordinates(y));
//                lightMarkersList.get(i).idTextView.setX(translateXfromROSmapCoordinates(x));
            }
        }
        /*ImageView hotpspotImageView = new ImageView(getContext());

        hotpspotImageView.setImageResource(R.drawable.marker);//设置图标
        LayoutParams hotpspot_lp=new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        hotpspot_lp.gravity= Gravity.BOTTOM|END;//一开始的位置，后面需要设置调
        hotpspot_lp.height=66;
        hotpspot_lp.width=66;
        hotpspotImageView.setLayoutParams(hotpspot_lp);
        //将起始点定位00
        hotpspotImageView.setY(map.getY()+map_x-(x));
        hotpspotImageView.setX(map.getX()+map_y-(y));

        addView(hotpspotImageView)*/

    }

    //输入的坐标
    public void updateRobotIcon(int x, int y)
    {
        robotPositionX=x;
        robotPositionY=y;
        updateRobotIconLocationOnMap();
    }

    //not used in smart installation
    public void updateUserIcon(int x, int y)
    {
        userPositionX=x;
        userPositionY=y;
        updateUserIconLocationOnMap();
    }

    private void updateUserIconLocationOnMap(){
        user_icon.setY(translateYfromROSmapCoordinates(userPositionY));
        user_icon.setX(translateXfromROSmapCoordinates(userPositionX));
    }

    private void updateRobotIconLocationOnMap(){
        robot_icon.setY(translateYfromROSmapCoordinates(robotPositionY));
        robot_icon.setX(translateXfromROSmapCoordinates(robotPositionX));
    }
    private void updateLightMarkersLocationOnMap(){
        for (int i = 0 ; i < lightMarkersList.size(); i++){
            if(lightMarkersList.get(i).installed){
                lightMarkersList.get(i).marker.setY(translateYfromROSmapCoordinates(lightMarkersList.get(i).y));
                lightMarkersList.get(i).marker.setX(translateXfromROSmapCoordinates(lightMarkersList.get(i).x));/*
                lightMarkersList.get(i).idTextView.setY(translateYfromROSmapCoordinates(lightMarkersList.get(i).y));
                lightMarkersList.get(i).idTextView.setX(translateXfromROSmapCoordinates(lightMarkersList.get(i).x));*/
            }
        }
    }

    private void addLightMakersOnMap(int count){
        lightMarkersList = new ArrayList<>();
        for(int i = 1; i<= count; i++){
            LightIdMarker lightMaker = new LightIdMarker(String.valueOf(i),0,0);
            lightMarkersList.add(lightMaker);
        }
    }

    public class LightIdMarker{
        public String lightId;
        public int x,y;
        public ImageView marker;
        public TextView idTextView;
        public boolean installed;

        public LightIdMarker(String id, int x_coor, int y_coor){
            this.lightId = id;
            this.x = x_coor;
            this.y = y_coor;
            this.marker = new ImageView(getContext());
            this.idTextView = new TextView(getContext());

            idTextView.setText(id);
            marker.setImageResource(R.drawable.marker);//设置图标
            LayoutParams marker_lp=new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            marker_lp.gravity= Gravity.BOTTOM|END;//一开始的位置，后面需要设置调
            marker_lp.height=66;
            marker_lp.width=66;
            marker.setLayoutParams(marker_lp);
            //将起始点定位00
            addView(marker);
            //addView(idTextView);


        }
    }


}
