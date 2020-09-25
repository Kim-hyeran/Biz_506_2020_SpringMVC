package com.biz.book.mapper;

import com.biz.book.model.BookVO;

/*
 * GenericDao를 extends함으로써 기본 CRUD method를 별도로 정의하지 않아도 된다.
 */
public interface BookDao extends GenericDao<BookVO, Long> {

}
