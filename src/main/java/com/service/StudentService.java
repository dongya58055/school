package com.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dto.LoginForm;
import com.entiy.Student;

public interface StudentService extends IService<Student>{

	Student login(LoginForm login);

}
