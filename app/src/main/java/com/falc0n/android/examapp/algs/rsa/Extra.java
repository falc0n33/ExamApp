package com.falc0n.android.examapp.algs.rsa;

import java.math.BigInteger;

public class Extra {
	public static String fastMultiply(long a, long m, long mod) {
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
	
	public static Pair solveWithEuler(long a, long b, long m) {
		Pair res = new Pair();
		StringBuilder sb = new StringBuilder();
		
		sb.append("Через теорему Эйлера: \n");
		sb.append(a + " * x = " + b + " mod " + m + "\n");

		long p = Extra.phi(m);
		sb.append("f(" + m + ") = " + p + "\n");

		BigInteger x = new BigInteger(a + "");
		x = x.pow((int)(p - 1));
		x = x.multiply(new BigInteger(b + ""));
		x = x.mod(new BigInteger(m + ""));

		sb.append("x = " + b + " * " + a + "^" + (p - 1) + " mod " + m + "\n");
		sb.append("x = " + x.toString() + " mod " + m + "\n");

		res.setU(sb.toString());
		res.setV(x.toString());
		
		return res;
	}
	
	public static long phi(long n) {
		float result = n;
		
		for (int p = 2; p * p <= n; ++p) {
			if (n % p == 0) {
				while (n % p == 0) 
					n /= p;
				result *= (1.0 - (1.0 / (float) p));
			}
		}
		
		if (n > 1) 
			result *= (1.0 - (1.0 / (float) n));
		
		return (long) result;
	}
}
