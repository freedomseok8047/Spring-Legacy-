package com.myspring.pro28.ex01;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


/*@Controller*/
public class FileDownloadController {
	//다운로드 위치, 파일 이미지 메모리에 읽어서 출력 대상 : 웹 브라우저
		// 출력대상, 보통은 디비에 경로를 저장 (현재는 로컬의 c드라이브, 보통 미디어 서버 url) 
		// c드라이브 임시, 파일 이미지 저장소
	private static String CURR_IMAGE_REPO_PATH = "c:\\spring\\image_repo";

	@RequestMapping("/download")
	//imageFileName -> 업로드 후, 추가한 이미지들의 파일 이름 들 
	protected void download(@RequestParam("imageFileName") String imageFileName,
			                 HttpServletResponse response)throws Exception {
		//메모리 상에 임시로 저장된 이미지르 , 웹 브라우저에 출력하는 도구.
		OutputStream out = response.getOutputStream();
		//불러올 이미지가 저장된 위치 경로 (절대 경로)
		// 예) downfile = C:\spring\image_repo\~~
		String downFile = CURR_IMAGE_REPO_PATH + "\\" + imageFileName;
		//이미지가 저장된 파일을 , 메모리상에서 처리하기 위해, 차일 객체를 이용함 
		File file = new File(downFile);
		//클라이언트와 서버간에 , 주고받는 도구가 request,response 인스턴스 
		//캐시 저장 안하고, 매번 똑같이 파일을 출력하겠다. 결론, 재사용 안함 
		response.setHeader("Cache-Control", "no-cache");
		// 응답 객체에 파일이름 첨부하겠다.
		response.addHeader("Content-disposition", "attachment; fileName=" + imageFileName);
		// file = C:\spring\image_repo\~~
		// 물리 경로에 있는 file(이미지 파일) 읽어서, 바이트 단위로 메모리에 저장
		// 참조형 변수 이름을 in 이라는 인스턴스에 담았음
		// 즉, in 인스턴스에 바이트 단위로 이미지가 저장 되어 있음 ex) 101000111101100.....
		FileInputStream in = new FileInputStream(file);
		// 임시 저장소 버퍼라는 배열을 만듦
		// 역할: 이미지에 있는 바이트를 특정 크기만큼 잘라서 ,담는 공간 
		byte[] buffer = new byte[1024 * 8];
		// 반복작업 -> in 메모리상에 바이트로 저장된 이미지 -> out 이라는 객체에 옮기는 작업
		// out 객체는 , 웹브라우저에 출력을 해주는 역할로 사용이 된 예정
		while (true) {
			//in.read -> buffer의 길이만큼 읽겠다.
			int count = in.read(buffer); 
			// if 다 읽어서 읽을게 없다. 음수로 반환 그러면 파일 이미지를 다 읽었음 
			//반복 종료
			if (count == -1) 
				break;
			//in이라는 객체에서 
			//8byte 잘라서, out이라는 객체(메모리) 옮겨 담기
			//buffer라는 배열을 길이 :1024*8 
			//시작 인덱스 0
			//count = 1024*8 만큼 복사하겟다. 
			//out에 옮겨담기
			out.write(buffer, 0, count);
		}
		in.close();
		out.close();
	}

}
