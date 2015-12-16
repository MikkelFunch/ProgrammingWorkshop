package com.mma.searchengine.benchmark;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import com.mma.searchengine.part3.URLList;
import com.mma.searchengine.part4.HashMap;

public class Benchmark {
	
	private static final int SEARCH_ITERATIONS	= 10000;
	
	private static TestResult testPart1(String filename, String[] searchWords) {
		try {
			long beforeListInit = System.nanoTime();
			com.mma.searchengine.part1.HTMLlist htmlList = com.mma.searchengine.part1.Searcher.readHtmlList(filename);
			long initTime = System.nanoTime() - beforeListInit;
			
			long[] searchTimes = new long[SEARCH_ITERATIONS]; 
			for(int i = 0; i < SEARCH_ITERATIONS; i++) {
				long beforeSearch = System.nanoTime();
				for(String s : searchWords) {
					com.mma.searchengine.part1.Searcher.exists(htmlList, s);
				}
				long searchTime = System.nanoTime() - beforeSearch;
				searchTimes[i] = (searchTime/searchWords.length);
			}
			
			return computeTestResults("part1", searchTimes, initTime);
		} catch(IOException e) {
			System.out.println("testPart1 failed");
			e.printStackTrace();
			return null;
		}
	}
	
	private static TestResult testPart2(String filename, String[] searchWords) {
		try {
			long beforeListInit = System.nanoTime();
			com.mma.searchengine.part2.HTMLlist htmlList = com.mma.searchengine.part2.Searcher.readHtmlList(filename);
			long initTime = System.nanoTime() - beforeListInit;
			
			long[] searchTimes = new long[SEARCH_ITERATIONS]; 
			for(int i = 0; i < SEARCH_ITERATIONS; i++) {
				long beforeSearch = System.nanoTime();
				for(String s : searchWords) {
					com.mma.searchengine.part2.Searcher.exists(htmlList, s);
				}
				long searchTime = System.nanoTime() - beforeSearch;
				searchTimes[i] = (searchTime/searchWords.length);
			}
			
			return computeTestResults("part2", searchTimes, initTime);
		} catch(IOException e) {
			System.out.println("testPart2 failed");
			e.printStackTrace();
			return null;
		}
	}
	
	private static TestResult testPart3(String filename, String[] searchWords) {
		try {
			long beforeListInit = System.nanoTime();
			com.mma.searchengine.part3.WordList wordList = com.mma.searchengine.part3.WordList.construct(new BufferedReader(new InputStreamReader(new FileInputStream(filename), StandardCharsets.UTF_8)));
			long initTime = System.nanoTime() - beforeListInit;
			
			long[] searchTimes = new long[SEARCH_ITERATIONS]; 
			for(int i = 0; i < SEARCH_ITERATIONS; i++) {
				long beforeSearch = System.nanoTime();
				for(String s : searchWords) {
					com.mma.searchengine.part3.SearchCmd.exists(wordList, s);
				}
				long searchTime = System.nanoTime() - beforeSearch;
				searchTimes[i] = (searchTime/searchWords.length);
			}
			
			return computeTestResults("part3", searchTimes, initTime);
		} catch(IOException e) {
			System.out.println("testPart3 failed");
			e.printStackTrace();
			return null;
		}
	}
	
	private static TestResult testPart4(String filename, String[] searchWords) {
		try {
			long beforeListInit = System.nanoTime();
			HashMap<String, URLList> wordList = com.mma.searchengine.part4.SearchCmd.constructHashMap(new BufferedReader(new InputStreamReader(new FileInputStream(filename), StandardCharsets.UTF_8)));
			long initTime = System.nanoTime() - beforeListInit;
			
			long[] searchTimes = new long[SEARCH_ITERATIONS]; 
			for(int i = 0; i < SEARCH_ITERATIONS; i++) {
				long beforeSearch = System.nanoTime();
				for(String s : searchWords) {
					com.mma.searchengine.part4.SearchCmd.exists(wordList, s);
				}
				long searchTime = System.nanoTime() - beforeSearch;
				searchTimes[i] = (searchTime/searchWords.length);
			}
			
			return computeTestResults("part4", searchTimes, initTime);
		} catch(IOException e) {
			System.out.println("testPart4 failed");
			e.printStackTrace();
			return null;
		}
	}
	
	private static TestResult testPart5(String filename, String[] searchWords) {
		try {
			long beforeListInit = System.nanoTime();
			com.mma.searchengine.part5.Searcher.readHtmlList(filename);
			long initTime = System.nanoTime() - beforeListInit;

				long[] searchTimes = new long[SEARCH_ITERATIONS]; 
			for(int i = 0; i < SEARCH_ITERATIONS; i++) {
				long beforeSearch = System.nanoTime();
				for(String s : searchWords) {
					com.mma.searchengine.part5.Searcher.exists(s);
				}
				long searchTime = System.nanoTime() - beforeSearch;
				searchTimes[i] = (searchTime/searchWords.length);
			}
			
			return computeTestResults("part5", searchTimes, initTime);
		} catch(IOException e) {
			System.out.println("testPart5 failed");
			e.printStackTrace();
			return null;
		}
	}
	
	private static TestResult computeTestResults(String name, long[] searchTimes, long initTime) {
		long totalTime = 0;
		long maxTime = Long.MIN_VALUE;
		long minTime = Long.MAX_VALUE;
		for(long l : searchTimes) {
			totalTime += l;
			if(l > maxTime) {
				maxTime = l;
			}
			if(l < minTime) {
				minTime = l;
			}
		}
		final double mean = totalTime / searchTimes.length;
		
		double variance = 0;
		for(int i = 0; i < searchTimes.length; i++) {
			variance += Math.pow((searchTimes[i] - mean), 2d);
		}
		variance /= searchTimes.length;
		
		variance = nsToMs(variance);
		final double standardDeviation = Math.sqrt(variance);
		return new TestResult(name, nsToMs(initTime), nsToMs(mean), nsToMs(maxTime), nsToMs(minTime), variance, standardDeviation);
	}
	
	private static void printResults(TestResult... testResults) {
		for(TestResult tr : testResults) {
			printResult(tr);
		}
	}
	
	private static void printResult(TestResult testResult) {
		System.out.println(testResult.getName());
		System.out.println("Times tested: " + SEARCH_ITERATIONS);
		System.out.println(format("Init time:     ", testResult.getInitTime()));
		System.out.println(format("Mean:          ", testResult.getMean()));
		System.out.println(format("Max:           ", testResult.getMax()));
		System.out.println(format("Min:           ", testResult.getMin()));
		System.out.println(format("Variance:      ", testResult.getVariance()));
		System.out.println(format("Std deviation: ", testResult.getStdDeviation()));
	}
	
	private static void writeResultsToFile(TestResult... testResults) {
		StringBuilder sb = new StringBuilder();

		sb.append("Data Type\t");
		for(TestResult tr : testResults) {
			sb.append(tr.getName()).append("\t");
		}
		sb.append("\n").append("mean").append("\t");
		for(TestResult tr : testResults) {
			sb.append(tr.getMean()).append("\t");
		}
		sb.append("\n").append("max").append("\t");
		for(TestResult tr : testResults) {
			sb.append(tr.getMax()).append("\t");
		}
		sb.append("\n").append("min").append("\t");
		for(TestResult tr : testResults) {
			sb.append(tr.getMin()).append("\t");
		}
		try {
			new FileWriter("mean-max-min.dat").append(sb.toString()).close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		sb = new StringBuilder();
		sb.append("Data Type\t").append("initTime\n");
		for(TestResult tr : testResults) {
			sb.append(tr.getName()).append("\t").append(tr.getInitTime()).append("\n");
		}
		try {
			new FileWriter("init-time.dat").append(sb.toString()).close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		sb = new StringBuilder();
		sb.append("Data Type\t").append("stdDeviation\n");
		for(TestResult tr : testResults) {
			sb.append(tr.getName()).append("\t").append(tr.getStdDeviation()).append("\n");
		}
		try {
			new FileWriter("std-deviation.dat").append(sb.toString()).close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static double nsToMs(double d) {
		return d / 1_000_000;
	}
	
	private static double nsToMs(long l) {
		return ((double) l) / 1_000_000;
	}
	
	private static String format(String text, double d) {
		return String.format(text + "%12.6f ms", d);
	}
	
	public static void main(String[] args) {
		final String filename = "data/itcwww-big.txt";
		final String[] searchWords = new String[] {"på", "forudsætninger", "varchar"};
		TestResult testPart1 = testPart1(filename, searchWords);
		TestResult testPart2 = testPart2(filename, searchWords);
		TestResult testPart3 = testPart3(filename, searchWords);
		TestResult testPart4 = testPart4(filename, searchWords);
		TestResult testPart5 = testPart5(filename, searchWords);
		
		printResults(testPart1, testPart2, testPart3, testPart4, testPart5);
		writeResultsToFile(testPart1, testPart2, testPart3, testPart4, testPart5);
	}
}

class TestResult {
	
	private final String name;
	private final double initTime;
	private final double mean;
	private final double max;
	private final double min;
	private final double variance;
	private final double stdDeviation;
	
	public TestResult(String name, double initTime, double mean, double max, double min, double variance, double stdDeviation) {
		this.name = name;
		this.initTime = initTime;
		this.mean = mean;
		this.max = max;
		this.min = min;
		this.variance = variance;
		this.stdDeviation = stdDeviation;
	}

	public String getName() {
		return name;
	}

	public double getInitTime() {
		return initTime;
	}

	public double getMean() {
		return mean;
	}

	public double getMax() {
		return max;
	}

	public double getMin() {
		return min;
	}

	public double getVariance() {
		return variance;
	}

	public double getStdDeviation() {
		return stdDeviation;
	}
}