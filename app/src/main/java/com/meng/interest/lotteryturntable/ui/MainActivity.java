package com.meng.interest.lotteryturntable.ui;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.meng.interest.lotteryturntable.R;
import com.meng.interest.lotteryturntable.view.LuckSpan;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mIvStart;
    private LuckSpan  mLuckSpan;
    private boolean   mIsClickStart; //默认为false避免还没点击开始转动就会提示

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

    }

    private void initViews() {
        mLuckSpan = (LuckSpan) findViewById(R.id.ls_lucky);
        mIvStart = (ImageView) findViewById(R.id.iv_start);

        mIvStart.setOnClickListener(this);

        mLuckSpan.setOnSpanRollListener(new LuckSpan.SpanRollListener() {
            @Override
            public void onSpanRollListener(double speed) {
                if (0 == speed) {
                    //已经停止下来了 提示中奖名并释放按钮
                    mIvStart.setEnabled(true);
                    if (mIsClickStart) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "恭喜你中奖了", Toast.LENGTH_SHORT).show();
                                mIsClickStart = false;
                            }
                        });
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_start:
                mIvStart.setEnabled(false);
                mIsClickStart = true;
                //传入的参数又后台返回指定中哪个奖项
                mLuckSpan.luckyStart(0);
                //模拟请求网络
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //网络请求6秒
                        SystemClock.sleep(5000);
                        //逐渐停止转盘
                        mLuckSpan.luckStop();
                    }
                }).start();
                break;
        }
    }
}
