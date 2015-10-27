package com.mma.searchengine.part4;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

public class HashMapTest {

	@Test
	public void testEmptyMap() {
		HashMap<String, String> map = new HashMap<String, String>();
		Assert.assertNull(map.get("test"));
	}
	
	@Test
	public void testMapFunctionalityPut() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("George", "Washington");
		map.put("John", "Adams");
		map.put("Thomas", "Jefferson");
		map.put("James", "Madison");
		map.put("John", "Quincy Adams");
		map.put("Andrew", "Jackson");
		map.put("Martin", "Van Buren");
	}
	
	@Test
	public void testMapFunctionalityContains() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("George", "Washington");
		map.put("John", "Adams");
		map.put("Thomas", "Jefferson");
		map.put("James", "Madison");
		map.put("John", "Quincy Adams");
		map.put("Andrew", "Jackson");
		map.put("Martin", "Van Buren");
		
		Assert.assertTrue(map.contains("James"));
		Assert.assertTrue(map.contains("Martin"));
		Assert.assertFalse(map.contains("Jarl"));
	}
	
	@Test
	public void testMapFunctionalityGet() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("George", "Washington");
		map.put("John", "Adams");
		map.put("Thomas", "Jefferson");
		map.put("James", "Madison");
		map.put("John", "Quincy Adams");
		map.put("Andrew", "Jackson");
		map.put("Martin", "Van Buren");
		
		Assert.assertEquals(map.get("James"), "Madison");
		Assert.assertEquals(map.get("John"), "Quincy Adams");
		Assert.assertEquals(map.get("Martin"), "Van Buren");
		Assert.assertEquals(map.get("Andrew"), "Jackson");
		Assert.assertEquals(map.get("George"), "Washington");
	}
	
	

}
