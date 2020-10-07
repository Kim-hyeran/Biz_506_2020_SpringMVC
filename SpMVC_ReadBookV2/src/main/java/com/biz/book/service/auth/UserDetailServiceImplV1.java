package com.biz.book.service.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.biz.book.mapper.AuthorityDao;
import com.biz.book.mapper.UserDao;
import com.biz.book.model.UserDetailsVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
/*
 * spring security 프로젝트에서 사용자 인가와 권한을 관리하는 클래스
 * UserDetailService를 customizing
 * 
 * Customizing
 * 패키지형 솔루션을 가지고 있는 it 회사에서 어떤 회사에 솔루션을 판매하면서 솔루션을 사용하는 회사의 실정, 업무환경 등 여러가지 여건을 요구분석하여 최적화하는 것
 */
@RequiredArgsConstructor
@Service("userDetailServiceV1")
public class UserDetailServiceImplV1 implements UserDetailsService {
	/*
	 * @Autowired를 사용하여 객체를 주입받아 사용해왔는데, @Autowired로 주입받은 객체에 메모리 누수 현상이 발생
	 * 주입받은 객체를 final로 선언하고 final로 선언한 객체는 반드시 생성자에서 객체 초기화(주입)가 필요
	 * 1. 주입받을 객체를 final로 선언
	 * 2. 생성자의 매개변수를 통하여 객체 초기화
	 *  ->주입받을 객체의 개수가 변동되면 생성자 또한 변경해야하는 번거로움 존재
	 * 이 경우 lombok의 @RequiredArgsConstructor를 사용하면 final로 선언된 모든 필드변수를 모아서 생성자로 만들어준다.
	 */
	private final UserDao userDao;
	private final AuthorityDao authDao;

	/*
	 * Customizing을 하는 목적
	 * 이 프로젝트에서 사용할 member(user) 관련 table에서 username으로 사용자 정보를 SELECT하고, 사용자의 ROLE 정보를 기준으로 사용자의 권한을 설정하여 기능을 수행을 제한
	 * 사용자의 여러 세부 정보를 VO 객체에 담아주는 역할 수행
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetailsVO userDetail=userDao.findById(username);
		
		
		/* 테스트를 위한 임시 사용자 정보 생성
		userDetail=UserDetailsVO.builder().username(username).password("12341234").enabled(true).build();
		*/
		
		if(userDetail==null) {
			// UsernameNotFoundException을 일부러 강제 발생시킨다.
			throw new UsernameNotFoundException(username+"의 정보를 찾을 수 없습니다.");
		}
		
		log.debug("USER : "+userDetail.toString());
		
		/*
		 * 사용자 정보 테이블에서 enable 칼럼의 값을 boolean형으로 설정
		 * 회원가입을 했을 때 이 값을 최초로 false로 세팅
		 * email 인증과 같은 절차를 통과했을 때 이 값을 true로 만들어서 로그인이 될 수 있도록 설정
		 */
		//userDetail.setEnabled(true);
		
		return userDetail;
	}

}
