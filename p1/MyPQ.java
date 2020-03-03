/**
 * Filename:   MyPQ.java
 * Project:    p1TestPQ
 * Version:    1.0
 * User:       deppeler
 * Date:       Aug 29, 2018
 * Authors:    Debra Deppeler
 *
 * Semester:   Fall 2018
 * Course:     CS400
 * Instructor: Deppeler (deppeler@cs.wisc.edu)
 * Credits:    n/a
 * Bugs:       no known bugs, but not complete either
 *
 * Due Date:   before 10:00 pm on September 17th
 */


import java.util.NoSuchElementException;

/** TODO: add class header comments here
 * @param <T>
 *
 */
public class MyPQ<T extends Comparable<T>> implements PriorityQueueADT<T> {

	private T[] elementData;
	int size;
	
	public MyPQ()
	{
		List list = new ArrayList();
		elementData = new T[31];
		size = 0;
		T[] elementData = (T[])new Object[size];

	}
	
	/* (non-Javadoc)
	 * @see p1.PriorityQueueADT#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		
		return size == 0;
	}

	/* (non-Javadoc)
	 * @see p1.PriorityQueueADT#insert(java.lang.Comparable)
	 */
	@Override
	public void insert(T value) {
		elementData[size + 1] = value;
		int index = size + 1;
		boolean found = false;
		while (hasParent(index) && !found)
		{
			int parent = parent(index);
			if (elementData[index] < elementData[parent])
			{
				swap(elementData, index, parent(index));
				index = parent(index);
			}
			else
			{
				found = true;
			}
		}
		size++;
		
	}

	/* (non-Javadoc)
	 * @see p1.PriorityQueueADT#removeMax()
	 */
	@Override
	public T removeMax() throws NoSuchElementException {
		T result = getMax();
		elementData[1] = elementData[size];
		size--;
		int index = 1;
		boolean found = false;
		while (!found && hasLeftChild(index))
		{
			int left = leftChild(index);
			int right = rightChild(index);
			int child = left;
			if (hasRightChild(index) && elementData[right] < elementData[left])
			{
				child = right;
			}
			if (elementData[index] > elementData[child])
			{
				swap(elementData, index, child);
				index = child;
			} else {
				found = true;
			}
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see p1.PriorityQueueADT#getMax()
	 */
	@Override
	public T getMax() throws NoSuchElementException {
		T implement = elementData[1];
		return elementData[1];
	}
	private int parent(int index)
	{
		return index / 2;
	}

	private int leftChild(int index)
	{
		return index * 2;
	}


	private int rightChild(int index)
	{
		return index * 2 + 1;
	}

	private boolean hasParent(int index)
	{
		return index > 1;
	}

	private boolean hasLeftChild(int index)
	{
		return leftChild(index) <= size;
	}

	private boolean hasRightChild(int index)
	{
		return rightChild(index) <= size;
	}

	private void swap(int[] a, int index1, int index2)
	{
		int temp = a[index1];
		a[index1] = a[index2];
		a[index2] = temp;
	}

//	public String toString()
//	{
//		String str = "[";
//		
//		for (int i = 1; i < size; i++)
//		{
//			str +=  elementData[i] + ", ";
//		}
//		str += elementData[size]+"]";
//		
//		if(str.equals("[0]"))
//		{
//			return "[ ]";
//		}
//		return str;
//	}

}
