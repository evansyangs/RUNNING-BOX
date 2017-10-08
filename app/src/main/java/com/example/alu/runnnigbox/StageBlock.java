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
    private static int WIDTH = 500;          // 宽
    private static int HEIGHT = 50;          // 高
    private Paint mPaint;       //绘制
    public static int MOVESPEED = 10;
    GameView gameView;

    public static int getWIDTH() {
        return WIDTH;
    }

    public static int getHEIGHT() {

        return HEIGHT;
    }

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

    //Seter 函数
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }


    public void draw(Canvas canvas){
        //此处添加move()函数
        canvas.drawRect(x,y,x+WIDTH,y+HEIGHT,mPaint);
        MoveToLeft();
    }

    //台阶向左移动的速度
    public void MoveToLeft(){
        this.x=x-MOVESPEED;
    }

}
