package com.biz.blog.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.biz.blog.model.BlogVO;
import com.biz.blog.service.BlogService;

@RequestMapping(value="/blog")
@Controller
public class BlogController {
	
	//@Service Annotation이 부착된 클래스를 주입 요청하는 코드
	@Autowired
	private BlogService bService;
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String home(Locale locale, Model model) {
		
		bService.selectAll();
		return "home";
	}
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public String list() {
		
		System.out.println("블로그 게시물 리스트 보기");
		
		return "home";
	}
	
	@RequestMapping(value="/input", method=RequestMethod.GET)
	public String input() {
		
		return "write";
	}
	
	/*
	 * @ModelAttribute
	 * form에서 input에 입력한 문자열이 전송되었을 때 input tag의 변수(name)를 분석하여 VO class의 필드변수와 일치하면 전달된 데이터(값)를 VO 객체에 담도록 요청
	 */
	@RequestMapping(value="/writer", method=RequestMethod.POST)
	public String writer(@ModelAttribute BlogVO blogVO, Model model) {
		System.out.println("USER : "+blogVO.getUser());
		System.out.println("TITLE : "+blogVO.getTitle());
		System.out.println("CONTENT : "+blogVO.getContent());
		
		bService.insert(blogVO);
		
		model.addAttribute("USER", blogVO.getUser());
		model.addAttribute("TITLE", blogVO.getTitle());
		model.addAttribute("CONTENT", blogVO.getContent());
		
		return "view";
	}

}