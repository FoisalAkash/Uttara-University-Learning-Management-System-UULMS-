package com.sky.coderx.uulms.AdminPanel.AddFaculty;

public class FacultyInfo {

        private String name,email,address,dept,username,password,degree,mobile_number,user_id;

    public FacultyInfo(){
        //required
    }

    public FacultyInfo(String name, String email, String address, String dept, String username, String password, String degree, String mobile_number, String user_id) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.dept = dept;
        this.username = username;
        this.password = password;
        this.degree = degree;
        this.mobile_number = mobile_number;
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getDept() {
        return dept;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDegree() {
        return degree;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public String getUser_id() {
        return user_id;
    }
}


