package etc;

/**
 * 
 * @author Maxmanski
 *
 * @param <A>
 */
public class DoubleLinkedList<A> {

	private ListNode root;
	
	public DoubleLinkedList(){
		this.root = null;
	}
	
	public void add(A a){
		this.addFirst(a);
	}
	
	public void addFirst(A a){
		
	}
	
	public void addLast(A a){
		
	}
	
	public void remove(A a){
		
	}
	
	public void removeFirst(){
		
	}
	
	public void removeLast(){
		
	}
	
	public void remove(int index){
		
	}
	
	public A get(int index){
		return null;
	}
	
	public A getFirst(){
		return null;
	}
	
	public A getLast(){
		return null;
	}
	
	public int size(){
		if(this.root == null){
			return 0;
		}else{
			return 0;
		}
	}
	
	public boolean isEmpty(){
		return this.size() == 0;
	}
	
	/**
	 * 
	 * @author Maxmanski
	 *
	 */
	private class ListNode<A>{
		private final A content;
		private ListNode<A> succ, pred;
		
		public ListNode(A content, ListNode<A> successor, ListNode<A> predecessor){
			this.content = content;
			this.succ = successor;
			this.pred = predecessor;
		}
		
		public A getContent(){
			return this.content;
		}
		public ListNode<A> getSuccessor(){
			return this.succ;
		}
		public ListNode<A> getPredecessor(){
			return this.pred;
		}
		
		public void setPredecessor(ListNode<A> node){
			this.pred = node;
		}
		
		public void setSuccessor(ListNode<A> node){
			this.succ = node;
		}
	}
}
