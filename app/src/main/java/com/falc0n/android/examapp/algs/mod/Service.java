package com.falc0n.android.examapp.algs.mod;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

public class Service {

	public static int phi(int n) {
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

		return (int) result;
	}

	public static int euclidExt(int a, int b, Pair p) {
		if (a == 0) {
			p.setU(0);
			p.setV(1);
			return b;
		}
		Pair p1 = new Pair();
		int d = euclidExt(b % a, a, p1);

		p.setU((int) p1.getV() - (b / a) * (int) p1.getU());
		p.setV((int) p1.getU());

		return d;
	}

	public static List<Integer> getCoeffsEuclid(int a, int b) {
		List<Integer> coefs = new LinkedList<>();
		StringBuilder sb = new StringBuilder();

		int q;
		int r = 0;

		int c = 0;
		if (a < b) {
			c = a;
			a = b;
			b = c;
		}

		do {
			q = a / b;
			r = a - (b * q);
			sb.append(a + " = " + b + " * " + q + " + " + r + "\n");
			coefs.add(q);
			a = b;
			b = r;
		} while (r != 0);
		sb.append("НСД = " + a + "\n");

		// System.out.println(sb.toString());
		return coefs;
	}

	public static BigInteger factorial(BigInteger n) {
		BigInteger result = BigInteger.ONE;

		while (!n.equals(BigInteger.ZERO)) {
			result = result.multiply(n);
			n = n.subtract(BigInteger.ONE);
		}

		return result;
	}

	public static boolean isPrime(int n) {
		if (n % 2 == 0)
			return false;
		for (int i = 3; i * i <= n; i += 2) {
			if (n % i == 0)
				return false;
		}
		return true;
	}

}
