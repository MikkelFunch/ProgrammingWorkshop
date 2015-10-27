package com.mma.searchengine.part3;

import static com.mma.searchengine.util.URLUtil.isURL;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SearchCmd {
	
	public static void search(WordList list, String word) {
    	while(list != null) {
    		if(!isURL(list.getLine()) && word.equals(list.getLine())) {
    			System.out.println("The word \""+word+"\" has been found in the following link(s):");
    			URLList urls = list.getUrls();
    			while(urls != null) {
    				System.out.println(urls.getUrl());
    				urls = urls.getNext();
    			}
    			return;
    		}
    		list = list.getNext();
    	}
    	System.out.println("The word \""+word+"\" has NOT been found");
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
        	search(wordList, word);
        }
	}
}