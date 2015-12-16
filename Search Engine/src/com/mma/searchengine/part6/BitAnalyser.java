package com.mma.searchengine.part6;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashSet;

public class BitAnalyser {

	private static final String urlPrefix = "*PAGE:";
	private int[] bits = new int[32];
	private int numberOfUpdateCalls = 0;
	
	public static void main(String[] args) throws IOException {
		BitAnalyser b1 = new BitAnalyser();
		BitAnalyser b2 = new BitAnalyser();

		b1.readHtmlList(args[0], false);
		b2.readHtmlList(args[0], true);
		
		System.out.println(Arrays.toString(b1.getBits()));
		System.out.println(Arrays.toString(b2.getBits()));
		System.out.println(b1.numberOfUpdateCalls);
		System.out.println(b2.numberOfUpdateCalls);
		System.out.println(Arrays.toString(b1.getPercentages()));
		System.out.println(Arrays.toString(b2.getPercentages()));
		System.out.println("--------------------");
		printDoubleArray(b1.getPercentages());
		System.out.println("--------------------");
		printDoubleArray(b2.getPercentages());
		System.out.println("--------------------");
	}
	
	public void readHtmlList (String filename, boolean asWString) throws IOException {
    	// Open the file given as argument
        BufferedReader infile = new BufferedReader(new FileReader(filename));
        
        String url;
        url = infile.readLine(); //Read the first line
        String word = infile.readLine();
        while(word != null) { // Loop as long as there are new lines
        	if(!word.startsWith(urlPrefix)){
        		if(asWString) {
        			updateBits(new WString(word).hashCode());
        		} else {
        			updateBits(word.hashCode());
        		}
        	}
        	word = infile.readLine(); //Read next word
        }
        infile.close(); // Close the file
    }
	
	private void updateBits(int integer) {
		numberOfUpdateCalls++;
		byte[] bytes = ByteBuffer.allocate(4).putInt(integer).array();
		int bit = 0;
		for (byte b : bytes) {
			int val = b;
			for (int i = 0; i < 8; i++) {
				bits[bit] += (val & 128) == 0 ? 0 : 1;
				val <<= 1;
				bit++;
			}
		}
	}
	
	private double[] getPercentages() {
		double[] result = new double[bits.length];
		for(int i = 0; i < bits.length; i++) {
			result[i] = ((double) bits[i]) / ((double)numberOfUpdateCalls);
		}
		return result;
	}
	
	private static void printDoubleArray(double[] d) {
		for(int i = 0; i < d.length; i++) {
			System.out.println(d[i]);
		}
	}
	
	public int[] getBits() {
		return bits;
	}
}
