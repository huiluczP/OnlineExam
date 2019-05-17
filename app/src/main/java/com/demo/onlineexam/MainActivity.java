package com.demo.onlineexam;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.onlineexam.HttpRequest.HttpUtil;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.Timer;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private static String url="http://10.0.2.2:8081/androiddata/examcontent.json";
    private Button start;
    private TextView showtext;

    OkHttpClient client=null;
    Request request=null;
    HttpUtil httputil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start=(Button)findViewById(R.id.startbtn);
        showtext=(TextView)findViewById(R.id.showtext);
        start.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                getExaminfo();
            }
        });
    }

    private class ExamCallback implements Callback{

        //访问失败，handle搞个toast出来
        @Override
        public void onFailure(Call call, IOException e) {
            handler.post(new errorshow());
        }

        //访问成功，跳转到考试页面，把response的信息传过去
        @Override
        public void onResponse(Call call, Response response) throws IOException {
            /* String JSONstr=response.body().string();
            runOnUiThread(new transformUI(JSONstr));*/

           Intent intent=new Intent(MainActivity.this,ExamActivity.class);
            intent.putExtra("examcontent",response.body().string());
            startActivity(intent);
        }
    }

    //包括http响应信息的获取线程调用以及ui变化的线程调用
    private void getExaminfo(){
        httputil.sendExamRequest(url,new ExamCallback());
    }

    /*
    //修改界面，显示考题
    class transformUI extends Thread{
        String JSONstr;
        transformUI(){}
        transformUI(String JSONstr){
            this.JSONstr=JSONstr;
        }
        @Override
        public void run() {
            showtext.setText(JSONstr);
        }
    }*/

    private static Handler handler=new Handler();

    //网络访问错误，输出提示信息
    class errorshow extends Thread{
        @Override
        public void run() {
            Toast.makeText(MainActivity.this,"网络连接错误",Toast.LENGTH_SHORT).show();
        }
    }
}
