package com.sumeet.model;

public class Account {
	private int id;
	private String currency;
	private int balance = 0;
	private String bank;
	private String userName;

	public Account(String currency, int initBal, String bank, String userName) {
		this.currency = currency;
		this.balance = initBal;
		this.bank = bank;
		this.userName = userName;
	}

	public Account() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
