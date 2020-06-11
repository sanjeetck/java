package com.sanjeet.hackerrank.java.advanced;

import java.io.*;
import java.util.*;

interface PerformOperation {
	boolean check(int a);
}

class MyMath {
	public static boolean checker(PerformOperation p, int num) {
		return p.check(num);
	}

	public PerformOperation isOdd() {
		return n -> (n % 2) != 0;
	}

	public PerformOperation isPrime() {
		return n -> { 
			if (n < 2) { return false; }
			else if (n == 2) { return true; }
			else if (n % 2 == 0) { return false; }
			int sqrtNum = (int) Math.sqrt(n);
			for (int i = 3; i <= sqrtNum; i+= 2) {
				if (n % i == 0) {
					return false;
				}
			}
			return true;
		};
	}
	public PerformOperation isPalindrome() {
		return n -> { 
			int original = n;
			int digit;
			int newNum = 0;           
			while (n > 0) {
				digit = n % 10;
				n = (int) n / 10;
				newNum = newNum * 10 + digit; 
			} 
			if (original == newNum) {
				return true;
			}
			return false;
		};
	}

}

public class LambdaExpression {

	public static void main(String[] args) throws IOException {
		MyMath ob = new MyMath();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		PerformOperation op;
		boolean ret = false;
		String ans = null;
		while (T--> 0) {
			String s = br.readLine().trim();
			StringTokenizer st = new StringTokenizer(s);
			int ch = Integer.parseInt(st.nextToken());
			int num = Integer.parseInt(st.nextToken());
			if (ch == 1) {
				op = ob.isOdd();
				ret = ob.checker(op, num);
				ans = (ret) ? "ODD" : "EVEN";
			} else if (ch == 2) {
				op = ob.isPrime();
				ret = ob.checker(op, num);
				ans = (ret) ? "PRIME" : "COMPOSITE";
			} else if (ch == 3) {
				op = ob.isPalindrome();
				ret = ob.checker(op, num);
				ans = (ret) ? "PALINDROME" : "NOT PALINDROME";

			}
			System.out.println(ans);
		}
	}
}
