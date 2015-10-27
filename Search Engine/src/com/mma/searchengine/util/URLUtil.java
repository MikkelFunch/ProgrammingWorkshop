package com.mma.searchengine.util;

public class URLUtil {
	
	private static final String urlPrefix = "*PAGE:";

	/**
	 * Removes the URL prefix from a given URL
	 * E.g. *PAGE:http://itu.dk will return http://itu.dk
	 * @param urlWithPrefix The URL with the prefix
	 * @return The URL without the prefix
	 */
	public static String removeUrlPrefix(String urlWithPrefix) {
		return urlWithPrefix.substring(urlPrefix.length());
	}
	
	/**
	 * Checks if the given line is a URL
	 * @param line The line to check
	 * @return true if the line is an URL otherwise false
	 */
	public static boolean isURL(String line) {
		return line.startsWith(urlPrefix);
	}
}