package com.sumeet.controller;

import static org.junit.Assert.assertEquals;

import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.client.util.StringContentProvider;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sumeet.model.Account;

public class TestAccount extends TestHelperController {
	@Test
	public void testAccountCreate() throws Exception {
		Request request = getHttpClient().POST("http://localhost:" + getPort() + "/account");
		request.content(new StringContentProvider(
				"{\"currency\":\"INR\",\"balance\":\"40\",\"bank\":\"test\",\"userName\":\"sumeet\"}"));
		ContentResponse response = request.send();
		String res = new String(response.getContent());
		assertEquals(HttpStatus.OK_200, response.getStatus());
		Account acc = new ObjectMapper().readValue(res, Account.class);
		assertEquals(40, acc.getBalance());
		assertEquals("test", acc.getBank());
		assertEquals("INR", acc.getCurrency());
		assertEquals("sumeet", acc.getUserName());
		assertEquals(1, acc.getId());

		response = request.send();
		assertEquals(HttpStatus.OK_200, response.getStatus());
		res = new String(response.getContent());
		acc = new ObjectMapper().readValue(res, Account.class);
		assertEquals(40, acc.getBalance());
		assertEquals("test", acc.getBank());
		assertEquals("INR", acc.getCurrency());
		assertEquals("sumeet", acc.getUserName());
		assertEquals(2, acc.getId());
	}

	@Test
	public void testAccountGet() throws Exception {
		getService().createAccount(new Account("INR", 40, "test1", "hello"));
		getService().createAccount(new Account("INR", 50, "test2", "world"));
		ContentResponse response = getHttpClient().GET("http://localhost:" + getPort() + "/account/1");
		String res = new String(response.getContent());
		assertEquals(HttpStatus.OK_200, response.getStatus());
		Account acc = new ObjectMapper().readValue(res, Account.class);
		assertEquals(40, acc.getBalance());
		assertEquals("test1", acc.getBank());
		assertEquals("INR", acc.getCurrency());
		assertEquals("hello", acc.getUserName());
		assertEquals(1, acc.getId());

		response = getHttpClient().GET("http://localhost:" + getPort() + "/account/2");
		res = new String(response.getContent());
		assertEquals(HttpStatus.OK_200, response.getStatus());
		acc = new ObjectMapper().readValue(res, Account.class);
		assertEquals(50, acc.getBalance());
		assertEquals("test2", acc.getBank());
		assertEquals("INR", acc.getCurrency());
		assertEquals("world", acc.getUserName());
		assertEquals(2, acc.getId());
	}

	@Test
	public void testAccountCreateGet() throws Exception {
		Request request = getHttpClient().POST("http://localhost:" + getPort() + "/account");
		request.content(new StringContentProvider(
				"{\"currency\":\"INR\",\"balance\":\"40\",\"bank\":\"test\",\"userName\":\"sumeet\"}"));
		ContentResponse response = request.send();
		String res = new String(response.getContent());
		assertEquals(HttpStatus.OK_200, response.getStatus());
		Account acc = new ObjectMapper().readValue(res, Account.class);
		assertEquals(40, acc.getBalance());
		assertEquals("test", acc.getBank());
		assertEquals("INR", acc.getCurrency());
		assertEquals("sumeet", acc.getUserName());
		assertEquals(1, acc.getId());

		response = getHttpClient().GET("http://localhost:" + getPort() + "/account/1");
		res = new String(response.getContent());
		assertEquals(HttpStatus.OK_200, response.getStatus());
		acc = new ObjectMapper().readValue(res, Account.class);
		assertEquals(40, acc.getBalance());
		assertEquals("test", acc.getBank());
		assertEquals("INR", acc.getCurrency());
		assertEquals("sumeet", acc.getUserName());
		assertEquals(1, acc.getId());
	}

	@Test
	public void testInvalidAccountRead() throws Exception {
		ContentResponse response = getHttpClient().GET("http://localhost:" + getPort() + "/account/1");
		String res = new String(response.getContent());
		assertEquals(HttpStatus.BAD_REQUEST_400, response.getStatus());
		assertEquals("No such account", res);
	}

	@Test
	public void testInvalidCurrencyCode() throws Exception {
		Request request = getHttpClient().POST("http://localhost:" + getPort() + "/account");
		request.content(new StringContentProvider(
				"{\"currency\":\"IN\",\"balance\":\"40\",\"bank\":\"test\",\"userName\":\"sumeet\"}"));
		ContentResponse response = request.send();
		String res = new String(response.getContent());
		assertEquals(HttpStatus.BAD_REQUEST_400, response.getStatus());
		assertEquals("Invalid currecny code", res);
	}

	@Test
	public void testAccountInvalidPayload1() throws Exception {
		Request request = getHttpClient().POST("http://localhost:" + getPort() + "/account");
		request.content(new StringContentProvider("test"));
		ContentResponse response = request.send();
		String res = new String(response.getContent());
		assertEquals(HttpStatus.BAD_REQUEST_400, response.getStatus());
		assertEquals("Invalid json payload", res);
	}

	@Test
	public void testAccountInvalidPayload2() throws Exception {
		Request request = getHttpClient().POST("http://localhost:" + getPort() + "/account");
		request.content(new StringContentProvider(
				"{\"currency1\":\"INR\",\"balance\":\"40\",\"bank\":\"test\",\"userName\":\"sumeet\"}"));
		ContentResponse response = request.send();
		String res = new String(response.getContent());
		assertEquals(HttpStatus.BAD_REQUEST_400, response.getStatus());
		assertEquals("Invalid payload", res);
	}
}
