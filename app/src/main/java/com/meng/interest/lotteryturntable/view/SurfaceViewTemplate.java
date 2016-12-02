package com.meng.interest.lotteryturntable.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * @author wangchengmeng
 *         SurfaceView的一般写法
 */

public class SurfaceViewTemplate extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    //一般都会用到的成员变量
    private SurfaceHolder mSurfaceHolder;
    private Canvas        mCanvas;

    //开启线程绘制的线程
    private Thread  mThread;
    //控制线程的开关
    private boolean isRunning;


    public SurfaceViewTemplate(Context context) {
        this(context, null);
    }

    public SurfaceViewTemplate(Context context, AttributeSet attrs) {
        super(context, attrs);
        //初始化
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
        //设置可获得焦点
        setFocusable(true);
        setFocusableInTouchMode(true);
        //这是常亮
        setKeepScreenOn(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        //surface创建的时候
        mThread = new Thread(this);
        //创建的时候就开启线程
        isRunning = true;
        mThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        //变化的时候
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        //销毁的时候  关闭线程
        isRunning = false;

    }

    @Override
    public void run() {
        //在子线程中不断的绘制
        while (isRunning) {
            draw();
        }
    }

    private void draw() {
        try {
            mCanvas = mSurfaceHolder.lockCanvas();
            if (null != mCanvas) {
                //避免执行到这里的时候程序已经退出 surfaceView已经销毁那么获取到canvas为null
            }
        } catch (Exception e) {
            //异常可以不必处理
        } finally {
            //一定要释放canvas避免泄露
            mSurfaceHolder.unlockCanvasAndPost(mCanvas);
        }
    }
}
