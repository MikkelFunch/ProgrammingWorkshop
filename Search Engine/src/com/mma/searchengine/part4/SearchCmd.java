package com.mma.searchengine.part4;

import static com.mma.searchengine.util.URLUtil.isURL;
import static com.mma.searchengine.util.URLUtil.removeUrlPrefix;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.mma.searchengine.part3.URLList;
import com.mma.searchengine.part3.WordList;

public class SearchCmd {
	
	public static boolean exists(HashMap<String, URLList> map, String word) {
		return map.containsKey(word);
	}
	
	public static void search(HashMap<String, URLList> map, String word) {
		if(!map.containsKey(word)) {
			System.out.println("The word \""+word+"\" has NOT been found");
		} else {
			System.out.println("The word \""+word+"\" has been found in the following link(s):");
			System.out.println(map.get(word).toString());
		}
    }
	
	public static void main(String[] args) throws IOException {
		BufferedReader fileReader = new BufferedReader (new FileReader (args[0]));
		HashMap<String, URLList> map = constructHashMap(fileReader);
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
        	search(map, word);
        }
	}
	
	public static HashMap<String, URLList> constructHashMap(BufferedReader fileReader) throws IOException {
		String currentLine, currentUrl;
		HashMap<String, URLList> map = new HashMap<String, URLList>();
		
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
		map.put(currentLine, new URLList(currentUrl));
		
		currentLine = fileReader.readLine();
		while(currentLine != null) {
			if(isURL(currentLine)) {
				currentUrl = removeUrlPrefix(currentLine);
			} else {
				if(map.containsKey(currentLine)) {
					map.get(currentLine).addUrl(currentUrl);
				} else {
					map.put(currentLine, new URLList(currentUrl));
				}
			}
			currentLine = fileReader.readLine();
		}
		return map;
	}
}