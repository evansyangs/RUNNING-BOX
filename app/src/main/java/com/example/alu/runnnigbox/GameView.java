package com.example.alu.runnnigbox;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

/**
 * Created by 10988 on 2017/10/7.
 */

public class GameView extends SurfaceView implements Callback,Runnable{
    public int width, height;
    private Canvas mCanvas;
    //刷新界面线程
    private Thread mThread;
    //界面处理句柄
    private SurfaceHolder mSurfaceHolder;
    private boolean mIsRunning = false;
    private int TIME_IN_FRAME = 50;
    GameActivity gameActivity;

    public GameView(Context context, int width, int height) {
        super(context);
        //setFocusable(true);
        //activity是 context的一个子类。
        gameActivity = (GameActivity) context;
        this.width = width;
        this.height = height;

        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
    }

    public void mDraw() {
        //设置画布的颜色
        mCanvas.drawColor(Color.WHITE);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        mIsRunning = true;
        //新建一个刷屏线程
        mThread = new Thread(this);
        mThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder,  int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        //销毁
        mIsRunning=false;
    }

    @Override
    public void run() {
//决定线程是否继续执行
        while (mIsRunning) {
            long startTime = System.currentTimeMillis();
            //调用mDraw进行绘制
            synchronized (mSurfaceHolder) {
                mCanvas = mSurfaceHolder.lockCanvas();
                mDraw();
                mSurfaceHolder.unlockCanvasAndPost(mCanvas);
            }

            long endTime = System.currentTimeMillis();

            int diffTime = (int) (endTime - startTime);

            while (diffTime < TIME_IN_FRAME) {
                diffTime = (int) (System.currentTimeMillis() - startTime);
                Thread.yield();
            }
        }
    }
}
