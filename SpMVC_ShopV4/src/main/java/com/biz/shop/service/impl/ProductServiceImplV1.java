package com.biz.shop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biz.shop.model.ProductVO;
import com.biz.shop.persistence.ProductDao;
import com.biz.shop.service.ProductService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service(value="proServiceV1")
public class ProductServiceImplV1 implements ProductService {
	
	@Autowired
	private ProductDao proDao;

	@Override
	public List<ProductVO> selectAll() {

		return proDao.selectAll();
	}

	@Override
	public ProductVO findById(String id) {
		return proDao.findById(id);
	}

	@Override
	public int insert(ProductVO vo) {
		vo.setP_image("이미지 없음");
		int ret=proDao.insert(vo);
		
		if(ret>0) {
			log.debug("INSERT 성공, {}개 데이터 추가", ret);
		} else {
			log.debug("INSERT 실패 {}", ret);
		}
		
		return ret;
	}

	@Override
	public int update(ProductVO vo) {
		//update를 수행하기 전에 삭제flag 칼럼을 강제로 null 값을 설정하여 해당 상품이 삭제표시가 되지 않도록 설정
		vo.setP_not_use(null);
		int ret=proDao.update(vo);
		
		return ret;
	}

	/*
	 * Master Data : 상품정보, 거래처정보, 회원정보와 같은 데이터
	 * 				Work Data의 기준이 되는 값을 가지는 데이터
	 * Work Data : 매입매출, 거래처 외상 정보, 회원 로그인 로그와 같은 데이터
	 * 어플을 사용하여 어떤 업무를 진행하는 과정에서 Work Data에 저장되는 데이터는 필수적으로 Master Data와 Join 관계가 맺어진다
	 * 만약 Work Data와 Master Data를 Join하여 어떤 통계 정보를 찾고자 할 때, Master에 해당하는 데이터(코드와 일치하는)가 없으면 Work Data의 내용 확인이 어려울 수 있다.
	 * 통상적으로 Master Data는 원칙상 PK 칼럼 값은 변경하지 않고, 한 번 Insert된 Master Data는 삭제하지 않는다.
	 * 필요 없어진(앞으로 사용하지 않을) 데이터는 Table에서 Delete하지 않고, 특정한 칼럼을 한 개 지정하여 그 칼럼에 flag를 세팅하고 삭제되었음을 표시
	 * 
	 * 1. id(p_code)로 table에서 데이터를 조회하여 VO에 담기
	 * 2. VO의 p_not_use 칼럼을 null이 아닌 값으로 세팅
	 * 3. update 수행
	 * 4. select 수행 시 p_not_use 칼럼이 null인 데이터만 조회
	 * 5. 삭제된 데이터는 제외하고 조회할 수 있도록 코드 변경
	 */
	@Override
	public int delete(String id) {
		int ret=0;
		ProductVO proVO=proDao.findById(id);
		
		if(proVO!=null) {
			proVO.setP_not_use((byte)1);
			ret=proDao.update(proVO);
		}
		
		//Controller에서 delete() method를 실행한 후, 결과값을 보고 delete 성공 여부 판단
		return ret;
	}

	@Override
	public List<ProductVO> findByTitle(String title) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPcode() {
		//tbl_product table에서 상품코드를 조회하는 데 가장 큰 값의 코드 소환
		//P0001, P0002, P0010과 같은 코드가 있다고 가정할 때, 가장 큰 값인 P0010 코드를 가져오는 SQL 생성
		String strMaxPcode=proDao.maxPcode();
		log.debug("조회한 상품코드 : {}", strMaxPcode);
		
		/*
		 * table에 상품정보가 하나도 없을 경우
		 * 미리 최초의 상품코드를 변수에 담아놓고 retPcode를 생성하는 코드를 실행하여 try에서 exception이 발생하여 건너뛰도록 한다.
		 * 이런 식으로 하면 상품코드는 P00001을 자동으로 return한다.
		 */
		String retPcode="P00001";
		try {
			String preCode=strMaxPcode.substring(0, 1);
			String pCode=strMaxPcode.substring(1);
			log.debug("분리된 상품코드 : {}, {}", preCode, pCode);
			retPcode=String.format("%s%05d", preCode, Integer.valueOf(pCode)+1);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		log.debug("새로 생성된 상품코드 : {}", retPcode);

		return retPcode;
	}

}
