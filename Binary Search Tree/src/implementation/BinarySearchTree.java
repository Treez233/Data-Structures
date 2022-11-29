package implementation;

import java.util.Comparator;
import java.util.TreeSet;

public class BinarySearchTree<K, V> {
	/*
	 * You may not modify the Node class and may not add any instance nor static
	 * variables to the BinarySearchTree class.
	 */
	private class Node {
		private K key;
		private V value;
		private Node left, right;

		private Node(K key, V value) {
			this.key = key;
			this.value = value;
		}
	}

	private Node root;
	private int treeSize, maxEntries;
	private Comparator<K> comparator;

	public BinarySearchTree(Comparator<K> comparator, int maxEntries) {
		if (maxEntries < 1) {
			throw new IllegalArgumentException("Max Entries cannot be less than 1");
		}
		root = null;
		treeSize = 0;
		this.comparator = comparator;
		this.maxEntries = maxEntries;
	}

	public BinarySearchTree<K, V> add(K key, V value) throws TreeIsFullException {
		if (key == null || value == null) {
			throw new IllegalArgumentException("Error, null parameters");
		} else if (isFull()) {
			throw new TreeIsFullException("Tree is full");
		}
		if (isEmpty()) {
			root = new Node(key, value);
		} else {
			addAux(key, value, root);
		}
		treeSize++;
		return this;
	}

	public boolean addAux(K key, V value, Node node) {
		int comparison = comparator.compare(key, node.key);

		if (comparison == 0) {
			node.value = value;
			return false;
		} else if (comparison < 0) {
			if (node.left == null) {
				node.left = new Node(key, value);
				return true;
			} else {
				return addAux(key, value, node.left);
			}
		} else {
			if (node.right == null) {
				node.right = new Node(key, value);
				return true;
			} else {
				return addAux(key, value, node.right);
			}
		}
	}

	public String toString() {
		if (size() == 0) {
			return "EMPTY TREE";
		} else {
			return toStringAux(root);
		}
	}

	public String toStringAux(Node node) {
		return node == null ? ""
				: toStringAux(node.left) + "{" + node.key + ":" + node.value + "}" + toStringAux(node.right);
	}

	public boolean isEmpty() {
		return root == null;
	}

	public int size() {
		return treeSize;
	}

	public boolean isFull() {
		return treeSize == maxEntries;
	}

	public KeyValuePair<K, V> getMinimumKeyValue() throws TreeIsEmptyException {
		if (isEmpty()) {
			throw new TreeIsEmptyException("EMPTY TREE");
		}
		Node node = getMinimumKeyValueAux(root);
		return new KeyValuePair<K, V>(node.key, node.value);
	}

	public Node getMinimumKeyValueAux(Node node) {
		if (node.left == null) {
			return node;
		}
		return getMinimumKeyValueAux(node.left);
	}

	public KeyValuePair<K, V> getMaximumKeyValue() throws TreeIsEmptyException {
		if (isEmpty()) {
			throw new TreeIsEmptyException("EMPTY TREE");
		}
		Node node = getMaximumKeyValueAux(root);
		return new KeyValuePair<K, V>(node.key, node.value);
	}

	public Node getMaximumKeyValueAux(Node node) {
		if (node.right == null) {
			return node;
		}
		return getMaximumKeyValueAux(node.right);
	}

	public KeyValuePair<K, V> find(K key) {
		if (key == null) {
			return null;
		}
		return findAux(key, root);
	}

	public KeyValuePair<K, V> findAux(K key, Node node) {
		if (node == null) {
			return null;
		}
		int comparison = comparator.compare(node.key, key);
		if (comparison == 0) {
			return new KeyValuePair<K, V>(node.key, node.value);
		} else if (comparison < 0) {
			return findAux(key, node.right);
		} else {
			return findAux(key, node.left);
		}
	}

	public BinarySearchTree<K, V> delete(K key) throws TreeIsEmptyException {
		if (key == null) {
			throw new IllegalArgumentException("Error, Null parameters");
		}
		if (isEmpty()) {
			throw new TreeIsEmptyException("EMPTY TREE");
		}
		root = deleteAux(root, key);
		return this;
	}

	public Node deleteAux(Node node, K key) {
		if (node == null) {
			return null;
		}
		int comparison = comparator.compare(key, node.key);
		if(comparison == 0) {
			treeSize--;
			if(node.left == null) {
				return node.right;
			}else if (node.right == null) {
				return node.left;
			}else {
				node.key = getMinimumKeyValueAux(node.right).key;
				node.right = deleteAux(node.right, node.key);
			}
			return node;
		}else if(comparison > 0) {
			node.right = deleteAux(node.right, key);
			return node;
		}else {
			node.left = deleteAux(node.left, key);
			return node;
		}

	}

	public void processInorder(Callback<K, V> callback) {
		if (callback == null) {
			throw new IllegalArgumentException("Error, Null Parameter");
		}
		processInorderAux(root, callback);
	}

	public void processInorderAux(Node node, Callback<K, V> callback) {
		if (node == null) {
			return;
		}
		processInorderAux(node.left, callback);
		callback.process(node.key, node.value);
		processInorderAux(node.right, callback);
	}

	public BinarySearchTree<K, V> subTree(K lowerLimit, K upperLimit) {
		if (lowerLimit == null || upperLimit == null || comparator.compare(lowerLimit, upperLimit) > 0) {
			throw new IllegalArgumentException("Error, Null Parameter");
		}
		BinarySearchTree<K, V> subTree = new BinarySearchTree<K, V>(this.comparator, this.maxEntries);
		try {
			subTreeAux(lowerLimit, upperLimit, root, subTree);
		} catch (TreeIsFullException e) {
			e.printStackTrace();
		}
		return subTree;
	}

	public void subTreeAux(K lower, K upper, Node node, BinarySearchTree<K, V> subTree) throws TreeIsFullException {
		if (node == null) {
			return;
		}
		int comparisonLow = comparator.compare(node.key, lower);
		int comparisonUp = comparator.compare(node.key, upper);
		if (comparisonLow < 0) {
			subTreeAux(lower, upper, node.right, subTree);
		} else if (comparisonUp > 0) {
			subTreeAux(lower, upper, node.left, subTree);
		} else {
			subTree.add(node.key, node.value);
			subTreeAux(lower, upper, node.right, subTree);
			subTreeAux(lower, upper, node.left, subTree);
		}

	}

	public TreeSet<V> getLeavesValues() {
		TreeSet<V> temp = new TreeSet<V>();
		return getLeavesValuesAux(root, temp);
	}

	public TreeSet<V> getLeavesValuesAux(Node node, TreeSet<V> treeSet) {
		if (node == null) {
			return treeSet;
		} else if (node.left == null && node.right == null) {
			treeSet.add(node.value);
			return treeSet;
		}
		treeSet.addAll(getLeavesValuesAux(node.left, treeSet));
		treeSet.addAll(getLeavesValuesAux(node.right, treeSet));
		return treeSet;
	}
}
