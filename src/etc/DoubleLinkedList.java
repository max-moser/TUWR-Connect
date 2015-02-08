package etc;

// TODO: derive a List which is always sorted. ..?
/**
 * An implementation of a Double Linked List, specifically designed
 * to meet the needs of the data package.
 * 
 * @author Maxmanski
 * @version 1.0
 *
 * @param <A> The type of objects to be contained in the List.
 */
public class DoubleLinkedList<A> {

	private ListNode root;
	
	/**
	 * Creates an empty instance of a DoubleLinkedList.
	 */
	public DoubleLinkedList(){
		this.root = null;
	}
	
	/**
	 * Adds the specified element to the first position of the List.
	 * 
	 * @param elem The element to be added
	 */
	public synchronized void add(A elem){
		this.addFirst(elem);
	}
	
	/**
	 * Adds the specified element to the first position of the List.
	 * 
	 * @param elem The element to be added
	 */
	public synchronized void addFirst(A elem){
		if(this.root == null){
			this.root = new ListNode(elem);
			this.root.setSuccessor(this.root);
			this.root.setPredecessor(this.root);
		}else{
			ListNode oldRoot = this.root;
			ListNode newRoot = new ListNode(elem);
			this.root = newRoot;
			this.root.setSuccessor(oldRoot);
			this.root.setPredecessor(oldRoot.pred);
			
			oldRoot.getPredecessor().setSuccessor(this.root);
			oldRoot.setPredecessor(this.root);
		}
	}
	
	/**
	 * Removes the first element in the List.
	 * 
	 * @return TRUE, if an element was removed and FALSE if the list was empty
	 */
	public synchronized boolean removeFirst(){
		if(this.root == null){
			return false;
		}else if(this.root.getPredecessor() == this.root){
			// the list only contains the root element
			this.root.setPredecessor(null);
			this.root.setSuccessor(null);
			this.root = null;
			return true;
		}else{
			// there are several elements in the list
			ListNode preRoot, succRoot;
			preRoot = this.root.getPredecessor();
			succRoot = this.root.getSuccessor();
			
			preRoot.setSuccessor(succRoot);
			succRoot.setPredecessor(preRoot);
			this.root.setSuccessor(null);
			this.root.setPredecessor(null);
			this.root = succRoot;
			return true;
		}
	}
	
	/**
	 * Removes the last element of the List.
	 * 
	 * @return TRUE, if an element was removed and FALSE, if the List was empty
	 */
	public synchronized boolean removeLast(){
		if(this.root == null){
			return false;
		}else if(this.root.getPredecessor() == this.root){
			// the list only contains the root element
			this.root.setSuccessor(null);
			this.root.setPredecessor(null);
			this.root = null;
			return true;
		}else{
			// there are several items in the list
			ListNode last, preLast;
			last = this.root.getPredecessor();
			preLast = last.getPredecessor();
			preLast.setSuccessor(this.root);
			this.root.setPredecessor(preLast);
			last.setPredecessor(null);
			last.setSuccessor(null);
			return true;
		}
	}
	
	/**
	 * Returns the element at the specified index.
	 * 
	 * @param index The index of the element to return (starting with 0)
	 * @return The element at the specified index if the List isn't empty and NULL otherwise.
	 */
	public synchronized A get(int index){
		if(this.root == null){
			return null;
		}
		
		int i = index % this.size();
		int pos = 0;
		ListNode node = this.root;
		
		while(pos < i){
			pos++;
			node = node.getSuccessor();
		}
		
		return node.getContent();
	}
	
	/**
	 * Returns the first element of the List.
	 * 
	 * @return The head element of the List, or NULL if the List is empty
	 */
	public synchronized A getFirst(){
		if(this.root == null){
			return null;
		}else{
			return this.root.getContent();
		}
	}
	
	/**
	 * Returns the last element of the List.
	 * 
	 * @return The last element of the List, or NULL if the List is empty
	 */
	public synchronized A getLast(){
		if(this.root == null){
			return null;
		}else{
			return this.root.getPredecessor().getContent();
		}
	}
	
	/**
	 * Returns the last element of the List and removes it from the List.
	 * 
	 * @return The last element of the List, or NULL if the List is empty
	 */
	public synchronized A getAndRemoveLast(){
		if(this.root == null){
			return null;
		}else{
			A ret = this.root.getPredecessor().getContent();
			this.removeLast();
			return ret;
		}
	}
	
	/**
	 * Returns the count of elements inside the List.
	 * 
	 * @return An integer greater or equal to 0
	 */
	public synchronized int size(){
		if(this.root == null){
			return 0;
		}else{
			int cnt = 1;
			ListNode curNode = this.root.getSuccessor();
			
			while(curNode != this.root){
				cnt++;
				curNode = curNode.getSuccessor();
			}
			
			return cnt;
		}
	}
	
	/**
	 * Returns a boolean value depending on whether or not the List is empty.
	 * 
	 * @return TRUE if the List is empty and FALSE otherwise.
	 */
	public synchronized boolean isEmpty(){
		return (this.root == null);
	}
	
	/**
	 * Clears the List's contents.
	 */
	public synchronized void clear(){
		this.root = null;
	}
	
	/**
	 * 
	 * @author Maxmanski
	 *
	 */
	private class ListNode{
		private final A content;
		private ListNode succ, pred;
		
		/**
		 * Creates a new ListNode with no predecessor or successor.
		 * 
		 * @param content The content of the Node.
		 */
		public ListNode(A content){
			this(content, null, null);
		}
		
		/**
		 * Creates a new ListNode with the specified content, predecessor and successor.
		 * 
		 * @param content The content of the Node.
		 * @param successor The successor of the Node.
		 * @param predecessor The predecessor of the Node.
		 */
		public ListNode(A content, ListNode successor, ListNode predecessor){
			this.content = content;
			this.succ = successor;
			this.pred = predecessor;
		}
		
		/**
		 * Returns the content of the Node
		 * 
		 * @return A reference to the content of the Node
		 */
		public A getContent(){
			return this.content;
		}
		
		/**
		 * Returns the successor of the Node.
		 * 
		 * @return An instance of the ListNode, or NULL if the Node does not have a successor.
		 */
		public ListNode getSuccessor(){
			return this.succ;
		}
		
		/**
		 * Returns the predecessor of the Node.
		 * 
		 * @return An instance of the ListNode, or NULL if the Node does not have a predecessor.
		 */
		public ListNode getPredecessor(){
			return this.pred;
		}
		
		/**
		 * Sets the predecessor to the specified Node.
		 * 
		 * @param node The new predecessor of the Node.
		 */
		public void setPredecessor(ListNode node){
			this.pred = node;
		}
		
		/**
		 * Sets the successor to the specified Node.
		 * 
		 * @param node The new successor of the Node.
		 */
		public void setSuccessor(ListNode node){
			this.succ = node;
		}
	}
}
