package com.myspring.pro29.ex01;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController 
//서버에서 클라이언트에게 json의 문자열 형타로 전달. 데이터만 전달하겟다.
// 하위에 있는 모든 메서드는 , json 문자열 형태로 전달. 데이터만 전달하겠다.
//@Controller -> 뷰 + 데이터, 데이터만 전달하는 메서드를 같이 혼합 구성 가능 
@RequestMapping("/test/*")
public class TestController {
  static Logger logger = LoggerFactory.getLogger(TestController.class);
  // /pro29/test/hello
  @RequestMapping("/hello")
  // 클라로 부터 서버 : 주소는 /pro29/test/hello
  // 서버가 응답하는데, 이제는 데이터만 전달해서, 
  // 응답을 데이터만 함 : Hello REST!!
  public String hello() {
	return "Hello REST!!";
  } 
  
  //서버에서 클라에게 : MemberVo 타입의 인스턴스르 , json 의 분자열로 응답할 예정 
  // MemberVo 타입의 인스턴스 -> Jackson 라이브러리 이용해서 -> 자동으로 json 문자열로 전달 예정 
  //클라 측 주소 : /pro29/test/member
  @RequestMapping("/member")
  public MemberVO member() {
    MemberVO vo = new MemberVO();
    vo.setId("hong");
    vo.setPwd("1234");
    vo.setName("홍길동");
    vo.setEmail("hong@test.com");
    return vo;
  } 	
  
  //위에는 단수 , 이제는 복수개로 전달함 
  // 앞 뒷단에 전달 형식 json타입으로 데이터를 많이 주고 받는 경우가 생김 
  //서버가 클라에게 , 데이터 복수개로 전달 , 요소는 memberVO 타입을 전달 
  @RequestMapping("/membersList")
  public List<MemberVO> listMembers () {
    List<MemberVO> list = new ArrayList<MemberVO>();
    //반복문을 인스턴스 10개 반복해서 ,리스트에 담기 
	for (int i = 0; i < 10; i++) {
		//임시인스턴스 10개를 반복해서 list에 담기 
	  MemberVO vo = new MemberVO();
	  vo.setId("hong"+i);
	  vo.setPwd("123"+i);
	  vo.setName("홍길동"+i);
	  vo.setEmail("hong"+i+"@test.com");
	  list.add(vo);
	}
    return list; 
  }	
  
  //반환타입 : 맵 타입 
  @RequestMapping("/membersMap")
  public Map<Integer, MemberVO> membersMap() {
	  //임시로 담을 컬렉션 뱁을 구성 
    Map<Integer, MemberVO> map = new HashMap<Integer, MemberVO>();
    //더미 데이터를 담아서, 전달 할 예정 
    for (int i = 0; i < 10; i++) {
      MemberVO vo = new MemberVO();
      vo.setId("hong" + i);
      vo.setPwd("123"+i);
      vo.setName("홍길동" + i);
      vo.setEmail("hong"+i+"@test.com");
      map.put(i, vo); 
    }
    // 서버 -> 클라이이언트에게 데이터만 전달. 전달 박스의 타임이 , 맴 형태.
    return map; 
  } 	
  //@PathVariable : 서버의 url 주소에서, 특정 매개변수를 서버로 가져올 때 사용
  //ex)게시판에서 , 특정 게시글의 상세페이지를 보고싶다. 
  // 해당 게시글의 번호를 , 서버에게 전달해야 , 해당 게시글 번호에 담긴 내용을 찾을 수 있다.
  @RequestMapping(value= "/notice/{num}" , method = RequestMethod.GET)
  public int notice(@PathVariable("num") int num ) throws Exception {
	  //서버에서 클라이언트 , 데이터만 전달, 클라이이언트에게 받은 매개변수 다시 재전달 
	  System.out.println("클라이이언트에게 받은 매개변수 서버에서 확인 :" + num);
	  return num;
  }	
  //@RequestBody -> 서버에서 클라에게 데이터만 전달하는 중이고
  // 전달하는 타입을 MemberVO 인스턴스를, 잭슨이라고 하는 기능을 통해서, json 문자열로 전달 
  //@Controller에서 혼합해서 사용가능 
  @RequestMapping(value= "/info", method = RequestMethod.POST)
  public void modify(@RequestBody MemberVO vo ){
	  //서버에서 넘어온 데이터, 로그로 확인 
    logger.info(vo.toString());
    logger.info(vo.getEmail());
  }
  //반환 타입 형식에서 , ResponseEntity 타입을 보는데 
  //데이너만 주거니 받거니 하면 , 잘 전달 되었는지 여부 확인 어려움 
  // 그래서 http status code, 전달 잘 받았다. 200
  // 3xx
  //4xx not found 
  //5xx 서버관련 
  @RequestMapping("/membersList2")
  public  ResponseEntity<List<MemberVO>> listMembers2() {
	List<MemberVO> list = new ArrayList<MemberVO>();
	for (int i = 0; i < 10; i++) {
	  MemberVO vo = new MemberVO();
	  vo.setId("lee" + i);
	  vo.setPwd("123"+i);
	  vo.setName("이상용" + i);
      vo.setEmail("lee"+i+"@test.com");
	  list.add(vo);
	}
	//list : 데이터도 전달하지만 ,
	// HttpStatus.INTERNAL_SERVER_ERROR : 상태 값도 같이 전달하느 연습 
	/* return new ResponseEntity(list,HttpStatus.INTERNAL_SERVER_ERROR); */
	return new ResponseEntity(list,HttpStatus.OK);
  }	
  
  //ResponseEntity -> 데이터 +상태도 간이 전달 
	@RequestMapping(value = "/res3")
	public ResponseEntity res3() {
		HttpHeaders responseHeaders = new HttpHeaders();
	    responseHeaders.add("Content-Type", "text/html; charset=utf-8");
	    String message = "<script>";
		message += " alert('res3 테스트 중');";
		message += " location.href='/pro29/test/membersList2'; ";
		message += " </script>";
		return  new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
	}
	
}
