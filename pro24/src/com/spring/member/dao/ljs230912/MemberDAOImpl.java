package com.spring.member.dao.ljs230912;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.dao.DataAccessException;

import com.spring.member.vo.MemberVO;

//동네 3-2 ,실제 sql 접근을 하기 위한 작업.
public class MemberDAOImpl implements MemberDAO {
	private SqlSession sqlSession;
	//동네 4에 외주 주기 위한 인스턴스
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@Override
	public List selectAllMemberList() throws DataAccessException {
		//임시로 담을 리스트
		List<MemberVO> membersList = null;
		/* sqlSession, 여러 매서드 조회 추가 수정 삭제 */
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
		int result =  sqlSession.delete("mapper.member.deleteMember", id);
		return result;
	}

	@Override
	public int updateMember(String id) throws DataAccessException {
		int result =  sqlSession.update("mapper.member.updateMember", id);
		return result;
	}
}
