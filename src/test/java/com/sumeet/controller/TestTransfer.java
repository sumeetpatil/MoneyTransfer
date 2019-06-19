package com.sumeet.controller;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.client.util.StringContentProvider;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sumeet.model.Account;

public class TestTransfer extends TestHelperController {
	@Test
	public void testTransferSuccess() throws Exception {
		int port = getPort();
		getService().createAccount(new Account("INR", 40, "test1", "hello"));
		getService().createAccount(new Account("INR", 50, "test2", "world"));
		Request request = getHttpClient().POST("http://localhost:" + port + "/transfer");
		request.content(new StringContentProvider("{\"srcAcc\":1,\"destAcc\":2,\"amount\":10}"));
		ContentResponse response = request.send();
		String res = new String(response.getContent());
		assertEquals(HttpStatus.OK_200, response.getStatus());
		assertEquals("Success", res);

		response = getHttpClient().GET("http://localhost:" + getPort() + "/account/1");
		res = new String(response.getContent());
		assertEquals(HttpStatus.OK_200, response.getStatus());
		Account acc = new ObjectMapper().readValue(res, Account.class);
		assertEquals(30, acc.getBalance());
		assertEquals("test1", acc.getBank());
		assertEquals("INR", acc.getCurrency());
		assertEquals("hello", acc.getUserName());
		assertEquals(1, acc.getId());

		response = getHttpClient().GET("http://localhost:" + getPort() + "/account/2");
		res = new String(response.getContent());
		assertEquals(HttpStatus.OK_200, response.getStatus());
		acc = new ObjectMapper().readValue(res, Account.class);
		assertEquals(60, acc.getBalance());
		assertEquals("test2", acc.getBank());
		assertEquals("INR", acc.getCurrency());
		assertEquals("world", acc.getUserName());
		assertEquals(2, acc.getId());
	}

	@Test
	public void testTransferInvalidAccountSrcAccount() throws Exception {
		Map<Integer, Account> map = getMap();
		int port = getPort();
		map.put(1, new Account("INR", 40, "test1", "hello"));
		map.put(2, new Account("INR", 50, "test2", "world"));
		Request request = getHttpClient().POST("http://localhost:" + port + "/transfer");
		request.content(new StringContentProvider("{\"srcAcc\":10,\"destAcc\":2,\"amount\":10}"));
		ContentResponse response = request.send();
		String res = new String(response.getContent());
		assertEquals(HttpStatus.BAD_REQUEST_400, response.getStatus());
		assertEquals("Invalid source account", res);
	}

	@Test
	public void testTransferInvalidAccountDestAccount() throws Exception {
		Map<Integer, Account> map = getMap();
		int port = getPort();
		map.put(1, new Account("INR", 40, "test1", "hello"));
		map.put(2, new Account("INR", 50, "test2", "world"));
		Request request = getHttpClient().POST("http://localhost:" + port + "/transfer");
		request.content(new StringContentProvider("{\"srcAcc\":1,\"destAcc\":20,\"amount\":10}"));
		ContentResponse response = request.send();
		String res = new String(response.getContent());
		assertEquals(HttpStatus.BAD_REQUEST_400, response.getStatus());
		assertEquals(res, "Invalid destination account");
	}

	@Test
	public void testTransferInvalidAccountInSuffBalance() throws Exception {
		Map<Integer, Account> map = getMap();
		int port = getPort();
		map.put(1, new Account("INR", 5, "test1", "hello"));
		map.put(2, new Account("INR", 10, "test2", "world"));
		Request request = getHttpClient().POST("http://localhost:" + port + "/transfer");
		request.content(new StringContentProvider("{\"srcAcc\":1,\"destAcc\":2,\"amount\":10}"));
		ContentResponse response = request.send();
		String res = new String(response.getContent());
		assertEquals(HttpStatus.BAD_REQUEST_400, response.getStatus());
		assertEquals(res, "Insufficient balance in sourc account");
	}

	@Test
	public void testInvalidPayload1() throws Exception {
		int port = getPort();
		Request request = getHttpClient().POST("http://localhost:" + port + "/transfer");
		request.content(new StringContentProvider("{\"srcAcc1\":1,\"destAcc\":2,\"amount\":10}"));
		ContentResponse response = request.send();
		String res = new String(response.getContent());
		assertEquals(HttpStatus.BAD_REQUEST_400, response.getStatus());
		assertEquals("Invalid payload", res);
	}

	@Test
	public void testInvalidPayload2() throws Exception {
		int port = getPort();
		Request request = getHttpClient().POST("http://localhost:" + port + "/transfer");
		request.content(new StringContentProvider("test"));
		ContentResponse response = request.send();
		String res = new String(response.getContent());
		assertEquals("Invalid json payload", res);
		assertEquals(HttpStatus.BAD_REQUEST_400, response.getStatus());
	}
}
