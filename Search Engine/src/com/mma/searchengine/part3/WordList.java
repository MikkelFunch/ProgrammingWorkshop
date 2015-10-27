package com.mma.searchengine.part3;

import static com.mma.searchengine.util.URLUtil.isURL;
import static com.mma.searchengine.util.URLUtil.removeUrlPrefix;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class WordList {
	
	private String line;
	private URLList urls;
	private WordList next;
	
	public WordList(String line) {
		this.line = line;
	}
	
	public WordList(String line, String url) {
		this.line = line;
		addUrl(url);
	}
	
	public void setNext(WordList next) {
		this.next = next;
	}
	
	/**
	 * Adds a URL to this line
	 * Will construct a new linked list if none was found,
	 * otherwise it will add the URL to the linked list  
	 * @param url to add
	 */
	public void addUrl(String url) {
		if(urls == null) {
			urls = new URLList(url);
		} else {
			urls.addUrl(url);
		}
	}
	
	/**
	 * Will add a line to the linked list.
	 * If the line is already in the linked list, it will not be added again.
	 * If the URL is already present in urls it will not be added.
	 * If the line is not present, it will be added with the url
	 * @param line to add
	 * @param url to add
	 */
	public void addLine(String line, String url) {
		if(this.line.equals(line)) {
			addUrl(url);
			return;
		} else if(next == null) {
			 next = new WordList(line, url);
		} else {
			next.addLine(line, url);
		}
	}
	
	public static WordList construct(BufferedReader fileReader) throws IOException {
		String currentLine, currentUrl;
		WordList rootList;
		
		// First line is supposed to be an URL
		currentLine = fileReader.readLine();
		if(!isURL(currentLine)) {
			// The first line is supposed to be an URL
			System.exit(1);
		}
		currentUrl = removeUrlPrefix(currentLine);
		
		// The second line is the first word
		currentLine = fileReader.readLine();
		rootList = new WordList(currentLine, currentUrl);
		
		do {
			currentLine = fileReader.readLine();
			if(isURL(currentLine)) {
				currentUrl = removeUrlPrefix(currentLine);
			} else {
				rootList.addLine(currentLine, currentUrl);
			}
		} while(currentLine != null);
		
		return rootList;
	}
	
	
	
	public static void main(String[] args) throws IOException {
		BufferedReader fileReader = new BufferedReader (new FileReader (args[0]));
		WordList wordList = WordList.construct(fileReader);
		fileReader.close();
		
		// Start reading input from the user
        BufferedReader userInput = new BufferedReader (new InputStreamReader (System.in));
        
        System.out.println ("Hit return to exit.");
        
        String word;
        while(true) {
        	System.out.print("Search for: ");
        	word = userInput.readLine();
        	
        	if (word == null || word.length() == 0) {
                return; // If the user only pressed enter then exit the method (and program)
            }
        }
	}
}














