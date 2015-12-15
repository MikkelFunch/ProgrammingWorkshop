package com.mma.searchengine.part5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;


// Contains the main functionality of the program.
// Methods are static, meaning that the class does not need to be instantiated
// for them to be used.
class Searcher {
	private static HashMap<String, HashSet<String>> map = new HashMap<String, HashSet<String>>();
	
	private static final String urlPrefix = "*PAGE:";
	
	// Search for word/s in the given list
	public static void search(String word) {
		HashSet<String> result = new HashSet<String>();
		
		int spaceIndex = word.indexOf(" ");
		if (spaceIndex != -1) {
			if (word.length() >= spaceIndex + 6 && word.substring(spaceIndex + 1, spaceIndex + 4).equals("AND")){ //If the search should look for pages containing several words
				//Words to look for
				String word1 = word.substring(0, word.indexOf(" AND ")); 
				String word2 = word.substring(word.indexOf(" AND ") + 5);
				
				//Links containing the words
				HashSet<String> one = map.get(word1);
				HashSet<String> two = map.get(word2);
				
				if(one == null || two == null){
					result = null;
				} else {
					//Add the links which is in both lists
					for (String s : one) {
						if (two.contains(s)) {
							result.add(s);
						}
					}
				}
			} else if (word.length() >= spaceIndex + 5 && word.substring(spaceIndex + 1, spaceIndex + 3).equals("OR")){ //If the search should look for pages containing either of the given words
				//Words to look for
				String word1 = word.substring(0, word.indexOf(" OR "));
				String word2 = word.substring(word.indexOf(" OR ") + 4);
				
				//Links containing the words
				HashSet<String> one = map.get(word1);
				HashSet<String> two = map.get(word2);
				
				if (one == null && two == null) { //Check if both or either words is not found
					result = null;
				} else if (one == null){
					result = two;
				} else if (two == null){
					result = one;
				} else {
					//Concatenate the returned lists which contains the words
					result = map.get(word1);
					result.addAll(map.get(word2));
				}
			} else {
				System.out.println("Do not use spaces for anything other than searching for two words with \"AND\" or \"OR\"");
			}
		} else { //Just search for one word
			result = map.get(word);
		}
		
		if (result != null && !result.isEmpty()) { //If any links are found, print them
			System.out.println("The word/s \""+word+"\" has been found on the following link(s):");
			for(String s : result) { //Print all the urls which contain the word
				System.out.println(s.substring(urlPrefix.length()));
			}
		} else {//If no links are found
			System.out.println("The word/s \""+word+"\" has NOT been found.");
		}
    }
	
    // Creates hashmap and set from a file.
    public static void readHtmlList(String filename) throws IOException {
    	// Open the file given as argument
        BufferedReader infile = new BufferedReader(new FileReader(filename));
        
        String url;
        url = infile.readLine(); //Read the first line
        String word = infile.readLine();
        while(word != null) { // Loop as long as there are new lines
        	if(word.startsWith(urlPrefix)){
        		url = word;
        	}
        	else if(map.containsKey(word)) { // Word has already been found
				map.get(word).add(url); //Add url to hashset
			} else { //else add word to hashmap and create hashset with url
				HashSet<String> m = new HashSet<String>();
				m.add(url);
				map.put(word, m);
			}
        	word = infile.readLine(); //Read next word
        }
        infile.close(); // Close the file
    }
}

public class SearchCmd {
    public static void main(String[] args) throws IOException {
        String name;

        // Check that a filename has been given as argument
        if(args.length != 1) {
            System.out.println("Usage: java SearchCmd <datafile>");
            System.exit(1);
        }

        // Read the file and create create the hashmap and set
        Searcher.readHtmlList(args[0]);

        // Start reading input from the user
        BufferedReader inuser = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Hit return to exit.");
        while(true) { // Infinite loop waiting for user input 
            System.out.print("Search for: ");
            name = inuser.readLine(); // Read a line from the terminal
            if(name == null || name.length() == 0) {
                return; // If the user only pressed enter then exit the method(and program)
            }
            Searcher.search(name);
        }
    }
}