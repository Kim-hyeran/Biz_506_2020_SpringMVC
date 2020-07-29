package com.biz.hello;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/*
 * Commnet : 코드가 실행되는 데 아무런 영향을 미치지 않는 설명문
 * @ : Annotation, 주석(Comment와는 다른 의미)
 * 		의미가 부여된 특별한 주석
 * 		Java1.5부터 사용할 수 있는 주석
 * 		실제로 interface, Class, method 등으로 구성된 모듈을 한두 개의 키워드로 현재 소스코드에 부착시키는(include) 키워드
 * 		메타데이터
 * 		사용 가능한 키워드가 정해져있고, 이 키워드를 사용하기 위해서는 어딘가에 이 키워드로 실행(호출) 가능한 class, method가 존재해야 한다.
 */
@Controller
public class HomeController {
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	/*
	 * 전통적인 MVC Controller
	 * jsp로 만들어진 view에 값을 return하는 형식
	 * view가 존재하지 않을 경우 에러 발생
	 */
	@RequestMapping(value = "/korea", method = RequestMethod.GET)
	public String home2(Locale locale, Model model) {
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", "Republic of Korea");
		
		return "home";
	}
	
	@RequestMapping(value = "/name", method = RequestMethod.GET)
	public String name(Locale locale, Model model) {
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", "Republic of Korea");
		
		return "name";
	}
	
}