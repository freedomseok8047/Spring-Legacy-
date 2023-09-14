package com.spring.member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.spring.member.vo.MemberVO;

public interface MemberController {
	//매서드 명세표 -> 한눈에 알수 있음
		// 어떤 메서드가 들어갓고 어떤 매서드가 더 필요한지 한눈에 확인 가능 
		public ModelAndView listMembers(HttpServletRequest request, HttpServletResponse response) throws Exception;
		public ModelAndView addMember(@ModelAttribute("info") MemberVO memberVO,HttpServletRequest request, HttpServletResponse response) throws Exception;
		public ModelAndView removeMember(@RequestParam("id") String id, HttpServletRequest request, HttpServletResponse response) throws Exception;
		public ModelAndView modMember(@RequestParam("id") String id,HttpServletRequest request, HttpServletResponse response) throws Exception;
		public ModelAndView updateMember(@ModelAttribute("memberVO") MemberVO memberVO,HttpServletRequest request, HttpServletResponse response) throws Exception;
		
}
