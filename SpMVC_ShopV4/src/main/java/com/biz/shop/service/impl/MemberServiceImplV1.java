package com.biz.shop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biz.shop.model.MemberVO;
import com.biz.shop.persistence.MemberDao;
import com.biz.shop.service.MemberService;

@Service(value="memServiceV1")
public class MemberServiceImplV1 implements MemberService {
	
	@Autowired
	private MemberDao memDao;

	@Override
	public List<MemberVO> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MemberVO findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * memService의 insert는 회원가입 join method를 대신한다.
	 * 
	 * 회원가입 정책(Policy)
	 *  1. tbl_member table에 아무런 데이터도 없이 최초로 가입된(insert) 회원 정보는 권한(Role)을 "admin"으로 부여
	 *  2. 두 번째부터 가입된 회원은 권한(role)을 "user"로 부여
	 *  3. 회원이 탈퇴를 요청하면 권한(role)을 "guest"로 변경
	 *  4. 같은 아이디로 재가입을 요청하면 권한을 "guest"에서 "user"로 변경
	 *  5. "admin" 권한은 현재 App의 모든 기능 사용 가능
	 * 		- 다른 사용자의 정보 변경 가능
	 * 		- 다른 사용자의 권한(role) 변경 가능
	 *  6. "user"권한은 "admin"권한으로 접근할 수 있는 부분 사용 볼가 
	 *  	- 본인의 사용자 정보만 변경 가능
	 *  	- 본인의 사용자 권한 변경 불가
	 *  7. "guest" 권한은 로그인과 재가입 요청만 가능
	 *  
	 *  회원가입 절차
	 *   1. tbl_member table에 회원정보가 1개라도 존재하는지 검사
	 *   1-1. 없을 경우 : 현재 추가할 회원은 "admin" 권한 부여
	 *   1-2. 있을 경우 : 현재 추가할 회원은 "user" 권한 부여
	 *   2. memberVO의 role 칼럼에 권한 문자열 추가
	 *   3. insert 수행
	 */
	@Override
	public int insert(MemberVO memVO) {
		//tbl_member table에 저장된 레코드 개수 가져오기
		int count=memDao.memberCount();
		
		/*
		 * if(count==0) {
		 * 		memVO.setM_role("ADMIN);
		 * }
		 * 
		 * = table에 데이터가 한 개도 없을 때, 권한을 admin으로 설정
		 * 위의 비교연산식은 문법적으로 아무런 문제가 없는 코드
		 * 실제 jdbc를 통해서 서버에 원격 연결을 하는 상황에서는 count 값이 실제로는 0이지만 0이 아닌 -1등의 값이 될 수도 있음
		 * 데이터가 없을 때, 0이거나 -1인 경우가 된다면
		 * 		if(count==0 || count==-1) 또는 if(count<=0)과 같은 비교연산식이 만들어져야 한다
		 * 비교연산식에서 가장 권장하는(좋은) 코드는 비교대상을 부등호로 한 번만 비교하는 것
		 * else문이 존재하는 상황이므로 값을 최대한 근접한 것으로 찾는 연산식으로 변경한다.
		 * if(count>0)의 형식이 가장 이상적
		 */
		
		//회원정보가 없으면 admin, 있으면 user
		if(count>0) {
			memVO.setM_role("USER");
		} else {
			memVO.setM_role("ADMIN");
		}
		
		int ret=memDao.insert(memVO);
		
		return ret;
	}

	@Override
	public int update(MemberVO vo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(String id) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	/*
	 * 로그인 절차
	 * 1. loginVO에 담긴 정보에서 user ID를 추출하여 사용자 정보 가져오기 : findByID()
	 * 2. 결과값이 null이면 Controller로 즉시 null return : Login Fail
	 * 3. 결과값이 null이 아니면 password 비교
	 * 	3-1. 일치하면 : Login OK, memVO를 Controller로 return
	 * 	3-2. 일치하지 않으면 : Password Fail, Controller로 Password Fail을 알림
	 */

	@Override
	public MemberVO login(MemberVO loginVO) {
		MemberVO memVO=memDao.findById(loginVO.getM_user_id());
		if(memVO!=null) {
			if(!loginVO.getM_password().equals(memVO.getM_password())) {
				memVO.setM_role("P_FAIL");
				memVO.setM_user_id("P_FAIL");
			}
		}
		
		return memVO;
	}

}
