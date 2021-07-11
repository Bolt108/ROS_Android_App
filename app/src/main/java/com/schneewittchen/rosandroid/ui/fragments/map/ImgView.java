package com.schneewittchen.rosandroid.ui.fragments.map;

import android.content.Context;
import androidx.appcompat.widget.AppCompatImageView;

import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

//编写自定义视图时，需使用此类
public class ImgView extends AppCompatImageView {

    //androidx.appcompat.widget.AppCompatImageView

    //设置一系列的属性变量
    // 属性变量
    private float translationX; // 移动X
    private float translationY; // 移动Y
    public float scale= 1; // 伸缩比例
    public float rotation; // 旋转角度

    // 移动过程中临时变量
    private float actionX;
    private float actionY;
    private float spacing;
    private float degree;
    private int moveType; // 0=未选择，1=拖动，2=缩放
    private int count = 0;//双击事件
    private long firstClick = 0;//第一次点击时间
    private long secondClick = 0;//第二次点击时间
    private final int totalTime = 400;//设定相邻两次点击的时间
    private boolean once = false;

    public ImgView(Context context) {
        super(context);
    }

    public ImgView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImgView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


//    public ImgView(Context context) {
//        super(context);
//    }
//
//    public ImgView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//    }
//
//    public ImgView(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//    }

    //在此定义onTouchEvent
    //属于交互式控件。
    //触摸屏幕事件处理程序，在屏幕被触摸、释放或者拖动时触发
    @Override
    public boolean onTouchEvent(MotionEvent event) { //关于motionEvent可以参考https://www.gcssloop.com/customview/motionevent
//        int action=event.getAction();//查找触发处理程序的事件类型。

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN://// 记录触摸点坐标（手指 初次接触到屏幕时触发。）
                moveType = 1;//更改标志位为拖动
                actionX = event.getRawX();//刚触摸时的x（获得触摸点在整个屏幕的 X 轴坐标。）
                actionY = event.getRawY();//刚触摸时的y（获得触摸点在整个屏幕的 Y 轴坐标。）
                count++;
                if ( count==1) {
                    firstClick = System.currentTimeMillis();//记录第一次点击时间
                } else if (count == 2) {
                    secondClick = System.currentTimeMillis();//记录第二次点击时间
                    if (secondClick - firstClick < totalTime) {//判断二次点击时间间隔是否在设定的间隔时间之内
                        rotation=0;
                        setRotation(0);
                        setRotation(0);
                        scale = 1;
                        setScaleX(1);
                        setScaleY(1);

                        count = 0;
                        firstClick = 0;
                    } else {
                        firstClick = secondClick;
                        count = 1;
                    }
                    secondClick = 0;
                }
                break;
            case MotionEvent.ACTION_POINTER_DOWN: //有非主要的手指按下(即按下之前已经有手指在屏幕上)
                moveType = 2;  //此时则实行缩放
                spacing = getSpacing(event);
                degree = getDegree(event);
                break;
            case MotionEvent.ACTION_MOVE:  //手指 在屏幕上滑动 时触发，会多次触发
                if (moveType == 1) {
                    translationX = translationX + event.getRawX() - actionX;
                    translationY = translationY + event.getRawY() - actionY;
                    setTranslationX(translationX);
                    setTranslationY(translationY);
                    actionX = event.getRawX();
                    actionY = event.getRawY();
                }
                else if (moveType == 2) {
                    scale = scale * getSpacing(event) / spacing;
//                    setScaleX(scale);
//                    setScaleY(scale);
                }
                break;
            case MotionEvent.ACTION_UP:  //手指 离开屏幕 时触发
            case MotionEvent.ACTION_POINTER_UP:   //有非主要的手指抬起(即抬起之后仍然有手指在屏幕上)。
                moveType = 0;
        }
        return true;
    }

    // 触碰两点间距离
    private float getSpacing(MotionEvent event) {
        //通过三角函数得到两点间的距离
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    // 取旋转角度
    private float getDegree(MotionEvent event) {
        //得到两个手指间的旋转角度
        double delta_x = event.getX(0) - event.getX(1);
        double delta_y = event.getY(0) - event.getY(1);
        double radians = Math.atan2(delta_y, delta_x);
        return (float) Math.toDegrees(radians);
    }

}
