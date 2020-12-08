package com.biz.data.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.biz.data.model.StationList;
import com.biz.data.service.BisServiceImplV1;

@Controller
@RequestMapping(value="/bis")
public class BisController {
	
	@Autowired
	private BisServiceImplV1 bisService;
	
	@ResponseBody
	@RequestMapping(value="/station", method=RequestMethod.GET)
	public StationList station() {
		
		return bisService.getStation();
	}

}
