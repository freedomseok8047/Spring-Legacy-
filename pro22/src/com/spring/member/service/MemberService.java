package com.spring.member.service;


import java.util.List;

import org.springframework.dao.DataAccessException;
//인터페이스를 구현 한 클래스
public interface MemberService {
	public List listMembers() throws DataAccessException;
}
