package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.entiy.Clazz;
import com.entiy.Student;
import com.service.ClazzService;
import com.service.StudentService;
import com.util.MD5;
import com.util.Result;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/sms/studentController")
public class StudentController {
	@Autowired
	private StudentService ss;

	@GetMapping("getStudentByOpr/{pageNo}/{pageSize}")
	public Result page(@PathVariable("pageNo") Integer pageNo, @PathVariable("pageSize") Integer pageSize,
			String name,String clazzName) {
		// 分页查询
		Page<Student> page = new Page<>(pageNo, pageSize);
		LambdaQueryWrapper<Student> lqw = new LambdaQueryWrapper<>();
		// lqw.eq(gradeName!=null, Grade::getName, gradeName);
		lqw.like(StringUtils.isNotBlank(clazzName), Student::getClazzName, clazzName);
		lqw.like(StringUtils.isNotBlank(name), Student::getName,name);
		lqw.orderByDesc(Student::getId);
		Page<Student> page2 = ss.page(page, lqw);
		// 封装对象返回
		return Result.ok(page2);

	}

	// 修改、保存
	@PostMapping("/addOrUpdateStudent")
	public Result save(@RequestBody Student student) {
		//学生如果是新增就要转成密文 如果是修改就不需要
		//判断当前学生是否在数据库中
		Student id = ss.getById(student.getId());
		if (id==null) {
			//说明是新增
			student.setPassword(MD5.encrypt(student.getPassword()));
		}
		ss.saveOrUpdate(student);
		return Result.ok();
	}

	// 删除
	@ApiOperation("删除信息")
	@DeleteMapping("/delStudentById")
	public Result delete(@ApiParam("要删除的id组信息") @RequestBody List<Integer> ids) {
		ss.removeByIds(ids);
		return Result.ok();
	}
}
