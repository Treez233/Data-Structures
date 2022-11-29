package listClasses;

import java.util.*;

public class BasicLinkedList<T> implements Iterable<T> {

	/* Node definition */
	protected class Node {
		protected T data;
		protected Node next;

		protected Node(T data) {
			this.data = data;
			next = null;
		}
	}

	/* We have both head and tail */
	protected Node head, tail;

	/* size */
	protected int listSize;

	// constructor
	public BasicLinkedList() {
		// set head and tail to null and listsize to 0
		head = null;
		tail = null;
		listSize = 0;
	}

	public int getSize() {
		// return the list size
		return listSize;
	}

	public BasicLinkedList<T> addToEnd(T data) {
		// create a new node
		Node nodeToAdd = new Node(data);
		if (head == null) {
			// if head is null it means there is no nodes in the list
			// set the head and tail to the new node and add 1 to the list size
			head = nodeToAdd;
			tail = nodeToAdd;
			listSize++;
		} else {
			// set the pointer of tail to the new node
			// set the tail to the new node add one to the list size
			tail.next = nodeToAdd;
			tail = nodeToAdd;
			listSize++;
		}
		return this;
	}

	public BasicLinkedList<T> addToFront(T data) {
		Node nodeToAdd = new Node(data);
		if (head == null) {
			// if there is no nodes in the list
			// set the head and tail to the new node and add one to the list
			head = nodeToAdd;
			tail = nodeToAdd;
			listSize++;
		} else {
			// set the new node's pointer to the head and the head to the new node
			nodeToAdd.next = head;
			head = nodeToAdd;
			listSize++;
		}
		return this;
	}

	public T getFirst() {
		// if the list is empty return null else return the head
		if (listSize == 0 || head == null) {
			return null;
		} else {
			return head.data;
		}
	}

	public T getLast() {
		// if the list is empty return null else return the tail
		if (listSize == 0) {
			return null;
		} else {
			return tail.data;
		}
	}

	public T retrieveFirstElement() {
		if (head == null) {
			// if the head is null return null
			return null;
		} else {
			// create a new node set it to head
			Node curr = head;
			// set head to the next node unlinks head
			head = head.next;
			// minus 1 to the list size
			listSize--;
			// return the new node
			return curr.data;
		}
	}

	public T retrieveLastElement() {
		Node curr = head;
		Node prev = null;
		if (listSize == 0) {
			return null;
		}
		if (curr.next == null) {
			// if there is only one node in the list
			// set the head to null
			head = null;
			listSize--;
		} else {
			while (curr.next != null) {
				// traverse the list til it reaches the tail
				prev = curr;
				curr = curr.next;
			}
			// set tail to the node before
			tail = prev;
			// unlinked the last node
			tail.next = null;
			listSize--;
		}
		// return the node
		return curr.data;

	}

	public BasicLinkedList<T> remove(T data, Comparator<T> comparator) {
		Node curr = head;
		Node prev = null;
		if (head == null) {
			return null;
		} else {
			while (curr != null) {
				// compare the two nodes' data
				// if the current node is equal to the data
				if (comparator.compare(curr.data, data) == 0) {
					if (curr.equals(head)) {
						// if the current node is the head
						// unlink the node and set the current node to the head
						head = head.next;
					} else {
						// unlink the node
						// set the previous node to the next node
						curr = curr.next;
						prev.next = curr;
					}
					listSize--;
				}
					// continue traversing through the list
					prev = curr;
					curr = curr.next;
			}
		}

		return this;
	}
	
	@Override
	public Iterator<T> iterator() {
		class BasicIterator implements Iterator<T> {
			Node curr;

			public BasicIterator() {
				curr = head;
			}

			public boolean hasNext() {
				return curr != null;
			}

			public T next() {
				T toReturn = curr.data;

				curr = curr.next;
				return toReturn;
			}

			public void remove() {
				throw new UnsupportedOperationException();
			}
		}
		return new BasicIterator();
	}

	public ArrayList<T> getReverseArrayList() {
		// create a new arraylist
		ArrayList<T> reversedList = new ArrayList<T>();
		// call the aux method
		getReverseArrayList(reversedList, head);
		return reversedList;
	}

	private ArrayList<T> getReverseArrayList(ArrayList<T> reversedList, Node head) {
		// base case
		if (head == null) {
			return reversedList;
		}
		// add the data of each node to the list
		reversedList.add(0, head.data);
		// recursively call the aux method itself with the next node
		return getReverseArrayList(reversedList, head.next);

	}

	public BasicLinkedList<T> getReverseList() {
		// crate a new linked list
		BasicLinkedList<T> newList = new BasicLinkedList<T>();
		// call the aux method
		getReverseList(head, newList);

		return newList;
	}

	private BasicLinkedList<T> getReverseList(Node head, BasicLinkedList<T> newList) {
		// base case
		if (head == null) {
			return newList;
		}
		// add the node to the front of the linked list
		newList.addToFront(head.data);
		// recursively call the aux method itself with the next node
		return getReverseList(head.next, newList);
	}

}