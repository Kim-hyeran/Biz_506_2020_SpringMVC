package com.biz.shop.persistence;

import java.util.List;

import com.biz.shop.model.ProductVO;

public interface GenericDao<VO,PK> {
	
	public List<VO> selectAll();
	public ProductVO findById(PK id); //ID=Primary Key 개념으로 생성하는 method
	public int insert(VO vo);
	public int update(VO vo);
	public int delete(PK id);

}
