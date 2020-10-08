package com.biz.book.controller;

import java.security.Principal;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.biz.book.model.UserDetailsVO;
import com.biz.book.service.MemberService;

import lombok.RequiredArgsConstructor;

/*
 * @SessionAttributes("객체이름") 항목을 설정하면 서버의 메모리에 변수를 마련해놓는다.
 * 이 변수는 서버가 재시작하더라도 유지되는 성질이 있고, 클라이언트에서 request를 수행했을 때 메모리에 계속 유지된다.
 * 	RequestMapping method에 @ModelAttribute("객체이름") 클래스 객체 형식으로 매개변수가 있으면
 * 	메모리에 저장된 객체변수에서 값을 추출하여 객체에 포함해준다.
 */
@SessionAttributes("memberVO")
@Controller
@RequestMapping(value="/member")
@RequiredArgsConstructor
public class MemberController {
	
	private final MemberService memberService;
	
	/*
	 * @SessionAttributes("memberVO")를 사용하려면 반드시 memberVO를 생성하는 method가 클래스에 있어야 한다.
	 * UserDetailsVO 클래스로 생성된 memberVO가 "memberVO"라는 이름으로 보관된다.
	 * @SessionAttribute()가 있는데- @ModelAttribute()가 붙은 method가 없으면 컴파일 오류가 발생한다.
	 */
	@ModelAttribute("memberVO")
	public UserDetailsVO newMember() {
		UserDetailsVO memberVO=UserDetailsVO.builder().build();
		
		return memberVO;
	}
	
	/*
	 * (VO) 클래스를 Controller의 매개변수로 설정하고, @ModelAttribute("이름")을 설정했을 경우
	 * 1. form에서 POST로 데이터를 보냈을 때 form에서 보낸 데이터가 담긴 VO객체를 생성하여 method 내의 코드에서 사용할 수 있도록 준비
	 * 2. 아무도(아무 곳에서도) 객체의 필드변수와 일치하는 변수를 전달하지 않을 경우 자체적으로 VO 클래스에 생성자를 호출하여 비어있는 객체를 만들어 method 내의 코드에서 사용할 수 있도록 준비
	 */
	@RequestMapping(value="/join", method=RequestMethod.GET)
	public String join(@ModelAttribute("memberVO") UserDetailsVO userVO, Model model) {
		// UserDetailsVO userVO=new UserDetailsVO();
		// 위의 코드를 @ModelAttribute("memberVO")가 대신한다.
		
		model.addAttribute("memberVO", userVO);
		model.addAttribute("BODY", "MEMBER-JOIN");
		
		return "home";
	}
	
	/*
	 * 회원가입 입력폼을 2개로 분리하여 사용하기 위해
	 * join get : member-write.jsp가 실행
	 * join post : member-write2.jsp가 실행
	 * member-write.jsp에서 입력한 id, pw를 join POST로 보내면 @ModelAttribute("memberVO") 설정을 확인하고
	 * 	server에 임시 보관중인 SessionAttributes("memberVO")를 찾아 입력 박스로부터 전달된 데이터를 보관한다.
	 * member-write2.jsp를 열고 나머지 데이터를 입력한 후 join_comp POST로 보내면
	 * 	먼저 입력받아 SessionAttributes에 보관 중인 id, pw와 나중에 입력한 이름, 전화번호 등의 정보와 함께 묶어서 join_comp userVO에 담아준다.
	 * 입력폼의 항목이 매우 많을 때 입력폼을 분리해서 코딩을 해도 SessionAttribute의 성질을 이용하여 입력마법사와 같은 기능을 구현할 수 있다.
	 */
	
	@RequestMapping(value="/join", method=RequestMethod.POST)
	public String join(@ModelAttribute("memberVO") UserDetailsVO userVO, Model model, String str) {
		model.addAttribute("memberVO", userVO);
		model.addAttribute("BODY", "MEMBER-JOIN-NEXT");
		
		return "home";
	}
	
	/*
	 * @SessionAttributes()를 사용할 때는 DB에 데이터를 insert, update를 최종 수행하고 나면
	 * 	SessionStatus 클래스의 setComplete() method를 호출하여 서버에 남아있는 메모리를 clear해주어야 한다.
	 */
	@RequestMapping(value="/join_comp", method=RequestMethod.POST)
	public String join(@ModelAttribute("memberVO") UserDetailsVO userVO, SessionStatus status) {
		memberService.insert(userVO);
		
		status.setComplete();	// 서버에 남아있는 메모리 clear
		
		return "redirect:/";
	}
	
	@RequestMapping(value="/mypage", method=RequestMethod.GET)
	public String mypage(@ModelAttribute("memberVO") UserDetailsVO userVO, Authentication authProvider, Model model) {
		
		/*
		 * 현재 로그인한 사용자의 정보를 추출하는 method
		 * spring security를 통과하여 login이 인가된 사용자의 정보는 현재 method가 호출될 때 spring security의 filter chain에 의해
		 * 	method의 매개변수로 설정된 Authentication 클래스로 선언한 객체에 담겨서 전달된다.
		 * 객체에서 getPrincipal() method를 호출하여 데이터를 UserDetailsVO의 userVO 객체에 담아서 일반 userVO(memberVO)처럼 취급하여 사용 가능
		 */
		userVO=(UserDetailsVO) authProvider.getPrincipal();
		userVO.setPassword("");
		
		model.addAttribute("memberVO", userVO);
		model.addAttribute("BODY", "MEMBER-UPDATE");
		
		return "home";
	}
	
	@RequestMapping(value="/mypage", method=RequestMethod.POST)
	public String mypage(@ModelAttribute("memberVO") UserDetailsVO userVO, Model model, String str) {
		model.addAttribute("memberVO", userVO);
		model.addAttribute("BODY", "MEMBER-UPDATE-NEXT");
		
		return "home";
	}
	
	@RequestMapping(value="/update_comp", method=RequestMethod.POST)
	public String update(@ModelAttribute("memberVO") UserDetailsVO userVO, SessionStatus status) {
		memberService.update(userVO);
		
		status.setComplete();	// 서버에 남아있는 메모리 clear
		
		return "redirect:/";
	}
	
	@ResponseBody
	@RequestMapping(value="/password_check", method=RequestMethod.POST)
	public String password_check(String username, String password) {
		return memberService.userNameAndPassword(username, password);
	}
	
	@ResponseBody
	@RequestMapping(value="/id_check", method=RequestMethod.POST)
	public String id_check(String username) {
		// TDD(Test Driven Developer)
		// memberService에 아직 구현되지 않은 method를 사용처에서 먼저 생성하고 문법오류가 발생하면 memberService에 method를 구체적으로 구현
		UserDetailsVO userVO=memberService.findById(username);
		
		// userVO가 null이면 username이 DB에 존재하지 않는다는 뜻
		if(userVO==null) {
			return "OK";
		} else {
			return "FAIL";
		}
		
	}
	
	// logout.jsp 파일을 보여주기 위한 URL Mapping
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public String logout() {
		return "member/logout";
	}
	
	@ResponseBody
	@RequestMapping(value="/user_info", method=RequestMethod.GET)
	public UserDetailsVO userInfo(Principal principal, Authentication authentication, @ModelAttribute("memberVO") @AuthenticationPrincipal UserDetailsVO userVO, Model model) {
		// spring security 프로젝트에서 로그인한 사용자 정보를 추출하는 여러 가지 방법
		/* 1. UserDetailsServiceImplV1에서 공급받기
		 * 	서버의 Session memory에 직접 접근하여 사용자 정보를 추출하는 방법으로, 보안에 상당이 취약하여 사용을 지양한다.
		 */
		//UsernamePasswordAuthenticationToken upa=(UsernamePasswordAuthenticationToken) principal;
		//userVO=(UserDetailsVO) upa.getPrincipal();
		
		// 2. SecurityContextHolder로부터 추출하기
		//userVO=(UserDetailsVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		// 3. Authentication 클래스를 매개변수로 설정
		// @AuthenticationPrincipal이 작동되지 않는 관계로 매개변수에 Authentication 클래스를 객체로 선언
		// authentication.getPrincipal() method를 호출하여 userVO를 추출
		userVO=(UserDetailsVO) authentication.getPrincipal();
		
		return userVO;
	}

}
