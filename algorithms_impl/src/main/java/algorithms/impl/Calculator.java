package algorithms.impl;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class Calculator {

	//1.遇到操作数直接输出
	//2.遇到操作符，弹出栈顶所有的大于或等于当前操作符优先级的元素，再将当前操作符入栈
	//3.遇到“(”，直接入栈
	//4.遇到“)”，弹出栈元素直到“(”弹出，即弹出“(”及其上的所有元素，“)”不入栈
	
	public static void main(String[] args) {
		Stack<Character> ops = new Stack<>();
		ArrayList<String> postfix = new ArrayList<>();
		Scanner scanner = new Scanner(System.in);
		String s = scanner.next();
		scanner.close();

		String opnum = "";

		System.out.println("input:" + s);
		for (char c : s.toCharArray()) {
			System.out.println("c=" + c);
			if (c == '+' || c == '-') {
				if (!"".equals(opnum)) {
					postfix.add(opnum);
					opnum = "";
				}

				if (ops.isEmpty()) {
					ops.push(c);
				} else {
					char top = ops.peek();
					while (top == '*' || top == '/' || top == '+' || top == '-') {
						postfix.add(String.valueOf(ops.pop()));
						if (!ops.isEmpty()) {
							top = ops.peek();
						} else {
							top = 0;
						}
					}
					ops.push(c);
				}
			} else if (c == '*' || c == '/') {
				if (!"".equals(opnum)) {
					postfix.add(opnum);
					opnum = "";
				}

				ops.push(c);
			} else if (c == ')') {
				if (!"".equals(opnum)) {
					postfix.add(opnum);
					opnum = "";
				}

				while (ops.peek() != '(') {
					postfix.add(String.valueOf(ops.pop()));
				}
				ops.pop();
			} else if (c == '(') {
				ops.push(c);
			} else if (c >= 48 && c <= 57) {
				opnum += c;
			}
		}

		if (!"".equals(opnum)) {
			postfix.add(opnum);
			opnum = "";
		}

		while (!ops.isEmpty()) {
			postfix.add(String.valueOf(ops.pop()));
		}

		for (String ss : postfix) {
			System.out.print(ss + " ");
		}
	}

}
