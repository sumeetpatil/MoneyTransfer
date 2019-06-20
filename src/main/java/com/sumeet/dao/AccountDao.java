package com.sumeet.dao;

import java.util.Map;

import com.sumeet.model.Account;

public class AccountDao {
	private Map<Integer, Account> accounts;

	public AccountDao(Map<Integer, Account> accounts) {
		this.accounts = accounts;
	}

	public Account read(int id) throws AccountDaoException {
		if (accounts == null) {
			throw new AccountDaoException("No DB configured");
		}
		return accounts.get(id);
	}

	public Account write(Account acc) throws AccountDaoException {
		if (accounts == null) {
			throw new AccountDaoException("No DB configured");
		}
		int accId = getCount();
		acc.setId(accId);
		accounts.put(accId, acc);
		return acc;
	}

	private synchronized int getCount() {
		return accounts.size() + 1;
	}

	public void update(Account acc) throws AccountDaoException {
		if (accounts == null) {
			throw new AccountDaoException("No DB configured");
		}
		accounts.put(acc.getId(), acc);
	}
}
