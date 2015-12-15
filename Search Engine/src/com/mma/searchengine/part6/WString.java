package com.mma.searchengine.part6;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class WString {
	
	private String s;
	
	public WString(String s) {
		this.s = s;
	}
	
	@Override
	public int hashCode() {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
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
