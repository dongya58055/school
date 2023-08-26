package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.entiy.Grade;
import com.service.GradeService;
import com.util.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;


@Api("年级控制")
@RestController
@RequestMapping("/sms/gradeController")
public class GradeController {

	@Autowired
	private GradeService gs;
	//分页
	@GetMapping("/getGrades/{pageNo}/{pageSize}")
	public Result getGrades(@PathVariable("pageNo") Integer pageNo, @PathVariable("pageSize") Integer pageSize,String gradeName) {
		//分页查询
		Page<Grade> page = new Page<>(pageNo,pageSize);
		LambdaQueryWrapper<Grade> lqw = new LambdaQueryWrapper<>();
		//lqw.eq(gradeName!=null, Grade::getName, gradeName);
		lqw.like(StringUtils.isNotBlank(gradeName), Grade::getName, gradeName);
		lqw.orderByDesc(Grade::getId);
		//lqw.select(Grade::getName).select(Grade::getId);
		
		Page<Grade> page2 = gs.page(page, lqw);
		//封装对象返回
		return Result.ok(page2);
	}
	//修改、保存
	@PostMapping("/saveOrUpdateGrade")
	public Result save(@RequestBody Grade grade) {
		gs.saveOrUpdate(grade);
		return Result.ok();
	}
	//删除
	@ApiOperation("删除信息") 
	@DeleteMapping("/deleteGrade")
	public Result delete(@ApiParam("要删除的id组信息") @RequestBody List<Integer> ids) {
		gs.removeByIds(ids);
		return Result.ok();
	}
	//查询所有年级,给班级做准备
	@GetMapping("/getGrades")
	public Result getGrages() {
		List<Grade> list = gs.list();
		return Result.ok(list);
		
	}
}
