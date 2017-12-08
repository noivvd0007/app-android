package com.vuvannoi.ndapptracnghiem.Cauhoi.model;

import java.io.Serializable;

/**
 * Created by Admin on 9/6/2017.
 */

public class question implements Serializable {
    private String id;
    private String question;
    private String ansA;
    private String ansB;
    private String ansC;
    private String ansD;
    private String result;
    private String num_exam;
    private String traloi = "";
    public int choiceID = -1; //hỗ trợ check Id của radiogroup

    public question(String id, String question, String ansA, String ansB, String ansC, String ansD, String result, String num_exam, String traloi) {
        this.id = id;
        this.question = question;
        this.ansA = ansA;
        this.ansB = ansB;
        this.ansC = ansC;
        this.ansD = ansD;
        this.result = result;
        this.num_exam = num_exam;
        this.traloi = traloi;
    }

    public question() {
    }




    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnsA() {
        return ansA;
    }

    public void setAnsA(String ansA) {
        this.ansA = ansA;
    }

    public String getAnsB() {
        return ansB;
    }

    public void setAnsB(String ansB) {
        this.ansB = ansB;
    }

    public String getAnsC() {
        return ansC;
    }

    public void setAnsC(String ansC) {
        this.ansC = ansC;
    }

    public String getAnsD() {
        return ansD;
    }

    public void setAnsD(String ansD) {
        this.ansD = ansD;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getNum_exam() {
        return num_exam;
    }

    public void setNum_exam(String num_exam) {
        this.num_exam = num_exam;
    }

    public String getTraloi() {
        return traloi;
    }

    public void setTraloi(String traloi) {
        this.traloi = traloi;
    }

}