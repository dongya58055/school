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
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.entiy.Clazz;
import com.entiy.Grade;
import com.service.ClazzService;
import com.util.Result;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/sms/clazzController")
public class CLazzController {

	@Autowired
	private ClazzService cs;

	@GetMapping("getClazzsByOpr/{pageNo}/{pageSize}")
	public Result page(@PathVariable("pageNo") Integer pageNo, @PathVariable("pageSize") Integer pageSize,
			String gradeName, String name) {
		// 分页查询
		Page<Clazz> page = new Page<>(pageNo, pageSize);
		LambdaQueryWrapper<Clazz> lqw = new LambdaQueryWrapper<>();
		// lqw.eq(gradeName!=null, Grade::getName, gradeName);
		lqw.like(StringUtils.isNotBlank(gradeName), Clazz::getGradeName, gradeName);
		lqw.like(StringUtils.isNotBlank(name), Clazz::getName, name);
		lqw.orderByDesc(Clazz::getId);
		Page<Clazz> page2 = cs.page(page, lqw);
		// 封装对象返回
		return Result.ok(page2);

	}

	// 修改、保存
	@PostMapping("/saveOrUpdateClazz")
	public Result save(@RequestBody Clazz clazz) {
		cs.saveOrUpdate(clazz);
		return Result.ok();
	}

	// 删除
	@ApiOperation("删除信息")
	@DeleteMapping("/deleteClazz")
	public Result delete(@ApiParam("要删除的id组信息") @RequestBody List<Integer> ids) {
		cs.removeByIds(ids);
		return Result.ok();
	}

	// 查询所有班级,给学生做准备
	@GetMapping("/getClazzs")
	public Result getClazz() {
		List<Clazz> list = cs.list();
		return Result.ok(list);

	}
}
