package com.example.alu.runnnigbox;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by yang on 2017/10/5.
 */

public class Box {
    private int x;
    private int y;          //坐标
    public static int WIDTH = 200;          // 边长
    private static int GRAVITY = 10;        //重力加速度
    private int jumpSpeed = 0;              //跳跃初始速度
    private Paint mPaint;       //绘制
    GameView gameView;
    //初始化坐标
    public Box(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Box(GameView gameView, int x, int y){
        this.gameView = gameView;
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        this.x = x;
        this.y = y;
    }
    //跳跃动作
    public void Jump(){

    }
    //碰撞检测
    public boolean isCrash(){
        return false;
    }
    //Geter 函数
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    //设置跳跃速度
    public void setJumpSpeed(int jumpSpeed) {
        this.jumpSpeed = jumpSpeed;
    }

    void draw(Canvas canvas){
        //此处添加move()函数

        canvas.drawRect(x,y,x+WIDTH,y+WIDTH,mPaint);
    }
}
