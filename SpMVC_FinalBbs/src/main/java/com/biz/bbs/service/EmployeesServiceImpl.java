package com.biz.bbs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biz.bbs.mapper.EmployeesDao;
import com.biz.bbs.model.EmployeesVO;

@Service("empServiceV1")
public class EmployeesServiceImpl implements EmployeesService {
	
	@Autowired
	private EmployeesDao empDao;

	@Override
	public List<EmployeesVO> selectAll() {
		// TODO Auto-generated method stub
		return empDao.selectAll();
	}

	@Override
	public EmployeesVO findById(long id) {
		// TODO Auto-generated method stub
		return empDao.findById(id);
	}

	@Override
	public int insert(EmployeesVO empVO) {
		// TODO Auto-generated method stub
		return empDao.insert(empVO);
	}

	@Override
	public int update(EmployeesVO empVO) {
		// TODO Auto-generated method stub
		return empDao.update(empVO);
	}

	@Override
	public int delete(long id) {
		// TODO Auto-generated method stub
		return empDao.delete(id);
	}

}
