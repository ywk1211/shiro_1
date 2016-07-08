package com.atguigu.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ShiroHandler {
	
	@Autowired
	private MyService myService;
	
	@RequestMapping("/test")
	public String test(){
		myService.test();
		return "success";
	}
	
	@RequestMapping("/shiro-login")
	public String login(@RequestParam("username") String username,
						@RequestParam("password") String password){
		
		//获取当前的User，调用了SecurityUtils.getSubject();
		Subject currentUser = SecurityUtils.getSubject();
		
		//检测用户是否被认证
		if(!currentUser.isAuthenticated()){
			UsernamePasswordToken token = new UsernamePasswordToken(username, password);
			
			token.setRememberMe(true);
			
			try {
				//执行登陆，调用Subject的login(UsernamePasswordToken)方法
				currentUser.login(token);
			} catch (AuthenticationException ae) {
				System.out.println("登录失败:"+ae.getMessage());
				return "redirect:/login.jsp";
			}
			
		}
		
		return "success";
	}
}

