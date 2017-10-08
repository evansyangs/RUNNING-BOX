package com.example.alu.runnnigbox;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.RequiresApi;

/**
 * Created by yang on 2017/10/5.
 */

public class Box {
    private int x;
    private int y;          //坐标
    private static int WIDTH = 100;          // 边长
    private static int GRAVITY = 1;        //重力加速度
    private int jumpSpeed = 0;              //跳跃初始速度
    public static final int HIGH_SPEED = 30;
    public static final int LOW_SPEED = 20;
    private Boolean isDown = true;
    private static int RADIAN = 20; //圆角矩阵的弧度
    private Paint mPaint;       //绘制
    GameView gameView;
    public static int getWIDTH() {
        return WIDTH;
    }

    //初始化坐标
    public Box(int x, int y){

        this.x = x;
        this.y = y;
    }

    public Box(GameView gameView, int x, int y){
        this.gameView = gameView;
        mPaint = new Paint();
        mPaint.setColor(Color.GRAY);
        //设置画出的图形填充的类型,fill为内部填充,stroke为只有边框,内容不填充
        mPaint.setStrokeWidth(10);//设置边框的宽度. 如矩形的边宽, 文字的宽度. 接收实参为像素单位
        mPaint.setStyle(Paint.Style.STROKE);
        this.x = x;
        this.y = y;
    }
    //跳跃动作
    public void Jump(){
            y -= jumpSpeed;
        jumpSpeed -= GRAVITY;
    }
    //左右碰撞检测 用的时候再拿出来
//    public boolean isCrash(StageBlock stageBlock){
//        if(x >= stageBlock.getX() - StageBlock.MOVESPEED
//                && x <= stageBlock.getX() + StageBlock.getWIDTH() - StageBlock.MOVESPEED
//                && y + WIDTH >= stageBlock.getY()
//                && y + WIDTH <= stageBlock.getY() + StageBlock.getHEIGHT()){
//            return true;
//        }
//        else if(x + WIDTH >= stageBlock.getX() - StageBlock.MOVESPEED
//                && x + WIDTH <= stageBlock.getX() + StageBlock.getWIDTH() - StageBlock.MOVESPEED
//                && y + WIDTH >= stageBlock.getY()
//                && y + WIDTH <= stageBlock.getY() + StageBlock.getHEIGHT()){
//            return true;
//        }
//        else {
//            return false;
//        }
//    }
    //上部碰撞反弹
    public void bump(StageBlock[] stageBlocks){

        for(StageBlock stageBlock : stageBlocks){
        if(x >= stageBlock.getX() - StageBlock.MOVESPEED
                && x <= stageBlock.getX() + StageBlock.getWIDTH() - StageBlock.MOVESPEED
                && y >= stageBlock.getY()
                && y <= stageBlock.getY() + StageBlock.getHEIGHT()
                ){
            jumpSpeed = -jumpSpeed;
        }
        else if(x + WIDTH >= stageBlock.getX() - StageBlock.MOVESPEED
                && x + WIDTH<= stageBlock.getX() + StageBlock.getWIDTH() - StageBlock.MOVESPEED
                && y >= stageBlock.getY()
                && y <= stageBlock.getY() + StageBlock.getHEIGHT()){
            jumpSpeed = -jumpSpeed;
        }
        }
    }
    //接触底部检测
    public boolean isCrash(int screenHight){
        if(y+WIDTH >= screenHight){
            return true;
        }
        else return false;
    }
    // 自由落体
    public void freeFall(StageBlock[] stageBlocks){
        boolean flag = true;
        for(StageBlock stageBlock: stageBlocks) {
            if (x >= stageBlock.getX() - StageBlock.MOVESPEED
                    && x <= stageBlock.getX() + StageBlock.getWIDTH() - StageBlock.MOVESPEED
                    && y + WIDTH >= stageBlock.getY()
                    && y + WIDTH <= stageBlock.getY() + StageBlock.getHEIGHT()
                    || x + WIDTH >= stageBlock.getX() - StageBlock.MOVESPEED
                    && x + WIDTH <= stageBlock.getX() + StageBlock.getWIDTH() - StageBlock.MOVESPEED
                    && y + WIDTH >= stageBlock.getY()
                    && y + WIDTH <= stageBlock.getY() + StageBlock.getHEIGHT()) {
                y = stageBlock.getY() - WIDTH;
                isDown = false;
                flag = false;
            }
        }
        if(flag){
            isDown = true;
        }
        if(isDown) {
            Jump();
        }
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
        isDown = true;
        this.jumpSpeed = jumpSpeed;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    void draw(Canvas canvas){
        RectF rectF = new RectF(x,y,x+WIDTH,y+WIDTH);
        canvas.drawRoundRect(rectF,RADIAN,RADIAN,mPaint);//圆角矩形
        //canvas.drawRect(x,y,x+WIDTH,y+WIDTH,mPaint);//绘制主人公Box
    }
}
