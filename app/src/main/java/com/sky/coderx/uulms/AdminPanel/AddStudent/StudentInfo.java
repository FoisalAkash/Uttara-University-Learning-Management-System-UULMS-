package com.sky.coderx.uulms.AdminPanel.AddStudent;

public class StudentInfo {

        private String id,name,batch,dept,username,password,email,mobile_number,user_id;

        public StudentInfo(){
            //required
        }

    public StudentInfo(String id, String name, String batch, String dept, String username, String password,String email, String mobile_number) {
        this.id = id;
        this.name = name;
        this.batch = batch;
        this.dept = dept;
        this.username = username;
        this.password = password;
        this.email = email;
        this.mobile_number = mobile_number;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getBatch() {
        return batch;
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

    public String getMobile_number() {
        return mobile_number;
    }

    /*public String getUser_id() {
        return user_id;
    }*/
}


