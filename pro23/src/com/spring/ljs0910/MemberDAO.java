package com.spring.ljs0910;

import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MemberDAO {
	public static SqlSessionFactory sqlMapper = null;

	//getInstance() -> sqlMapper 인스턴스가 할당이 안되었다면 , 초기화 작업
	// 인스턴스 구성요소
	// 1)디비에 연결하기 위한 환경변수, 서버주소, 드라이버 ,계정, 패스워드 
	private static SqlSessionFactory getInstance() {
		if (sqlMapper == null) {
			try {
				String resource = "mybatis/SqlMapConfig.xml";
				Reader reader = Resources.getResourceAsReader(resource);
				sqlMapper = new SqlSessionFactoryBuilder().build(reader);
				reader.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sqlMapper;

	}
	// 과제 연습, selectAllMemberList() : 단순 디비의 내용을 조회.
	// selectAllMemberList() : 이 메서드는 매개 변수 없음
	//반환은 모델 클래스 MemberVO 타입을 요소로 가지는 리스트 형
	
	public List<MemberVO> selectAllMemberList() {
		sqlMapper = getInstance();
		// session -> sql
		SqlSession session = sqlMapper.openSession();
		List<MemberVO> memlist = null;
		memlist = session.selectList("mapper.member.selectAllMemberList");
		return memlist;
	}
	
//	 public List<HashMap<String, String>> selectAllMemberList() { 
//		sqlMapper = getInstance(); 
//     	SqlSession session = sqlMapper.openSession();
//		List<HashMap<String, String>> memlist = null; 
//   	memlist = session.selectList("mapper.member.selectAllMemberList"); 
//		return memlist; 
//	 }
	
}
