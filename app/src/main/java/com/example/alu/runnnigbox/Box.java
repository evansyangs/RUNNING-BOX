package com.example.alu.runnnigbox;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

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
    public static final int LOW_SPEED = 25;
    private Boolean isDown = false;
    private static int RADIAN = 20; //圆角矩阵的弧度
    private Paint mPaint;       //绘制
    GameView gameView;
    private int sum = 0;
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
    //下部碰撞
    public StageBlock checkCrashStage(StageBlock[] stageBlocks){
        for(StageBlock stageBlock: stageBlocks) {
            if (x >= stageBlock.getX() - StageBlock.MOVESPEED
                    && x <= stageBlock.getX() + StageBlock.getWIDTH() - StageBlock.MOVESPEED
                    && y + WIDTH >= stageBlock.getY()
                    && y + WIDTH <= stageBlock.getY() + StageBlock.getHEIGHT()
                    || x + WIDTH >= stageBlock.getX() - StageBlock.MOVESPEED
                    && x + WIDTH <= stageBlock.getX() + StageBlock.getWIDTH() - StageBlock.MOVESPEED
                    && y + WIDTH >= stageBlock.getY()
                    && y + WIDTH <= stageBlock.getY() + StageBlock.getHEIGHT()) {
                return stageBlock;
            }
        }
        return null;
    }
    //这也是下部碰撞
    public boolean isCrash(StageBlock[] stageBlocks){
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
                return true;
            }
        }
        return false;
    }

    // 右边碰撞
    public void isRightCrash(StageBlock[] stageBlocks){
        for(StageBlock stageBlock: stageBlocks){
            if(stageBlock.getX() > 0) {
                if (x + WIDTH >= stageBlock.getX())
                    if(stageBlock.getY() > y
                        && stageBlock.getY() < y + WIDTH
                        || stageBlock.getY() +StageBlock.getHEIGHT() > y
                        && stageBlock.getY() +StageBlock.getHEIGHT() < y + WIDTH) {
                    x = stageBlock.getX() - WIDTH; //撞到后随方块退后
                    return;
                }
            }
        }
    }
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
        if(y >= screenHight + WIDTH){
            isDown = false;    //调试用 完善后删除
            return true;
        }
        else return false;
    }
    // 自由落体
    public void freeFall(StageBlock[] stageBlocks){
        boolean flag = true;
        sum ++;
        // x 坐标随时间缓慢向前
        if(sum  == 10){
            x++;
            sum = 0;
        }
        isRightCrash(stageBlocks);
        if(isCrash(stageBlocks)){
            isDown = false;
            flag = false;
        }
        if(flag){
            isDown = true;
        }
        if(isDown) {
            Jump();
            StageBlock stageBlock = checkCrashStage(stageBlocks);
            if(stageBlock != null){
                y = stageBlock.getY() - WIDTH;
            }
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

    void draw(Canvas canvas){
        RectF rectF = new RectF(x,y,x+WIDTH,y+WIDTH);
        canvas.drawRoundRect(rectF,RADIAN,RADIAN,mPaint);//圆角矩形
    }

    public Boolean getDown() {
        return isDown;
    }
}
