package com.biz.book.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.biz.book.mapper.BookDao;
import com.biz.book.model.BookVO;
import com.biz.book.model.ReadBookVO;

import lombok.extern.slf4j.Slf4j;

/*
 * @Transactional Annotation을 클래스 차원에서 설정하면 현재 클래스의 모든 method에서 DB와 연동되는 부분에 Transactional이 자동으로 작동된다.
 */
@Transactional
@SessionAttributes("bookVO")
@Slf4j
@Controller
@RequestMapping(value="/books")
public class BooksController {
	
	@Autowired
	private BookDao bookDao;
	
	@ModelAttribute("bookVO")
	public BookVO newBookVO() {
		LocalDate localDate=LocalDate.now();
		String todayString=DateTimeFormatter.ofPattern("yyyy-MM-dd").format(localDate);
		
		BookVO bookVO=BookVO.builder().buydate(todayString).buyprice(10000).build();
		
		return bookVO;
	}
	
	/*
	 * SqlSessionTemplate 대신 DataSourceTransactionManager를 mybatis-context.xml에 만들어주고,
	 * appServlet 폴더에 tx-context.xml을 만들어 <tx:annotaion-driven /> 설정
	 * transaction이 필요한 method에 @Transactional Annotation을 설정해주면 기본 정책으로 Transaction이 자동 수행된다.
	 * 
	 */
	
	// localhost:8080/book/books
	// localhost:8080/book/books/
	// @ResponseBody
	@RequestMapping(value={"/", ""}, method=RequestMethod.GET)
	public String list(Model model) {
		List<BookVO> bookList=bookDao.selectAll();
		
		model.addAttribute("BOOKS", bookList);
		model.addAttribute("BODY", "BOOK-LIST");
		
		return "home";
	}
	
	@RequestMapping(value="/input", method=RequestMethod.GET)
	public String input(@ModelAttribute("bookVO") BookVO bookVO, Model model) {
  	   return "home";
		
		// Controller의 Mapping method의 return type이 String일 때 null 값을 return하면 method를 호출할 때 사용했던 mapping URL.jsp형식의 return문이 자동으로 생성된다.
		// return null;
	}
	
	// spring form taglib를 사용하여 write form을 생성한 경우 VO 클래스나 객체를 매개변수로 사용할 때 @ModelAttribute("VO")를 필수 사용
	@RequestMapping(value="/input", method=RequestMethod.POST)
	public String input(@ModelAttribute("bookVO") BookVO bookVO, SessionStatus status) {
		log.debug(bookVO.toString());
		
		int ret=bookDao.insert(bookVO);
		if(ret<1) {
			// insert가 실패했으므로 그에 대한 메시지를 보여주는 페이지로 이동
		}
		
		/*
		 * SessionAttributes를 Controller에 설정했을 경우 입력박스에 담긴 값을 POST로 받아 DB에 반영한 후에는
		 * 반드시 SessionStatus.setComplete() method를 호출해서 session을 clear해주어야 한다.
		 * 그렇지 않으면 한 번 입력한 내용이 계속해서 입력창에 출력된다.
		 */
		status.setComplete();
		
		return "redirect:/books";
	}
	
	// localhost:8080/book/books/detail/(숫자)이라고 Request가 오면 맨 마지막 숫자를 Mapping 주소의 {book_seq} 위치에 Mapping
	// 매개변수에 설정된 PathVariable에 따라 String id 변수에 숫자 값이 할당되어 method에 전달
	@RequestMapping(value="/detail/{book_seq}", method=RequestMethod.GET, produces="application/json;charset=utf8")
	public String detail(@PathVariable("book_seq") String id, Model model) {
		log.debug("PATH : {}", id);

		long seq=Long.valueOf(id);
		BookVO bookVO=bookDao.findById(seq);
		model.addAttribute("BOOKVO", bookVO);
		
		LocalDateTime lDatetime=LocalDateTime.now();
		String lDate=DateTimeFormatter.ofPattern("yyyy-MM-dd").format(lDatetime);
		String lTime=DateTimeFormatter.ofPattern("HH:mm").format(lDatetime);
		
		ReadBookVO readBookVO=ReadBookVO.builder().r_date(lDate).r_stime(lTime).build();
		
		model.addAttribute("readBookVO", readBookVO);
		
		model.addAttribute("BODY", "BOOK-DETAIL");
		
		return "home";
	}
	
	@RequestMapping(value="/delete/{seq}", method=RequestMethod.GET)
	public String delete(@PathVariable("seq") String seq, @ModelAttribute("bookVO") BookVO bookVO) {
		long id=Long.valueOf(seq);		
		bookDao.delete(id);
		
		return "redirect:/books";
	}
	
	@RequestMapping(value="/update/{seq}", method=RequestMethod.GET)
	public String update(@PathVariable("seq") String seq, @ModelAttribute("bookVO") BookVO bookVO, Model model) {
		long id=Long.valueOf(seq);		
		bookVO=bookDao.findById(id);
		
		model.addAttribute("bookVO", bookVO);
		model.addAttribute("BODY", "BOOK-WRITE");
		
		return "home";
	}
	
	@RequestMapping(value="/update/{seq}", method=RequestMethod.POST)
	public String update(@PathVariable("seq") String seq, @ModelAttribute("bookVO") BookVO bookVO, Model model, SessionStatus status) {
		bookDao.update(bookVO);
		
		status.setComplete();
		
		return "redirect:/books/detail/"+seq;
	}

}
