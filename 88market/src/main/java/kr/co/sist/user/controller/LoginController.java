package kr.co.sist.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import jakarta.servlet.http.HttpSession;

@SessionAttributes({"name","age"})
@Controller
public class LoginController {
	
	@GetMapping("/session_getvalue")
	public String sessionGetValue(HttpSession session) {
		//세션 값 얻기
		String name=(String)session.getAttribute("name");
		Integer sessionAge=(Integer)session.getAttribute("age");
		
		int age=0;
		if(sessionAge != null) {
			age=sessionAge.intValue();
		}
		System.out.println("이름 " + name +" ,  나이 " + age);
		
		return "day0623/session_list";
	}//sessionGetValue
	
	@GetMapping("/model_getvalue")
	public String modelGetValue(Model model) {
		//Spring 5.2 이상에서만 가능하다.
		String name=(String)model.getAttribute("name");

		Integer modelAge=(Integer)model.getAttribute("age");
		
		int age=0;
		if(modelAge != null) {
			age=modelAge.intValue();
		}//end if
		
		System.out.println("Model 이름 " + name +" , Model  나이 " + age);
		
		return "day0623/session_list";
	} //modelGetValue

} //class
