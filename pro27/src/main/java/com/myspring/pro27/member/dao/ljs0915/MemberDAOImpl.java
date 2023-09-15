package com.myspring.pro27.member.dao.ljs0915;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.myspring.pro27.member.vo.MemberVO;

@Repository("memberDAO.ljs0915")
public class MemberDAOImpl implements MemberDAO {
	@Autowired
	private SqlSession sqlSession;

	@Override
	public List selectAllMemberList() throws DataAccessException {
		List<MemberVO> membersList = null;
		membersList = sqlSession.selectList("mapper.member.selectAllMemberList");
		return membersList;
	}

	@Override
	public int insertMember(MemberVO memberVO) throws DataAccessException {
		int result = sqlSession.insert("mapper.member.insertMember", memberVO);
		return result;
	}

	@Override
	public int deleteMember(String id) throws DataAccessException {
		int result = sqlSession.delete("mapper.member.deleteMember", id);
		return result;
	}
	
	@Override
	public MemberVO loginById(MemberVO memberVO) throws DataAccessException{
		//클라이언트 -> 서버 : 현재 박스에는 아디와 패스워드 만!
		  MemberVO vo = sqlSession.selectOne("mapper.member.loginById",memberVO);
		//디비 찍고 , -> 해당유저의 모든 정보를 가져 옴 , 박스에는 모든 데이터 들어있음
		return vo;
	}

	@Override
	public MemberVO selectOneMember(String id) throws DataAccessException {
		MemberVO memberVO = null;
		memberVO = (MemberVO) sqlSession.selectOne("mapper.member.selectMemberById", id);
		return memberVO;
	}

	@Override
	public int updateMember(MemberVO memberVO) throws DataAccessException {
		int result =  sqlSession.update("mapper.member.updateMember", memberVO);
		return result;
	}

}
