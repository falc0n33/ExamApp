package com.falc0n.android.examapp.algs.figure;

import java.util.HashSet;
import java.util.Set;

public class Extra {
	public static String leftRot(String s) {
		char[] c = s.toCharArray();
		char first = c[0];
		for (int i = 0; i < c.length - 1; i++) {
			c[i] = c[i + 1];
		}
		c[c.length - 1] = first;
		return new String(c);
	}

	public static String rightRot(String s) {
		char[] c = s.toCharArray();
		char last = c[c.length - 1];
		for (int i = c.length - 1; i > 0; i--) {
			c[i] = c[i - 1];
		}
		c[0] = last;
		return new String(c);
	}

	public static String mirror(String s, char c) {
		int n = s.length();
		int pos = s.indexOf(c);

		for (int i = 0; i < pos; i++) {
			s = Extra.leftRot(s);
		}

		char[] arr = s.toCharArray();

		Set<Integer> passed = new HashSet<>();
		if (n % 2 == 0) {
			for (int i = 0; i < arr.length; i++) {
				if ((i != 0 && i != n / 2 && !passed.contains(i))) {
					swap(arr, i, n - i);
					passed.add(i);
					passed.add(n - i);
				}
			}
		} else {
			for (int i = 0; i < arr.length; i++) {
				if (i != 0 && !passed.contains(i)) {
					swap(arr, i, n - i);
					passed.add(i);
					passed.add(n - i);
				}
			}
		}

		s = new String(arr);

		for (int i = 0; i < pos; i++) {
			s = Extra.rightRot(s);
		}

		return s;
	}

	public static String halfMirror(String s, int n) {
		char[] arr = s.toCharArray();

		Set<Integer> passed = new HashSet<>();
		for (int i = 0; i < arr.length; i++) {
			for (int j = i; j < arr.length; j++) {
				if ((i + j) % arr.length == n) {
					swap(arr, i, j);
					passed.add(i);
					passed.add(j);
				}
			}
		}

		return new String(arr);

	}

	private static void swap(char[] arr, int a, int b) {
		char c = arr[a];
		arr[a] = arr[b];
		arr[b] = c;
	}
}
