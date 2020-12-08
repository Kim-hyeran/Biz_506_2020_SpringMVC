package com.biz.data.service;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.biz.data.config.DataGoConfig;
import com.biz.data.model.GoPetVO;
import com.biz.data.model.RfcOpenAPI;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PetServiceImplV1 {
	
	public List<GoPetVO> getHosp(String hosp) {
		
		String queryString = DataGoConfig.PET_URL;
		queryString += "/getDongMulHospital";
		queryString += "?ServiceKey=" + DataGoConfig.SERVICE_KEY;
		queryString += "&pageNo=1";
		queryString += "&numOfRows=100";
		
		try {
			if(!hosp.isEmpty()) {
				queryString += "&dongName=" + URLEncoder.encode(hosp, "UTF-8");
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// springframework.http 패키지의 클래스 가져오기
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_XML));
		
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
		
		RestTemplate restTemp = new RestTemplate();
		URI restURI = null;
		
		ResponseEntity<RfcOpenAPI> result = null;
		
		// GoPetVO가 담긴 리스트 타입으로 RestTemplate의 데이터를 받고자 할 때, List는 인터페이스이기 때문에 RestTemplate에서 데이터 생성 불가
		// 인터페이스를 RestTemplate의 데이터 타입으로 사용하기 위해서 Parameterized... 클래스를 이용하여 변환하여야 한다.
//		ParameterizedTypeReference<List<GoPetVO>> paramType = new ParameterizedTypeReference<List<GoPetVO>>() {};
		
		try {
			restURI = new URI(queryString);
			result = restTemp.exchange(restURI, HttpMethod.GET, entity, RfcOpenAPI.class);
			List<GoPetVO> petList = result.getBody().body.data.list;
			
//			log.debug(petList.toString());
			
			return petList;
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}

}
