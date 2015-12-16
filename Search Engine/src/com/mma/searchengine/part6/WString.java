package com.mma.searchengine.part6;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class WString {
	
	public WString(String s) {
		this.s = s;
	}
	
	private static MessageDigest md;
	private String s;
	
	static {
		try {
			md = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int hashCode() {
		md.update(s.getBytes());
		byte[] bytes = md.digest();
		String str = new String(bytes, StandardCharsets.UTF_8);
		
		return str.hashCode();
	}
	  
	
	@Override
	public boolean equals(Object anObject) {
		return s.equals(anObject);
	}
	
	@Override
	public String toString() {
		return s.toString();
	}
}
