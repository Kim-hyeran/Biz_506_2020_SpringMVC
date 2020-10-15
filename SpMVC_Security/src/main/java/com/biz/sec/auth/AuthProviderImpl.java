package com.biz.sec.auth;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.biz.sec.controller.UserVO;

import lombok.extern.slf4j.Slf4j;

/*
 * spring security의 authentication-manager에서 사용할 authentication-provider 클래스 customizing 수행하기
 */
@Slf4j
public class AuthProviderImpl implements AuthenticationProvider {

	// spring security를 통하여 login을 수행했을 때 호출되는 method
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		// 사용자 id 추출
		// String username=(String) authentication.getPrincipal();
		// String username=authentication.getPrincipal().toString();
		String username=authentication.getName();
		
		// username "admin" or "user1" or "guest"
		boolean bUser=username.equals("admin");
		bUser|=username.equals("user1");
		bUser|=username.equals("guest");
		
		// 사용자 id 검사
		if(!username.equals("admin")) {
			String msg=String.format("[%s] 사용자 ID를 확인하세요", username);
			
			/*
			 * throw new Exception(message)
			 * throw : exception을 강제 발생
			 * 
			 * spring security login이 진행되는 도중에 어떤 문제가 발생했을 때
			 * 	메시지를 만들고 강제로 exception을 발생시키면 spring security에게 메시지를 전달하는 효과가 나타난다.
			 * authenticate() method는 실행을 멈추고 spring security가 메시지를 수신하여 다시 login 화면을 열어 메시지를 보여준다.
			 */
			throw new InternalAuthenticationServiceException(msg);
			//throw new UsernameNotFoundException(msg);
		}
		
		// password 추출
		String password=authentication.getCredentials().toString();
		
		if(!password.equals("1234")) {
			throw new BadCredentialsException("비밀번호를 확인해주세요.");
		}
		
		UserVO userVO=new UserVO();
		log.debug("USER VO : "+userVO.isAccountNonExpired());
		
		if(!userVO.isEnabled()) {
			throw new DisabledException("사용자 정보를 사용할 수 없습니다.");
		}
		
		if(!userVO.isAccountNonLocked()) {
			throw new LockedException("사용자 계정이 잠겨 있습니다.관리자에게 문의하세요.");
		}
		
		if(!userVO.isAccountNonExpired()) {
			throw new AccountExpiredException("사용자 계정이 만료되었습니다.");
		}
		
		if(!userVO.isCredentialsNonExpired()) {
			throw new CredentialsExpiredException("사용자 계정에 접근 권한이 없습니다.");
		}
		
		// role 정보 테스트 값 생성
		// 사용자 id에 부여된 role list를 만들어 추가하고, jsp 등에서 사용하기
		List<GrantedAuthority> authList=new ArrayList<GrantedAuthority>();
		
		if(username.equals("admin")) {
			authList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		} else if(username.equals("user1")) {
			authList.add(new SimpleGrantedAuthority("ROLE_USER"));
		} else {
			authList.add(new SimpleGrantedAuthority("ROLE_GUEST"));
		}
		
		
		// login만 성공하고 role과 인가 정보들이 모두 false인 사용자 데이터를 생성하여 login 성공 메시지 만들기
		UsernamePasswordAuthenticationToken token=new UsernamePasswordAuthenticationToken(new UserVO(), null, authList);
		
		return token;
	}
	
	// 현재 만들어진 AuthProviderImpl를 spring security에서 사용 가능하도록 설정
	// return 값을 true로 하여 사용 가능 상태로 전환
	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return true;
	}

}
