package com.sumeet.service;

import static org.junit.Assert.assertEquals;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.After;
import org.junit.Test;

import com.sumeet.dao.AccountDao;
import com.sumeet.dao.AccountDaoException;
import com.sumeet.model.Account;

public class TestAccountService {
	private Map<Integer, Account> map = new ConcurrentHashMap<>();
	private AccountService service = new AccountService(new AccountDao(map));

	@Test
	public void accountCreateRead() throws AccountServiceException, AccountDaoException {
		service.createAccount(new Account("INR", 0, "test1", "sumeet1"));
		service.createAccount(new Account("INR", 10, "test2", "sumeet2"));
		Account accRead1 = service.readAccount(1);
		assertEquals(0, accRead1.getBalance());
		assertEquals("test1", accRead1.getBank());
		assertEquals("INR", accRead1.getCurrency());
		assertEquals(1, accRead1.getId());
		assertEquals("sumeet1", accRead1.getUserName());

		Account accRead2 = service.readAccount(2);
		assertEquals(10, accRead2.getBalance());
		assertEquals("test2", accRead2.getBank());
		assertEquals("INR", accRead2.getCurrency());
		assertEquals(2, accRead2.getId());
		assertEquals("sumeet2", accRead2.getUserName());
	}

	@Test(expected = AccountServiceException.class)
	public void accountCreateInvalidCurrency() throws AccountServiceException, AccountDaoException {
		service.createAccount(new Account("INR1", 0, "test1", "sumeet1"));
	}

	@Test(expected = AccountServiceException.class)
	public void accountInvalidRead() throws AccountServiceException, AccountDaoException {
		service.readAccount(1);
	}

	@Test(expected = AccountServiceException.class)
	public void accountCreateMissingData1() throws AccountServiceException, AccountDaoException {
		service.createAccount(new Account("", 0, "", "sumeet1"));
	}

	@Test(expected = AccountServiceException.class)
	public void accountCreateMissingData2() throws AccountServiceException, AccountDaoException {
		service.createAccount(new Account("", 0, "", null));
	}

	@Test(expected = AccountServiceException.class)
	public void accountCreateMissingData3() throws AccountServiceException, AccountDaoException {
		service.createAccount(new Account("INR", 0, "test", ""));
	}

	@After
	public void clear() {
		map.clear();
	}
}
