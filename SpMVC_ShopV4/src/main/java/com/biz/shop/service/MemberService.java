package com.biz.shop.service;

import com.biz.shop.model.MemberVO;

public interface MemberService extends GenericService<MemberVO, String> {

	MemberVO login(MemberVO loginVO);

}
