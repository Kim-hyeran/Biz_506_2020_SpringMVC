package com.biz.book.exec;

import java.util.Map;
import java.util.Scanner;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

/* 
 * jasypt 패키지의 StandardPBEStringEncryptor 클래스를 사용하여 DB 접속용 username password를 암호화하여 문자열 추출
 */
public class SecurityTest {

	public static void main(String[] args) {
		
		StandardPBEStringEncryptor pbe=new StandardPBEStringEncryptor();
		Scanner scan=new Scanner(System.in);
		
		Map<String, String> envList=System.getenv();
		
		System.out.print("문자열>> ");
		String planText=scan.nextLine();
		
		// 암호화를 위해서 PBE... 객체 값 세팅
		pbe.setAlgorithm("PBEWithMD5AndDES");
		// salt : 암호화를 할 때 사용할 key 문자열
		/*
		 * salt 값을 일반 문자열로 사용하면 소스코드에 문자열이 노출되어 각종 값들을 암호화하는 용도로 사용할 때 문제 발생 가능성 존재
		 * 문자열을 바로 사용하지 않고 시스템(컴퓨터 운영체제)에 환경변수를 저장해두고, 환경변수를 가져다 salt 비밀번호로 사용
		 * BIZ.COM이라는 문자열이 노출되어도 네트워크를 통해서 salt 암호를 추출하기가 매우 어려워진다.
		 * 따라서 암호화하는 효과를 좀 더 배가시킬 수 있다.
		 */
		pbe.setPassword(envList.get("BIZ.NET"));
		
		for(int i=0; i < 10; i++) {
			// 평문을 암호화하여 암호문 추출 : encryption
			String encText=pbe.encrypt(planText);
			System.out.printf("P : %s, E : %s\t", planText, encText);

			// 암호문을 복호화하여 평문 추출 : decryption
			String dencText=pbe.encrypt(encText);
			System.out.printf("E : %s, P : %s\n", dencText, planText);		
		}
		
	}

}
