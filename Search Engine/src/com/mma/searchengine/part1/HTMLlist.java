package com.mma.searchengine.part1;

//A (item of a) linked list of strings.
public class HTMLlist {
	String str; // Some string (a line from the source file).
	HTMLlist next; // Next item in the list.

	// Class constructor
	HTMLlist(String s, HTMLlist n) {
		str = s;
		next = n;
	}
}