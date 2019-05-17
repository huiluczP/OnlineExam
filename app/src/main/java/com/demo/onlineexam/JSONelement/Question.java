package com.demo.onlineexam.JSONelement;

import java.util.ArrayList;
import java.util.List;

public class Question {
    String questiontitle;
    String trueanswer;
    ArrayList<String> answers;
    public Question(){
        answers=new ArrayList<String>();
    }
    public ArrayList<String> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<String> answers) {
        this.answers = answers;
    }

    public String getQuestiontitle() {
        return questiontitle;
    }

    public void setQuestiontitle(String questiontitle) {
        this.questiontitle = questiontitle;
    }

    public String getTrueanswer() {
        return trueanswer;
    }

    public void setTrueanswer(String trueanswer) {
        this.trueanswer = trueanswer;
    }
}
