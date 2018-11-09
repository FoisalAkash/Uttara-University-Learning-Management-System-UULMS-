package com.sky.coderx.uulms.AdminPanel.AddCourse;

public class CourseInfo {

        private String courseName,courseCredit;

    public CourseInfo(){
        //required
    }
    public CourseInfo(String courseName, String courseCredit) {
        this.courseName = courseName;
        this.courseCredit = courseCredit;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseCredit() {
        return courseCredit;
    }

}


