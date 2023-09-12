package com.spring.member.service.ljs230912;

import java.util.List;

import org.springframework.dao.DataAccessException;
import com.spring.member.vo.MemberVO;
//동네 2번 , 서비스, 실제적인 비지니스 로직들의 모음 
//한눈에 MemberService에 어떤 메서드로 기능을 구현했는지 알수 있다
public interface MemberService {
	 public List listMembers() throws DataAccessException;
	 public int addMember(MemberVO membeVO) throws DataAccessException;
	 public int removeMember(String id) throws DataAccessException;
	 public int updateMember(String id) throws DataAccessException;

}
