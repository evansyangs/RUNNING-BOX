package com.example.alu.runnnigbox;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Debug;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.widget.Toast;

import java.util.Random;

import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_UP;
import android.util.Log;




/**
 * Created by 10988 on 2017/10/7.
 */

public class GameView extends SurfaceView implements Callback,Runnable{
    private GameActivity mGameActivity;
    private Random mRandom = new Random();//随机数
    private int mStageNumber = 0;
    private Canvas mCanvas;
    private boolean mGameState = false; //游戏状态
    private Thread mThread;    //刷新界面线程
    private SurfaceHolder mSurfaceHolder;    //界面处理句柄
    private boolean mIsRunning = false;

    private boolean mIsGameOver = false;
    private int TIME_IN_FRAME = 20;//刷新频率

    private DisplayMetrics dm = getResources().getDisplayMetrics();    //获取屏幕信息
    private int screenWidth = dm.widthPixels;    //横屏宽度
    private int screenHeight = dm.heightPixels;    //横屏高度

    private int jumpSoundId;
    private int jumphighSoundId;
    private SoundPool mSoundPool;
    private MediaPlayer mediaPlayer; //背景音乐播放

    private String[] Tips={"长摁屏幕试试看","游戏结束了记得点击屏幕哦","快戳！用力戳！","我打赌，屏幕戳穿了你都到不了100个"};

    public int getmStageNumber() {
        return mStageNumber;
    }

    public void setmGameActivity(GameActivity mGameActivity) {
        this.mGameActivity = mGameActivity;
    }


    public boolean returnstate(){
        return mIsGameOver;
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
            mStageNumber++;
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
            //此处调用游戏结束

            mGameState = false;//界面暂停
            mIsGameOver = true;//结束
        }
        mBox.bump(mStageblock);
        mBox.freeFall(mStageblock);

        mBox.draw(mCanvas);//绘制主人公Box
        //绘制台阶方块
        for (StageBlock mStageblocks : mStageblock){
            mStageblocks.draw(mCanvas);
            StageblockIsOut(mStageblocks);//判断是否已经移除屏幕
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        mIsRunning = true;
        StageBlock.setWIDTH(400);
        StageBlock.setHEIGHT(100);
        //新建一个刷屏线程
        mThread = new Thread(this);
        mThread.start();
        //游戏开始界面
        mCanvas = mSurfaceHolder.lockCanvas();
        mDraw();//绘图
        mSurfaceHolder.unlockCanvasAndPost(mCanvas);

        //播放音乐
        mSoundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        jumpSoundId = mSoundPool.load(getContext(), R.raw.jump, 1);
        jumphighSoundId = mSoundPool.load(getContext(), R.raw.jumphigh, 1);
        mediaPlayer = MediaPlayer.create(getContext(), R.raw.background);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder,  int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        //销毁
        mIsRunning = false;
        //停止播放Bgm
        if (null != mediaPlayer) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public void setmGameState(boolean mGameState) {
        this.mGameState = mGameState;
    }

    public void run() {
//决定线程是否继续执行

        while (mIsRunning) {
            long startTime = System.currentTimeMillis();
            while (mGameState) {
                //调用mDraw进行绘制
                synchronized (mSurfaceHolder) {
                    mCanvas = mSurfaceHolder.lockCanvas();
                    mDraw();//绘图
                    mSurfaceHolder.unlockCanvasAndPost(mCanvas);
                }
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
        mGameState = true;//点击时开始游戏
        switch (action){
            case ACTION_DOWN:
                start = System.currentTimeMillis();
                break;
            case ACTION_UP:
                end = System.currentTimeMillis();
                // 修复重复跳
                if(mBox.getDown()){
                    return false;
                }
                if((int)(end - start) >= 200){
                    mBox.setJumpSpeed(Box.HIGH_SPEED);
                    mSoundPool.play(jumphighSoundId, 1, 1, 1, 0, 1);
                }
                else{
                    mBox.setJumpSpeed(Box.LOW_SPEED);
                    mSoundPool.play(jumpSoundId, 1, 1, 1, 0, 1);

                }
                mBox.Jump();
                break;
        }


        int CASE = mStageNumber % 30;
        //显示提示与体积变化
        switch (CASE){
            case 6:
            case 29:
                mGameActivity.TipShow(Tips[0]);
                StageBlock.setWIDTH(StageBlock.getWIDTH()-4);
                StageBlock.setHEIGHT(StageBlock.getHEIGHT()-2);
                break;
            case 18:
            case 39:
                mGameActivity.TipShow(Tips[1]);
                StageBlock.setWIDTH(StageBlock.getWIDTH()-4);
                StageBlock.setHEIGHT(StageBlock.getHEIGHT()-2);
                break;
            case 10:
                StageBlock.setWIDTH(StageBlock.getWIDTH()-4);
                StageBlock.setHEIGHT(StageBlock.getHEIGHT()-2);
                break;
            case 28:
            case 14:
                mGameActivity.TipShow(Tips[2]);
                StageBlock.setWIDTH(StageBlock.getWIDTH()+4);
                StageBlock.setHEIGHT(StageBlock.getHEIGHT()+2);
                break;
            case 34:
            case 22:
                mGameActivity.TipShow(Tips[3]);
                StageBlock.setWIDTH(StageBlock.getWIDTH()+4);
                StageBlock.setHEIGHT(StageBlock.getHEIGHT()+2);
                break;
            default:
                mGameActivity.TipHide();
        }

        if (mIsGameOver) {
            mGameActivity.GameOver();
            mIsRunning = false;//停止onDraw()
        }


        return true;
        //return super.onTouchEvent(event);
    }
}
