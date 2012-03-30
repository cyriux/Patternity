package com.patternity;

/**
 * Represents a violation of dependency
 * 
 * @author Mohamed Bourogaa
 * @author Cyrille Martraire
 */
// ValueObject
public class Violation {

	private final String from;
	private final String to;

	public Violation(String from, String to) {
		this.from = from;
		this.to = to;
	}

	@Override
	public String toString() {
		return "Unallowed dependency from " + from + " to" + to;
	}

}