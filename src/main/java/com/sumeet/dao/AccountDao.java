package com.sumeet.dao;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.sumeet.model.Account;

public class AccountDao {
	private Map<Integer, Account> accounts;

	public AccountDao(Map<Integer, Account> accounts) {
		this.accounts = accounts;
	}

	public Account read(int id) {
		return accounts.get(id);
	}

	public Account write(Account acc) {
		int accId = getCount();
		acc.setId(accId);
		if (accounts == null) {
			accounts = new ConcurrentHashMap<>();
		}

		accounts.put(accId, acc);
		return acc;
	}

	private synchronized int getCount() {
		return accounts.size() + 1;
	}

	public void update(Account acc) {
		accounts.put(acc.getId(), acc);
	}
}
