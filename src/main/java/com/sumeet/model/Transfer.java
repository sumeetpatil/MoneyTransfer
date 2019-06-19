package com.sumeet.model;

public class Transfer {
	private int srcAcc;
	private int destAcc;
	private int amount;

	public Transfer(int srcAcc, int destAcc, int amount) {
		super();
		this.srcAcc = srcAcc;
		this.destAcc = destAcc;
		this.amount = amount;
	}

	public Transfer() {
	}

	public int getSrcAcc() {
		return srcAcc;
	}

	public void setSrcAcc(int srcAcc) {
		this.srcAcc = srcAcc;
	}

	public int getDestAcc() {
		return destAcc;
	}

	public void setDestAcc(int destAcc) {
		this.destAcc = destAcc;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
}
