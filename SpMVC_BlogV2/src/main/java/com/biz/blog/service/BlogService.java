package com.biz.blog.service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.biz.blog.model.BlogVO;

@Service
public class BlogService {
	
	//blog에 작성한 내용을 txt 파일에 저장하는 코드
	private String blogFile="blog.txt";
	
	public void selectAll() {
		ClassPathResource rs=new ClassPathResource(blogFile);
		Path path=null;
		
		try {
			path=Paths.get(rs.getURI());
			List<String> blogList=Files.readAllLines(path);
			
			for (String str : blogList) {
				System.out.println(str);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//java 1.8 이상에서만 정상 작동되는 코드
	public void insert(BlogVO blogVO) {
		//Server의 class들이 저장되는 폴더 정보 가져오기
		ClassPathResource rs=new ClassPathResource(blogFile);
		
		Path path=null;
		FileWriter fileWriter=null;
		PrintWriter print=null;
		
		try {
			path=Paths.get(rs.getURI());
			fileWriter=new FileWriter(path.toString(), true);
			print=new PrintWriter(fileWriter);
			
			String strBlog=String.format("%s:%s:%s", blogVO.getTitle(), blogVO.getContent(), blogVO.getUser());
			
			print.println(strBlog);
			print.flush();
			print.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("저장 완료");
	}

}
