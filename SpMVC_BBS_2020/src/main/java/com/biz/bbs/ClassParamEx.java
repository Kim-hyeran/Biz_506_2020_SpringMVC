package com.biz.bbs;

import com.biz.bbs.model.BbsVO;

public class ClassParamEx {
	
	public static void main(String[] args) {
		
		BbsVO bbsVO = new BbsVO();
		long b_seq=99;
		
		b_seq(b_seq);
		
		bbsVO.setB_seq(b_seq);
		System.out.println(bbsVO.getB_seq());
		b_seq(bbsVO);
		System.out.println(bbsVO.getB_seq());
		
	}
	
	private static void b_seq(long b_seq) {
		b_seq=999;
	}
	
	private static void b_seq(BbsVO vo) {
//		vo=new BbsVO();
		vo.setB_seq(1000);
	}

}
