package com.example.alu.runnnigbox;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by 10988 on 2017/10/7.
 */

public class StageBlock {
    private int x;
    private int y;          //台阶坐标
    public static int WIDTH = 500;          // 宽
    public static int HEIGHT = 80;          // 高
    private Paint mPaint;       //绘制
    private static int MOVESPEED = 50;
    GameView gameView;
    //初始化坐标
    public StageBlock(int x, int y){
        this.x = x;
        this.y = y;
    }

    public StageBlock(GameView gameView, int x, int y){
        this.gameView = gameView;
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        this.x = x;
        this.y = y;
    }

    //Geter 函数
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void draw(Canvas canvas){
        //此处添加move()函数
        MoveToLeft();
        canvas.drawRect(x,y,x+WIDTH,y+HEIGHT,mPaint);
    }

    //台阶向左移动的速度
    public void MoveToLeft(){
        this.x=x-MOVESPEED;
    }
}
