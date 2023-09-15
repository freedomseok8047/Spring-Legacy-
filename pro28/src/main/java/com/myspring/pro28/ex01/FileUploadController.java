package com.myspring.pro28.ex01;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class FileUploadController  {
	//이미지 파일의 임시 물리 저장소 
	private static final String CURR_IMAGE_REPO_PATH = "c:\\spring\\image_repo";
	// 파일 이미지를 업로드 하기위 한 창
	@RequestMapping(value="/form")
	public String form() {
	    return "uploadForm";
	  }
	// 파일 이미지를 , 저장소에 upload 처리 로직
	@RequestMapping(value="/upload",method = RequestMethod.POST)
	// 일반 데이터 + 파일 데이터를 같이 처리하는 MultipartHttpServletRequest
	public ModelAndView upload(MultipartHttpServletRequest multipartRequest,HttpServletResponse response)
	  throws Exception{
		//이제, 폼 창에서, 사용자가 임력한 일반데이터 2개와 , 이미지 데이터 여러개르 처리준비 
		//이코딩 utf-8
		multipartRequest.setCharacterEncoding("utf-8");
		// 일반 데이터와 파일 이미지 데이터 불러오기
		Map map = new HashMap();
		// 일반 데이터를 반복을 도와주는 Enumeration 타입 할당 
		Enumeration enu=multipartRequest.getParameterNames();
		//일반 데이터의 키와 값의 구조로 저장 
		while(enu.hasMoreElements()){
			//키에 대응되는 값을 불러오기.
			String name=(String)enu.nextElement();
			
			String value=multipartRequest.getParameter(name);
			//System.out.println(name+", "+value);
			map.put(name,value);
		}
		//1번 종료
		
		//2번 시작 
		//저장된 이미지 목록도 가져오고, 실제 물리경로 파일로 생성해서 저장 
		List fileList= fileProcess(multipartRequest);
		// 임시 저장소 map 컬렉션이 파일의 목록을 저장 
		map.put("fileList", fileList);
		ModelAndView mav = new ModelAndView();
		//결과 뷰에서 사용할 수 있게 데이터 전달하는 과정 
		mav.addObject("map", map);
		mav.setViewName("result");
		return mav;
	}
	
	//실제 이미지르 처리하느 로직.
	private List<String> fileProcess(MultipartHttpServletRequest multipartRequest) throws Exception{
		// 임시 이미지 파일 ㅇ이름을 저장할 리스트
		List<String> fileList= new ArrayList<String>();
		// Iterator 반복 처리를 도와주는 도구 .
		// multipartRequest.getFileNames(); -> 이미지 파일의 이름ㅇ르 가지고 옴
		Iterator<String> fileNames = multipartRequest.getFileNames();
		while(fileNames.hasNext()){
			// 폼이름 형식으로 되어 있는 부부분을 하나씩 가지고 옴
			String fileName = fileNames.next();
			//file1 처리하는 인스턴스 생성
			MultipartFile mFile = multipartRequest.getFile(fileName);
			// mfile 인스턴스의 도구중에 , 실제 파일 명르 불러오는 메서드 사용 
			String originalFileName=mFile.getOriginalFilename();
			// 리스트에 파일 이미지 저장 
			fileList.add(originalFileName);
			// 실제 저장소, 이미지 파일 업로드 하느 로직 
			// 이미지 파을을 처리하기 위한 인스턴스, 절대 경로를 포함 
			File file = new File(CURR_IMAGE_REPO_PATH +"\\"+ fileName);
			// 이미지 사이즈가 0이 아니다. 이미지파일이 존재한다. 
			if(mFile.getSize()!=0){ //File Null Check
				// 만약, 파일이 존재 안한다 면 실행 
				if(! file.exists()){ 
					//해당 경로의 부모 폴더를 만들고 true 반환 -> 실행 
					if(file.getParentFile().mkdirs()){ 
						//실제 경로에 이미지 파일을 생성함 , 빈 파일 
						file.createNewFile(); 
					}
				}
				//메몰에 있는 이미지르 길제 저장소에 옮기는 작업 
				mFile.transferTo(new File(CURR_IMAGE_REPO_PATH +"\\"+ originalFileName)); //�ӽ÷� ����� multipartFile�� ���� ���Ϸ� ����
			}
		}
		return fileList;
	}
}
