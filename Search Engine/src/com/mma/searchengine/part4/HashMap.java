package com.mma.searchengine.part4;

/***
 * Stores value pairs. Supports put, contains and get in O(1).
 * @author Mark
 *
 * @param <K> The key type
 * @param <V> The value type
 */
public class HashMap<K, V> {
	
	// The factor which the array should be expanded with when it exceeds its capacity
	private static final int EXPAND_FACTOR = 2;
	// The number of entries allowed for each array cell before the array is expanded
	private static final double ENTRIES_PER_CELL = 0.5;
	
	private Entry<K, V>[] data;
	private int size;
	
	public HashMap() {
		data = new Entry[2];
		size = 0;
	}
	
	public V get(K key) {
		Entry<K, V> entry = getEntry(key);
		if(entry == null) {
			return null;
		} else {
			return entry.getValue();
		}
	}
	
	@SuppressWarnings("unchecked")
	private Entry<K, V> getEntry(K key) {
		int index = getIndex(key);
		Entry<K, V> current = (Entry<K, V>) data[index];
		while(current != null) {
			if(current.getKey().equals(key)) {
				return current;
			}
			current = current.getNext();
		}
		return null;
	}
	
	public boolean containsKey(K key) {
		if(get(key) == null) {
			return false;
		} else {
			return true;
		}
	}
	
	public void put(K key, V value) {
		if(containsKey(key)) {
			getEntry(key).setValue(value);
		} else {
			add(key, value);
			size++;
			
			if(size > data.length * ENTRIES_PER_CELL) {
				expand();
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private void expand() {
		Entry<K, V>[] oldData = data;
		data = new Entry[data.length*EXPAND_FACTOR];
		
		// Move all data from the previous array to the new array.
		for(int i = 0; i < oldData.length; i++) {
			if(oldData[i] != null) {
				Entry<K, V> current = (Entry<K, V>) oldData[i];
				while(current != null) {
					add(current.getKey(), current.getValue());
					current = current.getNext();
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private void add(K key, V value) {
		int index = getIndex(key);
		if(data[index] == null) {
			data[index] = new Entry<K, V>(key, value);
		} else {
			Entry<K, V> entry = (Entry<K, V>) data[index];
			entry.append(new Entry<K, V>(key, value));
		}
	}
	
	private int getIndex(K key) {
		int index = key.hashCode() % data.length;
		if(index < 0) {
			index = index * -1;
		}
		return index;
	}
	
	public int size() {
		return size;
	}
	
	/***
	 * The data object that holds the key and value pairs, along with a reference to the next object,
	 * such that they can be chained in case of index collision
	 * @author Mark
	 */
	@SuppressWarnings("hiding")
	public class Entry<K, V> {
		private K key;
		private V value;
		private Entry<K, V> next;

		public Entry(K key, V value) {
			super();
			this.key = key;
			this.value = value;
		}
		
		public void append(Entry<K, V> entry) {
			if(next == null) {
				next = entry;
			} else {
				next.append(entry);
			}
		}

		public K getKey() {
			return key;
		}

		public void setKey(K key) {
			this.key = key;
		}

		public V getValue() {
			return value;
		}

		public void setValue(V value) {
			this.value = value;
		}
		
		public Entry<K, V> getNext() {
			return next;
		}
	}
}
