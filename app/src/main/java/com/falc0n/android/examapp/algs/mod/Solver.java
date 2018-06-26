package com.falc0n.android.examapp.algs.mod;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.List;

public class Solver {

	public static void main(String[] args) {

		int[][] arr = new int[][] { { 256, 179, 337 }, { 243, 112, 551 }, { 1296, 1105, 2413 }, { 23, 17, 65 } };
		String result = Solver.solve(arr);

		try {
			OutputStream out = new BufferedOutputStream(new FileOutputStream("result.txt"));
			out.write(result.getBytes());
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println(result);

		// int a = 256; int b = 179; int m = 337;
		// int a = 243; int b = 112; int m = 551;
		// int a = 1296; int b = 1105; int m = 2413;
		// int a = 23; int b = 17; int m = 65;
		// int a = 54321; int b = 1532; int m = 9109;

		/*
		 * System.out.println(solveWithEvklid(a, b, m)); System.out.println();
		 * System.out.println(solveWithEuler(a, b, m)); System.out.println();
		 * System.out.println(solveWithContFract(a, b, m)); System.out.println();
		 * System.out.println(solveWithBinomCoef(a, b, m));
		 */
	}

	public static String solve(int[][] array) {
		StringBuilder sb = new StringBuilder();
		int var = 1;

		for (int[] l : array) {
			sb.append("№" + var++ + "\n");
			sb.append(solveWithEvklid(l[0], l[1], l[2]).getU() + "\n");
			sb.append(solveWithEuler(l[0], l[1], l[2]).getU() + "\n");
			sb.append(solveWithContFract(l[0], l[1], l[2]).getU() + "\n");
			sb.append(solveWithBinomCoef(l[0], l[1], l[2]).getU() + "\n");
			sb.append("\n");
		}

		return sb.toString();
	}

	private static Pair solveWithEvklid(int a, int b, int m) {
		Pair res = new Pair();
		StringBuilder sb = new StringBuilder();
		
		sb.append("Через линейную комбинацию: \n");
		sb.append(a + " * x = " + b + " mod " + m + "\n");

		Pair p = new Pair();
		sb.append("НОД = " + Service.euclidExt(a, m, p) + "\n");

		int u = (int) p.getU();
		int v = (int) p.getV();
		sb.append("U = " + u + "; V = " + v + "\n");

		int x = u * b % m;
		if (x < 0) {
			x += m;
		}
		sb.append("x = " + u + " * " + b + " mod " + m + "\n");
		sb.append("x = " + x + "\n");

		res.setU(sb.toString());
		res.setV(Integer.toString(x));

		return res;
	}

	private static Pair solveWithEuler(int a, int b, int m) {
		Pair res = new Pair();
		StringBuilder sb = new StringBuilder();
		
		sb.append("Через теорему Эйлера: \n");
		sb.append(a + " * x = " + b + " mod " + m + "\n");

		int p = Service.phi(m);
		sb.append("f(" + m + ") = " + p + "\n");

		BigInteger x = new BigInteger(a + "");
		x = x.pow((p - 1));
		x = x.multiply(new BigInteger(b + ""));
		x = x.mod(new BigInteger(m + ""));

		sb.append("x = " + b + " * " + a + "^" + (p - 1) + " mod " + m + "\n");
		sb.append("x = " + x.toString() + " mod " + m + "\n");

		res.setU(sb.toString());
		res.setV(x.toString());
		
		return res;
	}

	private static Pair solveWithContFract(int a, int b, int m) {
		Pair res = new Pair();
		StringBuilder sb = new StringBuilder();
		
		sb.append("Через подходящие дроби: \n");
		sb.append(a + " * x = " + b + " mod " + m + "\n");

		if (a > m)
			a %= m;
		if (b > m)
			b %= m;
		sb.append(a + " * x = " + b + " mod " + m + "\n\n");

		List<Integer> coefs = Service.getCoeffsEuclid(a, m);
		Table table = new Table(coefs);
		List<Integer> pn = table.getPn();

		sb.append(table.toString() + "\n");

		int n = pn.size() - 2;
		long x = (long) (Math.pow(-1, n) * pn.get(n) * b);
		sb.append("x = " + (-1) + "^" + n + " * " + pn.get(n) + " * " + b + " mod " + m + "\n");

		x %= m;
		if (x < 0) {
			x += m;
		}
		sb.append("x = " + x + " mod " + m + "\n");

		res.setU(sb.toString());
		res.setV(Long.toString(x));
		
		return res;
	}

	private static Pair solveWithBinomCoef(int a, int b, int m) {
		Pair res = new Pair();
		StringBuilder sb = new StringBuilder();
		
		sb.append("Через биномиальный коефициент: \n");
		sb.append(a + " * x = " + b + " mod " + m + "\n");

		if (Service.isPrime(m)) {

			if (a > m)
				a %= m;
			if (b > m)
				b %= m;
			sb.append(a + " * x = " + b + " mod " + m + "\n");

			BigInteger af = Service.factorial(new BigInteger(a + ""));
			BigInteger mf = Service.factorial(new BigInteger(m + ""));
			BigInteger maf = Service.factorial(new BigInteger((m - a) + ""));
			BigInteger c = mf.divide(af.multiply(maf));
			sb.append("C = " + c.toString() + "\n");

			sb.append(
					"x = " + b + " * " + -1 + "^" + (a - 1) + " * 1/" + m + " * " + c.toString() + " mod " + m + "\n");

			BigInteger x = new BigInteger(b + "");
			x = x.multiply(new BigInteger(-1 + "").pow(a - 1));
			c = c.divide(new BigInteger(m + ""));
			x = x.multiply(c);
			x = x.mod(new BigInteger(m + ""));

			sb.append("x = " + x.toString() + " mod " + m);
			
			res.setV(x.toString());
		} else {
			sb.append("p должно быть простым");
			res.setV(0);
		}
		
		res.setU(sb.toString());
		
		return res;
	}

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
