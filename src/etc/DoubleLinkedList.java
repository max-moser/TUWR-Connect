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
	
	public void addFirst(A elem){
		if(this.root == null){
			this.root = new ListNode(elem);
			this.root.setSuccessor(this.root);
			this.root.setPredecessor(this.root);
		}else{
			ListNode oldRoot = this.root;
			this.root = new ListNode(elem);
			this.root.setSuccessor(oldRoot);
			this.root.setPredecessor(oldRoot.pred);
			
			oldRoot.getSuccessor().setPredecessor(this.root);
			oldRoot.setPredecessor(this.root);
		}
	}
	
	public void remove(A a){
		throw new UnsupportedOperationException("Removing of individual Objects not yet implemented");
	}
	
	public boolean removeFirst(){
		if(this.root == null){
			return false;
		}else if(this.root.getPredecessor() == this.root){
			// es ist nur das Root-Element vorhanden
			this.root.setPredecessor(null);
			this.root.setSuccessor(null);
			this.root = null;
			return true;
		}else{
			// es sind mehrere Elemente vorhanden
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
	
	public boolean removeLast(){
		if(this.root == null){
			return false;
		}else if(this.root.getPredecessor() == this.root){
			// es ist nur das Root-Element vorhanden
			this.root = null;
			return true;
		}else{
			// es sind mehrere Elemente vorhanden
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
	
	public void remove(int index){
		throw new UnsupportedOperationException("Removing of Objects at specified indices not yet implemented");
	}
	
	public A get(int index){
		int i = index % this.size();
		int pos = 0;
		ListNode node = this.root;
		
		while(pos < i){
			pos++;
			node = node.getSuccessor();
		}
		
		return node.getContent();
	}
	
	public A getFirst(){
		if(this.root == null){
			return null;
		}else{
			return this.root.getContent();
		}
	}
	
	public A getLast(){
		if(this.root == null){
			return null;
		}else{
			return this.root.getPredecessor().getContent();
		}
	}
	
	public A getAndRemoveLast(){
		if(this.root == null){
			return null;
		}else{
			A ret = this.root.getPredecessor().getContent();
			this.removeLast();
			return ret;
		}
	}
	
	public int size(){
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
	
	public boolean isEmpty(){
		return (this.root == null);
	}
	
	/**
	 * 
	 * @author Maxmanski
	 *
	 */
	private class ListNode{
		private final A content;
		private ListNode succ, pred;
		
		public ListNode(A content){
			this(content, null, null);
		}
		
		public ListNode(A content, ListNode successor, ListNode predecessor){
			this.content = content;
			this.succ = successor;
			this.pred = predecessor;
		}
		
		public A getContent(){
			return this.content;
		}
		public ListNode getSuccessor(){
			return this.succ;
		}
		public ListNode getPredecessor(){
			return this.pred;
		}
		
		public void setPredecessor(ListNode node){
			this.pred = node;
		}
		
		public void setSuccessor(ListNode node){
			this.succ = node;
		}
	}
}
