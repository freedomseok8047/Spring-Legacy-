package com.spring.member.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.spring.member.dao.MemberDAO;

public class MemberServiceImpl implements MemberService {
	//DI, 스프링 프레임 워크 , 느슨한 결함으로, 인터페이스 참조형 변수를 가지고 있다. 또는 
		// 포함 관곌고 가지고 있다 서버가 동작시, 시스템 (스프링)에 메모리에 로드가 되어서 
		//사용이 가능하다 .(언제든)
	private MemberDAO memberDAO;

	public void setMemberDAO(MemberDAO memberDAO) {
		this.memberDAO = memberDAO;
	}

	@Override
	public List listMembers() throws DataAccessException {
		List membersList = null;
		// 서비스 -> DAO, 데이터에 접근하기 위한 작업
		membersList = memberDAO.selectAllMembers();
		return membersList;
	}

}
