package com.sumeet.controller;

import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.sumeet.model.Account;
import com.sumeet.model.Transfer;
import com.sumeet.service.AccountService;
import com.sumeet.service.AccountServiceException;

import io.javalin.Javalin;
import io.javalin.plugin.json.JavalinJson;

public class AccountController {
	private Logger logger = LoggerFactory.getLogger(AccountController.class);

	public void createApis(AccountService accService, Javalin app) {
		app.post("/account", ctx -> {
			Account acc = JavalinJson.fromJson(ctx.body(), Account.class);
			accService.createAccount(acc);
			ctx.result(JavalinJson.toJson(acc));
		});

		app.get("/account/:id", ctx -> {
			ctx.result(JavalinJson.toJson(accService.readAccount(Integer.valueOf(ctx.pathParam("id")))));
		});

		app.post("/transfer", ctx -> {
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
			ctx.result("Invalid json payload");
		});

		// TODO: This is currently a generalized exception. Please improve.
		app.exception(UnrecognizedPropertyException.class, (e, ctx) -> {
			logger.warn(e.getMessage(), e);
			ctx.status(HttpStatus.BAD_REQUEST_400);
			ctx.result("Invalid payload");
		});
	}
}