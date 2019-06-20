package com.sumeet.service;

import com.sumeet.dao.AccountDao;
import com.sumeet.dao.AccountDaoException;
import com.sumeet.model.Account;
import com.sumeet.model.Currency;
import com.sumeet.model.Transfer;

public class AccountService {
	private static final String SUCCESS = "Success";
	private static final String INSUFFICIENT_BALANCE_IN_SOURC_ACCOUNT = "Insufficient balance in sourc account";
	private static final String CURRENCY_MISMATCH = "Currency mismatch";
	private static final String INVALID_DESTINATION_ACCOUNT = "Invalid destination account";
	private static final String INVALID_SOURCE_ACCOUNT = "Invalid source account";
	private static final String NO_SUCH_ACCOUNT = "No such account";
	private static final String INVALID_CURRECNY_CODE = "Invalid currecny code";
	private static final String MISSING_USER_NAME = "Missing data";
	private AccountDao dao;

	public AccountService(AccountDao dao) {
		this.dao = dao;
	}

	public Account createAccount(Account acc) throws AccountServiceException, AccountDaoException {
		String userName = acc.getUserName();
		String bank = acc.getBank();
		String currency = acc.getCurrency();
		if (userName == null || userName.isEmpty() || bank == null || bank.isEmpty() || currency == null
				|| currency.isEmpty()) {
			throw new AccountServiceException(MISSING_USER_NAME);
		}

		if (!Currency.contains(currency)) {
			throw new AccountServiceException(INVALID_CURRECNY_CODE);
		}
		return dao.write(acc);
	}

	public Account readAccount(int id) throws AccountServiceException, AccountDaoException {
		Account acc = dao.read(id);
		if (acc == null) {
			throw new AccountServiceException(NO_SUCH_ACCOUNT);
		}
		return acc;
	}

	public String transfer(Transfer transfer) throws AccountServiceException, AccountDaoException {
		transfer.getSrcAcc();
		Account srcAcc = dao.read(transfer.getSrcAcc());
		if (srcAcc == null) {
			throw new AccountServiceException(INVALID_SOURCE_ACCOUNT);
		}
		Account destAcc = dao.read(transfer.getDestAcc());
		if (destAcc == null) {
			throw new AccountServiceException(INVALID_DESTINATION_ACCOUNT);
		}

		if (!destAcc.getCurrency().equals(srcAcc.getCurrency())) {
			throw new AccountServiceException(CURRENCY_MISMATCH);
		}

		int amount = transfer.getAmount();
		int srcBal = srcAcc.getBalance();
		int destBal = destAcc.getBalance();
		int diff = srcBal - amount;
		if (diff < 0) {
			throw new AccountServiceException(INSUFFICIENT_BALANCE_IN_SOURC_ACCOUNT);
		}

		srcAcc.setBalance(diff);
		destAcc.setBalance(destBal + amount);

		commit(srcAcc, destAcc);
		return SUCCESS;
	}

	/**
	 * Do this in a single commit to DB. This is to follow ACID properties
	 * 
	 * @param srcAcc
	 * @param destAcc
	 * @throws AccountDaoException
	 */
	private void commit(Account srcAcc, Account destAcc) throws AccountDaoException {
		dao.update(srcAcc);
		dao.update(destAcc);
	}
}
