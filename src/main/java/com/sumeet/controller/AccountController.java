package com.sumeet.controller;

import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.sumeet.dao.AccountDaoException;
import com.sumeet.model.Account;
import com.sumeet.model.Transfer;
import com.sumeet.service.AccountService;
import com.sumeet.service.AccountServiceException;

import io.javalin.Javalin;
import io.javalin.plugin.json.JavalinJson;

public class AccountController {
	private static final String INVALID_PAYLOAD = "Invalid payload";
	private static final String ACCOUNT_END_POINT = "/account";
	private static final String ACCOUNT_READ_END_POINT = "/account/:id";
	private static final String TRANSFER_END_POINT = "/transfer";
	private static final String INVALID_JSON_PAYLOAD = "Invalid json payload";
	private static final String INTERNAL_SERVER_ERROR = "Internal server error. Please contanct your admin.";
	private Logger logger = LoggerFactory.getLogger(AccountController.class);

	public void createApis(AccountService accService, Javalin app) {
		app.post(ACCOUNT_END_POINT, ctx -> {
			Account acc = JavalinJson.fromJson(ctx.body(), Account.class);
			accService.createAccount(acc);
			ctx.result(JavalinJson.toJson(acc));
		});

		app.get(ACCOUNT_READ_END_POINT, ctx -> {
			ctx.result(JavalinJson.toJson(accService.readAccount(Integer.valueOf(ctx.pathParam("id")))));
		});

		app.post(TRANSFER_END_POINT, ctx -> {
			Transfer transfer = JavalinJson.fromJson(ctx.body(), Transfer.class);
			String result = accService.transfer(transfer);
			ctx.result(result);
		});

		app.exception(AccountServiceException.class, (e, ctx) -> {
			logger.warn(e.getMessage(), e);
			ctx.status(HttpStatus.BAD_REQUEST_400);
			ctx.result(e.getMessage());
		});

		app.exception(JsonParseException.class, (e, ctx) -> {
			logger.warn(e.getMessage(), e);
			ctx.status(HttpStatus.BAD_REQUEST_400);
			ctx.result(INVALID_JSON_PAYLOAD);
		});

		// TODO: This is currently a generalized exception. Please improve.
		app.exception(UnrecognizedPropertyException.class, (e, ctx) -> {
			logger.warn(e.getMessage(), e);
			ctx.status(HttpStatus.BAD_REQUEST_400);
			ctx.result(INVALID_PAYLOAD);
		});

		app.exception(AccountDaoException.class, (e, ctx) -> {
			/* DB issue */
			logger.error(e.getMessage(), e);
			ctx.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
			ctx.result(INTERNAL_SERVER_ERROR);
		});
	}
}