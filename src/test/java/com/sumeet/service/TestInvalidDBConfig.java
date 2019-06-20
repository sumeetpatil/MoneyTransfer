package com.sumeet.service;

import org.junit.Test;

import com.sumeet.dao.AccountDao;
import com.sumeet.dao.AccountDaoException;
import com.sumeet.model.Account;
import com.sumeet.model.Transfer;

public class TestInvalidDBConfig {
	/* no account dao configured or invalid DB config */
	AccountService service = new AccountService(new AccountDao(null));

	@Test(expected = AccountDaoException.class)
	public void testInvalidDBCreate() throws AccountServiceException, AccountDaoException {
		service.createAccount(new Account("INR", 0, "test", "sumeet"));
	}

	@Test(expected = AccountDaoException.class)
	public void testInvalidDBRead() throws AccountServiceException, AccountDaoException {
		service.readAccount(1);
	}

	@Test(expected = AccountDaoException.class)
	public void testInvalidDBUTransfer() throws AccountServiceException, AccountDaoException {
		service.transfer(new Transfer(1, 2, 10));
	}
}
