package com.entiy;

import lombok.Data;

@Data
public class Student {
	private Integer id;
    private String sno;
    private String name;
    private char gender = '男';//default
    private String password;
    private String email;
    private String telephone;
    private String address;
    private String introducation;
    private String portraitPath;//存储头像的项目路径
    private String clazzName;//班级名称
    private int isDeleted;
}
