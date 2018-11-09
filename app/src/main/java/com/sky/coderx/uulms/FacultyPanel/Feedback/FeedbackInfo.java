package com.sky.coderx.uulms.FacultyPanel.Feedback;

public class FeedbackInfo {
    private String email,msg;

    public FeedbackInfo() {
    }

    public FeedbackInfo(String email, String msg) {
        this.email = email;
        this.msg = msg;
    }

    public String getEmail() {
        return email;
    }

    public String getMsg() {
        return msg;
    }
}
