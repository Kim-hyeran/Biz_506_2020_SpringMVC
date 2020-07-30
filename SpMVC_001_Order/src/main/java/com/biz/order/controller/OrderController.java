package com.biz.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.biz.order.model.OrderVO;
import com.biz.order.service.OrderService;

@Controller
public class OrderController {
	/*
	 * OrderService interface를 사용하여 oService를 선언하고,
	 * 이미 생성되어 Container에 보관 중인 OrderServiceImplV1 객체를 oService에 주입(Dependency Inject)
	 * 	-> oService 객체를 통하여 method를 호출할 준비가 된다.
	 */
	@Autowired
	private OrderService oService;
	
	@RequestMapping(value="/order")
	public String getOrder(Model model) {
		
		//서비스에 seq(1)을 전달하고, 데이터 record를 select한 결과를 받아서 orderVO에 담기
		OrderVO orderVO=oService.findBySeq(1);
		
		//view(*.jsp)파일에 전달하여 Rendering을 수행할 수 있도록 moedel 객체에 orderVO 데이터를 추가 해놓는다.
		model.addAttribute("ORDER", orderVO);
		
		/*
		 * Dispatcher는 /WEB-INF/view/order/view.jsp 파일을 읽어서 클라이언트(요청한 곳)으로 응답 명령
		 * 이 때 model에 담겨 있는 데이터를 사용하여 Rendering할 때 사용하도록 함
		 */
		return "order/view";
		
	}
	
}