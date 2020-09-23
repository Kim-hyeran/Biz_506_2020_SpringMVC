package com.biz.book.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.biz.book.model.BookVO;
import com.biz.book.service.NaverService;

import lombok.extern.slf4j.Slf4j;

/*
 * @RestController
 * view를 return response하지 않고 객체, 문자열 등을 직접 JSON 형태로 response하는 Controller
 */
@Slf4j
@RestController
@RequestMapping(value="/api")
public class BookApiController {

	@Autowired
	@Qualifier("naverServiceV2")
	private NaverService<BookVO> nService;
	
	// produces가 자동으로 설정되지 않는 경우(xml로 설정된 경우) 수동으로 직접 입력해주어야 한다.
	@RequestMapping(value="/isbn", method=RequestMethod.POST, produces="application/json;charset=utf8")
	public BookVO naverSearch(String search_text) {
		String queryURL=nService.queryURL("BOOK", search_text);
		/*
		 * getNaverList(query) method는 도서명, isbn으로 조회하여 도서 리스트를 return해주는 method이다.
		 * 도서명으로 검색하면 당연히 1개 이상의 데이터가 return되어 List형이 될 것이다.
		 * 만약 isbn으로 검색하면 isbn은 중복값이 없으므로 당연히 1개의 값만 return된다.
		 * 하지만 이 method는 태생이 List를 return하도록 만들어져있기 때문에 1개의 데이터라도 List<BookVO>에 담겨서 return된다.
		 * isbn으로 조회했을 때는 다른 데이터가 있더라도 무시하고 첫번째(0번) 데이터만 필요하므로 ...get(0)으로 첫번째 데이터만 추출한다.
		 */
		BookVO bookVO=nService.getNaverList(queryURL).get(0);
		
		log.debug("도서정보 : "+bookVO.toString());
		
		return bookVO;
	}
	
}
