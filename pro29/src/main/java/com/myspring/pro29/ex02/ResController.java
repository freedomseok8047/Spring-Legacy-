package com.myspring.pro29.ex02;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

//앞에서 형식은 완전 레스트 형식 , 하위 매서드 전체가 뷰 없이 데이터만 전달 

//지금은 상위에 @Controller 형식으로 선언해서 
//기본은 데이터 + 뷰
//추가로, 특정 메서드에 뷰없이 데이터만 전달하는 구조도 혼합가능 
@Controller
public class ResController {
	@RequestMapping(value = "/res1")
	@ResponseBody
	//@RequestBody MemberVO : 클라 -> 서버 
	//@ResponseBody : 서버 -> 클라
	public Map<String, Object> res1() {
		//서버 -> 클라 데이터 Map이라는 컬렉션에 담아서 전달
		//잭슨 라이브러리 알아서, json 형태의 타입을 변환해서 전달
		//이 때, @ResponseBody가 자동으로 변환해서 전달 
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", "hong");
		map.put("name", "홍길동");
		return map;
	}
	
	// res1 은 데이터만 전달하느 레스트 형식
	// res2 는 뷰로 전달하는 구조 
	// ModelAndView : 데이터 + 뷰 
	
	@RequestMapping(value = "/res2")
	public ModelAndView res2() {
		// 근데 데이터 없어서 뷰만 전달 
		return new ModelAndView("home");
	}
	
}
