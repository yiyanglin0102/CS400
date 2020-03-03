
/**
 * Filename:   TestAVLTree.java
 * Project:    p2
 * Authors:    DEBRA DEPPELER, YIYANG LIN, YU-TAI CHEN
 *
 * Semester:   Fall 2018
 * Course:     CS400
 * Lecture:    001,002
 * 
 * Due Date:   Before 10pm on October 8, 2018
 * Version:    1.0
 * 
 * Credits:    GeeksforGeeks, CS400 course readings
 * 
 * Bugs:       no known bugs, but not complete either
 */

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.lang.IllegalArgumentException;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

public class TestAVLTree {
	private static final int ONE_MILLION = 1000000;
	private static int points;
	private AVLTree<Integer> tree;
	private int randomNum;
	Random r = new Random();
	@Rule
	public Timeout globalTimeOut = new Timeout(2000, TimeUnit.MILLISECONDS);

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		points = 0;
	}

	@Before
	public void setUp() throws Exception {
		tree = new AVLTree<Integer>();
		randomNum = r.nextInt();
		// System.out.print(randomNum);
	}

	@After
	public void tearDown() throws Exception {
		System.out.println(points);
		tree = null;
		randomNum = 0;
	}

	/**
	 * Tests that an AVLTree is empty upon initialization. //
	 */
	@Test
	public void test01isEmpty() {
		assertTrue(tree.isEmpty());
		points++;
	}

	/**
	 * Tests that an AVLTree is not empty after adding a node.
	 * 
	 * @throws DuplicateKeyException
	 * @throws IllegalArgumentException
	 */
	@Test
	public void test02isNotEmpty() throws IllegalArgumentException, DuplicateKeyException {
		tree.insert(randomNum);
		assertFalse(tree.isEmpty());
		points++;
	}

	/**
	 * Tests functionality of a single delete following several inserts.
	 *
	 * @throws DuplicateKeyException
	 * @throws IllegalArgumentException
	 */
	@Test
	public void test03insertManyDeleteOne() throws IllegalArgumentException, DuplicateKeyException {

		for (int i = 0; i < ONE_MILLION / 100; i++) {
			randomNum = r.nextInt();
			try {
				tree.insert(randomNum);
			} catch (DuplicateKeyException e) {
				points++;
			} catch (Exception e) {
				fail("unexpacted exception insertManyDeleteOne" + e);
			}

		}
		int randomNum2 = randomNum;
		assertTrue(tree.search(randomNum2));
		tree.delete(randomNum2);
		assertFalse(tree.search(randomNum2));
		points++;

	}

	/**
	 * Tests functionality of many deletes following several inserts.
	 * 
	 * @throws DuplicateKeyException
	 * @throws IllegalArgumentException
	 */
	@Test
	public void test04insertManyDeleteMany() {
		int[] list = new int[10];

		for (int i = 0; i < ONE_MILLION / 100; i++) {
			randomNum = r.nextInt();
			try {
				tree.insert(randomNum);
				if (i < 10) {
					list[i] = randomNum;
				}
			} catch (DuplicateKeyException e) {
				points++;
			} catch (Exception e) {
				fail("unexpected exception in insertManyDeleteMany: " + e);
			}
		}

		for (int j = 0; j < list.length; j++) {
			assertTrue(tree.search(list[j]));
			tree.delete(list[j]);
			assertFalse(tree.search(list[j]));

		}
		points++;
	}

	/**
	 * Tests functionality of searching an element that has been delete
	 * 
	 * @throws DuplicateKeyException
	 * @throws IllegalArgumentException
	 */
	@Test
	public void test05DeleteOneCannotSearch() {
		int find = 0;
		for (int i = 0; i < ONE_MILLION / 100; i++) {
			randomNum = r.nextInt();
			try {
				tree.insert(randomNum);
				if (i == 0) {
					find = randomNum;
				}
			} catch (DuplicateKeyException e) {
				points++;
			} catch (Exception e) {
				fail("unexpected exception in DeleteOneCannotSearch: " + e);
			}
		}
		tree.delete(find);
		assertFalse(tree.search(find));
		points++;
	}

	/**
	 * Tests functionality of inserting duplicate value and correctly throws
	 * duplicateKeyException
	 * 
	 * @throws DuplicateKeyException
	 * 
	 */
	@Test
	public void test06InsertSameNumberException() throws DuplicateKeyException {

		try {
			tree.insert(randomNum);
			tree.insert(randomNum);
			fail("fail to throw duplicateKeyException");
		} catch (DuplicateKeyException e) {
			points++;
		} catch (Exception e) {
			fail("unexpected exception when inserting same number: " + e);
		}

	}

	/**
	 * Tests functionality of searching after insert and delete the same value
	 * 
	 * @throws DuplicateKeyException
	 * @throws IllegalArgumentException
	 *
	 */
	@Test
	public void test07InsertOneDeleteOneEmpty() throws IllegalArgumentException, DuplicateKeyException {
		tree.insert(randomNum);
		tree.delete(randomNum);
		try {
			if (tree.isEmpty()) {
				points++;
			} else
				fail("Insert one delete one failed ");
		} catch (Exception e) {
			fail("Unexpected excpetion " + e);
		}
	}

	/**
	 * Tests functionality of inserting null value and correctly throws
	 * IllegalArgumentException
	 * 
	 * @throws DuplicateKeyException
	 * @throws IllegalArgumentException
	 *
	 */
	@Test
	public void test08InsertNull() throws IllegalArgumentException, DuplicateKeyException {

		try {
			try {
				tree.insert(null);
				fail("Insert null has not been thrown");
			} catch (Exception e) {
				points++;
			}
			tree.insert(randomNum);
			tree.insert(null);
			fail("Insert null has not been thrown");
		} catch (Exception e) {
			points++;
		}

	}

	/**
	 * Tests functionality of searching null value and correctly throws
	 * IllegalArgumentException
	 * 
	 * @throws DuplicateKeyException
	 * @throws IllegalArgumentException
	 *
	 */
	@Test
	public void test09SearchNull() throws IllegalArgumentException, DuplicateKeyException {

		try {
			try {
				tree.search(null);
				fail("search null has not been thrown");
			} catch (Exception e) {
				points++;
			}
			tree.insert(randomNum);
			tree.search(null);
			fail("search null has not been thrown");
		} catch (Exception e) {
			points++;
		}
	}

	/**
	 * Tests functionality of deleting null value and correctly throws
	 * IllegalArgumentException
	 * 
	 * @throws DuplicateKeyException
	 * @throws IllegalArgumentException
	 *
	 */
	@Test
	public void test10DeleteNull() throws IllegalArgumentException, DuplicateKeyException {

		try {
			try {
				tree.delete(null);
				fail("delete null has not been thrown");
			} catch (Exception e) {
				points++;
			}
			tree.insert(randomNum);
			tree.delete(null);
			fail("delete null has not been thrown");
		} catch (Exception e) {
			points++;
		}
	}

	/**
	 * Test the print() method has the correct printing
	 * 
	 * @throws IllegalArgumentException
	 * @throws DuplicateKeyException
	 */
	@Test
	public void test11checkPrint() throws IllegalArgumentException, DuplicateKeyException {
		try {
			tree.insert(1);
			tree.insert(2);
			tree.insert(3);
			tree.insert(4);
			tree.insert(5);
		} catch (Exception e) {
			fail("unexpected exception : " + e);
		}
		assertEquals(tree.print(), "1 2 3 4 5 ");
	}

	/**
	 * Test the checkForBalancedTree() method has correct balance
	 * 
	 * @throws IllegalArgumentException
	 * @throws DuplicateKeyException
	 */
	@Test
	public void test12checkForBalancedTree() throws IllegalArgumentException, DuplicateKeyException {
		try {
			tree.insert(4);
			tree.insert(2);
			tree.insert(6);
			tree.insert(1);
			tree.insert(3);
			tree.insert(5);
			tree.insert(7);
		} catch (Exception e) {
			fail("unexpected exception : " + e);
		}
		assertEquals(tree.checkForBalancedTree(), true);

	}
	
	/**
	 * Test the checkForBinarySearchTree() method
	 * 
	 * @throws IllegalArgumentException
	 * @throws DuplicateKeyException
	 */
	@Test
	public void test13checkForBinarySearchTree() throws IllegalArgumentException, DuplicateKeyException {
		try {
			tree.insert(4);
			tree.insert(2);
			tree.insert(6);
			tree.insert(1);
			tree.insert(3);
			tree.insert(5);
			tree.insert(7);
		} catch (Exception e) {
			fail("unexpected exception : " + e);
		}
		assertEquals(tree.checkForBinarySearchTree(), true);

	}

}
