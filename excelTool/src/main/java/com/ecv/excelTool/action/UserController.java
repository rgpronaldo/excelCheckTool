package com.ecv.excelTool.action;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ecv.excelTool.dto.UserDto;
import com.ecv.excelTool.service.IUserService;
import com.ecv.excelTool.util.excelEmaiCheck;

@Controller
@RequestMapping("/user")
public class UserController {
	@Resource
	private IUserService userService;
	@RequestMapping("/showUser")
	public String toIndex(HttpServletRequest request,Model model){
		int userId = Integer.parseInt(request.getParameter("id"));
		UserDto user = this.userService.getUserById(userId);
		model.addAttribute("user", user);
		return "showUser";
	}
	@RequestMapping(value="/add", method=RequestMethod.POST)  
	public String add(@RequestParam MultipartFile myfile, HttpServletRequest request,Model model) throws Exception{
		if(myfile.isEmpty()){  
            System.out.println("文件未上传");  
        }else{  
            System.out.println("文件长度: " + myfile.getSize());  
            System.out.println("文件类型: " + myfile.getContentType());  
            System.out.println("文件名称: " + myfile.getName());  
            System.out.println("文件原名: " + myfile.getOriginalFilename());  
            System.out.println("========================================");  
            //如果用的是Tomcat服务器，则文件会上传到\\%TOMCAT_HOME%\\webapps\\YourWebProject\\WEB-INF\\upload\\文件夹中  
            String realPath = request.getSession().getServletContext().getRealPath("/WEB-INF/upload"); 
            File file = new File(realPath, myfile.getOriginalFilename());
            FileUtils.copyInputStreamToFile(myfile.getInputStream(), file);  
            String emailColInt = request.getParameter("emailColInt");
           List<String>  list = excelEmaiCheck.readExcel(file, emailColInt);
           model.addAttribute("result", list);
            //这里不必处理IO流关闭的问题，因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉，我是看它的源码才知道的  
        }  
		 return "result";
		
	}
	@RequestMapping("/importExcel")
	public String importExcel(){
		
		 return "importExcel";
		
	}

}
