package com.biz.bbs.service;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service("fileServiceV3")
public class FileServiceImplV3 extends FileServiceImplV1 {
	
	@Override
	public String fileUp(MultipartFile file) {
		
		String rootFolder="C:/bizwork/workspace/upload";
		
		File dir=new File(rootFolder);
		
		// file을 upload할 폴더를 검사하여 없으면 새로 생성
		if(!dir.exists()) {
			// mkdir()는 제을 끝의 폴더 1개만 생성
			// mkdirs()는 모든 경로의 폴더를 한꺼번에 생성
			dir.mkdirs();
		}
		
		String fileName=file.getOriginalFilename();
		
		// 서버의 저장 폴더+파일 이름을 합성하여 파일 저장 준비
		File saveFile = new File(rootFolder,fileName);
		
		try {
			file.transferTo(saveFile);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}
	
}
