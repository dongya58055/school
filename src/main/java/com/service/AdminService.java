package com.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dto.LoginForm;
import com.entiy.Admin;

public interface AdminService extends IService<Admin>{

	Admin login(LoginForm login);

}
