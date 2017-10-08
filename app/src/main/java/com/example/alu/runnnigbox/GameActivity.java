package com.example.alu.runnnigbox;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
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

    /*
     * 开启沉浸模式
     */
   @Override
   public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
           View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                           | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                           | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                           | View.SYSTEM_UI_FLAG_FULLSCREEN
                           | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

}
