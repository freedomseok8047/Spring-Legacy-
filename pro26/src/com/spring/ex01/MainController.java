package com.spring.ex01;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
//@Annotation 이요해서 시스템 설정 -> <bean>설정 대체
@Controller("mainController") 
@RequestMapping("/test") //클래스 레벨 @Annotation
public class MainController {
   @RequestMapping(value="/main1.do" ,method=RequestMethod.GET)//메서드 레벨 @Annotation
   public ModelAndView main1(HttpServletRequest request, HttpServletResponse response)  throws Exception{
      ModelAndView mav=new ModelAndView();
      //결과 뷰에 전달하느 데이터 설정 
      mav.addObject("msg","main1");
      //클라이언트에게 전달하는 결과 뷰
      mav.setViewName("main");
      return mav;
   }
   // 기능 똑같지만 설정 파일 xml 하나 줄어듬-> @RequestMapping이 대체
   @RequestMapping(value="/main2.do" ,method = RequestMethod.GET)
   public ModelAndView main2(HttpServletRequest request, HttpServletResponse response) throws Exception{
      ModelAndView mav=new ModelAndView();
      mav.addObject("msg","main2");
      mav.setViewName("main");
      return mav;
   }
}
