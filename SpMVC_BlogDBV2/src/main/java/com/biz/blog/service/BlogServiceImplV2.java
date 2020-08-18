package com.biz.blog.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import com.biz.blog.dao.BlogDao;
import com.biz.blog.model.BlogVO;

@Service(value="bServiceV2")
public class BlogServiceImplV2 implements BlogService {
	
	@Autowired
	private BlogDao blogDao;

	@Override
	public List<BlogVO> selectAll() {
		return blogDao.selectAll();
	}

	@Override
	public BlogVO findBySeq(long seq) {
		return blogDao.findBySeq(seq);
	}

	@Override
	public int insert(BlogVO blogVO) {
		/*
		 * Local Date
		 * - java 8(1.8) 이상에서 사용하는 날짜 관련 class
		 * - Date, Calendar 등의 클래스를 사용하여 날짜 관련 연산을 수행앴으나 많은 이슈로 문제 발생이 잦음
		 * - java 8에서 새로이 날짜와 시간을 연산하기 위한 클래스가 등장함
		 */
		LocalDate localDate=LocalDate.now();
		LocalTime localTime=LocalTime.now();
		
		//날짜를 문자열형으로 변환하기 위한 패턴 생성
		DateTimeFormatter dd=DateTimeFormatter.ofPattern("yyyy-MM-dd");
		DateTimeFormatter dt=DateTimeFormatter.ofPattern("hh:mm:ss");
		
		//만들어진 패턴에 따라 날짜 데이터를 문자열로 변환하여 VO에 세팅
		blogVO.setBl_date(localDate.format(dd).toString());
		blogVO.setBl_time(localTime.format(dt).toString());
		
		//insert()를 수행한 후, 추가된 데이터 개수만큼 정수로 return
		//ret값이 1 이상이면 정상적으로 데이터가 추가된 것
		int ret=blogDao.insert(blogVO);
		
		return ret;
	}

	@Override
	public int update(BlogVO blogVO) {
		int ret=blogDao.update(blogVO);
		
		return ret;
	}

	@Override
	public int delete(long seq) {
		return blogDao.delete(seq);
	}

}
