package com.spring.member.dao.ljs230912;

import java.util.List;

import org.springframework.dao.DataAccessException;
import com.spring.member.vo.MemberVO;

//동네 3번 
public interface MemberDAO {
	 public List selectAllMemberList() throws DataAccessException;
	 public int insertMember(MemberVO memberVO) throws DataAccessException ;
	 public int deleteMember(String id) throws DataAccessException;
	 public int updateMember(MemberVO memberVO) throws DataAccessException;
	//동네 2번에서 쓸 메서드 정의하기 : selectOneMember
	 public MemberVO selectOneMember(String id) throws DataAccessException;
}
