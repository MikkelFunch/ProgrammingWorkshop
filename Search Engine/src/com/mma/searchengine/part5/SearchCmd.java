package com.mma.searchengine.part5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

// A (item of a) linked list of strings.
/*class HTMLlist {    
    String str;     // Some string (a line from the source file).
    HTMLlist next;  // Next item in the list.

    // Class constructor
    HTMLlist (String s, HTMLlist n) {   
        str = s;
        next = n;
    }
}
*/
// Contains the main functionality of the program.
// Methods are static, meaning that the class does not need to be instantiated
// for them to be used.
class Searcher {
	
	private static final ArrayList<String> urls = new ArrayList<String>();
	private static HashMap<String, HashSet<String>> map = new HashMap<String, HashSet<String>>();
	
	private static final String urlPrefix = "*PAGE:";

	/*
    // Checks if the string words occurs in HTMLlist l.
    // Recall that l represents a single item of the linked list, but points to
    // the remainder of the list.
    public static boolean exists (HTMLlist l, String word) {
        while (l != null) {             //  Loop while l is not a null reference
            if (l.str.equals (word)) {  // If the str field of l equals word
                return true;            // return true
            }
            l = l.next;                 // Otherwise we move on to the next item
        }
        
        // If we exit the while loop, we know that we reached the end of the 
        // list without encountering the word, therefore it does not  exist and 
        // we can return false.
        return false;
    }
    
    public static void search(HTMLlist l, String word) {
    	boolean found = false;
    	
    	while(l != null) {
    		// Check if the word is a URL and contains the word that is searched for
    		if(l.str.startsWith(urlPrefix) && l.str.contains(word)) {
    			System.out.println(l.str.substring(urlPrefix.length()));
    			found = true;
    		} else if (l.str.equals(word)) {
    			System.out.println("The word \""+word+"\" has been found.");
    			found = true;
    			break;
    		}
    		l = l.next;
    	}
    	if(!found) {
    		System.out.println("The word \""+word+"\" has NOT been found.");
    	}
    }*/
	public static void search(String word) {
		HashSet<String> result = map.get(word);
		
		if (result != null) {
			for (String s : result) {
				System.out.println(s.substring(urlPrefix.length()));
			}
		} else {
			System.out.println("The word \""+word+"\" has NOT been found.");
		}
		
		
		
    	boolean found = false;
    	
//    	while(l != null) {
//    		// Check if the word is a URL and contains the word that is searched for
//    		if(l.str.startsWith(urlPrefix) && l.str.contains(word)) {
//    			System.out.println(l.str.substring(urlPrefix.length()));
//    			found = true;
//    		} else if (l.str.equals(word)) {
//    			System.out.println("The word \""+word+"\" has been found.");
//    			found = true;
//    			break;
//    		}
//    		l = l.next;
//    	}
    	if(!found) {
    		System.out.println("The word \""+word+"\" has NOT been found.");
    	}
    }
	

    // Creates a hashmap from a file.
    public static void readHtmlList (String filename) throws IOException {
    	// Open the file given as argument
        BufferedReader infile = new BufferedReader (new FileReader (filename));
        
        String url;
        url = infile.readLine(); //Read the first line
        urls.add(url);
        while (url != null) {    // Loop as long as there are new lines
        	String word = infile.readLine();
        	if(word.startsWith(urlPrefix)){
        		url = word;
        		urls.add(url);
        	}
        	else if (map.containsKey(word)) { // Words has already been found
				map.get(word).add(url);
			} else {
				HashSet<String> m = new HashSet<String>();
				m.add(url);
				map.put(word, m);
			}
        }
        infile.close(); // Close the file
    }
}

public class SearchCmd {
    public static void main (String[] args) throws IOException {
        String name;

        // Check that a filename has been given as argument
        if (args.length != 1) {
            System.out.println ("Usage: java SearchCmd <datafile>");
            System.exit (1);
        }

        // Read the file and create the linked list
        Searcher.readHtmlList (args[0]);

        // Start reading input from the user
        BufferedReader inuser = new BufferedReader (new InputStreamReader (System.in));

        System.out.println ("Hit return to exit.");
        while (true) { // Infinite loop waiting for user input 
            System.out.print ("Search for: ");
            name = inuser.readLine(); // Read a line from the terminal
            if (name == null || name.length() == 0) {
                return; // If the user only pressed enter then exit the method (and program)
            }
            Searcher.search(name);
        }
    }
}