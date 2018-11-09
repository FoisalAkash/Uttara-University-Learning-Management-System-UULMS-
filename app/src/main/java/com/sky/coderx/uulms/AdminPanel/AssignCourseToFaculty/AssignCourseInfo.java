package com.sky.coderx.uulms.AdminPanel.AssignCourseToFaculty;

public class AssignCourseInfo {
    private String courseName,batchNumber,selectedfaculty,faculty_id;

    public AssignCourseInfo() {

    }

    public AssignCourseInfo(String courseName, String batchNumber, String selectedfaculty, String faculty_id) {
        this.courseName = courseName;
        this.batchNumber = batchNumber;
        this.selectedfaculty = selectedfaculty;
        this.faculty_id = faculty_id;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public String getSelectedfaculty() {
        return selectedfaculty;
    }

    public String getFaculty_id() {
        return faculty_id;
    }
}
