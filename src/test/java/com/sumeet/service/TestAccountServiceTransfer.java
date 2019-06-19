package com.sumeet.service;

import static org.junit.Assert.assertEquals;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.After;
import org.junit.Test;

import com.sumeet.dao.AccountDao;
import com.sumeet.model.Account;
import com.sumeet.model.Transfer;

public class TestAccountServiceTransfer {
	private Map<Integer, Account> map = new ConcurrentHashMap<>();
	private AccountService service = new AccountService(new AccountDao(map));

	@Test
	public void transfer() throws AccountServiceException {
		service.createAccount(new Account("INR", 10, "test1", "sumeet1"));
		service.createAccount(new Account("INR", 0, "test2", "sumeet2"));
		Transfer transfer = new Transfer(1, 2, 10);

		assertEquals("Success", service.transfer(transfer));

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
	public void transferCurrencyMismatch() throws AccountServiceException {
		service.createAccount(new Account("INR", 10, "test1", "sumeet1"));
		service.createAccount(new Account("USD", 0, "test2", "sumeet2"));
		Transfer transfer = new Transfer(1, 2, 10);

		service.transfer(transfer);
	}

	@Test(expected = AccountServiceException.class)
	public void transferInvalidSrcAcc() throws AccountServiceException {
		service.createAccount(new Account("INR", 10, "test1", "sumeet1"));
		service.createAccount(new Account("INR", 0, "test2", "sumeet2"));
		Transfer transfer = new Transfer(10, 2, 10);
		service.transfer(transfer);
	}

	@Test(expected = AccountServiceException.class)
	public void transferInvalidDestAcc() throws AccountServiceException {
		service.createAccount(new Account("INR", 10, "test1", "sumeet1"));
		service.createAccount(new Account("INR", 0, "test2", "sumeet2"));
		Transfer transfer = new Transfer(1, 3, 10);
		service.transfer(transfer);
	}

	@Test(expected = AccountServiceException.class)
	public void transferInvalidBalance() throws AccountServiceException {
		service.createAccount(new Account("INR", 0, "test1", "sumeet1"));
		service.createAccount(new Account("INR", 0, "test2", "sumeet2"));
		Transfer transfer = new Transfer(1, 2, 10);
		service.transfer(transfer);
	}

	@After
	public void clear() {
		map.clear();
	}
}
