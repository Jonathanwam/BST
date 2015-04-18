


/**
 * A Binary Search Tree is a Binary Tree with the following
 * properties:
 * 
 * For any node x: (1) the left child of x compares "less than" x; 
 * and (2) the right node of x compares "greater than or equal to" x
 *
 *
 * @param <T> the type of data object stored by the BST's Nodes
 */
public class BST<T extends Comparable<T>> {

	/**
	 * The root Node is a reference to the 
	 * Node at the 'top' of a QuestionTree
	 */
	private Node<T> root;
	private Node<T> tempNode;
	private Node<T> parentHolder;
	private Node<T> min, max;
	
	// used in delete method
	private Node<T> aye, righty;
	
	
	/**
	 * Construct a BST
	 */
	public BST() {
		root = null;
		tempNode = null;
		parentHolder = null;
	}
	
	/**
	 * @return the root of the BST
	 */
	public Node<T> getRoot() { 
		return root;
	}
	
	/**
	 * _Part 1: Implement this method._
	 *
	 * Add a new piece of data into the tree. To do this, you must:
	 * 
	 * 1) If the tree has no root, create a root node 
	 *    with the supplied data
	 * 2) Otherwise, walk the tree from to the bottom (to a leaf) as though
	 *    searching for the supplied data. Then:
	 * 3) Add a new Node to the leaf where the search ended.
	 *
	 * @param data - the data to be added to the tree
	 */
	
	
	public void add(T data) {
		
		if (root == null){
			root = new Node<T>(data);
			return;
		}
		
		tempNode = root;
		parentHolder = root;
		
		// x holds left or right value for later use. 0 is left and 1 is right.
		int x = 0;
		
		while (tempNode != null){
			// if data is greater than or equal to the current node's data, do right stuff
			if (data.compareTo(tempNode.data) >= 0 ){
				parentHolder = tempNode;
				x = 1;
				tempNode = tempNode.right;
			}
			// else data is lesser than node's data so do left stuff
			else {
				parentHolder = tempNode;
				x = 0;
				tempNode = tempNode.left;
			}
		}
		
		// out of the while loop so tempNode is at desired location
		
		tempNode = new Node<T>(data);
		tempNode.parent = parentHolder;
		if (x == 0) tempNode.parent.left = tempNode;
		else tempNode.parent.right = tempNode;
		
		return;
	}
	

	/**
	 * _Part 2: Implement this method._
	 * 
	 * Find a Node containing the specified key and
	 * return it.  If such a Node cannot be found,
	 * return null.  This method works by walking
	 * through the tree starting at the root and
	 * comparing each Node's data to the specified 
	 * key and then branching appropriately.
	 * 
	 * @param key - the data to find in the tree
	 * @return a Node containing the key data, or null.
	 */
	public Node<T> find(T key) {
		
		tempNode = root;
		
		if (root == null) //Nothing in this tree!
			return null;
		
		while (tempNode != null){
			if (tempNode.data == key){
				return tempNode;
			}
			
			// else if key is less than data in tempNode
			else if (key.compareTo(tempNode.data) < 0){
				tempNode = tempNode.left;
			}
			
			// else key is greater than data in tempNode
			else
				tempNode = tempNode.right;
		}

		// did not find the key! Return null
		return null;
	}

	/**
	 * _Part 3: Implement this method._
	 *  
	 * @return the Node with the largest value in the BST
	 */
	public Node<T> maximum() {
		if (root == null || root.right == null){
			return root;
		}
		
		// tempNode will become the null after the max
		while (tempNode != null){
			max = tempNode;
			tempNode = tempNode.right;
		}
		
		return max;
	}
	
	/**
	 * _Part 4: Implement this method._
	 *  
	 * @return the Node with the smallest value in the BST
	 */
	public Node<T> minimum() {
		
		if (root == null || root.left == null){
			return root;
		}
		
		// tempNode will become the null after the min
		while (tempNode != null){
			min = tempNode;
			tempNode = tempNode.left;
			}
		
		return min;
	}
	

	
	/**
	 * _Part 5: Implement this method._
	 *  
	 * Searches for a Node with the specified key.
	 * If found, this method removes that node
	 * while maintaining the properties of the BST.
	 *  
	 * @return the Node that was removed or null.
	 */
	
	
	// first need to find it. Then there are several cases...
	// if there are no children, simply remove it.
	// if it has one child, simply replace the node with the child
	// if it has 2 children then things just get complicated sadly
	public Node<T> remove(T data) {
		tempNode = find(data);
		
		// if it didn't find the data then just return null right away.
		if (tempNode == null){
			return null;
		}
		
		// if no parent or children or data, it's entirely empty.
		// if no parent or children but has data, root is the only element.
		if (tempNode.parent == null && tempNode.left == null && tempNode.right == null){
			tempNode.data = null;
			return tempNode;
		}
		
		// if no children but has parent
		if (tempNode.right == null && tempNode.left == null){
			if (tempNode.parent.left == tempNode){
				tempNode.parent.left = null;
			}
			else tempNode.parent.right = null;
			
			return tempNode;
		}
		
		// if one child just change some pointers and leave tempNode for garbage collection
		if (tempNode.right == null || tempNode.left == null){
			if (tempNode.right != null){
				tempNode.parent.right = tempNode.right;
				tempNode.right.parent = tempNode.parent;
			}
			else {
				tempNode.parent.left = tempNode.left;
				tempNode.left.parent = tempNode.parent;
			}
			
			return tempNode;
		}
		
		// if 2 children ... not as complicated as I originally thought it would be
		// aye is the leftmost node on the tempNode's right branch, righty.
		righty = tempNode.right;
		while (righty != null){
			aye = righty;
			righty = righty.left;
		}
		
		// aye's data will replace tempNode. Then delete aye as a result.
		tempNode.data = aye.data;
		
		// remove aye here...
		// aye should have only 1 or 0 children...
		if (aye.right != null){
			aye.parent.left = aye.right;
			aye.right.parent = aye.parent;
		}
		
		// else, it has no children
		else aye.parent.left = null;
		
		return tempNode;
	}
	
	/**
	 * 
	 * The Node class for our BST.  The BST
	 * as defined above is constructed from zero or more
	 * Node objects. Each object has between 0 and 2 children
	 * along with a data member that must implement the
	 * Comparable interface.
	 * 
	 * @param <T>
	 */
	public static class Node<T extends Comparable<T>> {
		private Node<T> parent;
		private Node<T> left;
		private Node<T> right;
		private T data;
		
		private Node(T d) {
			data = d;
			parent = null;
			left = null;
			right = null;
		}
		public Node<T> getParent() { return parent; }
		public Node<T> getLeft() { return left; }
		public Node<T> getRight() { return right; }
		public T getData() { return data; }
	}
}