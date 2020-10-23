package com.biz.bbs.mapper;

import java.util.List;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;

import com.biz.bbs.model.ImageVO;
import com.biz.bbs.sql.ImageSQL;

public interface ImageDao {
	
	@InsertProvider(type=ImageSQL.class, method="insert")
	public int insert(@Param("vo") ImageVO imageVO, @Param("b_seq") long b_seq);
	
	public int insert_list(List<ImageVO> images, long b_seq);

}
