package com.example.alu.runnnigbox;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by 10988 on 2017/10/7.
 */

public class GameActivity extends Activity {
    GameView mView;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);// 设置无标题
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏模式
        //获取系统的屏幕属性
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        mView = new GameView(this, dm.widthPixels, dm.heightPixels);
        //应用这个布局
        setContentView(mView);
    }
}
