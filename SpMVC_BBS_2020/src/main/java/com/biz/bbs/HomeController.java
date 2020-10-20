package com.biz.bbs;

import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		/*
		 * Controller의 method에서 home 문자열을 return하면
		 * 1. tiles-layout.xml에서 definitions 항목에 home으로 선언된 tag가 있는지 검사
		 * 2. 만약 있으면 template으로 선언된 *.jsp(layout.jsp)파일 찾기
		 * 3. layout.jsp에 tiles:insertAttribute로 설정된 항목 찾기
		 * 4. tiles-layout.xml의 put-attribute로 선언된 이름 비교
		 * 5. 일치하는 이름이 있으면 value로 선언된 *.jsp 파일 가져오기
		 * 6. layout.jsp 파일과 합성하여 rendering 준비
		 * 7. 만약 home으로 선언된 항목이 tiles-layout.xml에 존재하지 않을 경우
		 * 8. 원래 설계된 순서대로 InternalViewResolver가 작동되어 /WEB-INF/view 폴더에서 home.jsp 파일을 찾아 rendering
		 */
		
		return "home";
	}
	
}
