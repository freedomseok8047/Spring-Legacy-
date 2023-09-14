<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" 
    isELIgnored="false"  %>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>

<%
  request.setCharacterEncoding("UTF-8");
%>    


<html>
<head>
<meta charset=UTF-8">
<title>결과창</title>
</head>
<body>
<%-- <h1>아이디 : ${userID}</h1> --%>
<%-- <h1>이름   : ${userName}</h1>  --%>
<%-- <h1>아이디 : ${userID2}</h1>
<h1>이름   : ${userName2}</h1> --%>
<%-- <h1>아이디 : ${info.userID }</h1>
<h1>이름   : ${info.userName }</h1>
<h1>이메일 : ${info.email }</h1> --%>
<!-- 모델 클래스를 이용해서 , 단순 서버에서 설정한 임시 더미 데이터만 가져와서 출력하는 부분. -->
		<!-- model.addAttribute("userID", "lsy");
		model.addAttribute("userName", "이상용"); -->
		<h1>아이디 : ${userID}</h1>
		<h1>이름   : ${userName}</h1>
		
</body>
</html>
