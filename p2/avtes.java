

public class avtes {

	public static void main(String[] args) throws IllegalArgumentException, DuplicateKeyException {
		
		
		
		
		AVLTree<Integer> tree = new AVLTree<Integer>();
		

		/* Constructing tree given in the above figure */
//		tree.insert(null);
//		for (int i = 0; i < 100; i++) {
//			tree.insert(i);
//		}
		
		
//		tree.insert(8);
//		tree.insert(null);
//		tree.insert(4);
//		
//		tree.insert(7);
//		tree.insert(9);	
//		tree.insert(5);
//		
//		
//		
////		tree.insert(3);
//		tree.insert(2);
//		
//		
//		tree.insert(1);
//		tree.insert(3);
//		
//		tree.delete(6);
		
		
		tree.insert(1);
		tree.insert(2);
		tree.insert(3);
		tree.insert(4);
		tree.insert(5);
		tree.insert(6);
		tree.insert(7);
		
		
//		tree.insert(11);		
//		tree.insert(-1);
//		tree.insert(1);
//		tree.insert(2);
		tree.print();
	
		System.out.println(tree.checkForBalancedTree());
		
//		tree.getBalance(tree);
		
		tree.printSideways();
		/* The constructed AVL Tree would be 
        9 
       /  \ 
      1    10 
     /  \    \ 
     0    5    11 
     /    /  \ 
   -1   2    6 
      */


	}

}
