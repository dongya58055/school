package com.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dto.LoginForm;
import com.entiy.Teacher;

public interface TeacherService extends IService<Teacher>{

	Teacher login(LoginForm login);

}
