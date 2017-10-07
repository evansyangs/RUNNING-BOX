package com.example.alu.runnnigbox;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by 10988 on 2017/10/7.
 */

public class StartStage {
    private int x;
    private int y;          //开始台阶的坐标
    private static int WIDTH = 500;          // 宽
    private static int HEIGHT = 1000;          // 高保证延伸到屏幕下面
    private Paint mPaint;       //绘制
    private static int MOVESPEED = 10;
    GameView gameView;
    //初始化坐标
    public StartStage(GameView gameView, int x, int y){
        this.gameView = gameView;
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        this.x = x;
        this.y = y;
    }

    public void draw(Canvas canvas){
        //此处添加move()函数
        MoveToLeft();
        canvas.drawRect(x,y,x+WIDTH,y+HEIGHT,mPaint);
    }

    //当点击屏幕跳跃时开始台阶向左移动
    public void MoveToLeft(){
//        if() {
//            this.x = x - MOVESPEED;
//        }
    }

}
