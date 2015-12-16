package com.mma.searchengine.part5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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