package com.sumeet.model;

public enum Currency {
	/**
	 * US Doller
	 */
	USD,

	/**
	 * Indian Rupee
	 */
	INR,
	
	/**
	 * United Kingdom Pound
	 */
	GBP;
	public static boolean contains(String s) {
		try {
			Currency.valueOf(s);
			return true;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}
}
