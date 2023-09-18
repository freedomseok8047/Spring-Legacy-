package com.myspring.pro30.member.controller;

import java.util.List;
import java.util.Locale;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
//pro28서 import 추가
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.myspring.pro30.member.service.MemberService;
import com.myspring.pro30.member.vo.MemberVO;



@Controller("memberController")
@EnableAspectJAutoProxy
public class MemberControllerImpl   implements MemberController {
//	private static final Logger logger = LoggerFactory.getLogger(MemberControllerImpl.class);
	@Autowired
	private MemberService memberService;
	@Autowired
	MemberVO memberVO ;

	
	public class HomeController {
	  private final Logger logger = LoggerFactory.getLogger(HomeController.class);
	  /**
	  * Simply selects the home view to render by returning its name.
	  */
	  @RequestMapping(value = "/main", method = RequestMethod.GET)
	  public String home(Locale locale, Model model) {
	    logger.info("Welcome home! The client locale is {}.", locale);
	    //나중에 , 파일명에서 , 중복파일 피하기 위해서 사용하는 네이밍 기법 중 하나인데
	    // 1)시간 및 날짜 를 이용하고, 
	    // 2) UUID , 특정 랜덤한 숫자 및 영문자를 생성해주는 도구를 이요하기도 함. 
	    Date date = new Date();
	    DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, 
	    DateFormat.LONG, locale);
	    String formattedDate = dateFormat.format(date);
	    //단순 데이터만, 뷰에 전달하는 구조만 잠시 보면 됨. 
	    model.addAttribute("serverTime", formattedDate );
	    //결과 뷰는, 모델 &뷰 형식이 아니라, 단순 뷰 리졸버 해당 뷰로 이동함.
		/* return "home"; */
	    // 뷰 리졸버 대신, 타일즈 이용해서 서버에 접속해보기 
	    return "main";
	  }
	 } 


	
	@Override
	@RequestMapping(value="/member/listMembers.do" ,method = RequestMethod.GET)
	public ModelAndView listMembers(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = getViewName(request);
//		String viewName = (String)request.getAttribute("viewName");
		//System.out.println("viewName: " +viewName);
//		logger.info("viewName: "+ viewName);
//		logger.debug("viewName: "+ viewName);
		List membersList = memberService.listMembers();
		ModelAndView mav = new ModelAndView(viewName);
		mav.addObject("membersList", membersList);
		return mav;
	}

	@Override
	@RequestMapping(value="/member/addMember.do" ,method = RequestMethod.POST)
	public ModelAndView addMember(@ModelAttribute("member") MemberVO member,
			                  HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		int result = 0;
		result = memberService.addMember(member);
		ModelAndView mav = new ModelAndView("redirect:/member/listMembers.do");
		return mav;
	}
	
	@Override
	@RequestMapping(value="/member/removeMember.do" ,method = RequestMethod.GET)
	public ModelAndView removeMember(@RequestParam("id") String id, 
			           HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("utf-8");
		memberService.removeMember(id);
		ModelAndView mav = new ModelAndView("redirect:/member/listMembers.do");
		return mav;
	}
	/*
	@RequestMapping(value = { "/member/loginForm.do", "/member/memberForm.do" }, method =  RequestMethod.GET)
	@RequestMapping(value = "/member/*Form.do", method =  RequestMethod.GET)
	public ModelAndView form(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = getViewName(request);
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);
		return mav;
	}
	*/
	
	@Override
	@RequestMapping(value = "/member/login.do", method = RequestMethod.POST)
	public ModelAndView login(@ModelAttribute("member") MemberVO member,
				              RedirectAttributes rAttr,
		                       HttpServletRequest request, HttpServletResponse response) throws Exception {
	//처음 member, 클라이언트로 부터 입력받은 id,pw만 있음 
	ModelAndView mav = new ModelAndView();
	//로그인이 잘 된경우, 해당 유저의 나머지 정보를 다 가지고 옴
	memberVO = memberService.login(member);
	//로그인 후, 해당 회우너의 정보를 다 가지고 있음 
	if(memberVO != null) {
		    //서버의 세션의 인스턴스르 가지로 오는 로직
		    HttpSession session = request.getSession();
		    //세션에 맴버르 등록
		    session.setAttribute("member", memberVO);
		    //세션에 상태변수에, isLogOn -> true 기록
		    session.setAttribute("isLogOn", true);
		    //로그인 후, 결과 페이지로  리다이렉트 이동 시키기
		    mav.setViewName("redirect:/member/listMembers.do");
	}else {
		//로그인 실패시, 해당 result 라는 키로 , loginFailed라는 문자열 추가 
		    rAttr.addAttribute("result","loginFailed");
		    //다시 로그인 폼으로
		    mav.setViewName("redirect:/member/loginForm.do");
	}
	return mav;
	}

	@Override
	@RequestMapping(value = "/member/logout.do", method =  RequestMethod.GET)
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		//서버 세션의 키부분 삭제 
		session.removeAttribute("member");
		session.removeAttribute("isLogOn");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/member/listMembers.do");
		return mav;
	}	

	@RequestMapping(value = "/member/*Form.do", method =  RequestMethod.GET)
	private ModelAndView form(@RequestParam(value= "result", required=false) String result,
						       HttpServletRequest request, 
						       HttpServletResponse response) throws Exception {
		System.out.println("*Form.do 실행여부 확인 ====================");
		String viewName = getViewName(request);
		//String viewName = (String)request.getAttribute("viewName");
		System.out.println("viewName 확인 ====================" + viewName);
		ModelAndView mav = new ModelAndView();
		System.out.println("result 확인 ====================" + result);
		mav.addObject("result",result);
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
			viewName = viewName.substring(viewName.lastIndexOf("/", 1), viewName.length());
		}
		return viewName;
	}

	@Override
	@RequestMapping(value = "/member/modMember.do", method =  RequestMethod.GET)
	public ModelAndView modMember(@RequestParam("id") String id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
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
	public ModelAndView updateMember(@ModelAttribute("memberVO") MemberVO memberVO, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
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
	
	