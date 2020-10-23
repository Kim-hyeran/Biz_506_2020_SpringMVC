package com.biz.bbs.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.biz.bbs.model.ImageVO;

public interface FileService {
	
	public String fileUp(MultipartFile file);

	public boolean fileDelete(String b_file);

	public List<ImageVO> filesUp(MultipartHttpServletRequest files);

}
