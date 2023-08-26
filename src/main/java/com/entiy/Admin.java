package com.entiy;

import lombok.Data;

@Data
public class Admin {
	    private Integer id;
	    private String name;
	    private char gender;
	    private String password;
	    private String email;
	    private String telephone;
	    private String address;
	    private String portraitPath;// 头像的图片路径
	    private int isDeleted;
}
