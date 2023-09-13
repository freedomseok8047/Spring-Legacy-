package com.spring.account;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation=Propagation.REQUIRED)
public class AccountService {
	//동네2번 
	private AccountDAO accDAO;

	public void setAccDAO(AccountDAO accDAO) {
		this.accDAO = accDAO;
	}
	//비지니스 로직에서 , 하나의 기능에 2가지 sql 문장을 그룹화 
	public void sendMoney() throws Exception {
		//항상 세트로 실행되어야함 
		//실제작업 동네 3번으로 외주 
		accDAO.updateBalance1();
		accDAO.updateBalance2();
	}
}


