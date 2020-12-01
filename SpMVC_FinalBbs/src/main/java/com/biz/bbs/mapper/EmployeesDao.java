package com.biz.bbs.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import com.biz.bbs.model.EmployeesVO;

public interface EmployeesDao {
	
	@Select("SELECT * FROM tbl_employees")
	public List<EmployeesVO> selectAll();
	
	@Select("SELECT * FROM tbl_employees WHERE id = #{id}")
	public EmployeesVO findById(long id);
	
	public int insert(EmployeesVO empVO);
	
	public int update(EmployeesVO empVO);
	
	@Delete("DELETE FROM tbl_employees WHERE id = #{id}")
	public int delete(long id);

}