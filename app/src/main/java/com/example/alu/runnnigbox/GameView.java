package com.example.alu.runnnigbox;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

import java.util.Random;

import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_UP;

/**
 * Created by 10988 on 2017/10/7.
 */

public class GameView extends SurfaceView implements Callback,Runnable{
    public Random mRandom = new Random();//随机数
    private Canvas mCanvas;
    private Thread mThread;    //刷新界面线程
    private SurfaceHolder mSurfaceHolder;    //界面处理句柄
    private boolean mIsRunning = false;
    private int TIME_IN_FRAME = 20;//刷新频率

    public DisplayMetrics dm = getResources().getDisplayMetrics();    //获取屏幕信息
    private int screenWidth = dm.widthPixels;    //横屏宽度
    private int screenHeight = dm.heightPixels;    //横屏高度

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {

        return screenHeight;
    }

    //初始化各个位置
    Box mBox = new Box(this,100,screenHeight/2);   //游戏开始时主人公Box的位置
    //下面的台阶初始位置有点简单粗暴...到时我再按照设备屏幕调一下
    StageBlock[] mStageblock = new StageBlock[]{new StageBlock(this,0,screenHeight/2+Box.getWIDTH() + 1),
            new StageBlock(this,screenWidth/3+mRandom.nextInt(5),screenHeight-StageBlock.getHEIGHT()-mRandom.nextInt(100)),
            new StageBlock(this,screenWidth*2/3+mRandom.nextInt(5),screenHeight-StageBlock.getHEIGHT()-mRandom.nextInt(200)),
            new StageBlock(this,screenWidth+mRandom.nextInt(5),screenHeight-StageBlock.getHEIGHT()-mRandom.nextInt(300))};//三个台阶的位置//游戏开始时主人公Box站的台阶位置
    //加载背景图片资源

    //判断台阶是否已通过整个界面,如果已通过，重新设置初始位置
    public void StageblockIsOut(StageBlock mStageblock){
        if (mStageblock.getX() < -screenWidth/3){
            mStageblock.setX(screenWidth+mRandom.nextInt(5));
            mStageblock.setY(screenHeight-StageBlock.getHEIGHT()-mRandom.nextInt(300));
        }
    }

    public GameView(Context context) {
        super(context);
        setFocusable(true);
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context,attrs);
        setFocusable(true);
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
    }

    //所有图像在此绘制
    public void mDraw() {
        //设置画布的颜色
        mCanvas.drawColor(Color.WHITE);
        //方块状态变化和检测
        if(mBox.isCrash(screenHeight)){
            mBox.setJumpSpeed(0);
        }
        mBox.bump(mStageblock);
        mBox.freeFall(mStageblock);

        drawBG(); // 绘制背景

        mBox.draw(mCanvas);//绘制主人公Box
        //绘制台阶方块
        for (StageBlock mStageblocks : mStageblock){
            mStageblocks.draw(mCanvas);
            StageblockIsOut(mStageblocks);//判断是否已经移除屏幕
        }
    }
    //画背景，起参照作用，之后可以删除，改成背景图
    public void drawBG() {
//        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.welcome_background_white);
//        Bitmap resizeBmp = Bitmap.createScaledBitmap(bmp, screenWidth, screenHeight, true);
//        mCanvas.drawBitmap(resizeBmp, 0, 0, null);
//        Paint mPaint = new Paint();
//        mPaint.setColor(Color.BLACK);
//        //设置透明度
//        mPaint.setAlpha(50);
//        //设置抗锯齿
//        mPaint.setAntiAlias(true);
//
//        float h = screenHeight * 0.01666667f;
//        for (int i = 0; i < screenHeight; i += h) {
//            mCanvas.drawLine(0, i, screenWidth, i, mPaint);
//        }
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
                mDraw();//绘图
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
    //只用来处理时间差
    private long start;
    private long end;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action){
            case ACTION_DOWN:
                start = System.currentTimeMillis();
                break;
            case ACTION_UP:
                end = System.currentTimeMillis();
                if((int)(end - start) >= 500){
                    mBox.setJumpSpeed(Box.HIGH_SPEED);
                }
                else{
                    mBox.setJumpSpeed(Box.LOW_SPEED);
                }
                mBox.Jump();
                break;
        }
        return true;
        //return super.onTouchEvent(event);
    }
}
