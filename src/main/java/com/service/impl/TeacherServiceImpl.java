package com.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dto.LoginForm;
import com.entiy.Student;
import com.entiy.Teacher;
import com.mapper.TeacherMappper;
import com.service.TeacherService;
import com.util.MD5;

@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMappper, Teacher> implements TeacherService{

	@Override
	public Teacher login(LoginForm login) {
		//判断用户名、密码是否匹配
		LambdaQueryWrapper<Teacher> lqw = new LambdaQueryWrapper<>();
		lqw.eq(Teacher::getName, login.getUsername());
		//密码加密 因为数据库也是加密
		lqw.eq(Teacher::getPassword,MD5.encrypt(login.getPassword()));
		Teacher teacher = this.getOne(lqw);
		//baseMapper.selectOne(lqw);
		return teacher;
	}

}
