package com.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.entiy.Grade;
import com.mapper.GradeMapper;
import com.service.GradeService;

@Service
public class GradeServiceImpl extends ServiceImpl<GradeMapper, Grade> implements GradeService{

}
