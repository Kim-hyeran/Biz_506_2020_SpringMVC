package com.biz.bbs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.biz.bbs.model.EmployeesVO;
import com.biz.bbs.service.EmployeesService;

@RestController
public class EmployeesController {
	
	@Autowired
	@Qualifier("empServiceV1")
	private EmployeesService empService;
	
	@RequestMapping(value="/list", method=RequestMethod.GET, produces="application/json;charset=utf8")
	public List<EmployeesVO> list() {
		
		List<EmployeesVO> empList = empService.selectAll();
		
		return empList;
	}

}
