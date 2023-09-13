package com.spring.member.controller.ljs230912;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
//1/import 변경 
import com.spring.member.service.ljs230912.MemberService;
import com.spring.member.service.ljs230912.MemberServiceImpl;
import com.spring.member.vo.MemberVO;
//동네 1-1,
public class MemberControllerImpl extends MultiActionController implements MemberController {
	private MemberService memberService;

	public void setMemberService(MemberServiceImpl memberService) {
		this.memberService = memberService;
	}
	/* 동네 2번에 외주 맡길때 필요한 인스턴스 초기화 설정 -> DI(의존성 주입) */
	
	//조회기능 : listMembers
	@Override
	public ModelAndView listMembers(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
	public ModelAndView addMember(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		MemberVO memberVO = new MemberVO();
		/*
		String id=request.getParameter("id");
		String pwd=request.getParameter("pwd");
		String name=request.getParameter("name");
		String email = request.getParameter("email");
		memberVO.setId(id);
		memberVO.setPwd(pwd);
		memberVO.setName(name);
		memberVO.setEmail(email);
		 */
		bind(request, memberVO);
		int result = 0;
		result = memberService.addMember(memberVO);
		ModelAndView mav = new ModelAndView("redirect:/member_ljs230912/listMembers.do");
		return mav;
	}
	
	@Override
	public ModelAndView removeMember(HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("utf-8");
		String id=request.getParameter("id");
		memberService.removeMember(id);
		ModelAndView mav = new ModelAndView("redirect:/member_ljs230912/listMembers.do");
		return mav;
	}
	
	@Override
	public ModelAndView modMember(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//mav 에 데이터를 넣는 구조 , 회원가입에서 복붙 
		String viewName = getViewName(request);
		ModelAndView mav = new ModelAndView();
		System.out.println("2.viewName(수정폼) 뭐야? : " + viewName);
		String id=request.getParameter("id"); //id를 가져오는 구조, 삭제에서 복붙 
		mav.addObject("user_id", id);  //mav 에 데이터를 넣는 구조 , 회원가입에서 복붙 
		System.out.println("3.id 뭐야? : " + id);
		//추가, 해당 아이디로 , 정보를 가져오기 
		//조회된 한 회원의 정보를 담을 임시 인스턴스 : memberOne
		//getOneMember : 서비스 아직 없는 메서드 임의로 추가 
		//외주 , 서비스 동네 2번 인터페이스와 클래스에서 정의 해야함 
		MemberVO memberOne = memberService.getOneMember(id);
		mav.addObject("member", memberOne); 
		mav.setViewName(viewName);
		return mav;
	}

	
	@Override
	public ModelAndView updateMember(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		MemberVO memberVO = new MemberVO();
		bind(request, memberVO);
		int result = 0;
		result = memberService.updateMember(memberVO);
		ModelAndView mav = new ModelAndView("redirect:/member_ljs230912/listMembers.do");
		return mav;
	}	

	
	public ModelAndView form(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = getViewName(request);
		ModelAndView mav = new ModelAndView();
		System.out.println("1.viewName(회원가입) 뭐야? : " + viewName);
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

		String fileName = uri.substring(begin, end);
		if (fileName.indexOf(".") != -1) {
			fileName = fileName.substring(0, fileName.lastIndexOf("."));
		}
		if (fileName.lastIndexOf("/") != -1) {
			fileName = fileName.substring(fileName.lastIndexOf("/"), fileName.length());
		}
		return fileName;
	}

}
