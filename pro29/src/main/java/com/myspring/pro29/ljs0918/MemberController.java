package com.myspring.pro29.ljs0918;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController // -> 데이터로만 전달하겠다 
@RequestMapping("/boards2")
public class MemberController {
	static Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	//클라이언트 주소 : /pro29/boards/all
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	// ResponseEntity : 데이터만 전달하면서 , 상태 확인도 같이하자
	//덤으로, 헤더에 특정으 속성도 같ㅇ ㅣ추가 가능 
	public ResponseEntity<List<MemberVO>> listMembers() {
		//로그 기록을 조금 더 기능을 잘 구현한 라이브러리를 사용해서 ,기록하자 
		logger.info("listMembers 호출");
		//서버에서 -> 클라로  데이터 전달하는 샘플 더미 데이터 
		//동네 1 ~ 동네 4 다 순회 후, 데이터 직접 전달.
		List<MemberVO> list = new ArrayList<MemberVO>();
		// ArticleVO 타입으로만, 박스에 담아서 전달 해 주세요! 강력한 요구사항 
		for (int i = 0; i < 10; i++) {
			MemberVO vo = new MemberVO();
			vo.setMemberNO(i);
			vo.setId("hong"+i);
		    vo.setPwd("1234"+i);
		    vo.setName("홍길동"+i);
		    vo.setEmail("hong@test.com"+i);
			list.add(vo);
		}
		//서버 -> 클라 : 박스(데이터) 전달 + 상태 코드로 같이 전달 
		return new ResponseEntity(list,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{memberVO}", method = RequestMethod.GET)
	public ResponseEntity<MemberVO> findMembers (@PathVariable("memberNO") Integer memberNO) {
		logger.info("findMembers 호출");
		MemberVO vo = new MemberVO();
		vo.setMemberNO(memberNO);
		vo.setId("hong");
	    vo.setPwd("1234");
	    vo.setName("홍길동");
	    vo.setEmail("hong@test.com");
		return new ResponseEntity(vo,HttpStatus.OK);
	}	
	
	//POST : 추가(피카추), PUT : U 업데이트 , GET : 조회 , PATCH: 부분 수정, DELETE: 삭제
	//추가테스트
	//전달 방법 2가지
	//1)웹 , 자바 스크립트 보내기. -> JSONTeste.jsp
	// 2)포스트 맨으로 보내기 
	@RequestMapping(value = "", method = RequestMethod.POST)
	//ResponseENtity : 데이터 + 상태
	//@RequestBody : 서버-> 클라에 전달 된 데이터 서버에서 자동으로 모델 클래스 매핑
	public ResponseEntity<String> addMember (@RequestBody MemberVO memberVO) {
		
		ResponseEntity<String>  resEntity = null;
		try {
			//클라이언트 에서 -> 서버 데이터 도착확인
			logger.info("addMember 호출");
			logger.info(memberVO.toString());
			//서버-> 클라이언트 데이터 전달 내용물
			//1) 메세지 :ADD_SUCCEEDED
			//2) http상태코드 : 200(ok)
			resEntity =new ResponseEntity("ADD_SUCCEEDED",HttpStatus.OK);
		}catch(Exception e) {
			resEntity = new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
		
		return resEntity;
	}	
	
	//수정확인 
	//@PathVariable("articleNO") Integer articleNO : 주소의 매개변수 서버에서 가져와서 사용
	//@RequestBody ArticleVO articleVO: 서버에 전달된 데이터를, 모델 클래스에 자동 
	//클라 주소 : /pro29/boards/777
	@RequestMapping(value = "/{memberNO}", method = RequestMethod.PUT)
	public ResponseEntity<String> modMembers (@PathVariable("memberNO") Integer memberNO, @RequestBody MemberVO memberVO) {
		ResponseEntity<String>  resEntity = null;
		try {
			logger.info("modMembers 확인222");
			logger.info(memberVO.toString());
			resEntity =new ResponseEntity("MOD_SUCCEEDED",HttpStatus.OK);
		}catch(Exception e) {
			resEntity = new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
		
		return resEntity;
	}
	
	//DELETE, 레스트 형식으로 CRUD
	//클라측에서 서버로 데이터 전달 
	//수정, 삭제 할때 , 어느 게시글 수정 또는 삭제 할지 번호를 알려주면 
	//클라주소 : /pro29/boards/777, DELETE
	//확인 방법 2가지 1)웹 JS , 2)포스트맨 도구로
	@RequestMapping(value = "/{memberNO}", method = RequestMethod.DELETE)
	public ResponseEntity<String> removeMember (@PathVariable("memberNO") Integer memberNO) {
		ResponseEntity<String>  resEntity = null;
		try {
			//처음에는 반드시, 클라로 부터 전달 받은 데이터 , 서버에서 확인하는 연습
			// 개발 단계에서 , 어느정도  검토 및 확인이 1차로 끝나면 
			//배포시에는 각 주석을 모두 제거하고 깔끔하게 업로드 
			//문제발생시, 우리는 개발하는 단계(주석등 메세지 포함)
			//버전 점검후, 다시 배포함 
			logger.info("removeMember 호출");
			System.out.println("반드시 클라이언트로 부터 넘어온 정보를 확인하는 습관");
			logger.info(memberNO.toString());
			//데이터와 상태코드 함께 전달 
			resEntity =new ResponseEntity("REMOVE_SUCCEEDED",HttpStatus.OK);
		}catch(Exception e) {
			resEntity = new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
		
		return resEntity;
	}	

}
