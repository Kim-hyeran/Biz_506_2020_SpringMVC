package com.biz.book.mapper;

import java.util.List;

import com.biz.book.model.AuthorityVO;
import com.biz.book.model.UserDetailsVO;

public interface AuthorityDao extends GenericDao<UserDetailsVO, Long>{
	
	public int insertAll(List<AuthorityVO> authList);
	public List<AuthorityVO> findByUserName(String username);

}
