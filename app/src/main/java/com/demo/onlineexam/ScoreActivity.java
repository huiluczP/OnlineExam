package com.demo.onlineexam;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

//成绩显示页面
public class ScoreActivity extends Activity {

    private TextView scoreshow;
    private TextView scoreresult;
    private Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_layout);
        scoreshow=(TextView)findViewById(R.id.scoreshow);
        scoreresult=(TextView)findViewById(R.id.result);
        back=(Button)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ScoreActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        initScore();
    }

    private void initScore(){
        Intent intent=getIntent();
        //获取成绩，默认为0
        int score=intent.getIntExtra("score",0);
        boolean timeout=intent.getBooleanExtra("timeout",false);
        if(!timeout) {
            if (score < 80) {
                scoreshow.setText("获得的分数为: "+score);
                scoreresult.setTextColor(Color.RED);
                scoreresult.setText("检定结果: 不合格");
            } else {
                scoreshow.setText("获得的分数为: "+score);
                scoreresult.setTextColor(Color.GREEN);
                scoreresult.setText("检定结果: 合格");
            }
        }
        else{
            scoreresult.setTextColor(Color.RED);
            scoreshow.setText("获得的分数为: "+0);
            scoreresult.setText("答题超时");
        }
    }

}
