package algorithms.impl;

import java.util.LinkedList;
import java.util.Queue;

public class BinarySearchTree<T extends Comparable<? super T>> {
	static class BinaryNode<T> {
		T element;
		BinaryNode<T> left;
		BinaryNode<T> right;

		public BinaryNode(T element) {
			this(element, null, null);
		}

		public BinaryNode(T element, BinaryNode<T> left, BinaryNode<T> right) {
			this.element = element;
			this.left = left;
			this.right = right;
		}
	}

	private BinaryNode<T> root;

	public BinarySearchTree() {
		root = null;
	}

	public boolean isEmpty() {
		return root == null;
	}

	public boolean contains(T x) {
		return contains(x, root);
	}

	private boolean contains(T x, BinaryNode<T> t) {
		if (t == null)
			return false;
		int result = x.compareTo(t.element);
		if (result < 0) {
			return contains(x, t.left);
		} else if (result > 0) {
			return contains(x, t.right);
		} else {
			return true;
		}
	}

	public T findMin() {
		if (isEmpty()) {
			return null;
		}
		return findMin(root).element;
	}

	private BinaryNode<T> findMin(BinaryNode<T> t) {
		if (t == null)
			return null;
		else if (t.left == null) {
			return t;
		} else {
			return findMin(t.left);
		}
	}

	public T findMax() {
		if (isEmpty()) {
			return null;
		}
		return findMax(root).element;
	}

	/**
	 * 非递归写法
	 * 
	 * @param t
	 * @return
	 */
	private BinaryNode<T> findMax(BinaryNode<T> t) {
		if (t != null) {
			while (t.right != null) {
				t = t.right;
			}
		}
		return t;
	}

	public void insert(T x) {
		root = insert(x, root);
	}

	private BinaryNode<T> insert(T x, BinaryNode<T> t) {
		if (t == null) {
			return new BinaryNode<T>(x);
		}

		int result = x.compareTo(t.element);
		if (result < 0) {
			t.left = insert(x, t.left);
		} else if (result > 0) {
			t.right = insert(x, t.right);
		} else {

		}
		return t;
	}

	public T remove(T x) {
		BinaryNode<T> del = remove(x, root);
		if (del != null) {
			return del.element;
		}
		return null;
	}

	private BinaryNode<T> remove(T x, BinaryNode<T> t) {
		if (t == null)
			return null;
		int result = x.compareTo(t.element);
		if (result < 0) {
			t.left = remove(x, t.left);
		} else if (result > 0) {
			t.right = remove(x, t.right);
		} else if (t.left != null && t.right != null) {
			t.element = findMin(t.right).element;
			t.right = remove(t.element, t.right);
		} else {
			return t.left != null ? t.left : t.right;
		}
		return t;
	}

	/**
	 * 
	 * @param type
	 *            1.先序;2.中序;3.后序;4.层序;
	 */
	public void printTree(int type) {
		switch (type) {
		case 1:
			print1(root);
			break;
		case 2:
			print2(root);
			break;
		case 3:
			print3(root);
			break;
		case 4:
			print4(root);
			break;
		default:
			print4(root);
			break;
		}
	}

	private void print1(BinaryNode<T> t) {
		if (t != null) {
			System.out.print(t.element + " ");
			print1(t.left);
			print1(t.right);
		}
	}

	private void print2(BinaryNode<T> t) {
		if (t != null) {
			print2(t.left);
			System.out.print(t.element + " ");
			print2(t.right);
		}
	}

	private void print3(BinaryNode<T> t) {
		if (t != null) {
			print3(t.left);
			print3(t.right);
			System.out.print(t.element + " ");
		}
	}

	private void print4(BinaryNode<T> t) {
		Queue<BinaryNode<T>> queue = new LinkedList<>();
		if (t != null) {
			queue.add(t);
			while (!queue.isEmpty()) {
				BinaryNode<T> h = queue.poll();
				System.out.print(h.element + " ");
				if (h.left != null)
					queue.add(h.left);
				if (h.right != null)
					queue.add(h.right);
			}
		}
	}

	public static void main(String[] args) {
		BinarySearchTree<Integer> st = new BinarySearchTree<>();

		/**
		 *        4
		 *      /   \
		 *    2       6 
		 *   / \     / \
		 *  1   3   5   7
		 */
		st.insert(4);
		st.insert(2);
		st.insert(1);
		st.insert(3);
		st.insert(6);
		st.insert(5);
		st.insert(7);

		st.printTree(4);
		System.out.println("");
		st.printTree(1);
		System.out.println("");
		st.printTree(2);
		System.out.println("");
		st.printTree(3);
	}

}
