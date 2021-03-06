package com.falc0n.android.examapp.algs.asmuth_bloom;

import java.math.BigInteger;

public class Extra {
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
