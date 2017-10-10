package com.example.alu.runnnigbox;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface.OnClickListener;


/**
 * Created by 10988 on 2017/10/7.
 */

/*
 * 此为游戏主界面,按钮功能在此处完成
 */
public class GameActivity extends Activity {

    private GameView mView = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //此设定必须要写在setContentView之前，否则会有异常）
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏模式
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 设置无标题
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);        //强制为横屏

        setContentView(R.layout.activity_game_view);

        Button button =  this.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder builder = new Builder(GameActivity.this);
                builder.setTitle("提示");
                builder.setMessage("你确定要退出游戏吗");

                builder.setPositiveButton("重新开始", new OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

                        Intent intent = new Intent(GameActivity.this, GameActivity.class);
                        GameActivity.this.startActivity(intent);
                        GameActivity.this.finish();
                    }
                });

                builder.setNegativeButton("退出游戏", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent(GameActivity.this,StartMenu.class);
                        GameActivity.this.startActivity(intent);
                        GameActivity.this.finish();
                    }
                });

                AlertDialog b = builder.create();
                b.show();
            }
        });


        mView = (GameView) findViewById(R.id.gameView);//获取gameView资源


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
