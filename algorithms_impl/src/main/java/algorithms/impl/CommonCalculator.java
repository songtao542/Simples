package algorithms.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

public class CommonCalculator {

	static final HashMap<Character, Integer> opins = new HashMap<Character, Integer>() {
		{
			put('+', 1);
			put('-', 1);
			put('*', 2);
			put('/', 2);
			put('(', 3);

		}
	};

	static final HashMap<Character, Integer> opouts = new HashMap<Character, Integer>() {
		{
			put('+', 1);
			put('-', 1);
			put('*', 2);
			put('/', 2);
			put('(', 0);
		}
	};

	static final HashMap<Character, Character> oppairs = new HashMap<Character, Character>() {
		{
			put(')', '(');
		}
	};

	public static void main(String[] args) {
		Stack<Character> ops = new Stack<>();
		ArrayList<String> postfix = new ArrayList<>();
		Scanner scanner = new Scanner(System.in);
		String s = scanner.next();
		scanner.close();

		String num = "";

		for (char c : s.toCharArray()) {
			if (!"".equals(num)) {
				postfix.add(num);
				num = "";
			}

			if (opins.containsKey(c)) {
				if (ops.isEmpty()) {
					ops.push(c);
				} else {
					int pri = opins.get(c);
					while (!ops.isEmpty() && opouts.get(ops.peek()) >= pri) {
						postfix.add(String.valueOf(ops.pop()));
					}
					ops.push(c);
				}
			} else if (oppairs.containsKey(c)) {
				char pair = oppairs.get(c);
				while (!ops.isEmpty() && ops.peek() != pair) {
					postfix.add(String.valueOf(ops.pop()));
				}
				ops.pop();
			} else {
				num += c;
			}
		}
		if (!"".equals(num)) {
			postfix.add(num);
			num = "";
		}

		while (!ops.isEmpty()) {
			postfix.add(String.valueOf(ops.pop()));
		}

		for (String ss : postfix) {
			System.out.print(ss + " ");
		}
	}
}
