package com.falc0n.android.examapp.algs.diffi_hellman;

import java.math.BigInteger;

public class Extra {
	public static String fastMultiply(int a, int m, int mod) {
		BigInteger result = new BigInteger("1");
		String binary = Long.toBinaryString(m);
		// System.out.println(binary);
		char[] arr = binary.toCharArray();

		for (int i = 0; i < arr.length; i++) {
			int p = Integer.valueOf(arr[i] + "");
			result = result.multiply(new BigInteger((long) Math.pow(a, p) + ""));
			if (i != arr.length - 1)
				result = result.pow(2);
			result = result.mod(new BigInteger(mod + ""));
		}

		return result.toString();
	}
}
