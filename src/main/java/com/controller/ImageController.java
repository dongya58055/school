package com.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.util.Result;

@RequestMapping("/upload")
@RestController
public class ImageController {
	@Value("${school.basepath}")
	private String path;
	@GetMapping("/{name}")
	public Result down(@PathVariable String name,HttpServletResponse response) throws IOException {
		//输入流 读取文件内容  对应路径加上前端传的文件名称
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(new File(path+name)));
		//输出流 将文件写到浏览器
		ServletOutputStream outputStream = response.getOutputStream();
		byte[] bytes = new byte[1024];
		int len=0;
		while ((len=bis.read(bytes))!=-1) {
			outputStream.write(bytes, 0, len);
			outputStream.flush();
		}
		bis.close();
		outputStream.close();
		return Result.ok();
		
	}
}
