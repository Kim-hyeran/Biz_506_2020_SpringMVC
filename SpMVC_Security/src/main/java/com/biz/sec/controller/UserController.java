package com.biz.sec.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/user")
public class UserController {
	
	@RequestMapping(value="/join", method=RequestMethod.GET)
	public String join() {
		
		// return "users/join";
		return "user/join";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login() {
		
		return "users/login";
	}
	
	@RequestMapping(value="/mypage", method=RequestMethod.GET)
	public String mypage() {
		
		// InternalView를 사용한 rendering
		// return "users/mypage";		internalView를 사용한 rendering
		// return "mypage";		tiles의 mypage definition을 찾아 rendering
		return "user/mypage";	// tiles의 user/*를 찾고, * 대신 mypage 문자열을 전달하여 rendering
		/*
		 * 1. tiles-layout.xml에서 user/mypage 설정값 찾아보기
		 * 2. tiles-layout.xml에서 user/* 설정값 찾아보기
		 *  - 1 또는 2에서 해당 설정을 찾게 되면 template으로 설정된 layout.jsp를 열기
		 *  - attribute로 설정된 jsp 파일들을 loading하여 layout.jsp에 설정된 대로 layout 만들기
		 *  - HTML로 rendering한 후 response
		 * 3. /WEB-INF/views/user/mypage.jsp 파일 찾기
		 *  - 파일이 있으면 internalView에게 보내 rendering을 수행한 후 response
		 * 위의 모든 경우에 해당하지 않는 경우 404 오류를 response
		 */
	}

}
