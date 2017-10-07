package com.example.alu.runnnigbox;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

import java.util.Random;

/**
 * Created by 10988 on 2017/10/7.
 */

public class GameView extends SurfaceView implements Callback,Runnable{
    public int width, height;
    Random mRandom = new Random();//随机数
    private Canvas mCanvas;
    //刷新界面线程
    private Thread mThread;
    //界面处理句柄
    private SurfaceHolder mSurfaceHolder;
    private boolean mIsRunning = false;
    private int TIME_IN_FRAME = 50;
    GameActivity gameActivity;
    //获取屏幕信息
    DisplayMetrics dm = getResources().getDisplayMetrics();
    //横屏宽度
    int screenWidth = dm.widthPixels;
    //横屏高度
    int screenHeight = dm.heightPixels;


    Box box = new Box(this,0,screenHeight/2+Box.WIDTH/2);
//    StageBlock[] stageblock = new StageBlock[]{new StageBlock(this,screenWidth,100),new StageBlock(this,screenWidth+100,200),
//            new StageBlock(this,screenWidth+200,300),new StageBlock(this,screenWidth+300,400) };
    StageBlock stageblock1 = new StageBlock(this,screenWidth+mRandom.nextInt(50),screenHeight-StageBlock.HEIGHT-mRandom.nextInt(100));
    StageBlock stageblock2 = new StageBlock(this,screenWidth+mRandom.nextInt(50)+800,screenHeight-StageBlock.HEIGHT-mRandom.nextInt(500));
    StageBlock stageblock3 = new StageBlock(this,screenWidth+mRandom.nextInt(50)+1600,screenHeight-StageBlock.HEIGHT-mRandom.nextInt(900));


    public GameView(Context context, int width, int height) {
        super(context);
        setFocusable(true);
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
        drawBG(mCanvas);
        box.draw(mCanvas);
        stageblock1.draw(mCanvas);
        stageblock2.draw(mCanvas);
        stageblock3.draw(mCanvas);

//        for (StageBlock mstageblock : stageblock){
//            mstageblock.draw(mCanvas);
//        }
    }

    public void drawBG(Canvas mCanvas) {
        Paint mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        //设置透明度
        mPaint.setAlpha(50);
        //设置抗锯齿
        mPaint.setAntiAlias(true);
        float h = height * 0.01666667f;
        for (int i = 0; i < height; i += h) {
            mCanvas.drawLine(0, i, width, i, mPaint);
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
