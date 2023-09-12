package com.spring.member.controller.ljs230912;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

import org.springframework.dao.DataAccessException;
import org.springframework.web.servlet.ModelAndView;
//동네1번의 인터 페이스
public interface MemberController {
	//매서드 명세표 -> 한눈에 알수 있음
	// 어떤 메서드가 들어갓고 어떤 매서드가 더 필요한지 한눈에 확인 가능 
	// 수정 매서드가 없다는 거 알아냄!
	public ModelAndView listMembers(HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ModelAndView addMember(HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ModelAndView removeMember(HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ModelAndView modMember(HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ModelAndView updateMember(HttpServletRequest request, HttpServletResponse response) throws Exception;
	
}
