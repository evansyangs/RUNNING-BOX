package com.example.alu.runnnigbox;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by 10988 on 2017/10/7.
 */

/*
 * 此为游戏主界面
 */
public class GameActivity extends Activity {
    GameView mView;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏模式
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 设置无标题

        mView = new GameView(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);        //强制为横屏

        setContentView(mView);
    }
}
