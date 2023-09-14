package com.spring.member.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.spring.member.service.MemberService;
import com.spring.member.vo.MemberVO;




@Controller("memberController")
public class MemberControllerImpl implements MemberController {
	@Autowired
	private MemberService memberService;
	@Autowired
	MemberVO memberVO ;
	
	@Override
	@RequestMapping(value="/member/listMembers.do" ,method = RequestMethod.GET)
	public ModelAndView listMembers(HttpServletRequest request, HttpServletResponse response) throws Exception {
		/*
		 * String viewName = getViewName(request); List membersList =
		 * memberService.listMembers(); ModelAndView mav = new ModelAndView(viewName);
		 * mav.addObject("membersList", membersList); return mav;
		 */
		//request 내부객체에서 데이터 가져오기
				String viewName = getViewName(request);
				List membersList = memberService.listMembers();
				ModelAndView mav = new ModelAndView(viewName);
				//데이터를 설정하는 작업
				mav.addObject("membersList", membersList);
				//
				return mav;
	}

	@Override
	@RequestMapping(value="/member/addMember.do" ,method = RequestMethod.POST)
	public ModelAndView addMember(@ModelAttribute("member") MemberVO member, HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		int result = 0;
		System.out.println("뷰 로부터 데이터 확인 : member.getId() " + member.getId());
		result = memberService.addMember(member);
		ModelAndView mav = new ModelAndView("redirect:/member/listMembers.do");
		return mav;
	}
	
	@Override
	@RequestMapping(value="/member/removeMember.do" ,method = RequestMethod.GET)
	public ModelAndView removeMember(@RequestParam("id") String id, HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("utf-8");
		memberService.removeMember(id);
		ModelAndView mav = new ModelAndView("redirect:/member/listMembers.do");
		return mav;
	}
	
	/*@RequestMapping(value = { "/member/loginForm.do", "/member/memberForm.do" }, method =  RequestMethod.GET)*/
	@RequestMapping(value = "/member/*Form.do", method =  RequestMethod.GET)
	public ModelAndView form(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = getViewName(request);
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);
		return mav;
	}
	
	

	private String getViewName(HttpServletRequest request) throws Exception {
		String contextPath = request.getContextPath();
		String uri = (String) request.getAttribute("javax.servlet.include.request_uri");
		if (uri == null || uri.trim().equals("")) {
			uri = request.getRequestURI();
		}

		int begin = 0;
		if (!((contextPath == null) || ("".equals(contextPath)))) {
			begin = contextPath.length();
		}

		int end;
		if (uri.indexOf(";") != -1) {
			end = uri.indexOf(";");
		} else if (uri.indexOf("?") != -1) {
			end = uri.indexOf("?");
		} else {
			end = uri.length();
		}

		String viewName = uri.substring(begin, end);
		if (viewName.indexOf(".") != -1) {
			viewName = viewName.substring(0, viewName.lastIndexOf("."));
		}
		if (viewName.lastIndexOf("/") != -1) {
			viewName = viewName.substring(viewName.lastIndexOf("/"), viewName.length());
		}
		return viewName;
	}

	@Override
	@RequestMapping(value = "/member/modMember.do", method =  RequestMethod.GET)
	public ModelAndView modMember(@RequestParam("id") String id,HttpServletRequest request, HttpServletResponse response) throws Exception {
		//mav 에 데이터를 넣는 구조 , 회원가입에서 복붙 
				String viewName = getViewName(request);
				ModelAndView mav = new ModelAndView();
				System.out.println("2.viewName(수정폼) 뭐야? : " + viewName);
				mav.addObject("user_id", id);  //mav 에 데이터를 넣는 구조 , 회원가입에서 복붙 
				System.out.println("3.id 뭐야? : " + id);
				MemberVO memberOne = memberService.getOneMember(id);
				mav.addObject("member", memberOne); 
				mav.setViewName(viewName);
				return mav;
	}
	
	@Override
	@RequestMapping(value = "/member/updateMember.do", method =  RequestMethod.POST)
	public ModelAndView updateMember(@ModelAttribute("memberVO") MemberVO memberVO, HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		/*
		 * MemberVO memberVO = new MemberVO(); 
		 * bind(request, memberVO);
		 */
		int result = 0;
		//동네 2번으로 외주 주기 
		//동네 2번에서 updateMember 정의해야함
		result = memberService.updateMember(memberVO);
		ModelAndView mav = new ModelAndView("redirect:/member/listMembers.do");
		return mav;
	}


}
