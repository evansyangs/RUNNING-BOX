package com.example.alu.runnnigbox;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by 10988 on 2017/10/7.
 */

public class Ball {
    float r, x, y;
    private Paint mPaint;
    // 最大垂直速度 方向向上 -16.
    float MAXVERTICALSPEED = -16, MAXVERTICALA = 1;
    // 默认最大高度
    float defaultJumpHight;
    // 小球在垂直方向上需要移动的距离！
    float verticalMove;
    float ha = 0, va = 1f, v0v, vtv, v0h, vth;
    GameView gameView;

    public Ball(GameView gameView) {
        this.gameView = gameView;
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        defaultJumpHight = gameView.height / 3;
        r = gameView.width / 36;
        y = gameView.height - r * 2;
        x = gameView.width / 2 - r;
        //根据加速度公式计算得出Vt^2-V0^2=2AX
        MAXVERTICALSPEED = -(int) ((float) Math.sqrt(2 * gameView.height / 3) - 1);
        verticalMove = defaultJumpHight;
        v0v = MAXVERTICALSPEED;
    }

    public void draw(Canvas canvas) {
        move();
        canvas.drawCircle(x, y, r, mPaint);
    }

    public void move() {
        // Vt=V0+aT 当前速度=初始速度+加速度*时间
        vtv = v0v + va;
        // 当下降速度达到一定程度时，设置加速度为0.4f。
        if (vtv > -5 * MAXVERTICALSPEED / 8) {
            va = 0.4f;
        } else {
            va = MAXVERTICALA;
        }
        // 当当前还需上升的高度 大于默认高度时，速度继续保持最大速度，vtv<0表示方向向上
        if (verticalMove > defaultJumpHight && vtv < 0) {
            vtv = MAXVERTICALSPEED;
        }
        float vMove = (v0v + vtv) / 2; // 这一次垂直高度移动距离。
        verticalMove = verticalMove + vMove;// 减小时，表示网上移动了
        y = y + vMove;
        v0v = vtv;
        if (y > gameView.height) {// 触地了
            y = gameView.height - this.r;
            v0v = MAXVERTICALSPEED;
            verticalMove = defaultJumpHight;
        }

/*        //水平移动设置
        if(gameView.leftKeyDown==true){
            xx=x-10;
        }
        if(gameView.rightKeyDown==true){
            xx=x+10;
        }
        if (x < r) {
            x = r;
        }
        if (x > gameView.width - r) {
            x = gameView.width - r;
        }*/
    }
}
