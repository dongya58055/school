package com.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dto.LoginForm;
import com.entiy.Admin;
import com.mapper.AdminMapper;
import com.service.AdminService;
import com.util.MD5;

@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService{

//	@Autowired
//	AdminService as;
	@Override
	public Admin login(LoginForm login) {
		//判断用户名、密码是否匹配
		LambdaQueryWrapper<Admin> lqw = new LambdaQueryWrapper<>();
		lqw.eq(Admin::getName, login.getUsername());
		//密码加密 因为数据库也是加密
		lqw.eq(Admin::getPassword,MD5.encrypt(login.getPassword()));
		Admin admin = this.getOne(lqw);
		//Admin admin = baseMapper.selectOne(lqw);
		return admin;
	}

}
