package application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Collections;

/**
 * Implementation of a B+ tree to allow efficient access to
 * many different indexes of a large data set. 
 * BPTree objects are created for each type of index
 * needed by the program.  BPTrees provide an efficient
 * range search as compared to other types of data structures
 * due to the ability to perform log_m N lookups and
 * linear in-order traversals of the data items.
 * 
 * @author sapan (sapan@cs.wisc.edu)
 *
 * @param <K> key - expect a string that is the type of id for each item
 * @param <V> value - expect a user-defined type that stores all data for a food item
 */
public class BPTree<K extends Comparable<K>, V> implements BPTreeADT<K, V> {

    // Root of the tree
    private Node root;
    
    // Branching factor is the number of children nodes 
    // for internal nodes of the tree
    private int branchingFactor;
    
    
    /**
     * Public constructor
     * 
     * @param branchingFactor 
     */
    public BPTree(int branchingFactor) {
        if (branchingFactor <= 2) {
            throw new IllegalArgumentException(
               "Illegal branching factor: " + branchingFactor);
        }
        this.branchingFactor = branchingFactor;
        root = new LeafNode();
    }
    
    
    /*
     * (non-Javadoc)
     * @see BPTreeADT#insert(java.lang.Object, java.lang.Object)
     */
    @Override
    public void insert(K key, V value) {
        root.insert(key, value);
    }
    
    
    /*
     * (non-Javadoc)
     * @see BPTreeADT#rangeSearch(java.lang.Object, java.lang.String)
     */
    @Override
    public List<V> rangeSearch(K key, String comparator) {
        if (!comparator.contentEquals(">=") && 
            !comparator.contentEquals("==") && 
            !comparator.contentEquals("<=") )
            return new ArrayList<V>();

        return root.rangeSearch(key, comparator);
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        Queue<List<Node>> queue = new LinkedList<List<Node>>();
        queue.add(Arrays.asList(root));
        StringBuilder sb = new StringBuilder();
        while (!queue.isEmpty()) {
            Queue<List<Node>> nextQueue = new LinkedList<List<Node>>();
            while (!queue.isEmpty()) {
                List<Node> nodes = queue.remove();
                sb.append('{');
                Iterator<Node> it = nodes.iterator();
                while (it.hasNext()) {
                    Node node = it.next();
                    sb.append(node.toString());
                    if (it.hasNext())
                        sb.append(", ");
                    if (node instanceof BPTree.InternalNode)
                        nextQueue.add(((InternalNode) node).children);
                }
                sb.append('}');
                if (!queue.isEmpty())
                    sb.append(", ");
                else {
                    sb.append('\n');
                }
            }
            queue = nextQueue;
        }
        return sb.toString();
    }
    
    
    /**
     * This abstract class represents any type of node in the tree
     * This class is a super class of the LeafNode and InternalNode types.
     * 
     * @author sapan
     */
    private abstract class Node {
        
        // List of keys
        List<K> keys;
        
        /**
         * Package constructor
         */
        Node() {
            keys = new ArrayList<K>();
        }
        
        /**
         * Inserts key and value in the appropriate leaf node 
         * and balances the tree if required by splitting
         *  
         * @param key
         * @param value
         */
        abstract void insert(K key, V value);

        /**
         * Gets the first leaf key of the tree
         * 
         * @return key
         */
        abstract K getFirstLeafKey();
        
        /**
         * Gets the new sibling created after splitting the node
         * 
         * @return Node
         */
        abstract Node split();
        
        /*
         * (non-Javadoc)
         * @see BPTree#rangeSearch(java.lang.Object, java.lang.String)
         */
        abstract List<V> rangeSearch(K key, String comparator);

        /**
         * 
         * @return boolean
         */
        abstract boolean isOverflow();
        
        public String toString() {
            return keys.toString();
        }
    
    } // End of abstract class Node
    
    /**
     * This class represents an internal node of the tree.
     * This class is a concrete sub class of the abstract Node class
     * and provides implementation of the operations
     * required for internal (non-leaf) nodes.
     * 
     * @author sapan
     */
    private class InternalNode extends Node {

        // List of children nodes
        List<Node> children;
        
        /**
         * Package constructor
         */
        InternalNode() {
            super();
            children = new ArrayList<Node>();
            
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#getFirstLeafKey()
         */
        K getFirstLeafKey() {
            return children.get(0).getFirstLeafKey();
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#isOverflow()
         */
        boolean isOverflow() {
            return children.size() > branchingFactor;
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#insert(java.lang.Comparable, java.lang.Object)
         */
        void insert(K key, V value) {
            Node node = getNode(key);
            node.insert(key, value);
            
            if(node.isOverflow()) {
            	Node newNode = node.split();
            	insertNode(newNode.getFirstLeafKey(), newNode);
            }
            
            if(root.isOverflow()) {
            	Node newNode = split();
            	InternalNode nRoot = new InternalNode();
            	nRoot.keys.add(newNode.getFirstLeafKey());
            	nRoot.children.add(this);
            	nRoot.children.add(newNode);
            	root = nRoot;
            }
        }
        
        private Node getNode(K key) {
        	int index = Collections.binarySearch(keys, key);
        	int insertIndex = index >= 0 ? index + 1 : -index - 1;
        	return children.get(insertIndex);
        }
        
        private void insertNode(K key, Node node) {
        	int index = Collections.binarySearch(keys, key);
        	int insertIndex = index >= 0 ? index + 1 : -index - 1;
        	
        	if(index < 0) {
        		keys.add(insertIndex, key);
        		children.add(insertIndex + 1, node);
        	} else 
        		children.set(insertIndex, node);
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#split()
         */
        Node split() {
        	InternalNode node = new InternalNode();
            int src = (keys.size() + 1) / 2;
            int des = keys.size();
            
            node.keys.addAll(keys.subList(src, des));
            node.children.addAll(children.subList(src, des + 1));
            
            keys.subList(src - 1, des).clear();
            children.subList(src, des + 1);
            
            return node;
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#rangeSearch(java.lang.Comparable, java.lang.String)
         */
        List<V> rangeSearch(K key, String comparator) {
            List<V> list = getNode(key).rangeSearch(key, comparator);
            return list;
        }
    
    } // End of class InternalNode
    
    
    /**
     * This class represents a leaf node of the tree.
     * This class is a concrete sub class of the abstract Node class
     * and provides implementation of the operations that
     * required for leaf nodes.
     * 
     * @author sapan
     */
    private class LeafNode extends Node {
        
        // List of values
        List<V> values;
        
        // Reference to the next leaf node
        LeafNode next;
        
        // Reference to the previous leaf node
        LeafNode previous;
        
        /**
         * Package constructor
         */
        LeafNode() {
            super();
            values = new ArrayList<V>();
        }
        
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#getFirstLeafKey()
         */
        K getFirstLeafKey() {
            return keys.get(0);
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#isOverflow()
         */
        boolean isOverflow() {
            return values.size() > branchingFactor - 1;
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#insert(Comparable, Object)
         */
        void insert(K key, V value) {
            int index = Collections.binarySearch(keys, key);
            int insertIndex = index >= 0 ? index : -index - 1;
            
            if(index < 0) {
            	keys.add(insertIndex, key);
            	values.add(insertIndex, value);
            } else {
            	values.set(insertIndex, value);
            }
            
            if(root.isOverflow()) {
            	Node node = split();
            	InternalNode nRoot = new InternalNode();
            	nRoot.keys.add(node.getFirstLeafKey());
            	nRoot.children.add(this);
            	nRoot.children.add(node);
            	root = nRoot;
            }
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#split()
         */
        Node split() {
            LeafNode node = new LeafNode();
            int src = (keys.size() + 1) / 2;
            int des = keys.size();
            
            node.keys.addAll(keys.subList(src, des));
            node.values.addAll(values.subList(src, des));
            
            keys.subList(src, des).clear();
            values.subList(src, des).clear();
            
            node.next = next;
            node.previous = this;
            next = node;
            
            return node;
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#rangeSearch(Comparable, String)
         */
        List<V> rangeSearch(K key, String comparator) {
            LeafNode node = this;
            List<V> list = new LinkedList<V>();
            
            while(node != null) {
            	Iterator<V> valueIt = node.values.iterator();
            	Iterator<K> keyIt = node.keys.iterator();
            	
            	while(keyIt.hasNext()) {
            		V curVal = valueIt.next();
            		K curKey = keyIt.next();
            		int included = curKey.compareTo(key);
            		
            		if((comparator.equals(">=") && included >= 0) || 
            				(comparator.equals("<=") && included <= 0) || 
            				(comparator.equals("==") && included == 0)) {
            			list.add(curVal);
            		}
            	}
            	if(comparator.equals(">="))
            		node = node.next;
            	
            	if(comparator.equals("<="))
            		node = node.previous;
            }
            return list;
        }
        
    } // End of class LeafNode
    
    
    /**
     * Contains a basic test scenario for a BPTree instance.
     * It shows a simple example of the use of this class
     * and its related types.
     * 
     * @param args
     */
    public static void main(String[] args) {
//        // create empty BPTree with branching factor of 3
//        BPTree<Double, Double> bpTree = new BPTree<>(3);
//
//        // create a pseudo random number generator
//        Random rnd1 = new Random();
//
//        // some value to add to the BPTree
//        Double[] dd = {0.0d, 0.5d, 0.2d, 0.8d};
//
//        // build an ArrayList of those value and add to BPTree also
//        // allows for comparing the contents of the ArrayList 
//        // against the contents and functionality of the BPTree
//        // does not ensure BPTree is implemented correctly
//        // just that it functions as a data structure with
//        // insert, rangeSearch, and toString() working.
//        List<Double> list = new ArrayList<>();
//        for (int i = 0; i < 400; i++) {
//            Double j = dd[rnd1.nextInt(4)];
//            list.add(j);
//            bpTree.insert(j, j);
//            System.out.println("\n\nTree structure:\n" + bpTree.toString());
//        }
//        List<Double> filteredValues = bpTree.rangeSearch(0.2d, ">=");
//        System.out.println("Filtered values: " + filteredValues.toString());
    	
    	BPTree<Integer, Integer> tree = new BPTree<>(4);
    	Integer[] intt = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    	
    	List<Integer> list = new ArrayList<>();
    	
    	for(int i = 0; i < 10; i++) {
    		Integer x = intt[i];
    		tree.insert(x, x);
    		
    		System.out.println("\n\nTree structure:\n" + tree.toString());
    	}
    	List<Integer> filteredValues = tree.rangeSearch(5, "<=");
        System.out.println("Filtered values: " + filteredValues.toString());
    }

} // End of class BPTree
