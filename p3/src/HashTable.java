
/**
 * Filename:   HashTable.java
 * Project:    p3
 * Authors:    YIYANG LIN, YU-TAI CHEN
 * Lecture:    001, 002
 * Semester:   Fall 2018
 * Course:     CS400
 * 
 * Due Date:   Before 10pm on October 19, 2018
 * Version:    1.0
 * 
 * Credits:    CS 400 TA, Book - Data Structures & Algorithms in Java 2nd Edition
 * 
 * Bugs:       no known bugs, but not complete either
 */

import java.util.*;

/**
 * HashTable<K, V> Class implements to HashTableADT<K, V> in generic
 *
 * @param <K>
 * @param <V>
 */
public class HashTable<K extends Comparable<K>, V> implements HashTableADT<K, V> {

	/**
	 * inner class to create nodes as bucket object containing Key and Value in the
	 * SortedList
	 */
	class Node {

		private K key;
		private V value;
		public Node next;

		/**
		 * Constructor for a Node
		 * 
		 * @param key
		 * @param value
		 */
		public Node(K key, V value) {
			this.key = key;
			this.value = value;
		}

		/* accessors */
		// accessor methods for private fields
		K getKey() {
			return key;
		}

		V getValue() {
			return value;
		}

		/**
		 * display the key and value in a bracket
		 */
		void displayNode() {
			System.out.print(" [" + key + "," + value + "], ");
		}

	}

	/**
	 * inner class to create each SortedList Object in each ArrayList as separate
	 * chaining
	 */
	public class SortedList {
		private int nodeSize = 0;
		private Node first;

		/**
		 * Constructor for a SortedList
		 */
		public SortedList() {
			first = null;
		}

		public Node getNode() {
			return first;
		}

		/**
		 * insert each node into the SortedList
		 * 
		 * @param theNode
		 */
		public void insert(Node theNode) {
			K key = theNode.getKey();
			Node previous = null;
			Node current = first;

			while (!(current == null) && key.compareTo(current.getKey()) > 0) {
				previous = current;
				current = current.next;
			}
			if (previous == null)
				first = theNode;
			else
				previous.next = theNode;
			theNode.next = current;

		}

		/**
		 * delete the Key in the SortedList
		 * 
		 * @param key
		 */
		public void delete(K key) {
			Node previous = null;
			Node current = first;

			while (current != null && key != current.getKey()) {
				previous = current;
				current = current.next;
			}
			if (previous == null)
				first = first.next;
			else if (current == null)
				return;
			else
				previous.next = current.next;
		}

		/**
		 * helper method for get method
		 * 
		 * @param key
		 * @return the value assigned to the key
		 */
		public Node find(K key) {
			Node current = first;

			while (current != null && current.getKey().compareTo(key) <= 0) {
				if (current.getKey() == key)
					return current;
				current = current.next;
			}
			return null;
		}

		/**
		 * helper method for print method
		 */
		public void displayList() {
			System.out.print("List: ");
			Node current = first;
			while (current != null) {
				current.displayNode();
				current = current.next;
			}
			System.out.println("");
		}

		/**
		 * @return the SortedList size (bucket chain size)
		 */
		public int getSize() {
			nodeSize = 0;
			Node current = first;
			while (current != null) {
				current = current.next;
				nodeSize++;
			}
			return nodeSize;
		}

	}

	/* fields */
	private ArrayList<SortedList> hashArray;
	private ArrayList<SortedList> resizedHashArray;
	private int arraySize;
	private double loadFactor;
	private int count = 0;
	private int newSize;

	/**
	 * constructor of Hash Table, set default Size and LoadFactor
	 */
	public HashTable() {
		this(10, 0.75);
	}

	/**
	 * constructor of Hash Table
	 * 
	 * @param initialCapacity
	 * @param loadFactor
	 */
	public HashTable(int initialCapacity, double loadFactor) {
		this.loadFactor = loadFactor;
		arraySize = initialCapacity;
		hashArray = new ArrayList<>(arraySize);
		for (int j = 0; j < arraySize; j++) {
			hashArray.add(new SortedList());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see HashTableADT#put(java.lang.Comparable, java.lang.Object)
	 */
	@Override
	public void put(K key, V value) throws IllegalArgumentException {

		if (key == null) {
			throw new IllegalArgumentException("Key cannot be null");
		}
		Node newNode = new Node(key, value);

		int translate = key.hashCode();
		int hashVal = hashFunc(translate);
		Node theNode = hashArray.get(hashVal).getNode();
		try {
			for (int i = 0; i < hashArray.get(hashVal).getSize(); i++) {
				if (theNode.getKey() == key) {
					count--;
					break;
				}

				theNode = theNode.next;
			}

			hashArray.get(hashVal).insert(newNode);
			count++;
			if ((double) count / arraySize > loadFactor) {
				resize();
			}
		} catch (Exception e) {
			 e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see HashTableADT#get(java.lang.Comparable)
	 */
	@Override
	public V get(K key) throws IllegalArgumentException, NoSuchElementException {
		if (key == null) {
			throw new IllegalArgumentException("Key cannot be null");
		}
		int translate = key.hashCode();
		int hashVal = hashFunc(translate);
		try {
			V theValue = hashArray.get(hashVal).find(key).getValue();
			return theValue;
		} catch (Exception e) {
			throw new NoSuchElementException();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see HashTableADT#remove(java.lang.Comparable)
	 */
	@Override
	public void remove(K key) throws IllegalArgumentException, NoSuchElementException {
		if (key == null) {
			throw new IllegalArgumentException("key cannot be null");
		}
		int translate = key.hashCode();
		int hashVal = hashFunc(translate);
		try {
			hashArray.get(hashVal).delete(key);
			count--;
		} catch (Exception e) {
			throw new NoSuchElementException("key " + key + " does not exist in the Hash Table");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see HashTableADT#size()
	 */
	@Override
	public int size() {
		return count;
	}

	/**
	 * put the key in the remainder index
	 * 
	 * @param key
	 * @return
	 */
	public int hashFunc(int key) // hash function
	{
		return key % arraySize;
	}

	/**
	 * after resizing, put the key in the remainder index
	 * 
	 * @param key
	 * @return
	 */
	public int hashFunc2(int key) // resize hash function
	{
		return key % newSize;
	}

	/**
	 * print the Hash Table, whether original or resized
	 */
	public void print() {
		for (int i = 0; i < arraySize; i++) {
			System.out.print("index: " + i + " ");
			if ((double) count / arraySize > loadFactor) {
				resizedHashArray.get(i).displayList();
			} else
				hashArray.get(i).displayList();
		}
	}

	/**
	 * temporarily put the Key and Value into a temp arrayList, expand and assign to
	 * the original one
	 */
	public void resize() {
		newSize = arraySize * 2;
		resizedHashArray = new ArrayList<>(newSize);
		for (int j = 0; j < newSize; j++) {
			resizedHashArray.add(new SortedList());
		}
		K subKey;
		V subValue;
		Node newNode = null;

		for (int i = 0; i < arraySize; i++) {
			try {
				Node theNode = hashArray.get(i).getNode();
				for (int j = 0; j < hashArray.get(i).getSize(); j++) {
					if (theNode == null)
						break;
					subKey = theNode.getKey();
					subValue = theNode.getValue();
					newNode = new Node(subKey, subValue);
					int translate = subKey.hashCode();
					int hashVal = hashFunc2(translate);
					resizedHashArray.get(hashVal).insert(newNode);

					theNode = theNode.next;

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		hashArray = resizedHashArray;
		arraySize = newSize;
		resizedHashArray = null;
	}
}
