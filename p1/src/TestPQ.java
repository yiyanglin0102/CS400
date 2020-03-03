
/**
 * Filename:   TestPQ.java
 * Project:    p1TestPQ
 * Authors:    Debra Deppeler, Yiyang Lin
 *
 * Semester:   Fall 2018
 * Course:     CS400
 * Lecture:    001
 * 
 * Note: Warnings are suppressed on methods that construct new instances of 
 * generic PriorityQueue types.  The exceptions that occur are caught and 
 * such types are not able to be tested with this program.
 * 
 * Due Date:   Before 10pm on September 17, 2018
 * Version:    2.0
 * 
 * Credits:    None
 * 
 * Bugs:       no known bugs, but not complete either
 */

import java.util.*;

/**
 * Runs black-box unit tests on the priority queue implementations passed in as
 * command-line arguments (CLA).
 * 
 * If a class with the specified class name does not exist or does not implement
 * the PriorityQueueADT interface, the class name is reported as unable to test.
 * 
 * If the class exists, but does not have a default constructor, it will also be
 * reported as unable to test.
 * 
 * If the class exists and implements PriorityQueueADT, and has a default
 * constructor, the tests will be run.
 * 
 * Successful tests will be reported as passed.
 * 
 * Unsuccessful tests will include: input, expected output, and actual output
 * 
 * Example Output: Testing priority queue class: PQ01 5 PASSED 0 FAILED 5 TOTAL
 * TESTS RUN Testing priority queue class: PQ02 FAILED test00isEmpty:
 * unexpectedly threw java.lang.NullPointerException FAILED
 * test04insertRemoveMany: unexpectedly threw
 * java.lang.ArrayIndexOutOfBoundsException 3 PASSED 2 FAILED 5 TOTAL TESTS RUN
 * 
 * ... more test results here
 * 
 * @author deppeler
 */
public class TestPQ {

	public static final int ONE_MILLION = 1000000;

	// set to true to see call stack trace for exceptions
	private static final boolean DEBUG = false;

	/**
	 * Run tests to determine if each Priority Queue implementation works as
	 * specified. User names the Priority Queue types to test. If there are no
	 * command-line arguments, nothing will be tested.
	 * 
	 * @param args
	 *            names of PriorityQueueADT implementation class types to be tested.
	 */
	public static void main(String[] args) {
		for (int i = 0; i < args.length; i++)
			test(args[i]);

		if (args.length < 1)
			print("no PQs to test");
	}

	/**
	 * Run all tests on each priority queue type that is passed as a class name.
	 * 
	 * If constructing the priority queue in the first test causes exceptions, then
	 * no other tests are run.
	 * 
	 * @param className
	 *            the name of the class that contains the priority queue
	 *            implementation.
	 */
	private static void test(String className) {
		print("Testing priority queue class: " + className);
		int passCount = 0;
		int failCount = 0;
		try {

			if (test00isEmpty(className))
				passCount++;
			else
				failCount++;
			if (test01getMaxEXCEPTION(className))
				passCount++;
			else
				failCount++;
			if (test02removeMaxEXCEPTION(className))
				passCount++;
			else
				failCount++;
			if (test03insertRemoveOne(className))
				passCount++;
			else
				failCount++;
			if (test04insertRemoveMany(className))
				passCount++;
			else
				failCount++;
			if (test05duplicatesAllowed(className))
				passCount++;
			else
				failCount++;
			if (test06manyDataItem(className))
				passCount++;
			else
				failCount++;
			if (test07getMaxOne(className))
				passCount++;
			else
				failCount++;
			if (test08insertNullException(className))
				passCount++;
			else
				failCount++;
			if (test09getMaxEqualRemoveMax(className))
				passCount++;
			else
				failCount++;

			String passMsg = String.format("%4d PASSED", passCount);
			String failMsg = String.format("%4d FAILED", failCount);
			print(passMsg);
			print(failMsg);

		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			if (DEBUG)
				e.printStackTrace();
			print(className + " FAIL: Unable to construct instance of " + className);
		} finally {
			String msg = String.format("%4d TOTAL TESTS RUN", passCount + failCount);
			print(msg);
		}

	}

	/////////////////////////
	// TODO: ADD YOUR TEST METHODS HERE
	// Must test each operation of the PriorityQueueADT
	// Find and report cases that cause problems.
	// Do not try to fix or debug implementations.
	/////////////////////////

	/**
	 * TODO: provided as an example Confirm that inserted values of getMax is equal
	 * to the values of removeMax. Fail if equivalence does not match.
	 * 
	 * @param className
	 * @return true if getMax is equal to removeMax
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	private static boolean test09getMaxEqualRemoveMax(String className)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {

		PriorityQueueADT<Integer> pq = newIntegerPQ(className);
		for (int i = 0; i < ONE_MILLION; i++) {
			Random r = new Random();
			int randomNum;
			randomNum = r.nextInt(ONE_MILLION);
			pq.insert(randomNum);
			try {
				if (!pq.getMax().equals(pq.removeMax())) {
					print("FAILED test09getMaxEqualRemoveMax: getMax is not equal to removeMax");
					return false;
				}
			} catch (NullPointerException e) {
				print("FAILED test09getMaxEqualRemoveMax: unexpectedly threw " + e.getClass().getName());
				return false;
			} catch (Exception e) {
				print("FAILED test09getMaxEqualRemoveMax: unexpectedly threw " + e.getClass().getName());
				return false;
			}
		}
		return true;

	}

	/**
	 * TODO: provided as an example Confirm that insert null throws
	 * NullPointerException if called on an priority queue. Any other exception
	 * indicates a fail.
	 * 
	 * @param className
	 * @return true when inserts null and throws NullPointerException only
	 */
	private static boolean test08insertNullException(String className)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		PriorityQueueADT<Integer> pq = newIntegerPQ(className);
		try {
			pq.insert(null);
		} catch (NullPointerException e) {
			return true;
		} catch (Exception e) {
			if (DEBUG)
				e.printStackTrace();
			print("FAILED test08insertNullException: unexpectedly threw " + e.getClass().getName());
			return false;
		}
		print("FAILED test08insertNullException: null cannot be inserted");
		return false;
	}

	/**
	 * TODO: provided as an example Confirm that getMax is always the same as the
	 * value that was inserted. Throws NullPointerException when the tested priority
	 * queues fail unexpectedly.
	 * 
	 * @param className
	 * @return false if getMax fails the same value as inserted priority queue
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	private static boolean test07getMaxOne(String className)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		PriorityQueueADT<Integer> pq = newIntegerPQ(className);

		Random r = new Random();
		int randomNum;

		for (int i = 1; i <= ONE_MILLION; i++) {
			randomNum = r.nextInt(ONE_MILLION);
			pq.insert(randomNum);
			try {
				if (randomNum != pq.getMax()) {
					print("FAILED test07getMaxOne: getMax fails the same value as inserted priority queue");
					return false;
				}
			} catch (NullPointerException e) {
				print("FAILED test07getMaxOne: unexpectedly threw " + e.getClass().getName());
				return false;
			} catch (Exception e) {
				print("FAILED test09getMaxEqualRemoveMax: unexpectedly threw " + e.getClass().getName());
				return false;
			}
			pq.removeMax();
		}

		return true;
	}

	/**
	 * TODO: provided as an example confirm that the number of insert is the same
	 * times of the number of removeMax and can expand properly to allow many items
	 * to be added and removed in maximum to minimum order
	 *
	 * @param className
	 * @return true if the internal data structure does expand properly
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	private static boolean test06manyDataItem(String className)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {

		PriorityQueueADT<Integer> pq = newIntegerPQ(className);
		Random r = new Random();
		int randomNum;

		// divide 100 to ONE_MILLION, since run time may take long

		try {
			for (int i = 1; i <= ONE_MILLION / 100; i++) {
				randomNum = r.nextInt(ONE_MILLION);
				pq.insert(randomNum);
			}
			for (int j = 1; j <= ONE_MILLION / 100; j++) {
				pq.removeMax();
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			print("FAILED test06manyDataItem: unexpectedly threw " + e.getClass().getName());
			return false;
		} catch (NullPointerException e) {
			print("FAILED test06manyDataItem: unexpectedly threw " + e.getClass().getName());
			return false;
		} catch (Exception e) {
			print("FAILED test06manyDataItem: unexpectedly threw " + e.getClass().getName());
			return false;
		}
		return true;
	}

	/**
	 * TODO: provided as an example confirm that if duplicate values are able to be
	 * inserted and then removed.
	 * 
	 * @param className
	 * @return true if duplicate values are able to be inserted (and then removed)
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	private static boolean test05duplicatesAllowed(String className)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {

		PriorityQueueADT<Integer> pq = newIntegerPQ(className);
		Random r = new Random();
		for (int i = 1; i <= ONE_MILLION; i++) {
			int randomNum = r.nextInt(ONE_MILLION);
			try {
				pq.insert(randomNum);
				pq.insert(randomNum);
			} catch (NullPointerException e) {
				print("FAILED test05duplicatesAllowed: unexpectedly threw " + e.getClass().getName());
				return false;
			
		} catch (Exception e) {
			print("FAILED test09getMaxEqualRemoveMax: unexpectedly threw " + e.getClass().getName());
			return false;
		}
			if (!pq.removeMax().equals(pq.removeMax())) {
				print("FAILED test05duplicatesAllowed: duplicate values are not able to be inserted and then removed");
				return false;
			}
		}
		return true;
	}

	/**
	 * TODO: provided as an example confirm that removeMax is always return in
	 * priority order when inserts many items.
	 * 
	 * @param className
	 * @return true if inserted items match removeMax and return the max values in
	 *         priority order
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	private static boolean test04insertRemoveMany(String className)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		PriorityQueueADT<Integer> pq = newIntegerPQ(className);
		Random r = new Random();
		int randomNum;
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 1; i <= 1000; i++) {
			randomNum = r.nextInt(ONE_MILLION);
			list.add(randomNum);
			try {
				pq.insert(randomNum);
			} catch (NullPointerException e) {
				print("FAILED test04insertRemoveMany: unexpectedly threw " + e.getClass().getName());
				return false;
			} catch (Exception e) {
				print("FAILED test09getMaxEqualRemoveMax: unexpectedly threw " + e.getClass().getName());
				return false;
			}
		}
		Collections.sort(list, Collections.reverseOrder());
		for (int j = 0; j < 1000; j++) {
			try {
				if (!list.get(j).equals(pq.removeMax())) {
					print("FAILED test04insertRemoveMany: removeMax does not return the max values in the priority order");
					return false;
				}
			} catch (ArrayIndexOutOfBoundsException e) {
				print("FAILED test04insertRemoveMany: unexpectedly threw " + e.getClass().getName());
				return false;
			} catch (Exception e) {
				print("FAILED test09getMaxEqualRemoveMax: unexpectedly threw " + e.getClass().getName());
				return false;
			}
		}
		return true;
	}

	/**
	 * TODO: provided as an example confirm that removeMax is always the same as the
	 * value that was inserted. Throws NullPointerException when the tested priority
	 * queues fail unexpectedly.
	 * 
	 * @param className
	 * @return false if removeMax fails the same value as inserted priority queue
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	private static boolean test03insertRemoveOne(String className)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		PriorityQueueADT<Integer> pq = newIntegerPQ(className);
		Random r = new Random();
		int randomNum;
		for (int i = 0; i < ONE_MILLION; i++) {
			randomNum = r.nextInt(ONE_MILLION);
			pq.insert(randomNum);
			try {
				if (!pq.removeMax().equals(randomNum)) {
					print("FAILED test03insertRemoveOne: inserted item fails the same value as removeMax on newly constructed PQ");
					return false;
				}
			} catch (NullPointerException e) {
				print("FAILED test03insertRemoveOne: unexpectedly threw " + e.getClass().getName());
				return false;
			} catch (Exception e) {
				print("FAILED test09getMaxEqualRemoveMax: unexpectedly threw " + e.getClass().getName());
				return false;
			}
		}
		return true;
	}

	/**
	 * TODO -- provided as an example Confirm that removeMax throws
	 * NoSuchElementException if called on an empty priority queue. Any other
	 * exception indicates a fail.
	 * 
	 * @param className
	 * @return true if removeMax on empty priority queue throws
	 *         NoSuchElementException
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	private static boolean test02removeMaxEXCEPTION(String className)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		PriorityQueueADT<Integer> pq = newIntegerPQ(className);
		try {
			pq.removeMax();
		} catch (NoSuchElementException e) {
			return true;
		} catch (Exception e) {
			if (DEBUG)
				e.printStackTrace();
			print("FAILED test02removeMaxEXCEPTION: unexpectedly threw " + e.getClass().getName());
			return false;
		}
		print("FAILED test02removeMaxEXCEPTION: removeMax did not throw NoSuchElement exception on newly constructed PQ");
		return false;
	}

	/**
	 * DO NOT EDIT -- provided as an example Confirm that getMax throws
	 * NoSuchElementException if called on an empty priority queue. Any other
	 * exception indicates a fail.
	 * 
	 * @param className
	 *            name of the priority queue implementation to test.
	 * @return true if getMax on empty priority queue throws NoSuchElementException
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	private static boolean test01getMaxEXCEPTION(String className)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		PriorityQueueADT<Integer> pq = newIntegerPQ(className);
		try {
			pq.getMax();
		} catch (NoSuchElementException e) {
			return true;
		} catch (Exception e) {
			if (DEBUG)
				e.printStackTrace();
			print("FAILED test01getMaxEXCEPTION: unexpectedly threw " + e.getClass().getName());
			return false;
		}
		print("FAILED test01getMaxEXCEPTION: getMax did not throw NoSuchElement exception on newly constructed PQ");
		return false;
	}

	/**
	 * DO NOT EDIT THIS METHOD
	 * 
	 * @return true if able to construct Integer priority queue and the instance
	 *         isEmpty.
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	private static boolean test00isEmpty(String className)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		PriorityQueueADT<Integer> pq = newIntegerPQ(className);
		try {
			if (pq.isEmpty())
				return true;
		} catch (Exception e) {
			if (DEBUG)
				e.printStackTrace();
			print("FAILED test00isEmpty: unexpectedly threw " + e.getClass().getName());
			return false;
		}
		print("FAILED test00isEmpty: isEmpty returned false on newly constructed PQ");
		return false;
	}

	/**
	 * DO NOT EDIT THIS METHOD Constructs a max Priority Queue of Integer using the
	 * class that is name.
	 * 
	 * @param className
	 *            The specific Priority Queue to construct.
	 * @return a PriorityQueue
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings({ "unchecked" })
	public static final PriorityQueueADT<Integer> newIntegerPQ(String className)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Class<?> pqClass = Class.forName(className);
		Object obj = pqClass.newInstance();
		if (obj instanceof PriorityQueueADT) {
			return (PriorityQueueADT<Integer>) obj;
		}
		return null;
	}

	/**
	 * DO NOT EDIT THIS METHOD Constructs a max Priority Queue of Double using the
	 * class that is named.
	 * 
	 * @param className
	 *            The specific Priority Queue to construct.
	 * @return a PriorityQueue
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings({ "unchecked" })
	public static final PriorityQueueADT<Double> newDoublePQ(final String className)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Class<?> pqClass = Class.forName(className);
		Object obj = pqClass.newInstance();
		if (obj instanceof PriorityQueueADT) {
			return (PriorityQueueADT<Double>) obj;
		}
		return null;
	}

	/**
	 * DO NOT EDIT THIS METHOD Constructs a max Priority Queue of String using the
	 * class that is named.
	 * 
	 * @param className
	 *            The specific Priority Queue to construct.
	 * @return a PriorityQueue
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings({ "unchecked" })
	public static final PriorityQueueADT<String> newStringPQ(final String className)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Class<?> pqClass = Class.forName(className);
		Object obj = pqClass.newInstance();
		if (obj instanceof PriorityQueueADT) {
			return (PriorityQueueADT<String>) obj;
		}
		return null;
	}

	/**
	 * DO NOT EDIT THIS METHOD Write the message to the standard output stream.
	 * Always adds a new line to ensure each message is on its own line.
	 * 
	 * @param message
	 *            Text string to be output to screen or other.
	 */
	private static void print(String message) {
		System.out.println(message);
	}

}
