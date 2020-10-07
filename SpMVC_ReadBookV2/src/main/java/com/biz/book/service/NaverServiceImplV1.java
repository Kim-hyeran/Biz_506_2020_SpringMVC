package com.biz.book.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import com.biz.book.config.NaverSecret;
import com.biz.book.model.BookVO;

import lombok.extern.slf4j.Slf4j;

/*
 * naver API를 통하여 도서명을 보내고, 그 결과를 JSON 형태로 수신하여 Parsing처리를 수행하는 서비스 클래스
 * naver가 server가 되고, ReadBook 프로젝트가 client가 된다.
 * 1. naver API에 보낼 쿼리 문자열이 포함된 URL 생성
 * 2. naver API에서 보내온 문자열을 JSON객체로 변환
 * 3. parsing 수행
 * 4. BookVO에 저장
 * 5. List<BookVO>에 담기
 */

@Slf4j
@Service("naverServiceV1")
public class NaverServiceImplV1 implements NaverService<BookVO> {
	
	// 도서명을 매개변수로 받아서 queryURL을 생성
	public String queryURL(String category, String bookName) {
		String queryURL=NaverSecret.NAVER_BOOK_XML;
		if(category.equalsIgnoreCase("NEWS")) {
			queryURL=NaverSecret.NAVER_NEWS_JSON;
		} else if(category.equalsIgnoreCase("MOVIE")) {
			queryURL=NaverSecret.NAVER_MOVIE_JSON;
		}
		
		// 한글 검색을 위해서 검색어를 UTF-8로 encoding 처리해주어야 한다.
		String encodeText=null;
		try {
			encodeText=URLEncoder.encode(bookName.trim(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// url?query=자바
		// queryURL+=String.format("?query=%s", bookName);
		queryURL += String.format("?query=%s", encodeText);
		
		// 한번에 조회할 데이터 개수를 지정할 수 있다.
		queryURL+="&display=10";
		
		return queryURL;
	}
	
	// queryURL을 naver API에게 보내고 데이터를 수신하는 method
	private String getNaverBook(String queryURL) {
		// queryURL 문자열을 http 프로토콜을 통해서 전송하기 위한 형식으로 만드는 클래스
		URL url=null;
		
		//http 프로토콜을 사용하여 데이터를 주고받는 Helper class
		HttpURLConnection httpConn=null;
		
		try {
			url=new URL(queryURL);
			httpConn=(HttpURLConnection)url.openConnection();
			httpConn.setRequestMethod("GET");
			// http 프로토콜에 X-Naver-Client-Id라는 변수로 Client Id값 저장(심기)
			httpConn.setRequestProperty("X-Naver-Client-Id", NaverSecret.NAVER_CLIENT_ID);
			httpConn.setRequestProperty("X-Naver-Client-Secret", NaverSecret.NAVER_CLIENT_SECRET);
			
			// URL을 만들고, GET 방식으로 요청하면서 Property에 client id와 client secret을 저장하여 보냈으니 응답을 요청하는 코드
			int resCode=httpConn.getResponseCode();
			
			/*
			 * HTTP 프로토콜에서 response code
			 * 200 : Server가 요청(Request)를 정상적으로 수신하고 response할 데이터를 준비 중
			 * 3xx : 요청(Request)은 정상적으로 수신하였으나 보낼 데이터가 없거나, 또는 이미 보냈기 때문에 재전송하지 않겠다는 의미
			 *  302 : redirect
			 *  304 : 직전에 보낸 데이터에 변화가 없으니 그대로 사용하라
			 * 4xx : 요청(Request) 정보에 문제 존재
			 *  404 : 요청 주소(URL, URI)가 서버에서 처리할 end point가 존재하지 않음
			 *  405 : 요청 주소는 존재하나 method(GET, POST)가 지정되지 않음
			 *  	  브라우저 주소창에 'http://localhost:8080/주소/상세주소'를 request 하였을 때, 서버 @RequestMapping에 POST만 존재하는 경우
			 *  	  form의 action="/주소" method="POST"로 지정된 데이터를 submit했을 때, 서버 @RequestMapping에 GET만 존재하는 경우
			 *  400 : form에 데이터를 입력하고 서버로 전송했을 때, Controller의 매개변수 차원에서 문제가 발생하는 경우
			 *  	  <input name="age">라는 input box에 값을 입력하지 않고 submit했을 때 public String input(int age)라고 Controller method의 매개변수로 설정해두면
			 *  	  spring의 Dispatcher는 age 변수에 담긴 값을 int형으로 형변환을 시도한다.
			 *  	  이 때 input box에 아무런 값이 없으면 age=(Integer.valueOf("") 코드가 실행되는 것과 같다.
			 *  	  이럴 때 내부에[서 exception이 발생하고 response code로 400을 전송한다.
			 * 500 : Server Internal Error
			 * 		 사용자의 요청을 처리하는 과정에서 Controller, Service, Dao 등 코드를 실행하는 도중 어떠한 원인으로 exception이 발생
			 * 		 이 오류가 발생하였을 경우 오류메시지를 제일 마지막부터 역순으로 검토
			 * 		 Error Stack 메시지는 발생된 순서가 역순으로 나타나기 때문
			 */
			
			BufferedReader buffer=null;
			InputStreamReader is=null;
			
			if(resCode==200) {
				// naver가 정상적으로 응답 수행
				is=new InputStreamReader(httpConn.getInputStream());
			} else {
				// naver의 응답 거부
				is=new InputStreamReader(httpConn.getErrorStream());
			}
			
			// InputStreamReader와 BufferedReader를 파이프로 연결
			buffer=new BufferedReader(is);
			StringBuffer sBuffer=new StringBuffer();
			
			// naver가 보낸 payload(response data)를 한 개의 문자열로 통합하여 수신
			// String sBuffer="";
			String reader=new String();
			
			while((reader=buffer.readLine())!=null) {
				sBuffer.append(reader);
				//sBuffer+=reader;	반복문 내에서는 주석처리된 코드와 같이 작성해도 내부 컴파일러가 자동으로 명령을 수행
			}

//			while(true) {
//				reader=buffer.readLine();
//				if(reader==null) break;
//				sBuffer.append(reader);
//			}
			
			buffer.close();
			return sBuffer.toString();
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	// jsonString을 parsing하여 Object(VO 등)으로 바꾸는 기능
	private List<BookVO> getJsonObject(String jsonString) {
		List<BookVO> bookList=new ArrayList<BookVO>();
		
		/*
		 * JSON* 클래스를 사용하여 JSON 문자열을 객체로 변환하기
		 * 1. JSONParser를 사용하여 JSONObject로 변환
		 * 2. JSONObject에서 "items"를 기분으로 잘라 JSONArray로 변환
		 * 3. JSONArray를 for문으로 반복하면서 각 요소를 다시 JSONObject로 변환
		 * 4. 요소로 변환된 JSONObject에서 각각의 항목을 추출하여 VO에 담기
		 * 5. VO를 List에 add()하기
		 */
		
		JSONParser jParser=new JSONParser();
		
		try {
			// JSONParser 도구를 사용하여 JSON형태의 문자열을 JSONObject(객체)로 변환하기
			JSONObject jObject=(JSONObject) jParser.parse(jsonString);
			JSONArray jArray=(JSONArray) jObject.get("items");
			
			int size=jArray.size();
			for(int i=0; i<size; i++) {
				JSONObject jo=(JSONObject) jArray.get(i);
				
				//bookVO.new BookVO(title, image, link, author, price...);
				/*
				bookVO.setTitle(jo.get("title").toString());
				bookVO.setImage(jo.get("image").toString());
				bookVO.setLink(jo.get("link").toString());
				*/
				
				/*
				 * VO @Builder를 설정함으로써 VO객체를 생성할 때 Builder 패턴을 사용할 수 있다.
				 * GoF 패턴에서 생성자 패턴 중 1가지
				 */
				String descript="";
				if(jo.get("director") != null) {
					descript += String.format("감독: %s <br>", jo.get("director").toString());
				}
				if(jo.get("actor") != null) {
					descript += String.format("출연: %s <br>", jo.get("actor").toString());
				}
				if(jo.get("userRating") != null) {
					descript += String.format("평점: %s <br>", jo.get("userRating").toString());
				}
				
				/*
				 * 빌드 패턴을 사용하여 bookVO 객체 생성
				 *  - 일반적인 VO 객체를 생성하고 데이터를 Setting하는 방법
				 * 1. 비어있는 VO 객체 생성 : vo=new VO();
				 * 		setter method를 사용하여 데이터를 입력하는 방법
				 * 2. 생성자에 값을 설정하고 VO 객체 생성 : vo=new VO(값1, 값2, 값3, ...)
				 * 		가. 생성자에 값을 설정(주입)하고 VO 객체를 생성하는 방법은 데이터의 순서가 완전히 개발자의 책임
				 * 			혹여 순서가 바뀐 채로 VO 객체가 생성되면 이후에 발생하는 모든 문제를 개발자가 책임져야 한다.
				 * 		나. 일부 데이터만 사용하여 데이터를 생성하려면 생성자를 필요한 매개변수만 존재하는 상태로 또 만들어야 한다.
				 * 			vo=new VO(값1, 값2) : VO(String 값1, String 값2)
				 * 			vo=new VO(값1, 값2, 값3) : VO(String 값1, String 값2, String 값3)과 같이 많은 생성자의 중복선언이 발생할 수 있다.
				 * 3. Builder(Build) 패턴을 사용한 객체 생성
				 * 		VO=VO.builder().변수1(값1).변수2(값2).build();
				 * 		가. 생성자를 통해서 객체를 필요할 때 즉시 생성
				 * 		나. 생성자에 매개변수를 주입하는 방식이지만, 여기서는 필요한 데이터만 매개변수로 주입 가능
				 * 			모든 변수를 생성자에 주입할 필요가 없다.
				 * 		다. 매개변수를 주입할 때, set*()와 같은 method를 사용하지 않고, 매개변수의 이름을 통해 직접 설정할 수 있다.
				 * 		라. 객체를 생성할 때 객체 chaining 코딩을 사용할 수 있다.
				 * 			객체.변수1().변수2().변수3().변수4();
				 */
				
				BookVO bookVO = BookVO.builder()
						.title(jo.get("title").toString())
						.image(jo.get("image") == null ? "noImage" : jo.get("image").toString())
						.link(jo.get("link").toString())
						.description(jo.get("description") == null ? descript : jo.get("description").toString())
						.build();
				
				bookList.add(bookVO);
			}
			
			return bookList;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public String getNaverSearch(String queryURL) {
		// TODO Auto-generated method stub
		return this.getNaverBook(queryURL);
	}

	@Override
	public List<BookVO> getNaverList(String jSonString) {
		// TODO Auto-generated method stub
		return this.getJsonObject(jSonString);
	}

}
