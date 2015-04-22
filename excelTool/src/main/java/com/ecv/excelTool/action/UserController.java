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
            System.out.println("�ļ�δ�ϴ�");  
        }else{  
            System.out.println("�ļ�����: " + myfile.getSize());  
            System.out.println("�ļ�����: " + myfile.getContentType());  
            System.out.println("�ļ�����: " + myfile.getName());  
            System.out.println("�ļ�ԭ��: " + myfile.getOriginalFilename());  
            System.out.println("========================================");  
            //����õ���Tomcat�����������ļ����ϴ���\\%TOMCAT_HOME%\\webapps\\YourWebProject\\WEB-INF\\upload\\�ļ�����  
            String realPath = request.getSession().getServletContext().getRealPath("/WEB-INF/upload"); 
            File file = new File(realPath, myfile.getOriginalFilename());
            FileUtils.copyInputStreamToFile(myfile.getInputStream(), file);  
            String emailColInt = request.getParameter("emailColInt");
           List<String>  list = excelEmaiCheck.readExcel(file, emailColInt);
           model.addAttribute("result", list);
            //���ﲻ�ش���IO���رյ����⣬��ΪFileUtils.copyInputStreamToFile()�����ڲ����Զ����õ���IO���ص������ǿ�����Դ���֪����  
        }  
		 return "result";
		
	}
	@RequestMapping("/importExcel")
	public String importExcel(){
		
		 return "importExcel";
		
	}

}
