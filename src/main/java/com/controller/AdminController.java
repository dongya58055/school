package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.entiy.Admin;
import com.service.AdminService;
import com.util.MD5;
import com.util.Result;

@RestController
@RequestMapping("/sms/adminController")
public class AdminController {
	
	@Autowired
	AdminService as;
	//修改或删除
	@PostMapping("/saveOrUpdateAdmin")
	public Result update(@RequestBody Admin admin) {
		Admin id = as.getById(admin.getId());
		if(id==null) {
			//新增
			admin.setPassword(MD5.encrypt(admin.getPassword()));
		}
		as.saveOrUpdate(admin);
		return Result.ok();
		
	}
}
