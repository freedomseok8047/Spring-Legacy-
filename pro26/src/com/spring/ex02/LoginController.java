package com.spring.ex02;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller("loginController")
public class LoginController {
	
	@RequestMapping(value = { "/test/loginForm.do", "/test/loginForm2.do" }, method = { RequestMethod.GET })
	public ModelAndView loginForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("loginForm");
		return mav;
	}
	//데이터 가져오기 1, 단수, 낱개로 가져오기 
    @RequestMapping(value = "/test/login.do", method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("result");
		String userID = request.getParameter("userID");
		String userName = request.getParameter("userName");
		mav.addObject("userID", userID);
		mav.addObject("userName", userName);

		return mav;
	}

	/*
	 * @RequestMapping(value = "/test/login2.do", method = { RequestMethod.GET,
	 * RequestMethod.POST }) public ModelAndView login2(@RequestParam("userID2")
	 * String userID2,
	 * 
	 * @RequestParam("userName2") String userName2, HttpServletRequest request,
	 * HttpServletResponse response) throws Exception {
	 * request.setCharacterEncoding("utf-8"); ModelAndView mav = new ModelAndView();
	 * mav.setViewName("result");
	 * 
	 * // String userID = request.getParameter("userID"); // String userName =
	 * request.getParameter("userName");
	 * 
	 * System.out.println("userID2: "+userID2);
	 * System.out.println("userName2: "+userName2); mav.addObject("userID2",
	 * userID2); mav.addObject("userName2", userName2);
	 * 
	 * return mav; }
	 */

	
	@RequestMapping(value = "/test/login2.do", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView login2(@RequestParam("userID") String userID, 
                               @RequestParam(value="userName", required=true) String userName,
			                   @RequestParam(value="email", required=false) String email,
			                  HttpServletRequest request, HttpServletResponse response) throws Exception {
		//@RequestParam 의 default [required=true] null값 허용 안함 
		//@RequestParam 의 [required=false] null값 허용함 
		request.setCharacterEncoding("utf-8");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("result");
		
		// String userID = request.getParameter("userID");
		// String userName = request.getParameter("userName");
		
		System.out.println("userID: "+userID);
		System.out.println("userName: "+userName);
		System.out.println("email: "+ email);
		mav.addObject("userID", userID);
		mav.addObject("userName", userName);
		return mav;
	}
	
	//Map 타입의 참조형 변수, info에 클라이언트에서 입력된 정보가 자동르 매핑된다. 
	@RequestMapping(value = "/test/login3.do", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView login3(@RequestParam Map<String, String> info,
			                   HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		ModelAndView mav = new ModelAndView();
		//클라로 부터 데이터 받아서
		String userID = info.get("userID");
		String userName = info.get("userName");
		String userEmail = info.get("userEmail"); //로그인 폼에서 name 테그에 맞게 불러옴 
		System.out.println("userID: "+userID);
		System.out.println("userName: "+userName);
		System.out.println("userEmail: "+userEmail);
		//결과뷰에 데이터 전달
		mav.addObject("info", info);
		//결과뷰를 전달
		mav.setViewName("result");
		return mav;
	}
	
	@RequestMapping(value = "/test/login4.do", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView login4(@ModelAttribute("info") LoginVO loginVO,
			                   HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		ModelAndView mav = new ModelAndView();
		System.out.println("userID: "+loginVO.getUserID());
		System.out.println("userName: "+loginVO.getUserName());
		System.out.println("email: "+loginVO.getEmail());
		//모델엔뷰으 ㅣ인스턴스에 성정된 뷰와 데이터를 이용해서 , 결과 뷰에 전달이 된다 .
		mav.setViewName("result");
		return mav;
	}
	   
	@RequestMapping(value = "/test/login5.do", method = { RequestMethod.GET, RequestMethod.POST })
	//메서드 봔환 타입이 모델 엔뷰가 아니라, 단순 문자열로 되어있고, 매개변소는 모델이라는 클래스를 이용중 
	//서버에서 임의로 설정한 데이터를 결과뷰에 전달함 
	public String login5(Model model,
			                   HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		//클라이언트의 뷰를 받아서 작ㅇㅂ도 가능한데, 일단, 임의로 서버에서 설정한 더미 데이터 테스트 중 
		model.addAttribute("userID", "lsy");
		model.addAttribute("userName", "이상용");
		//뷰 리졸버에 등록이 된 결과 뷰임 
		return "result";
	}	
}
