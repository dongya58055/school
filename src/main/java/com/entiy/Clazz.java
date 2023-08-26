package com.entiy;

import lombok.Data;

@Data
public class Clazz {
	private Integer id;             //班级Id
    private String name;            //班级名称
    private String number;          //班级人数
    private String introducation;   //班级介绍
    //班主任信息
    private String headmaster;      //班主任姓名
    private String telephone;       //班主任电话
    private String email;           //班主任邮箱
    //所属年级
    private String gradeName;      //班级所属年级
    private int isDeleted;
}
