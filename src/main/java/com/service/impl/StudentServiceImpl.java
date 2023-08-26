package com.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dto.LoginForm;
import com.entiy.Student;
import com.mapper.StudentMapper;
import com.service.StudentService;
import com.util.MD5;

@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService{

	
	@Override
	public Student login(LoginForm login) {
			//判断用户名、密码是否匹配
			LambdaQueryWrapper<Student> lqw = new LambdaQueryWrapper<>();
			lqw.eq(Student::getName, login.getUsername());
			//密码加密 因为数据库也是加密
			lqw.eq(Student::getPassword,MD5.encrypt(login.getPassword()));
			Student student = this.getOne(lqw);
			//baseMapper.selectOne(lqw);
			return student;
	}

}
