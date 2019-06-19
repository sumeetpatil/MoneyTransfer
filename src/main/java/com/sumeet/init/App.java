package com.sumeet.init;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.sumeet.controller.AccountController;
import com.sumeet.dao.AccountDao;
import com.sumeet.model.Account;
import com.sumeet.service.AccountService;

import io.javalin.Javalin;

public class App {
	public static void main(String args[]) {
		Map<Integer, Account> myMockDBForAccounts = new ConcurrentHashMap<>();
		Javalin javalin = Javalin.create().start(7000);
		AccountService accountService = new AccountService(new AccountDao(myMockDBForAccounts));
		AccountController controller = new AccountController();
		controller.createApis(accountService, javalin);
	}
}
