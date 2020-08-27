package com.biz.shop.controller;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
/*
 * RestController
 *  표준 Controller는 return 문자열을 가지고 view파일을 읽고 rendering을 수행한 후 web client에 HTML 코드로 response를 수행
 *  그에 반해 RestController는 view 없이 데이터를 직접 web client에 전송할 때 사용하는 Controller
 *  표준 Controller에서는 특정 method를 지정하여 view가 아닌 데이터를 직접 web client로 전송할 때 method에 @ResponseBody를 설정
 *  RestController는 모든 method에 @ResponseBody가 설정된 것과 같은 효과
 *  
 * RequestMapping을 api로 시작하는 이유
 *  API(Application Programmerble Interface)는 서로 다른 서버 간, 서비스 간에 데이터를 주고 받는 방식을 일컫는 용어
 */

import com.biz.shop.service.ProductService;
@RestController
@RequestMapping(value="/api/product")
public class ProductRestController {
	
	@Autowired
	@Qualifier("proServiceV1")
	ProductService proService;
	
	@RequestMapping(value="/get_pcode", method=RequestMethod.GET)
	public String keyinput() {
		
		/*
		 * ProductService의 getPcode()를 호출하여 새로운 상품코드를 생성하여 받고 싶다
		 * 
		 * TDD(Test Driven Developer)
		 * 요구사항을 먼저 만들고 실제 구현되는 코드를 나중에 생성하는 방식
		 */
		String strPcode=proService.getPcode();
		
		return strPcode;
	}

}