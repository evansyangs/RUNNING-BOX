package com.example.alu.runnnigbox;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;



/**
 * Created by 10988 on 2017/10/7.
 */

/*
 * 此为游戏主界面,按钮功能在此处完成
 */
public class GameActivity extends Activity {

    private GameView mView = null;
    private RelativeLayout mGameOverLayout = null;
    private TextView mGameGrade = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //此设定必须要写在setContentView之前，否则会有异常）
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏模式
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 设置无标题
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);        //强制为横屏

        setContentView(R.layout.activity_game_view);

        mView = (GameView) findViewById(R.id.gameView);//获取gameView资源
        mView.mGameActivity = this;
        mGameOverLayout = (RelativeLayout)  findViewById(R.id.game_over);
        mGameGrade = (TextView) findViewById(R.id.game_grade) ;

        Button button =  this.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CustomDialog();//构建自定义Dialog
            }
        });

//        while (mView.ismIsGameOver()){
//            mGameOverLayout.setVisibility(View.VISIBLE);
//            String str = "哇！你总共跳过了"+mView.getmStageNumber()+"块台阶!";
//            mGameGrade.setText(str);
//        }

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

    //自定义的Dialog
    public void CustomDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
        builder.setTitle("提示");
        builder.setMessage("你确定要退出游戏吗");

        mView.setmGameState(false);

        builder.setNeutralButton("重新开始", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                Intent intent = new Intent(GameActivity.this, GameActivity.class);
                GameActivity.this.startActivity(intent);
                GameActivity.this.finish();
            }
        });

        builder.setPositiveButton("退出游戏", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                Intent intent = new Intent(GameActivity.this,StartMenu.class);
                GameActivity.this.startActivity(intent);
                GameActivity.this.finish();
            }
        });

        builder.setNegativeButton("继续游戏", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface mDialog, int arg1) {

            }
        });

        AlertDialog b = builder.create();
        b.show();
        //点击dialog之外的空白处，dialog不能消失
        b.setCanceledOnTouchOutside(false);
    }

    public void GameOver(){
        mGameOverLayout.setVisibility(View.VISIBLE);
        String str = "哇！你总共跳过了"+mView.getmStageNumber()+"块台阶!";
        mGameGrade.setText(str);
    }

}
