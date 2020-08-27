package com.biz.shop.service;

import java.util.List;

import com.biz.shop.model.ProductVO;

/**
 * GenericService interface
 * @param <VO>
 * @param <PK>
 * 
 * DB와 관련된 프로젝트를 수행할 때 Service 클래스를 만들기 앞서 Service 인터페이스 생성을 권장
 * 다수의 table이 존재하는 프로젝트일 경우 비슷한(같은) 이름의 method를 중복하여 선언해야하는 경우 다수
 * 	→ 이런 경우 Service 인터페이스를 만들기 우한 상위 인터페이스를 선언하고 인터페이스에 Generic<VO,PK>을 선언
 * 이 인터페이스를 상속받아서 실제 사용할 인터페이스를 선언해주면 기본 method를 별도로 작성(코딩)하지 않아도 클래스 생성 시 자동으로 method 선언 가능
 */
public interface GenericService<VO,PK> {
	
	public List<VO> selectAll();
	public VO findById(PK id); //ID=Primary Key 개념으로 생성하는 method
	public int insert(VO vo);
	public int update(VO vo);
	public int delete(PK id);

}
