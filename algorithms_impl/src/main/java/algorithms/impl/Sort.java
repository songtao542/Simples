package algorithms.impl;

import java.util.Arrays;

public class Sort {

	public static void main(String[] args) {
		int[] a = { 34, 56, 12, 45, 23, 35, 52, 21, 58, 89, 90, 14, 25 };
		print(a);

		int[] b = Arrays.copyOf(a, a.length);
		insertSort(b);
		print(b);

		int[] c = Arrays.copyOf(a, a.length);
		bubbleSort(c);
		print(c);

		int[] d = Arrays.copyOf(a, a.length);
		shellSort(d);
		print(d);

	}

	private static void print(int[] a) {
		for (int i : a) {
			System.out.print(i + " ");
		}
		System.out.println("");
	}

	private static void insertSort(int[] a) {
		int j = 0;
		for (int i = 1; i < a.length; i++) {
			int temp = a[i];
			for (j = i; j > 0 && temp < a[j - 1]; j--) {
				a[j] = a[j - 1];
			}
			a[j] = temp;
		}
	}

	private static void bubbleSort(int[] a) {
		for (int i = 0; i < a.length - 1; i++) {
			for (int j = a.length - 1; j > i; j--) {
				if (a[j] < a[j - 1]) {
					int temp = a[j];
					a[j] = a[j - 1];
					a[j - 1] = temp;
				}
			}
		}
	}

	private static void shellSort(int[] a) {
		int j = 0;
		for (int gap = a.length / 2; gap > 0; gap /= 2) {
			for (int i = gap; i < a.length; i++) {
				int temp = a[i];
				for (j = i; j >= gap && temp < a[j - gap]; j -= gap) {
					a[j] = a[j - gap];
				}
				a[j] = temp;
			}
		}
	}

}
