package com.biz.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.biz.blog.model.BlogVO;
import com.biz.blog.service.BlogService;

import lombok.extern.slf4j.Slf4j;

@Slf4j // lombok을 사용하여 slf4와 logback을 연동하고 log를 사용할 수 있도록 설정
@RequestMapping(value = "/blog")
@Controller
public class BlogController {

	// @Autowired Annotation이 부착된 클래스 주입 요청
	@Autowired
	@Qualifier("bServiceV2")
	private BlogService bService;

	// http://localhost:8080/blog/blog/list 주소로 request 했을 때 응답할 함수
	// method=RequestMethod.GET : request를 할 때 <a href=주소>를 클릭했을 때 응답하는 method
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model) {
		List<BlogVO> blogList = bService.selectAll();
		// System.out.println(blogList.get(0).getBl_title());
		model.addAttribute("BLOGS", blogList);

		return "list";
	}

	@RequestMapping(value = "/getblog", method = RequestMethod.GET)
	public String getBlog(Model model) {

		System.out.println("블로그 게시물 리스트 보기");

		List<BlogVO> blogList = bService.selectAll();

		if (blogList != null) {
			int size = blogList.size();
			model.addAttribute("TITLE", blogList.get(size - 1).getBl_title());
			model.addAttribute("CONTENT", blogList.get(size - 1).getBl_contents());
			model.addAttribute("USER", blogList.get(size - 1).getBl_user());
		} else {
			model.addAttribute("TITLE", "데이터 없음");
		}

		return "view";
	}

	@RequestMapping(value = "/input", method = RequestMethod.GET)
	public String input(Model model) {
		//writer.jsp에서 input POST로 데이터를 전달할 때 비어있는("") 데이터 때문에 발생하는 400 오류를 방지하기 위해 공백의 새로운 BlogVO를 만들어서 writer.jsp로 전송
		model.addAttribute("BLOG", new BlogVO());
		
		return "writer";
	}

	@RequestMapping(value = "/input", method = RequestMethod.POST)
	public String writer(@ModelAttribute BlogVO blogVO, Model model) {
		/*
		 * Debugging Code : 어떤 값을 확인하는 용도 form에서 건너온 데이터가 정확히 VO에 담기는지 확인하기 위하여 사용하는 코드
		 * 이 코드는 실제 프로젝트 수행과는 아무 관련이 없음
		 */
		log.debug("USER : " + blogVO.getBl_user());
		log.debug("TITLE : " + blogVO.getBl_title());
		log.debug("CONTENT : " + blogVO.getBl_contents());

		bService.insert(blogVO);

//		model.addAttribute("TITLE", blogVO.getTitle());
//		model.addAttribute("CONTENT", blogVO.getContent());
//		model.addAttribute("USER", blogVO.getUser());

		return "redirect:/blog/list";
	}

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String view(String seq, Model model) {
		log.debug("SEQ {}", seq);

		// SQL Injection 공격을 사전에 차단하기 위해 Controller에서 SEQ 값을 문자열형에서 Long형으로 변경하는 코드 추가

		long long_seq = 0;
		try {
			long_seq = Long.valueOf(seq);
		} catch (Exception e) {
			model.addAttribute("ERROR", seq + " 형식의 query 금지");
			return "view_error";
		}
		BlogVO blogVO = bService.findBySeq(long_seq);
		model.addAttribute("BLOG", blogVO);

		return "view";
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(String seq) {
		long long_seq = 0;
		try {
			long_seq = Long.valueOf(seq);
		} catch (Exception e) {
			return "view_error";
		}

		bService.delete(long_seq);
		return "redirect:/blog/list";
	}

	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String update(String seq, Model model) {
		long long_seq = 0;
		try {
			long_seq = Long.valueOf(seq);
		} catch (Exception e) {
			return "view_error";
		}

		// update할 데이터를 select 해오기
		BlogVO blogVO = bService.findBySeq(long_seq);

		// update할 데이터를 model에 담기
		model.addAttribute("BLOG", blogVO);

		// 입력폼 화면 열기
		return "writer";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(BlogVO blogVO, Model model) {
		log.debug("UPDATE POST Method");
		log.debug(blogVO.toString());

		bService.update(blogVO);
		// 수정이 완료되면 다시 detail view로 화면 전환하기
		model.addAttribute("seq", blogVO.getBl_seq());

		return "redirect:/blog/view";
		// return "redirect:/blog/view?seq="+blogVO.getBl_seq();
	}

}