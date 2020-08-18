package com.biz.blog.service;

import java.util.List;

import com.biz.blog.model.BlogVO;

public interface BlogService {
	
	public List<BlogVO> selectAll();
	//pk칼럼을 대상으로 조회하면 데이터는 절대 2개 이상 출력되지 않는다(반드시 1개 출력). 때문에 return type이 절대 List형이 될 수 없다.
	public BlogVO findBySeq(long seq);
	/*
	 * 제목으로 검색하기
	 * public List<BlogVO> findByTitle(String title);
	 * pk 칼럼이 아닌 나머지 칼럼을 대상으로 조회하면 설령 데이터가 1개만 출력되더라도 이 결과값은 반드시 List형이다.
	 */
	//VO에 담긴 데이터를 DB로 보내 Table에 추가하고, 성공하면 1 이상의 return 결과를 정수형으로 return
	public int insert(BlogVO blogVO);
	public int update(BlogVO blogVO);
	public int delete(long seq); //pk 값을 기준으로 명령을 수행하기 때문

}
