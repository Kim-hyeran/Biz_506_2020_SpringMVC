# Spring Security

# 개요
 * Spring Security 프로젝트에서 지원하는 인증, 인가, 권한 부여를 쉽게 구현할 수 있도록 만들어진 Framework
 * Spring Framework와 별도의 프로젝트로 진행되며, 초기 버전에 비해 매우 쉽고 간편하게 변화가 이루어지고 있다.
 * Spring Framework와의 연동이 매우 잘 이루어져서 그 동안 HttpSession 등을 사용하는 Login의 복잡한 과정을 비교적 쉽게 사용할 수 있다.
 
## Security에서 중요한 3가지 개념
 1. 접근 주체 : 누가(User)
 2. 인증 : 누가 로그인을 수행했는지, 정상과 비정상 여부 판단
 3. 인가 : 인증된 사용자가 어떤 페이지에 접근할 수 있도록 허락할 것인가
 4. 권한 : 인증되고 인가를 받은 사용자가 어떤 동작을 수행하도록 허락할 것인가

## Spring Security Dependency 설정
 * spring-context-support
 * spring-security-core
 * spring-security-web
 * spring-security-config
 * spring-security-taglibs
 
## Spring Security와 별도로 DB 정보(username, password) 암호화
 * jasypt, jasypt-spring Denpendency
 