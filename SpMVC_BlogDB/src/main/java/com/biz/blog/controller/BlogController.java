package com.biz.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.biz.blog.model.BlogVO;
import com.biz.blog.service.BlogService;

import lombok.extern.slf4j.Slf4j;

@Slf4j //lombok을 사용하여 slf4와 logback을 연동하고 log를 사용할 수 있도록 설정
@RequestMapping(value="/blog")
@Controller
public class BlogController {
	
	//@Autowired Annotation이 부착된 클래스 주입 요청
	@Autowired
	private BlogService bService;
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public String list(Model model) {
		List<BlogVO> blogList=bService.selectAll();
		//System.out.println(blogList.get(0).getBl_title());
		model.addAttribute("BLOGS", blogList);
		
		return "list";
	}
	
	@RequestMapping(value="/getblog", method=RequestMethod.GET)
	public String getBlog(Model model) {
		
		System.out.println("블로그 게시물 리스트 보기");
		
		List<BlogVO> blogList=bService.selectAll();
		
		if(blogList!=null) {
			int size=blogList.size();
			model.addAttribute("TITLE", blogList.get(size-1).getBl_title());
			model.addAttribute("CONTENT", blogList.get(size-1).getBl_contents());
			model.addAttribute("USER", blogList.get(size-1).getBl_user());
		} else {
			model.addAttribute("TITLE", "데이터 없음");
		}
		
		return "view";
	}
	
	@RequestMapping(value="/input", method=RequestMethod.GET)
	public String input() {
		
		return "writer";
	}
	
	@RequestMapping(value="/writer", method=RequestMethod.POST)
	public String writer(@ModelAttribute BlogVO blogVO, Model model) {
		/*
		 * Debugging Code : 어떤 값을 확인하는 용도
		 * form에서 건너온 데이터가 정확히 VO에 담기는지 확인하기 위하여 사용하는 코드
		 * 이 코드는 실제 프로젝트 수행과는 아무 관련이 없음
		 */
		log.debug("USER : "+blogVO.getBl_user());
		log.debug("TITLE : "+blogVO.getBl_title());
		log.debug("CONTENT : "+blogVO.getBl_contents());
		
//		bService.insert(blogVO);
		
//		model.addAttribute("TITLE", blogVO.getTitle());
//		model.addAttribute("CONTENT", blogVO.getContent());
//		model.addAttribute("USER", blogVO.getUser());
		
		return "redirect:/blog/list";
	}

}