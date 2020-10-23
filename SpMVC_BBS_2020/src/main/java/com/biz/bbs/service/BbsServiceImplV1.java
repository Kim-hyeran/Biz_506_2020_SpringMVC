package com.biz.bbs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.biz.bbs.mapper.BbsDao;
import com.biz.bbs.mapper.ImageDao;
import com.biz.bbs.model.BbsVO;
import com.biz.bbs.model.ImageVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("bbsServiceV1")
public class BbsServiceImplV1 implements BbsService {
	
	@Autowired
	protected BbsDao bbsDao;
	
	@Autowired
	protected ImageDao imageDao;
	
	@Autowired
	@Qualifier("fileServiceV5")
	protected FileService fileService;

	@Override
	public List<BbsVO> selectAll() {
		return bbsDao.selectAll();
	}

	@Override
	public void insert(BbsVO bbsVO, MultipartFile file) {
		String fileName = fileService.fileUp(file);
		bbsVO.setB_file(fileName);
		
		bbsDao.insert(bbsVO);
	}

	@Override
	public BbsVO findBySeq(long long_seq) {
		return bbsDao.findBySeq(long_seq);
	}

	@Override
	public int delete(long long_seq) {
		
		/*
		 * 첨부파일이 있는 게시물 데이터를 삭제할 때
		 * 1. seq에 해당하는 VO를 dao에서 findBySeq()
		 * 2. 파일 이름을 fileDelete()에 보내서 파일 먼저 삭제
		 * 3. 게시물 데이터 삭제
		 */
		BbsVO bbsVO=bbsDao.findBySeq(long_seq);
		
		String b_file=bbsVO.getB_file();
		if(b_file!=null) {
			fileService.fileDelete(b_file);
		}
		
		return bbsDao.delete(long_seq);
	}

	@Override
	public void insert(BbsVO bbsVO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<String> insert(BbsVO bbsVO, MultipartHttpServletRequest files) {
		/*
		 * 업로드된 멀티파일 정보에서 개별 파일들을 List에 추출
		 * file.getFiles(이름) : "이름"은 input tag의 name 값을 지정
		 */
		List<MultipartFile> fileList=files.getFiles("files");
		
		for(MultipartFile f : fileList) {
			log.debug("업로드 된 파일 : {}", f.getOriginalFilename());
		}
		
		// 1. 파일 업로드를 수행하고 파일 이름 리스트 확보
		List<ImageVO> fileNames=fileService.filesUp(files);
		
		// 2. bbsVO insert 수행
		bbsDao.insert(bbsVO);
		
		long b_seq=bbsVO.getB_seq();
		
		log.debug("BBS SEQ 값 : {}", b_seq);
		
		for(ImageVO vo : fileNames) {
			imageDao.insert(vo, b_seq);
		}
		
		return null;
	}

}
