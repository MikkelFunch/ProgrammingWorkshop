package com.mma.searchengine.benchmark;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import com.mma.searchengine.part3.URLList;
import com.mma.searchengine.part4.HashMap;

public class Benchmark {
	
	private static final int SETUP_ITERATIONS	= 1;
	private static final int SEARCH_ITERATIONS	= 10000;
	
	private static void testPart1(String filename, String searchWord) {
		try {
			long[] initTimes = new long[SETUP_ITERATIONS];
			com.mma.searchengine.part1.HTMLlist htmlList = null;
			for(int i = 0; i < SETUP_ITERATIONS; i++) {
				long beforeListInit = System.nanoTime();
				htmlList = com.mma.searchengine.part1.Searcher.readHtmlList(filename);
				long listInitTime = System.nanoTime() - beforeListInit;
				initTimes[i] = listInitTime;
			}
			
			long[] searchTimes = new long[SEARCH_ITERATIONS]; 
			for(int i = 0; i < SEARCH_ITERATIONS; i++) {
				long beforeSearch = System.nanoTime();
				com.mma.searchengine.part1.Searcher.exists(htmlList, searchWord);
				long searchTime = System.nanoTime() - beforeSearch;
				searchTimes[i] = searchTime;
			}
			
			printResults("Part 1", initTimes, searchTimes);
		} catch(IOException e) {
			System.out.println("testPart1 failed");
			e.printStackTrace();
		}
	}
	
	private static void testPart2(String filename, String searchWord) {
		try {
			long[] initTimes = new long[SETUP_ITERATIONS];
			com.mma.searchengine.part2.HTMLlist htmlList = null;
			for(int i = 0; i < SETUP_ITERATIONS; i++) {
				long beforeListInit = System.nanoTime();
				htmlList = com.mma.searchengine.part2.Searcher.readHtmlList(filename);
				long listInitTime = System.nanoTime() - beforeListInit;
				initTimes[i] = listInitTime;
			}
			
			long[] searchTimes = new long[SEARCH_ITERATIONS]; 
			for(int i = 0; i < SEARCH_ITERATIONS; i++) {
				long beforeSearch = System.nanoTime();
				com.mma.searchengine.part2.Searcher.exists(htmlList, searchWord);
				long searchTime = System.nanoTime() - beforeSearch;
				searchTimes[i] = searchTime;
			}
			
			printResults("Part 2", initTimes, searchTimes);
		} catch(IOException e) {
			System.out.println("testPart1 failed");
			e.printStackTrace();
		}
	}
	
	private static void testPart3(String filename, String searchWord) {
		try {
			long[] initTimes = new long[SETUP_ITERATIONS];
			com.mma.searchengine.part3.WordList wordList = null;
			for(int i = 0; i < SETUP_ITERATIONS; i++) {
				long beforeListInit = System.nanoTime();
				wordList = com.mma.searchengine.part3.WordList.construct(new BufferedReader(new InputStreamReader(new FileInputStream(filename), StandardCharsets.UTF_8)));
				long listInitTime = System.nanoTime() - beforeListInit;
				initTimes[i] = listInitTime;
			}
			
			long[] searchTimes = new long[SEARCH_ITERATIONS]; 
			for(int i = 0; i < SEARCH_ITERATIONS; i++) {
				long beforeSearch = System.nanoTime();
				com.mma.searchengine.part3.SearchCmd.exists(wordList, searchWord);
				long searchTime = System.nanoTime() - beforeSearch;
				searchTimes[i] = searchTime;
			}
			
			printResults("Part 3", initTimes, searchTimes);
		} catch(IOException e) {
			System.out.println("testPart1 failed");
			e.printStackTrace();
		}
	}
	
	private static void testPart4(String filename, String searchWord) {
		try {
			long[] initTimes = new long[SETUP_ITERATIONS];
			HashMap<String, URLList> wordList = null;
			for(int i = 0; i < SETUP_ITERATIONS; i++) {
				long beforeListInit = System.nanoTime();
				wordList = com.mma.searchengine.part4.SearchCmd.constructHashMap(new BufferedReader(new InputStreamReader(new FileInputStream(filename), StandardCharsets.UTF_8)));
				long listInitTime = System.nanoTime() - beforeListInit;
				initTimes[i] = listInitTime;
			}
			
			long[] searchTimes = new long[SEARCH_ITERATIONS]; 
			for(int i = 0; i < SEARCH_ITERATIONS; i++) {
				long beforeSearch = System.nanoTime();
				com.mma.searchengine.part4.SearchCmd.exists(wordList, searchWord);
				long searchTime = System.nanoTime() - beforeSearch;
				searchTimes[i] = searchTime;
			}
			
			printResults("Part 4", initTimes, searchTimes);
		} catch(IOException e) {
			System.out.println("testPart1 failed");
			e.printStackTrace();
		}
	}
	
	private static void testPart5(String filename, String searchWord) {
		try {
			long[] initTimes = new long[SETUP_ITERATIONS];
			for(int i = 0; i < SETUP_ITERATIONS; i++) {
				long beforeListInit = System.nanoTime();
				com.mma.searchengine.part5.Searcher.readHtmlList(filename);
				long listInitTime = System.nanoTime() - beforeListInit;
				initTimes[i] = listInitTime;
			}
			
			long[] searchTimes = new long[SEARCH_ITERATIONS]; 
			for(int i = 0; i < SEARCH_ITERATIONS; i++) {
				long beforeSearch = System.nanoTime();
				com.mma.searchengine.part5.Searcher.exists(searchWord);
				long searchTime = System.nanoTime() - beforeSearch;
				searchTimes[i] = searchTime;
			}
			
			printResults("Part 5", initTimes, searchTimes);
		} catch(IOException e) {
			System.out.println("testPart1 failed");
			e.printStackTrace();
		}
	}
	
	public static void printResults(String name, long[] listInitTime, long[] searchTime) {
		System.out.println(name);
		printResult("Data structure init time", listInitTime);
		printResult("Search time", searchTime);
		
		System.out.println();
	}
	
	private static void printResult(String heading, long[] arr) {
		long totalTime = 0;
		long maxTime = Long.MIN_VALUE;
		long minTime = Long.MAX_VALUE;
		for(long l : arr) {
			totalTime += l;
			if(l > maxTime) {
				maxTime = l;
			}
			if(l < minTime) {
				minTime = l;
			}
		}
		final double mean = totalTime / arr.length;
		
		double variance = 0;
		for(int i = 0; i < arr.length; i++) {
			variance += Math.pow((arr[i] - mean), 2d);
		}
		variance /= arr.length;
		
		final double standardDeviation = Math.sqrt(variance);
		
		System.out.println(heading);
		System.out.println("Times tested: " + arr.length);
		System.out.println(format("Mean:          ", mean));
		System.out.println(format("Max:           ", maxTime));
		System.out.println(format("Min:           ", minTime));
		System.out.println(format("Variance:      ", variance));
		System.out.println(format("Std deviation: ", standardDeviation));
	}
	
	/**
	 * 
	 * @param text
	 * @param d Double in ns
	 * @return return in ms
	 */
	private static String format(String text, double d) {
		return String.format(text + "%12.5f ms", d/1_000_000);
	}
	
	private static String format(String text, long l) {
		return format(text , (double) l);
	}

	public static void main(String[] args) {
		final String filename = "data/itcwww-big.txt";
		final String searchWord = "på";
		testPart1(filename, searchWord);
		testPart2(filename, searchWord);
		testPart3(filename, searchWord);
		testPart4(filename, searchWord);
		testPart5(filename, searchWord);
	}
	
	
}