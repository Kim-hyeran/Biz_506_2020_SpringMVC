package com.biz.shop.service;

import java.util.List;

import com.biz.shop.model.ProductVO;
/*
 * ProductService 인터페이스는 GenericService 인터페이스를 상속(extends)함으로써 CRUD 기본 method를 별도로 선언하지 않아도 된다.
 * 추가가 필요한 method가 있으면 별도로 선언할 수 있다.
 * 이 인터페이스를 implements한 클래스는 인터페이스의 영향을 받아 method를 구현하게 된다.
 */
public interface ProductService extends GenericService<ProductVO, String> {
	
	//Generic에는 없지만 Product를 구현하는 데 필요한 method가 있으면 별도로 선언
	public List<ProductVO> findByTitle(String title);
	
}
