package com.myspring.pro27.member.controller.ljs0915;

import java.util.List;
import java.util.ArrayList;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;


import com.myspring.pro27.member.service.ljs0915.MemberService;
import com.myspring.pro27.member.vo.MemberVO;



@Controller("memberController.ljs0915")
@EnableAspectJAutoProxy
public class MemberControllerImpl   implements MemberController {
//	private static final Logger logger = LoggerFactory.getLogger(MemberControllerImpl.class);
	@Autowired
	private MemberService memberService;
	@Autowired
	MemberVO memberVO ;
	
	@Override
	@RequestMapping(value="/memb_ljs0915/listMembers.do" ,method = RequestMethod.GET)
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
	@RequestMapping(value="/memb_ljs0915/addMember.do" ,method = RequestMethod.POST)
	public ModelAndView addMember(@ModelAttribute("member") MemberVO member,
			                  HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		int result = 0;
		result = memberService.addMember(member);
		ModelAndView mav = new ModelAndView("redirect:/memb_ljs0915/listMembers.do");
		return mav;
	}
	
	@Override
	@RequestMapping(value="/memb_ljs0915/removeMember.do" ,method = RequestMethod.GET)
	public ModelAndView removeMember(@RequestParam("id") String id, 
			           HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("utf-8");
		memberService.removeMember(id);
		ModelAndView mav = new ModelAndView("redirect:/memb_ljs0915/listMembers.do");
		return mav;
	}
	/*
	@RequestMapping(value = { "/memb_ljs0915/loginForm.do", "/memb_ljs0915/memberForm.do" }, method =  RequestMethod.GET)
	@RequestMapping(value = "/memb_ljs0915/*Form.do", method =  RequestMethod.GET)
	public ModelAndView form(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = getViewName(request);
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);
		return mav;
	}
	*/
	
	@Override
	@RequestMapping(value = "/memb_ljs0915/login.do", method = RequestMethod.POST)
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
		    mav.setViewName("redirect:/memb_ljs0915/listMembers.do");
	}else {
		//로그인 실패시, 해당 result 라는 키로 , loginFailed라는 문자열 추가 
		    rAttr.addAttribute("result","loginFailed");
		    //다시 로그인 폼으로
		    mav.setViewName("redirect:/memb_ljs0915/loginForm.do");
	}
	return mav;
	}

	@Override
	@RequestMapping(value = "/memb_ljs0915/logout.do", method =  RequestMethod.GET)
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		//서버 세션의 키부분 삭제 
		session.removeAttribute("member");
		session.removeAttribute("isLogOn");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/memb_ljs0915/listMembers.do");
		return mav;
	}	

	@RequestMapping(value = "/memb_ljs0915/*Form.do", method =  RequestMethod.GET)
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
	@RequestMapping(value = "/memb_ljs0915/modMember.do", method =  RequestMethod.GET)
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
	@RequestMapping(value = "/memb_ljs0915/updateMember.do", method =  RequestMethod.POST)
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
		ModelAndView mav = new ModelAndView("redirect:/memb_ljs0915/listMembers.do");
		return mav;
	}
	
	
	
	private static String CURR_IMAGE_REPO_PATH = "c:\\spring\\image_repo";

	
	public void download(@RequestParam("imageFileName") String imageFileName,HttpServletResponse response)throws Exception {
		//메모리 상에 임시로 저장된 이미지르 , 웹 브라우저에 출력하는 도구.
		OutputStream out = response.getOutputStream();
		//불러올 이미지가 저장된 위치 경로 (절대 경로)
		// 예) downfile = C:\spring\image_repo\~~
		String downFile = CURR_IMAGE_REPO_PATH + "\\" + imageFileName;
		//이미지가 저장된 파일을 , 메모리상에서 처리하기 위해, 차일 객체를 이용함 
		File file = new File(downFile);
		//클라이언트와 서버간에 , 주고받는 도구가 request,response 인스턴스 
		//캐시 저장 안하고, 매번 똑같이 파일을 출력하겠다. 결론, 재사용 안함 
		response.setHeader("Cache-Control", "no-cache");
		// 응답 객체에 파일이름 첨부하겠다.
		response.addHeader("Content-disposition", "attachment; fileName=" + imageFileName);
		// file = C:\spring\image_repo\~~
		// 물리 경로에 있는 file(이미지 파일) 읽어서, 바이트 단위로 메모리에 저장
		// 참조형 변수 이름을 in 이라는 인스턴스에 담았음
		// 즉, in 인스턴스에 바이트 단위로 이미지가 저장 되어 있음 ex) 101000111101100.....
		FileInputStream in = new FileInputStream(file);
		// 임시 저장소 버퍼라는 배열을 만듦
		// 역할: 이미지에 있는 바이트를 특정 크기만큼 잘라서 ,담는 공간 
		byte[] buffer = new byte[1024 * 8];
		// 반복작업 -> in 메모리상에 바이트로 저장된 이미지 -> out 이라는 객체에 옮기는 작업
		// out 객체는 , 웹브라우저에 출력을 해주는 역할로 사용이 된 예정
		while (true) {
			//in.read -> buffer의 길이만큼 읽겠다.
			int count = in.read(buffer); 
			// if 다 읽어서 읽을게 없다. 음수로 반환 그러면 파일 이미지를 다 읽었음 
			//반복 종료
			if (count == -1) 
				break;
			//in이라는 객체에서 
			//8byte 잘라서, out이라는 객체(메모리) 옮겨 담기
			//buffer라는 배열을 길이 :1024*8 
			//시작 인덱스 0
			//count = 1024*8 만큼 복사하겟다. 
			//out에 옮겨담기
			out.write(buffer, 0, count);
		}
		in.close();
		out.close();
	}
	
	@RequestMapping(value="/form")
	public String form() {
	    return "uploadForm";
	  }
	
	// 파일 이미지를 , 저장소에 upload 처리 로직
	@RequestMapping(value="/upload",method = RequestMethod.POST)
	// 일반 데이터 + 파일 데이터를 같이 처리하는 MultipartHttpServletRequest
	public ModelAndView upload(MultipartHttpServletRequest multipartRequest,HttpServletResponse response)
	  throws Exception{
		//이제, 폼 창에서, 사용자가 임력한 일반데이터 2개와 , 이미지 데이터 여러개르 처리준비 
		//이코딩 utf-8
		multipartRequest.setCharacterEncoding("utf-8");
		// 일반 데이터와 파일 이미지 데이터 불러오기
		Map map = new HashMap();
		// 일반 데이터를 반복을 도와주는 Enumeration 타입 할당 
		Enumeration enu=multipartRequest.getParameterNames();
		//일반 데이터의 키와 값의 구조로 저장 
		while(enu.hasMoreElements()){
			//키에 대응되는 값을 불러오기.
			String name=(String)enu.nextElement();
			
			String value=multipartRequest.getParameter(name);
			//System.out.println(name+", "+value);
			map.put(name,value);
		}
		//1번 종료
		
		//2번 시작 
		//저장된 이미지 목록도 가져오고, 실제 물리경로 파일로 생성해서 저장 
		List fileList= fileProcess(multipartRequest);
		// 임시 저장소 map 컬렉션이 파일의 목록을 저장 
		map.put("fileList", fileList);
		ModelAndView mav = new ModelAndView();
		//결과 뷰에서 사용할 수 있게 데이터 전달하는 과정 
		mav.addObject("map", map);
		mav.setViewName("result");
		return mav;
	}
	
	//실제 이미지를 처리하는 로직.
	private List<String> fileProcess(MultipartHttpServletRequest multipartRequest) throws Exception{
		// 임시 이미지 파일 이름을 저장할 리스트
		List<String> fileList= new ArrayList<String>();
		// Iterator 반복 처리를 도와주는 도구 .
		// multipartRequest.getFileNames(); -> 이미지 파일의 이름ㅇ르 가지고 옴
		Iterator<String> fileNames = multipartRequest.getFileNames();
		while(fileNames.hasNext()){
			// 폼이름 형식으로 되어 있는 부부분을 하나씩 가지고 옴
			String fileName = fileNames.next();
			//file1 처리하는 인스턴스 생성
			MultipartFile mFile = multipartRequest.getFile(fileName);
			// mfile 인스턴스의 도구중에 , 실제 파일 명르 불러오는 메서드 사용 
			String originalFileName=mFile.getOriginalFilename();
			// 리스트에 파일 이미지 저장 
			fileList.add(originalFileName);
			// 실제 저장소, 이미지 파일 업로드 하느 로직 
			// 이미지 파을을 처리하기 위한 인스턴스, 절대 경로를 포함 
			File file = new File(CURR_IMAGE_REPO_PATH +"\\"+ fileName);
			// 이미지 사이즈가 0이 아니다. 이미지파일이 존재한다. 
			if(mFile.getSize()!=0){ //File Null Check
				// 만약, 파일이 존재 안한다 면 실행 
				if(! file.exists()){ 
					//해당 경로의 부모 폴더를 만들고 true 반환 -> 실행 
					if(file.getParentFile().mkdirs()){ 
						//실제 경로에 이미지 파일을 생성함 , 빈 파일 
						file.createNewFile(); 
					}
				}
				//메몰에 있는 이미지르 길제 저장소에 옮기는 작업 
				mFile.transferTo(new File(CURR_IMAGE_REPO_PATH +"\\"+ originalFileName)); //�ӽ÷� ����� multipartFile�� ���� ���Ϸ� ����
			}
		}
		return fileList;
	}

}

