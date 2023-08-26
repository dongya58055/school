package com.entiy;

import lombok.Data;

@Data
public class Teacher {
	private Integer id;
    private String tno;
    private String name;
    private char gender;
    private String password;
    private String email;
    private String telephone;
    private String address;
    private String clazzName;
    private String portraitPath;//存储头像的项目路径
    private int isDeleted;
}
