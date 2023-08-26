package com.controller;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.filefilter.OrFileFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.druid.support.logging.LogFactory;
import com.dto.LoginForm;
import com.entiy.Admin;
import com.entiy.Student;
import com.entiy.Teacher;
import com.service.AdminService;
import com.service.StudentService;
import com.service.TeacherService;
import com.util.CreateVerifiCodeImage;
import com.util.JwtHelper;
import com.util.Result;
import com.util.ResultCodeEnum;

@RestController
@RequestMapping("/sms/system")
public class SystemController {
	@Autowired
	AdminService adminService;
	@Autowired
	StudentService studentService;
	@Autowired
	TeacherService teacherService;

	// 验证码
	@GetMapping("/getVerifiCodeImage")
	public void getCodeImage(HttpSession session, HttpServletResponse response) throws IOException {
		// 获取图片
		BufferedImage bufferedImage = CreateVerifiCodeImage.getVerifiCodeImage();
		// 获取图片上的验证码
		char[] code = CreateVerifiCodeImage.getVerifiCode();
		// 转换类型
		String valueOf = String.valueOf(code);
		// 将验证码放在session中，为验证做准备
		// HttpSession session = request.getSession();
		session.setAttribute("code", valueOf);
		// 发送给前端
		// 获取请求的io流
		ImageIO.write(bufferedImage, "JPEG", response.getOutputStream());
	}

	// 页面登录验证
	@PostMapping("/login")
	public Result login(@RequestBody LoginForm login, HttpServletRequest request) {
		// 验证码校验
		// 保存的验证码
		HttpSession session = request.getSession();
		String sessionCode = (String) session.getAttribute("code");
		// 传递的验证码
		String code = login.getVerifiCode();
		// if(!sessionCode.equalsIgnoreCase(code)) {
		// equals是判断大小写的
		if (!sessionCode.equalsIgnoreCase(code)) {
			System.out.println(sessionCode);
			return Result.fail().message("验证码错误");
		} else if ("".equals(sessionCode) || sessionCode == null) {
			// 保存的session已经失效所以为空
			return Result.fail().message("验证码失效");
		}
		// 说明一致
		// 移除先有验证码
		session.removeAttribute("code");
		// 准备map存放用户响应的数据
		Map<String, String> map = new HashMap<>();
		// 用户类型校验
		switch (login.getUserType()) {
		case 1:
			// 查询数据库用户是否存在
			Admin admin = adminService.login(login);
			try {
				if (admin != null) {
					// 准备发送token
					String token = JwtHelper.createToken(admin.getId().longValue(), 1);
					map.put("token", token);
					return Result.ok(map);
				} else {
					throw new RuntimeException("用户名或密码错误");
				}
			} catch (RuntimeException e) {
				// TODO: handle exception
				e.printStackTrace();
				return Result.fail().message(e.getMessage());
			}
		case 2:
			// 查询数据库用户是否存在
			Student student = studentService.login(login);
			try {
				if (student != null) {
					// 准备发送token
					String token = JwtHelper.createToken(student.getId().longValue(), 2);
					map.put("token", token);
					return Result.ok(map);
				} else {
					throw new RuntimeException("用户名或密码错误");
				}
			} catch (RuntimeException e) {
				// TODO: handle exception
				e.printStackTrace();
				return Result.fail().message(e.getMessage());
			}
		case 3:
			// 查询数据库用户是否存在
			Teacher teacher = teacherService.login(login);
			try {
				if (teacher != null) {
					// 准备发送token
					String token = JwtHelper.createToken(teacher.getId().longValue(), 3);
					map.put("token", token);
					return Result.ok(map);
				} else {
					throw new RuntimeException("用户名或密码错误");
				}
			} catch (RuntimeException e) {
				// TODO: handle exception
				e.printStackTrace();
				return Result.fail().message(e.getMessage());
			}
		}
		return Result.fail().message("查无此人");
	}

	// 登录token
	@GetMapping("/getInfo")
	public Result getInof(@RequestHeader("token") String token) {
		// 校验token是否过期
		boolean expiration = JwtHelper.isExpiration(token);
		if (expiration) {
			// 如果过期
			return Result.build(null, ResultCodeEnum.TOKEN_ERROR);
		}
		// 从token中解析用户id和类型
		Long userId = JwtHelper.getUserId(token);
		Integer userType = JwtHelper.getUserType(token);
		Map<String, Object> map = new HashMap<>();
		switch (userType) {
		case 1:
			Admin admin = adminService.getById(userId);
			map.put("userType", 1);
			map.put("user", admin);
			break;
		case 2:
			Student student = studentService.getById(userId);
			map.put("userType", 2);
			map.put("user", student);
			break;
		case 3:
			Teacher teacher = teacherService.getById(userId);
			map.put("userType", 3);
			map.put("user", teacher);
			break;
		}
		return Result.ok(map);
	}

	// 文件上传和下载
	@Value("${school.basepath}")
	private String path;

	// 图片上传
	@PostMapping("/headerImgUpload")
	public Result upLoad(@RequestPart("multipartFile") MultipartFile file) throws IllegalStateException, IOException {
		// 更改图片名字
		// 生成新的名称
		String name = UUID.randomUUID().toString().replace("-", "").toLowerCase();
		String originalFilename = file.getOriginalFilename();
		// 获取后缀名
		String lastname = originalFilename.substring(originalFilename.lastIndexOf("."));
		// 文件新名称
		String fileName = name + lastname;
		// 自定义路径
		path = path + fileName;
		// 保存文件
		file.transferTo(new File(path));
		// 返回图片路径
		return Result.ok("upload/" + fileName);

	}
	// 图片下载
//	@GetMapping("/upload/{file}")
//	public Result down(@PathVariable String name,HttpServletResponse response) throws IOException {
//		//输入流 读取文件内容  对应路径加上前端传的文件名称
//		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(new File(path+name)));
//		//输出流 将文件写到浏览器
//		ServletOutputStream outputStream = response.getOutputStream();
//		byte[] bytes = new byte[1024];
//		int len=0;
//		while ((len=bis.read(bytes))!=-1) {
//			outputStream.write(bytes, 0, len);
//			outputStream.flush();
//		}
//		bis.close();
//		outputStream.close();
//		return Result.ok();
//		
//	}
}
