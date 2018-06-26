package com.falc0n.android.examapp.algs.elliptic_curve;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class EllipticCurve {
	private String equation;
	private Expression expression;
	private int field;
	private int a, b;
	private Map<String, int[]> table;

	public EllipticCurve(String equation, int a, int b, int field) {
		this.a = a;
		this.b = b;
		this.field = field;
		this.equation = equation;
		this.expression = new ExpressionBuilder(this.equation).variables("x", "a", "b").build();
		this.expression.setVariable("a", a).setVariable("b", b);
		this.table = initTable();
	}

	private Map<String, int[]> initTable() {
		Map<String, int[]> table = new TreeMap<>();
		int p = 1;

		for (int x = 0; x < field; x++) {
			int quad = (int) expression.setVariable("x", x).evaluate();
			quad = mod(quad, field);
			double y = Math.sqrt(quad);
			if (y % 1 == 0) {
				table.put("p" + p++, new int[] { x, ((int) y) });
				if (y != 0) {
					table.put("p" + p++, new int[] { x, mod(((int) -y), field) });
				}
			} else {
				int res = solveQuad(quad, field);
				if (res != -1) {
					table.put("p" + p++, new int[] { x, ((int) res) });
					table.put("p" + p++, new int[] { x, mod(((int) -res), field) });
				}
			}
		}

		return table;
	}

	public int solveQuad(int r, int m) {
		int x = -1;
		for (x = 0; x < field; x++) {
			int n = (int) Math.pow(x, 2) % m;
			if (n == r) {
				return x;
			}
		}
		return -1;
	}
	
	public int reverse(int n, int m) throws ArithmeticException {
		if (n == 0) 
			throw new ArithmeticException();
		int x = 0;
		do {
			if (n * x % m == 1) {
				break;
			}
			x++;
		} while (true);
		return x;
	}

	public String sum(String p1, String p2) {
		int[] xy1 = table.get(p1);
		int[] xy2 = table.get(p2);
		try {
			int a1 = mod((xy2[1] - xy1[1]), field); 
			int a2 = mod((xy2[0] - xy1[0]), field);
			a2 = reverse(a2, field);
			int a = (a1 * a2) % field;
			if (a == 0)
				throw new ArithmeticException();
			int x3 = mod(a * a - xy1[0] - xy2[0], field);
			int y3 = mod(a * (xy1[0] - x3) - xy1[1], field);
			return getP(new int[] { x3, y3 });
		} catch (ArithmeticException e) {
			return "O";
		}
	}
	
	public String dub(String p) {
		int[] xy = table.get(p);
		int x = xy[0];
		int y = xy[1];
		try {
			int a1 = mod(3 * x * x + this.a, field); 
			int a2 = mod(2 * y, field);
			a2 = reverse(a2, field);
			int a = (a1 * a2) % field;
			if (a == 0) 
				throw new ArithmeticException();
			int x3 = mod(a * a - 2 * x, field);
			int y3 = mod(a * (x - x3) - y, field);
			return getP(new int[] { x3, y3 });
		} catch (ArithmeticException e) {
			return "O";
		}
	}

	public String getP(int[] xy) {
		for (Entry<String, int[]> e : table.entrySet()) {
			if (Arrays.equals(e.getValue(), xy))
				return e.getKey();
		}
		return "O";
	}

	public String tableToString() {
		StringBuilder sb = new StringBuilder();
		sb.append(" \t" + "x   y\n");

		for (Entry<String, int[]> e : table.entrySet()) {
			sb.append(e.getKey() + "\t" + Arrays.toString(e.getValue()) + "\n");
		}

		sb.append("{O, ");
		for (int i = 1; i <= table.size(); i++) {
			if (i > 1)
				sb.append(", ");
			sb.append("p" + i);
		}
		sb.append("}\n");

		return sb.toString();
	}

	public int getDiscriminant() {
		Expression discriminant = new ExpressionBuilder("4*a^3+27*b^2").variables("a", "b").build();
		discriminant.setVariable("a", a).setVariable("b", b);
		int discr = (int) discriminant.evaluate();
		discr = mod(discr, field);
		return discr;
	}

	public static int mod(int n, int m) {
		n %= m;
		if (n < 0)
			n += m;
		return n;
	}
}
