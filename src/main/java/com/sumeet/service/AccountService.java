package com.sumeet.service;

import com.sumeet.dao.AccountDao;
import com.sumeet.model.Account;
import com.sumeet.model.Currency;
import com.sumeet.model.Transfer;

public class AccountService {
	private AccountDao dao;

	public AccountService(AccountDao dao) {
		this.dao = dao;
	}

	public Account createAccount(Account acc) throws AccountServiceException {
		String userName = acc.getUserName();
		String bank = acc.getBank();
		String currency = acc.getCurrency();
		if (userName == null || userName.isEmpty() || bank == null || bank.isEmpty() || currency == null
				|| currency.isEmpty()) {
			throw new AccountServiceException("Missing user name");
		}

		if (!Currency.contains(currency)) {
			throw new AccountServiceException("Invalid currecny code");
		}
		return dao.write(acc);
	}

	public Account readAccount(int id) throws AccountServiceException {
		Account acc = dao.read(id);
		if (acc == null) {
			throw new AccountServiceException("No such account");
		}
		return acc;
	}

	public String transfer(Transfer transfer) throws AccountServiceException {
		transfer.getSrcAcc();
		Account srcAcc = dao.read(transfer.getSrcAcc());
		if (srcAcc == null) {
			throw new AccountServiceException("Invalid source account");
		}
		Account destAcc = dao.read(transfer.getDestAcc());
		if (destAcc == null) {
			throw new AccountServiceException("Invalid destination account");
		}

		if (!destAcc.getCurrency().equals(srcAcc.getCurrency())) {
			throw new AccountServiceException("Currency mismatch");
		}

		int amount = transfer.getAmount();
		int srcBal = srcAcc.getBalance();
		int destBal = destAcc.getBalance();
		int diff = srcBal - amount;
		if (diff < 0) {
			throw new AccountServiceException("Insufficient balance in sourc account");
		}

		srcAcc.setBalance(diff);
		destAcc.setBalance(destBal + amount);

		commit(srcAcc, destAcc);
		return "Success";
	}

	/**
	 * Do this in a single commit to DB. This is to follow ACID properties
	 * 
	 * @param srcAcc
	 * @param destAcc
	 */
	private void commit(Account srcAcc, Account destAcc) {
		dao.update(srcAcc);
		dao.update(destAcc);
	}
}
