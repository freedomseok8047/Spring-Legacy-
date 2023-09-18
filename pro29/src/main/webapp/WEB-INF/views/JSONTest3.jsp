<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"  isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"  />
<!DOCTYPE html>
<html>
<head>
<title>JSONTest3</title>
<script src="http://code.jquery.com/jquery-latest.js"></script>  
<script>
  $(function() {
      $("#checkJson").click(function() {
      	var member = {memberNO:"888", 
	            id : "ljs update", 
	            pwd :"1234 update",
      			name:"이준석 업데이트",
	            email:"qqq@gmail.com 업데이트", 
	              };
  
  	$.ajax({
  	    //type:"POST",
        //url:"${contextPath}/boards2",
        //type:"PUT",
        //url:"${contextPath}/boards2/111",
        type:"DELETE",
        url:"${contextPath}/boards2/111",
        contentType: "application/json",
        data :JSON.stringify(member),
      success:function (data,textStatus){
          alert(data);
      },
      error:function(data,textStatus){
        alert("에러가 발생했습니다.");ㅣ
      },
      complete:function(data,textStatus){
      }
   });  //end ajax	

   });
});
</script>
</head>
<body>
  <input type="button" id="checkJson" value="새글 쓰기"/><br><br>
  <div id="output"></div>
</body>
</html>