package listClasses;

import java.util.*;


/**
 * Implements a generic sorted list using a provided Comparator. It extends
 * BasicLinkedList class.
 * 
 *  @author Dept of Computer Science, UMCP
 *  
 */

public class SortedLinkedList<T> extends BasicLinkedList<T> {
	private Comparator<T> comparator;
	
	public SortedLinkedList(Comparator<T> comparator) {
		//call for the super class constructor
		super();
		//initiate the comparator
		this.comparator = comparator;
	}
	public SortedLinkedList<T> add(T element){
		//create a new node
		Node nodeToAdd =new Node(element);

		if(element == null) {
			return null;
		}
		if(listSize > 0) {
			Node curr = head;
			Node prev = null;
			//traverse through the list
			while(curr != null) {
				//if the element is less than the current node
				if(comparator.compare(nodeToAdd.data, curr.data) < 0) {
					//if the current node is the head
					if(curr.equals(head)) {
						//make the pointer of the new node to point to the head
						nodeToAdd.next = curr;
						//set the head to the new node
						head = nodeToAdd;
					}else {
						//set the new node's pointer to the current node
						nodeToAdd.next = curr;
						//set the previous node's pointer to the new node
						prev.next = nodeToAdd;
					}
					listSize++;
					break;
				}else if(comparator.compare(nodeToAdd.data, curr.data) == 0) {
					//if the current node equals to the new node
					//set the pointer of the new node to the next node
					nodeToAdd.next = curr.next;
					//set the next node to the new node
					curr.next = nodeToAdd;
					listSize++;
					break;
				}else if(comparator.compare(nodeToAdd.data, curr.data) > 0) {
					//if the new node is bigger than the current ndoe
					if(curr.equals(tail)) {
						//if the node is the tail
						//set the pointer of the last node to point to the new node
						tail.next = nodeToAdd;
						//make the new node to be the tail
						tail = nodeToAdd;
						listSize++;
						break;
					}else {
						//continue to traverse the list
						prev = curr;
						curr = curr.next;
					}
				}
			}
		}else {
			//if there is no nodes in the list
			//set the head and tail to the new node
			head = nodeToAdd;
			tail = nodeToAdd;
			listSize++;
		}
		return this;
	}
	public SortedLinkedList<T> remove(T targetData){
		//call the super class's remove method
		super.remove(targetData, comparator);
		return this;
	}
	public SortedLinkedList<T> addtoEnd(T data){
		throw new UnsupportedOperationException("Invalid operation for sorted list");
	}
	public SortedLinkedList<T> addToFront(T data){
		throw new UnsupportedOperationException("Invalid operation for sorted list");
	}
}