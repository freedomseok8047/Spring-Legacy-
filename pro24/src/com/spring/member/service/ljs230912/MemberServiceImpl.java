package com.spring.member.service.ljs230912;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
//2/import 변경 
import com.spring.member.dao.ljs230912.MemberDAO;
import com.spring.member.vo.MemberVO;

/*@Transactional(propagation=Propagation.REQUIRED) */
//동네 2 -2
public class MemberServiceImpl  implements MemberService{
		//동네 3번으로 외주보내기 위한 인스턴스 작업
	   private MemberDAO memberDAO;
	   public void setMemberDAO(MemberDAO memberDAO){
	      this.memberDAO = memberDAO;
	   }

	   @Override
	   public List listMembers() throws DataAccessException {
		   //임시 데이터를 담기 위한 인스턴스
	      List membersList = null;
	      //실제 작업 동네 3번으로 갑니다.
	      membersList = memberDAO.selectAllMemberList();
	      // 동네 3번 4번 갓다가 DB찍고 돌아옴
	      return membersList;
	   }
	   @Override
		public MemberVO getOneMember(String id) throws DataAccessException {
		   MemberVO memberVO = null;
		   // 동네 3번에 , dao 외주 주기 
		   // 없는 메서드 동네 3번에서 정의 : selectOneMember()
		   memberVO = memberDAO.selectOneMember(id);
		   return memberVO;
	   } 
		
	   @Override
	   public int addMember(MemberVO memberVO) throws DataAccessException {
	     return memberDAO.insertMember(memberVO);
	   }


	   @Override
	   public int removeMember(String id) throws DataAccessException {
	      return memberDAO.deleteMember(id);
	   }

	@Override
	public int updateMember(MemberVO memberVO) throws DataAccessException {
		return memberDAO.updateMember(memberVO);
	}


}
