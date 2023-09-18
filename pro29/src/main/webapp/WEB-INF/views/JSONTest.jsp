<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"  isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"  />
<!DOCTYPE html>
<html>
<head>
<title>JSONTest</title>
<script src="http://code.jquery.com/jquery-latest.js"></script>  
<script>
/* 클라이언트 -> 서버  : 데이터를 json이라는 문자열 형태로 전달함. */ 

  $(function() {
	  /* 아이디 checkJson 클릭 이벤트가 발생하면, 동작하는 함수로 작성 */ 
      $("#checkJson").click(function() {
      	var member = { id:"park", 
  			    name:"박지성",
  			    pwd:"1234", 
  			    email:"park@test.com" };
      	/* js에서 비동기 통신을 하는 것을 말함, 서버에 데이터 ,비동기 형식으로 전달함. */
  	$.ajax({
        type:"post",
        //폼 태그에 action 속성과 비슷한 열할 , 서버에게 전달하는 주소
        url:"${contextPath}/test/info",
        //MIME TYPE, 웹 상에서 데이터 의 형타를 구분 짓는 요소
        contentType: "application/json",
        //작성은 JS로 했지만 ,전달은 문자열로 바꿔서 전달.
        data :JSON.stringify(member),
        //서버로 부터, 응답을 잘 받거나 못받았을 때의 로직 
     success:function (data,textStatus){
     },
     error:function(data,textStatus){
        alert("에러가 발생했습니다.");
     },
     complete:function(data,textStatus){
     }
  });  //end ajax	

   });
});
</script>
</head>
<body>
  <input type="button" id="checkJson" value="회원 정보 보내기"/><br><br>
  <div id="output"></div>
</body>
</html>