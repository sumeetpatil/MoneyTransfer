package com.sumeet.controller;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.client.util.StringContentProvider;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.Test;

public class TestInvalidDBConfig extends TestHelperControllerInvalidDB {
	@Test
	public void testInvalidDBCreate() throws InterruptedException, TimeoutException, ExecutionException {
		Request request = getHttpClient().POST(HTTP_HOST + getPort() + "/account");
		request.content(new StringContentProvider(
				"{\"currency\":\"INR\",\"balance\":\"40\",\"bank\":\"test\",\"userName\":\"sumeet\"}"));
		ContentResponse response = request.send();
		String res = new String(response.getContent());
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR_500, response.getStatus());
		assertEquals("Internal server error. Please contanct your admin.", res);
	}

	@Test
	public void testInvalidDBRead() throws InterruptedException, TimeoutException, ExecutionException {
		ContentResponse response = getHttpClient().GET(HTTP_HOST + getPort() + "/account/1");
		String res = new String(response.getContent());
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR_500, response.getStatus());
		assertEquals("Internal server error. Please contanct your admin.", res);
	}

	@Test
	public void testInvalidDBTransfer() throws InterruptedException, TimeoutException, ExecutionException {
		int port = getPort();
		Request request = getHttpClient().POST(HTTP_HOST + port + "/transfer");
		request.content(new StringContentProvider("{\"srcAcc\":1,\"destAcc\":2,\"amount\":10}"));
		ContentResponse response = request.send();
		String res = new String(response.getContent());
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR_500, response.getStatus());
		assertEquals("Internal server error. Please contanct your admin.", res);
	}
}
