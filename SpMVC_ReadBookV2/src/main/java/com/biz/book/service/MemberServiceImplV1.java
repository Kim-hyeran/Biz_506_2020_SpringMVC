package com.biz.book.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.biz.book.mapper.AuthorityDao;
import com.biz.book.mapper.UserDao;
import com.biz.book.model.UserDetailsVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("memberServiceV1")
/*
 * MemberServiceImplV1 클래스의 필드변수가 있는 생성자를 자동으로 만들고,
 * private final로 선언된 객체에 Container로부터 객체를 주입받아 사용할 수 있도록 만들어주는 lombok의 Annotation
 * 
 * 아래 코드를 자동으로 생성한 것과 같다.
 * MemberServiceImplV1(PasswordEncoder passwordEncoder) {
 * 		this.passwordEncoder=passwordEncoder;
 * }
 */
@RequiredArgsConstructor
public class MemberServiceImplV1 implements MemberService {

	@Qualifier("passwordEncoder")
	private final PasswordEncoder passwordEncoder;
	
	// 회원정보 CRUD 구현
	private final UserDao userDao;
	
	// 회원의 권한(Role)정보 CRUD 구현
	private final AuthorityDao authDao;
	
	// Controller에서 회원정보를 전달받아 비밀번호를 암호화하고, userDao에 보내서 저장하기
	// 사용자의 Role 정보를 생성하여 AuthorityVO에 담고 저장하기
	public int insert(UserDetailsVO userVO) {
		String password=userVO.getPassword();
		String encPassword=passwordEncoder.encode(password);
		
		log.debug("password {}, encPassword {}", password, encPassword);
		
		/*
		 * 회원 테이블에 값이 없을 때(0) 회원가입이 이루어지면 그 회원은 admin 권한을 갖고 enabled 칼럼을 1로 세팅하여 즉시 로그인이 가능하도록 함
		 * 자바에서 true로 값을 설정하면 oracle db에서는 1로 저장
		 * 
		 * 두 번째 가입되는 회원은 enabled 칼럼을 0으로 세팅하여 즉시 로그인이 불가하도록 설정
		 * 자바에서 false로 값을 설정하면 oracle db에서는 0으로 저장
		 * 
		 * mySQL은 true와 false값 그대로 저장됨
		 */
		int nCount=userDao.userCount();
		
		if(nCount>0) {
			userVO.setEnabled(false);	// 0으로 세팅
		} else {
			userVO.setEnabled(true);	// 1로 세팅
		}
		
		// 평문으로 입력된 비밀번호를 암호화된 비밀번호로 대치
		userVO.setPassword(encPassword);
		userDao.insert(userVO);
		
		return 0;
	}
	
	public String userNameAndPassword(String username, String password) {
		UserDetailsVO userVO=userDao.findById(username);
		
		// passwordEncoder를 사용하여 암호화한 비밀번호는 일방향 비밀번호이기 때문에 decoder가 존재하지 않는다.
		// 사용자가 입력한 비밀번호와 DB에 저장된 비밀번호를 password를 passwordEncoder.matches() method에 매개변수로 전달하면
		// 비밀번호가 일치하는지 확인하여 true, false를 return한다.
		
		// 결과값에 따라 3항 연산자를 사용하여 OK 또는 FAIL 문자열을 Controller로 return
		return passwordEncoder.matches(password, userVO.getPassword()) ? "OK" : "FAIL";
	}

	@Override
	public UserDetailsVO findById(String username) {
		UserDetailsVO userVO=userDao.findById(username);
		
		return userVO;
	}

	@Override
	public int update(UserDetailsVO userVO) {
		return userDao.update(userVO);
	}
	
}
