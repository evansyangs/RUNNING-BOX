package com.example.alu.runnnigbox;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * Created by 10988 on 2017/10/7.
 */

public class StageBlock {
    private int x;
    private int y;          //台阶坐标
    private static int WIDTH = 400;          // 宽
    private static int HEIGHT = 100;          // 高
    private Paint mPaint;       //绘制
    private static int RADIAN = 20; //圆角矩阵的弧度
    public static int MOVESPEED = 10;
    GameView gameView;

    public static void setWIDTH(int WIDTH) {
        StageBlock.WIDTH = WIDTH;
    }

    public static int getWIDTH() {
        return WIDTH;
    }

    public static void setHEIGHT(int HEIGHT) {
        StageBlock.HEIGHT = HEIGHT;
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
        mPaint.setColor(Color.DKGRAY);
        //设置画出的图形填充的类型,fill为内部填充,stroke为只有边框,内容不填充
        mPaint.setStrokeWidth(10);//设置边框的宽度. 如矩形的边宽, 文字的宽度. 接收实参为像素单位
        mPaint.setStyle(Paint.Style.STROKE);
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

    //绘制StageBlock
    public void draw(Canvas canvas){
        //此处添加move()函数
        RectF rectF = new RectF(x,y,x+WIDTH,y+HEIGHT);
        canvas.drawRoundRect(rectF,RADIAN,RADIAN,mPaint);//圆角矩形
        MoveToLeft();
    }

    //台阶向左移动的速度
    public void MoveToLeft(){
        this.x=x-MOVESPEED;
    }
}
