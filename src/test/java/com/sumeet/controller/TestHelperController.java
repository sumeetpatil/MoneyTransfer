package com.sumeet.controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.jetty.client.HttpClient;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import com.sumeet.dao.AccountDao;
import com.sumeet.model.Account;
import com.sumeet.service.AccountService;

import io.javalin.Javalin;

public class TestHelperController {
	private static int port;
	private static Map<Integer, Account> map = new ConcurrentHashMap<>();
	private static Javalin app;
	private static HttpClient httpClient;
	private static AccountService service;

	@BeforeClass
	public static void setup() throws Exception {
		port = getRandomPort();
		service = new AccountService(new AccountDao(map));
		Javalin javalin = Javalin.create();
		javalin.config.showJavalinBanner = false;
		app = javalin.start(port);
		new AccountController().createApis(service, app);
		httpClient = new HttpClient();
		httpClient.start();
	}

	@Before
	public void clear() {
		map.clear();
	}

	@AfterClass
	public static void tearDown() throws Exception {
		app.stop();
		httpClient.stop();
	}

	private static Integer getRandomPort() throws IOException {
		try (ServerSocket socket = new ServerSocket(0);) {
			return socket.getLocalPort();

		}
	}

	public static int getPort() {
		return port;
	}

	public static Map<Integer, Account> getMap() {
		return map;
	}

	public static HttpClient getHttpClient() {
		return httpClient;
	}

	public static AccountService getService() {
		return service;
	}
}
