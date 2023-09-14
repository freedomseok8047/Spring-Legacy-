package com.spring.ex02;

public class LoginVO {
	private String userID;
	private String userName;
	private String email;
	
	//지금은, 수동 오버라이딩 해서 , 게터 세터 설정하지만 ,
	//롬복 라이브러리 사용하면 자동으로 가능 
	
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
