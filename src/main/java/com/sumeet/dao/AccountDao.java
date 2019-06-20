package com.sumeet.dao;

import java.util.Map;

import com.sumeet.model.Account;

public class AccountDao {
	private static final String NO_DB_CONFIGURED = "No DB configured";
	private Map<Integer, Account> accounts;

	public AccountDao(Map<Integer, Account> accounts) {
		this.accounts = accounts;
	}

	public Account read(int id) throws AccountDaoException {
		if (accounts == null) {
			throw new AccountDaoException(NO_DB_CONFIGURED);
		}
		return accounts.get(id);
	}

	public Account write(Account acc) throws AccountDaoException {
		if (accounts == null) {
			throw new AccountDaoException(NO_DB_CONFIGURED);
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
			throw new AccountDaoException(NO_DB_CONFIGURED);
		}
		accounts.put(acc.getId(), acc);
	}
}
