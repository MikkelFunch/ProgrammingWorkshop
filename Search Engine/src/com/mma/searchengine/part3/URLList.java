package com.mma.searchengine.part3;

public class URLList {
	private String url;
	private URLList next;
	
	public URLList(String url) {
		this.url = url;
	}
	
	public void addUrl(String url) {
		if(this.url.equals(url)) {
			return;
		}
		if(next == null) {
			next = new URLList(url);
		} else {
			next.addUrl(url);
		}
	}
	
	public String getUrl() {
		return url;
	}
	
	public URLList getNext() {
		return next;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(url);
		URLList currentList = getNext();
		while(currentList != null) {
			sb.append("\n");
			sb.append(currentList.getUrl());
			currentList = currentList.getNext();
		}
		return sb.toString();
	}
}