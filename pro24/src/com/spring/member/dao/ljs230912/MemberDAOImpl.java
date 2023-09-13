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
	
	//하나의 정보를 조회 할 때 필요한 매서드 : selectOne, 시스템 메서드
	//조건, 첫번째 인자 : member.xml  sql 문장 식별 아이디
	//두번째 인자 : id 를 전달  where id 여기에 사용될 예정 
	@Override 
	public MemberVO selectOneMember(String id) throws DataAccessException {
		MemberVO memberVO = null;
		// 한 아이디로 전체 회원정보 조회하는 sql 있어서, 재사용
		// type 안 맞아서 오류낫는데, 다운캐스트로 해결!
		memberVO = (MemberVO) sqlSession.selectOne("mapper.member.selectMemberById", id);
		return memberVO;
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
	public int updateMember(MemberVO memberVO) throws DataAccessException {
		int result =  sqlSession.update("mapper.member.updateMember", memberVO);
		return result;
	}

	
}
