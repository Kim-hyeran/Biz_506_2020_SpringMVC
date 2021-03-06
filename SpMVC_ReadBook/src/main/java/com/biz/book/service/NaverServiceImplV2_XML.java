package com.biz.book.service;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.biz.book.config.NaverSecret;
import com.biz.book.model.Book_JSON_parent;
import com.biz.book.model.BookVO;
import com.biz.book.model.Book_XML_Parent;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("naverServiceV2_XML")
public class NaverServiceImplV2_XML extends NaverServiceImplV1 {
	
	public String queryURL(String category, String bookName) {
		String queryURL=NaverSecret.NAVER_BOOK_XML;

		// 한글 검색을 위해서 검색어를 UTF-8로 encoding 처리해주어야 한다.
		String encodeText=null;
		try {
			encodeText=URLEncoder.encode(bookName.trim(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// url?query=자바
		// queryURL+=String.format("?query=%s", bookName);
		queryURL += String.format("?query=%s", encodeText);
		
		// 한번에 조회할 데이터 개수를 지정할 수 있다.
		queryURL+="&display=10";
		
		return queryURL;
	}
	
	// NaverServiceV1의 3개 method를 모두 상속받아서 여기에 아무런 코드가 없기 때문에 프로젝트가 정상 수행된다.
	
	// 3개의 method 중에서 getNaverList(jsonString) method만 upgrade
	@Override
	public List<BookVO> getNaverList(String queryURL) {
		
		// queryURL(요청주소포함) 문자열을 URI 객체로 만들어 http 프로토콜에서 사용할 수 있도록 만드는 클래스
		// 기존의 URL 클래스가 존재하나 새로운 기능을 수행하기 위해서 별도로 URI 클래스를 만들어 놓았으며, 많은 기능들이 추가되어 있다.
		// RestTemplate를 사용하기 위해서는 queryURL 문자열을 URL 객체가 아니라 URI를 사용하여야 한다.
		URI restURI=null;
		
		/*
		 * spring framework에서 외부 API를 사용하여 데이터를 가져올 때
		 * 기존에는 JSON(XML)형식으로 가져오고, 여러가지 외부 라이브러리를 사용하여 객체로 parsing하는 과정을 복잡하게 설정하여야 했다.
		 * spring에서 외부 API를 사용하여 데이터를 가져올 일이 점점 많아지면서, RestTemplate라는 클래스를 새로 만들어 framework 프로젝트에서 사용할 수 있도록 제공하였다.
		 * HttpHeader, HttpEntity, ResponseEntity 객체만 잘 작성하면 외부 API에 요청하고 응답받은 데이터를 parsing하는 절차를 대신 수행해준다.
		 */
		RestTemplate restTemp=new RestTemplate();
		
		try {
			restURI=new URI(queryURL);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		HttpHeaders headers=new HttpHeaders();
		headers.set("X-Naver-Client-Id", NaverSecret.NAVER_CLIENT_ID);
		headers.set("X-Naver-Client-Secret", NaverSecret.NAVER_CLIENT_SECRET);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_XML));
		
		HttpEntity<String> entity=new HttpEntity<String>("parameter", headers);
		
		ResponseEntity<Book_XML_Parent> bookList=null;
		bookList=restTemp.exchange(restURI, HttpMethod.GET, entity, Book_XML_Parent.class);
		
		log.debug(bookList.toString());
		
		return bookList.getBody().channel.item;
	}

}
