
/**
 * Filename:   AVLTree.java
 * Project:    p2
 * Authors:    DEBRA DEPPELER, YIYANG LIN, YU-TAI CHEN
 *
 * Semester:   Fall 2018
 * Course:     CS400
 * Lecture:    001, 002
 * 
 * Due Date:   Before 10pm on October 1, 2018
 * Version:    1.0
 * 
 * Credits:    GeeksforGeeks, CS400 course readings
 * 
 * Bugs:       no known bugs, but not complete either
 */

import java.lang.IllegalArgumentException;

/**
 * AVLTree Class implements to AVLTreeADT<K> in generic
 * 
 * @param <K>
 */
public class AVLTree<K extends Comparable<K>> implements AVLTreeADT<K> {

	/**
	 * inner class to create Binary Search Tree node
	 * 
	 * @param <K>
	 */
	@SuppressWarnings("hiding")
	class BSTNode<K> {
		/* fields */
		private K key; // key in each node
		private int height; // height in each node's position plus one
		private BSTNode<K> left, right; // left or right node connect with key

		/**
		 * c
		 * 
		 * @param key
		 */
		BSTNode(K key, BSTNode<K> left, BSTNode<K> right) {
			this.key = key;
			this.left = left;
			this.right = right;
		}

		/* accessors */
		// accessor methods for private fields
		K getKey() {
			return this.key;
		}

		BSTNode<K> getLeft() {
			return this.left;
		}

		BSTNode<K> getRight() {
			return this.right;
		}

		int getHeight(BSTNode<K> N) {
			if (N == null)
				return 0;
			return N.height;
		}

		/* mutators */
		// mutator methods for private fields
		void setKey(K newK) {
			key = newK;
		}

		void setLeft(BSTNode<K> newL) {
			left = newL;
		}

		void setRight(BSTNode<K> newR) {
			right = newR;
		}

		void setHeight(int newH) {
			height = newH;
		}
	}

	/* fields */
	BSTNode<K> bst;
	BSTNode<K> previous;

	/**
	 * AVLTree class, outer class, constructor for a BST node
	 */
	public AVLTree() {
		bst = null;
	}

	/**
	 * @return true if binary search tree is empty
	 */
	@Override
	public boolean isEmpty() {
		return bst == null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see AVLTreeADT#insert(java.lang.Comparable)
	 */
	@Override
	public void insert(K key) throws DuplicateKeyException, IllegalArgumentException {

		if (key == null) {
			throw new IllegalArgumentException();
		}
		bst = insert(bst, key);
	}

	/**
	 * @param node
	 * @param key
	 * @return
	 * @throws DuplicateKeyException
	 */
	private BSTNode<K> insert(BSTNode<K> node, K key) throws DuplicateKeyException {
		// Perform the normal BST insertion
		if (node == null)
			return new BSTNode<K>(key, null, null);
		if (node.getKey().equals(key)) {
			throw new DuplicateKeyException();
		}
		if (key.compareTo(node.getKey()) < 0)
			node.left = insert(node.left, key);
		else if (key.compareTo(node.getKey()) > 0)
			node.right = insert(node.right, key);

		// update height of this ancestor node
		node.height = 1 + max(height(node.left), height(node.right));

		// get the balance factor of this ancestor node to check whether this node
		// became unbalanced

		int balance = getBalance(node);

		// if this node becomes unbalanced, then there
		// are 4 cases Left Left Case
		if (balance > 1 && key.compareTo(node.getLeft().getKey()) < 0)
			return rightRotate(node);

		// Right Right case
		if (balance < -1 && key.compareTo(node.getRight().getKey()) > 0)
			return leftRotate(node);

		// Left Right case
		if (balance > 1 && key.compareTo(node.getLeft().getKey()) > 0) {
			node.left = leftRotate(node.left);
			return rightRotate(node);
		}

		// Right Left case
		if (balance < -1 && key.compareTo(node.getRight().getKey()) < 0) {
			node.right = rightRotate(node.right);
			return leftRotate(node);
		}

		// return the (unchanged) node pointer
		return node;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see AVLTreeADT#delete(java.lang.Comparable)
	 */
	@Override
	public void delete(K key) throws IllegalArgumentException {
		if (key == null)
			throw new IllegalArgumentException();
		bst = delete(bst, key);
	}

	/**
	 * @param root
	 * @param key
	 * @return
	 */
	private BSTNode<K> delete(BSTNode<K> root, K key) {
		if (root == null) {
			return root;
		}

		if (key.compareTo(root.getKey()) < 0)
			root.left = delete(root.left, key);

		else if (key.compareTo(root.getKey()) > 0)
			root.right = delete(root.right, key);
		else {

			// node with only one child or no child
			if ((root.left == null) || (root.right == null)) {
				BSTNode<K> temp = null;
				if (temp == root.left)
					temp = root.right;
				else
					temp = root.left;

				// No child case
				if (temp == null) {
					temp = root;
					root = null;
				} else // One child case
					root = temp; // Copy the contents of
									// the non-empty child
			} else {

				// node with two children: get the in-order
				// successor (smallest in the right subtree)
				BSTNode<K> temp = minValueNode(root.right);

				// copy the in-order successor's data to this node
				root.key = temp.key;

				// delete the in-order successor
				root.right = delete(root.right, temp.key);
			}

		}
		// if the tree had only one node then return
		if (root == null)
			return root;

		// update height of the current node
		root.height = max(bst.getHeight(root.left), bst.getHeight(root.right)) + 1;

		// get the balance factor of this node to check whether this node unbalanced
		int balance = getBalance(root);

		// if this node becomes unbalanced, then there are 4 cases
		// Left Left case
		if (balance > 1 && getBalance(root.left) >= 0)
			return rightRotate(root);

		// Left Right case
		if (balance > 1 && getBalance(root.left) < 0) {
			root.left = leftRotate(root.left);
			return rightRotate(root);
		}

		// Right Right case
		if (balance < -1 && getBalance(root.right) <= 0)
			return leftRotate(root);

		// Right Left case
		if (balance < -1 && getBalance(root.right) > 0) {
			root.right = rightRotate(root.right);
			return leftRotate(root);
		}

		return root;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see AVLTreeADT#search(java.lang.Comparable)
	 */
	@Override
	public boolean search(K key) throws IllegalArgumentException {
		if (key == null)
			throw new IllegalArgumentException();
		return search(bst, key);
	}

	/**
	 * @param n
	 * @param key
	 * @return
	 */
	private boolean search(BSTNode<K> n, K key) {
		if (n == null) {
			return false;
		}

		if (n.getKey().equals(key)) {
			return true;
		}

		if (key.compareTo(n.getKey()) < 0) {
			// key < this node's key; look in left subtree
			return search(n.getLeft(), key);
		}

		else {
			// key > this node's key; look in right subtree
			return search(n.getRight(), key);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see AVLTreeADT#print()
	 */
	@Override
	public String print() {
		printInorder(bst);
		return "";
	}

	/**
	 * @param node
	 * @return
	 */
	String printInorder(BSTNode<K> node) {

		if (node == null)
			return "";

		// first recur on left child
		printInorder(node.left);

		// then print the data of node
		System.out.print(node.key + " ");

		return printInorder(node.right);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see AVLTreeADT#checkForBalancedTree()
	 * 
	 * @return checkForBalancedTree(BSTNode<K> root)
	 */
	@Override
	public boolean checkForBalancedTree() {
		return checkForBalancedTree(bst);
	}

	/**
	 * @param root
	 * @return
	 */
	public boolean checkForBalancedTree(BSTNode<K> root) {
		int lh; // for height of left subtree

		int rh; // for height of right subtree

		// if tree is empty then return true
		if (root == null)
			return true;

		// get the height of left and right sub trees
		lh = height(root.left);
		rh = height(root.right);

		if (Math.abs(lh - rh) <= 1 && checkForBalancedTree(root.left) && checkForBalancedTree(root.right))
			return true;

		// if reach here then tree is not height-balanced
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see AVLTreeADT#checkForBinarySearchTree()
	 * 
	 * @return BinarySearchTree(BSTNode<K> root)
	 */
	@Override
	public boolean checkForBinarySearchTree() {
		previous = null;
		return BinarySearchTree(bst);
	}

	/**
	 * @param root
	 * @return true if it is a Binary Search Tree
	 */
	public boolean BinarySearchTree(BSTNode<K> root) {
		// traverse the tree in in-order fashion and
		// keep a track of previous node
		if (root != null) {
			if (!BinarySearchTree(root.left))
				return false;

			// allows only distinct values node
			if (previous != null && root.key.compareTo(previous.key) <= 0)
				return false;
			previous = root;
			return BinarySearchTree(root.right);
		}
		return true;
	}

	/**
	 * @param a
	 * @param b
	 * @return a or b, the larger one
	 */
	public int max(int a, int b) {
		return (a > b) ? a : b;
	}

	/**
	 * @param y
	 * @return
	 */
	public BSTNode<K> rightRotate(BSTNode<K> y) {
		BSTNode<K> x = y.getLeft();
		BSTNode<K> T2 = x.getRight();

		// Perform rotation
		x.right = y;
		y.left = T2;

		// Update heights
		y.height = max(bst.getHeight(y.left), bst.getHeight(y.right)) + 1;
		x.height = max(bst.getHeight(x.left), bst.getHeight(x.right)) + 1;

		// Return new root
		return x;
	}

	/**
	 * @param x
	 * @return
	 */
	public BSTNode<K> leftRotate(BSTNode<K> x) {
		BSTNode<K> y = x.getRight();
		BSTNode<K> T2 = y.getLeft();

		// Perform rotation
		y.left = x;
		x.right = T2;

		// Update heights
		x.height = max(bst.getHeight(x.left), bst.getHeight(x.right)) + 1;
		y.height = max(bst.getHeight(y.left), bst.getHeight(y.right)) + 1;

		// Return new root
		return y;
	}

	/**
	 * @param N
	 * @return integer number, check the distance whether larger than one
	 */
	public int getBalance(BSTNode<K> N) {
		if (N == null)
			return 0;

		return height(N.left) - height(N.right);
	}

	/**
	 * @param node
	 * @return integer number, which is the current entire tree's height and pick
	 *         larger left or right plus one
	 */
	public int height(BSTNode<K> node) {
		// base case tree is empty
		if (node == null)
			return 0;

		// If tree is not empty then height = 1 + max of left height and right heights
		return 1 + Math.max(height(node.left), height(node.right));
	}

	/**
	 * @param node
	 * @return minValueNode(BSTNode<K> node) finding the smallest node
	 */
	public BSTNode<K> minValueNode(BSTNode<K> node) {
		BSTNode<K> current = node;

		// loop down to find the leftmost leaf
		while (current.left != null)
			current = current.left;

		return current;
	}

	/**
	 * print the big picture of the tree
	 */
	public void printSideways() {
		System.out.println("------------------------------------------");
		recursivePrintSideways(bst, "");
		System.out.println("------------------------------------------");
	}

	/**
	 * @param current
	 * @param indent
	 */
	public void recursivePrintSideways(BSTNode<K> current, String indent) {
		if (current != null) {
			recursivePrintSideways(current.getRight(), indent + "     ");
			System.out.println(indent + current.getKey());
			recursivePrintSideways(current.getLeft(), indent + "     ");
		}
	}
}
