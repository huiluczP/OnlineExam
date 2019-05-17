package com.demo.onlineexam;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.demo.onlineexam.JSONelement.ExamContent;
import com.demo.onlineexam.JSONelement.Question;

import java.util.ArrayList;

public class UIUpdate {

    LayoutInflater inflater;
    //单选控件，方便之后处理
    private ArrayList<RadioGroup> answers;
    public UIUpdate(){ }
    public UIUpdate(LayoutInflater inflater){
        this.inflater=inflater;
    }

    //获取问题的数量并设置对应数量的问题布局（考虑返回radiogroup）
    public void update(LinearLayout layout,ExamContent content){
        ArrayList<Question> queslist=content.getQuestions();
        //选项集合，用来在最后判断回答的正误
        ArrayList<RadioGroup> answers=new ArrayList<>();
        for(int i=0;i<queslist.size();i++){
            int index=i+1;
            //获取布局
            View questionview=inflater.inflate(R.layout.questionlayout,null);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            //设置问题内容
            TextView title=(TextView) questionview.findViewById(R.id.questitle);
            title.setText(index+". "+queslist.get(i).getQuestiontitle());
            layout.addView(questionview,params);

            //设置选项内容
            LinearLayout answerlayout=(LinearLayout)questionview.findViewById(R.id.answerlayout);
            RadioGroup group=new RadioGroup(layout.getContext());//存放单选框组
            for(String answer:queslist.get(i).getAnswers()){
                RadioButton button=new RadioButton(group.getContext());
                button.setText(answer);
                group.addView(button);
            }
            answerlayout.addView(group,params);
            answers.add(group);
        }
    }

    //参数为正确答案
    public int CalculScore(String []trueanswer){
        int score=0;
        int num=answers.size();
        for(int i=0;i<num;i++){
            String tag="";
            RadioGroup radioGroup=(RadioGroup)answers.get(i);
            int count = radioGroup.getChildCount();
            for(int t = 0 ;t < count;t++) {
                RadioButton rb = (RadioButton) radioGroup.getChildAt(t);
                if (rb.isChecked()) {
                    tag=rb.getText().toString();
                }
            }
            Log.i("answer++",tag+"");
            if(tag.equals(trueanswer[i])){
                score+=(100/num);
            }
        }
        return score;
    }
}
