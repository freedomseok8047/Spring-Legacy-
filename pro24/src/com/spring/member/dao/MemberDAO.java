package com.spring.member.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import com.spring.member.vo.MemberVO;

//동네 3번 
public interface MemberDAO {
	 public List selectAllMemberList() throws DataAccessException;
	 public int insertMember(MemberVO memberVO) throws DataAccessException ;
	 public int deleteMember(String id) throws DataAccessException;
	 

}
