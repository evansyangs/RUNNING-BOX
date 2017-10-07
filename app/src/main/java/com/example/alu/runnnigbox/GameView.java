package com.example.alu.runnnigbox;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by 10988 on 2017/10/7.
 */

//本身是一个Runable接口
public class GameView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    public int width,height;
    private Canvas mCanvas;
    //刷新界面线程
    private Thread mThread;
    //处理者
    private SurfaceHolder mSurfaceHolder;
    private boolean mIsRunning = false;
    private int TIME_IN_FRAME = 50;
    GameActivity gameActivity;
    //创建小球
    Ball ball = new Ball(this);

    public GameView(Context context,int width,int height){
        super(context);
        //activity是context的一个子类
        gameActivity = (GameActivity) context;
        this.width = width;
        this.height = height;

        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
    }

    public void mDraw(){
        //设置画布的颜色
        mCanvas.drawColor(Color.WHITE);
        ball.draw(mCanvas);
        drawBG(mCanvas);
    }

    public void drawBG(Canvas mCanvas){
        Paint mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        //设置透明度
        mPaint.setAlpha(50);
        //设置抗锯齿
        mPaint.setAntiAlias(true);
        float h = height * 0.01666667f;
        for(int i = 0;i < height;i += h){
            mCanvas.drawLine(0,i,width,i,mPaint);
        }
    }
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        mIsRunning = true;
        //新建一个刷屏线程
        mThread = new Thread(this);
        mThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        mIsRunning = false;
    }

    @Override
    public void run() {
        //决定线程是否继续执行
        while (mIsRunning){
            long startTime = System.currentTimeMillis();
            //调用mDraw进行绘制
            synchronized (mSurfaceHolder){
                mCanvas = mSurfaceHolder.lockCanvas();
                mDraw();
                mSurfaceHolder.unlockCanvasAndPost(mCanvas);
            }

            long endTime = System.currentTimeMillis();

            int diffTime = (int)(endTime - startTime);

            while(diffTime < TIME_IN_FRAME){
                diffTime = (int)(System.currentTimeMillis() - startTime);
                Thread.yield();
            }
        }
    }

/*    //按键控制
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            gameActivity.finish();
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
            leftKeyDown = true;
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
            rightKeyDown = true;
        }
        return true;
    }
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
            leftKeyDown = false;
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
            rightKeyDown = false;
        }
        return true;
    }*/
}
