package com.demo.onlineexam;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.onlineexam.JSONelement.ExamContent;
import com.demo.onlineexam.JSONelement.JSONChange;
import com.demo.onlineexam.JSONelement.Question;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;

//专门activity显示考试题目
public class ExamActivity extends Activity {

    String JSONstr=null;
    LinearLayout contentlayout;
    UIUpdate updater;
    String[] trueanswer;

    ExamContent content=null;
    Button send;
    private int timeremain=300;
    private TextView time;

    timethread timethread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exam_layout);
        contentlayout=(LinearLayout)findViewById(R.id.contentlayout);
        send=(Button)findViewById(R.id.send);

        LayoutInflater inflater=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        updater=new UIUpdate(inflater);

        //intent中获取json数据
        CharSequence temple=getIntent().getStringExtra("examcontent");
        Log.i("json+++++++",temple.toString());
        JSONstr=temple.toString();
        initExamContent();
        initSend();
        time=(TextView)findViewById(R.id.time);

        timethread=new timethread();
        //每秒运行一次线程修改ui
        handler.postDelayed(timethread,1000);
    }

    private void initExamContent(){
        try {
            content= (ExamContent) JSONChange.jsonToObj(new ExamContent(),JSONstr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        runOnUiThread(new contentthread());
    }

    //该线程进行ui动态生成
    private class contentthread extends Thread{
        @Override
        public void run() {
            if(content==null){
                Log.i("json+++++++","咋回事妹题目啊");
            }
            else{
                //获取正确答案
                ArrayList<Question> questions=content.getQuestions();
                int num=questions.size();
                trueanswer=new String[num];
                for(int i=0;i<num;i++){
                    trueanswer[i]=questions.get(i).getTrueanswer();
                }
                for(int i=0;i<num;i++){
                    Log.i("true+++",trueanswer[i]+"");
                }
                updater.update(contentlayout,content);
            }
        }
    }

    private void initSend(){
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timethread.over=true;
                timethread.interrupt();
                int result=updater.CalculScore(trueanswer);
                Log.i("result+++++++",result+"");
                Intent intent=new Intent(ExamActivity.this,ScoreActivity.class);
                intent.putExtra("score",result);
                startActivity(intent);
            }
        });
    }

    private Handler handler=new Handler();
    class timethread extends Thread{
        public boolean over=false;
        @Override
        public void run() {
            synchronized (this){
                timeremain--;
                String show="测试会在"+timeremain+"秒之后关闭";
                time.setText(show);
                if(timeremain<=0){
                    Intent intent=new Intent(ExamActivity.this,ScoreActivity.class);
                    intent.putExtra("timeout",true);
                    startActivity(intent);
                }
                if(timeremain>0&&!over)
                    handler.postDelayed(this,1000);
            }
        }
    }

}
