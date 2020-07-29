package com.biz.hello;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biz.hello.service.HelloService;
import com.biz.hello.service.ScoreService;

/*
 * @RestController
 * Spring Framework 4.x 이상에서 지원되는 새로운 Controller
 */
@RestController
public class HelloRestController {
	/*
	 * @Autowired
	 * Spring Framework 프로젝트에서 @Annotation으로 설정된 클래스들을 객체로 생성한 Container 존재
	 * Container에 저장된 객체를 찾아서 선언된 객체에 주입하여 초기화 및 사용할 수 있도록 만들어준다.
	 * 객체를 생성하기 위해서 생성자를 만들필요가 없어진다.
	 */
	@Autowired
	private HelloService hService;
	@Autowired
	private ScoreService sService;
	
	@Autowired
	private HomeController hController;
	
	/*
	 * 표준 자바 코드에서 사용하는 (기본) 생성자
	public HelloRestController() {
		hService=new HelloServiceImpl();
		sService=new ScoreService();
	}
	*/
	
	//Spring FrameWork에서 권장하는 생성자
	public HelloRestController(HelloService hService, ScoreService sService) {
		this.hService=hService;
		this.sService=sService;
	}
	/*
	 * Spring project에서는 외부의 클래스를 객체로 만들 때 직접 new 생성자를 사용하여 만들지 않는다.
	 * 프로젝트가 시작될 때 @Annotation이 붙어있는 모든 클래스는 이미 객체로 생성되어 Container에 저장되어 있다.
	 * 다른 클래스를 객체로 생성하여 사용이 필요한 곳이 있으면 Container에서 객체를 꺼내 직접 주입한다.
	 * 
	 * DI(Dependency Inject) : 의존성 주입, 필요한 곳에 주입, 필요한 곳에 가져다주기
	 * IOC(Inversion of Control) : 제어의 역전
	 */
	
	/*
	 * Rest Full
	 * 새 버전에서 사용 가능한 Controller
	 * view를 별도로(독립적으로) 생성한 경우 사용하는 Annotation
	 * 어떤 의미 없는 값(null)을 return해도 오류를 출력하지 않음
	 */
	//'localhost:8080/hello/rest'라고 요청하면 응답
	@RequestMapping(value="/rest")
	public String rest(Model model) {
		model.addAttribute("myname", "korea");
		
		return "Republic of Korea";
	}
	
	@RequestMapping(value="/null")
	public String mNull(Model model) {
		model.addAttribute("myname", "korea");
		
		return null;
	}
	
	@RequestMapping(value="/int")
	public String hello() {
		int ret=hService.add(20, 40);
		return ret+"";
	}
	/*
	 * NullPointerException
	 * - 클래스를 객체로 만들어서 method를 호출하는 경우 가장 많이 발생하는 Exception
	 * - 클래스를 객체로 선언은 했으나 초기화, 생성은 하지 않은 경우
	 * - 이 method에서 'intList=new ArrayList<Integer>();'를 생략하면 'List<Integer> intList = null;'가 선언만 되고 초기화되지 않아
	 * 	 'intList.add(100);'를 실행했을 때 NullPointerException이 발생한다.
	 */
	public void nullPointer() {
		List<Integer> intList = null;
		intList=new ArrayList<Integer>();
		intList.add(100);
	}

}
