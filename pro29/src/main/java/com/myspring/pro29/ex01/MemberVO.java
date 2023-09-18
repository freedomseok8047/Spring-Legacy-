package com.myspring.pro29.ex01;


public class MemberVO {
	
	private String id;
	private String pwd;
	private String name;
	private String email;
	
	public MemberVO() {
		
	}
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	//인스턴스를 문자열로 변호나해주는 부분도, Object 타입의 toString 사용하는 것이 아니라
	// 임의로 우리가 변환해서 사용 중 . 
	// 인스턴를 문자열로 변환해서 , 전달 시, 이 메서드를 활용함 
	public String toString() {
		String info = id+", "+ pwd+", "+ name+", "+ email;
		return info;
	}

}
