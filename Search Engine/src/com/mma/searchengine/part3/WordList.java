package com.mma.searchengine.part3;

import static com.mma.searchengine.util.URLUtil.isURL;
import static com.mma.searchengine.util.URLUtil.removeUrlPrefix;

import java.io.BufferedReader;
import java.io.IOException;

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
	
	public URLList getUrls() {
		return urls;
	}
	
	public String getLine() {
		return line;
	}
	
	public WordList getNext() {
		return next;
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
//	public void addLine(String line, String url) {
//		if(this.line.equals(line)) {
//			addUrl(url);
//			return;
//		} else if(next == null) {
//			 next = new WordList(line, url);
//		} else {
//			next.addLine(line, url);
//		}
//	}
	
	public void addLine(String word, String url) {
		WordList currentWord = this;
		while(true) {
			if (currentWord.line.equals(word)) {
				currentWord.addUrl(url);
				break;
			} else if (currentWord.next == null) {
				currentWord.next = new WordList(word, url);
				break;
			} else {
				currentWord = currentWord.next;
			}
		}
	}
	
	/**
	 * Will construct a WordList from the given reader.
	 * @param fileReader The reader providing the lines to parse
	 * @return A linked list with all words and URLs
	 * @throws IOException
	 */
	public static WordList construct(BufferedReader fileReader) throws IOException {
		String currentLine, currentUrl;
		WordList rootList;
		
		// First line is supposed to be an URL
		currentLine = fileReader.readLine();
		if(!isURL(currentLine)) {
			// The first line is supposed to be an URL
			System.err.println("Invalid input file");
			System.exit(1);
		}
		currentUrl = removeUrlPrefix(currentLine);
		
		// The second line is the first word
		currentLine = fileReader.readLine();
		rootList = new WordList(currentLine, currentUrl);
		
		currentLine = fileReader.readLine();
		while(currentLine != null) {
			if(isURL(currentLine)) {
				currentUrl = removeUrlPrefix(currentLine);
			} else {
				rootList.addLine(currentLine, currentUrl);
			}
			currentLine = fileReader.readLine();
		}
		
		return rootList;
	}
}