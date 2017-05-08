package algorithms.impl;

import java.util.LinkedList;
import java.util.Queue;

public class AVLTree<T extends Comparable<? super T>> {

	private static final int ALLOWED_IMBALANCE = 1;

	static class BinaryNode<T> {
		T element;
		BinaryNode<T> left;
		BinaryNode<T> right;
		int height;

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

	public AVLTree() {
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
		return balance(t);
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
			t = t.left != null ? t.left : t.right;
		}
		return balance(t);
	}

	private BinaryNode<T> balance(BinaryNode<T> t) {
		if (t == null)
			return t;

		if (height(t.left) - height(t.right) > ALLOWED_IMBALANCE) {
			if (height(t.left.left) >= height(t.left.right)) {
				// 左-左，一次单旋转
				t = rotateWithLefChild(t);
			} else {
				// 左-右，一次双旋转
				t = doubleRotateWithLeftChild(t);
			}
		} else if (height(t.right) - height(t.left) > ALLOWED_IMBALANCE) {
			if (height(t.right.right) >= height(t.right.left)) {
				// 右-右，一次单旋转
				t = rotateWithRightChile(t);
			} else {
				// 右-左，一次双旋转
				t = doubleRotateWithRightChild(t);
			}
		}
		t.height = Math.max(height(t.left), height(t.right)) + 1;
		return t;
	}

	private int height(BinaryNode<T> t) {
		return t == null ? -1 : t.height;
	}

	private BinaryNode<T> rotateWithLefChild(BinaryNode<T> t) {
		BinaryNode<T> k2 = t;
		BinaryNode<T> k1 = k2.left;
		k2.left = k1.right;
		k1.right = k2;
		k2.height = Math.max(height(k2.left), height(k2.right)) + 1;
		k1.height = Math.max(height(k1.left), k2.height) + 1;
		return k1;
	}

	private BinaryNode<T> rotateWithRightChile(BinaryNode<T> t) {
		BinaryNode<T> k2 = t;
		BinaryNode<T> k1 = t.right;
		k2.right = k1.left;
		k1.left = k2;
		k2.height = Math.max(height(k2.left), height(k2.right)) + 1;
		k1.height = Math.max(height(k1.left), k2.height) + 1;
		return k1;
	}

	private BinaryNode<T> doubleRotateWithLeftChild(BinaryNode<T> t) {
		t.left = rotateWithRightChile(t.left);
		return rotateWithLefChild(t);
	}

	private BinaryNode<T> doubleRotateWithRightChild(BinaryNode<T> t) {
		t.right = rotateWithLefChild(t.right);
		return rotateWithRightChile(t);
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
		AVLTree<Integer> st = new AVLTree<>();

		st.insert(3);
		st.insert(2);
		st.insert(1);
		st.insert(4);
		st.insert(5);
		st.insert(6);
		st.insert(7);

		st.printTree(4);

		st.insert(16);
		st.insert(15);
		System.out.println("\nafter insert 15:");
		st.printTree(4);
		st.insert(14);
		System.out.println("\nafter insert 14:");
		st.printTree(4);
		st.insert(13);
		System.out.println("\nafter insert 13:");
		st.printTree(4);
		st.insert(12);
		System.out.println("\nafter insert 12:");
		st.printTree(4);
		st.insert(11);
		st.insert(10);

		st.insert(8);
		st.insert(9);
		System.out.println("\nafter insert 9:");
		st.printTree(4);
	}

}
