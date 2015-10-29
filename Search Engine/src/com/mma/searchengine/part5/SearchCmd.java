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
	
	// Search for word in the given list
	public static void search(String word) {
		//Get the hashset with the links containing the word
		HashSet<String> result = map.get(word);
		boolean found = false;
		if(result != null) { //If there exist any links
			found = true; //Word is found at least once
			//Print the word is found
			System.out.println("The word \""+word+"\" has been found on the following site(s):");
			for(String s : result) { //Print all the urls which contain the word
				System.out.println(s.substring(urlPrefix.length()));
			}
		}
		if(!found) { //Word is not found
			System.out.println("The word \""+word+"\" has NOT been found.");
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