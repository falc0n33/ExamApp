package com.falc0n.android.examapp.algs.furier;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;

public class GaloisField {
	private int n;
	private int exp;
	private String polynomial;
	private List<String> xs;
	private List<String> ps;
	private int[] coefs;

	public GaloisField(int n, int exp, String polynomial) {
		this.n = n;
		this.exp = exp;
		this.polynomial = polynomial;
		this.xs = getXs(n);
		this.ps = getPs(polynomial);
		this.coefs = getRegisterCoefs(this.xs, this.ps);
	}
	/*
	public int[][] getTable() {
		int[][] table = new int[(int) Math.pow(n, exp)][xs.size()];
		table[1][0] = 1;
		for (int i = 2; i < table.length; i++) {
			int[] prev = table[i - 1];
			int[] row = table[i];
			row[0] = (prev[row.length - 1] * coefs[0]) % n;
			for (int j = 1; j < row.length; j++) {
				row[j] = (prev[j - 1] + prev[row.length - 1] * coefs[j]) % n;
			}
		}
		return table;
	}
	*/
	public int[][] getTable() {
		int[][] table = new int[(int) Math.pow(n, exp)][xs.size()];
		table[1][0] = 1;
		if (xs.size() != 1) {
			for (int i = 2; i < table.length; i++) {
				int[] prev = table[i - 1];
				int[] row = table[i];
				row[0] = (prev[row.length - 1] * coefs[0]) % n;
				for (int j = 1; j < row.length; j++) {
					row[j] = (prev[j - 1] + prev[row.length - 1] * coefs[j]) % n;
				}
			}
		} else {
			for (int i = 2; i < table.length; i++) {
				table[i][0] = i;
			}
		}
		return table;
	}

	public String tableToString(int[][] table) {
		StringBuilder sb = new StringBuilder();
		for (String x : xs)
			sb.append(x + "\t");
		sb.append("\n");

		for (int i = 0; i < table.length; i++) {
			for (int n : table[i])
				sb.append(n + "\t");
			if (i == 0)
				sb.append("a-8\t");
			else
				sb.append("a" + (i - 1) + "\t");

			for (int j = 0; j < table[i].length; j++) {
				if (j != 0)
					sb.append(" + ");
				sb.append(table[i][j] + xs.get(j));
			}
			sb.append("\n");
		}

		return sb.toString();
	}

	public Map<String, List<String>> getClasses(int[][] table) {
		Map<String, int[]> alphas = getAlphas(table);
		Set<String> used = new HashSet<>();

		Map<String, List<String>> classes = new TreeMap<String, List<String>>(new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				Integer i1 = Integer.parseInt(o1.replace("a", ""));
				Integer i2 = Integer.parseInt(o2.replace("a", ""));
				return i1.compareTo(i2);
			}
		});

		for (Entry<String, int[]> alpha : alphas.entrySet()) {
			if (alpha.getKey().equals("a-8")) {
				classes.put(alpha.getKey(), Arrays.asList(alpha.getKey()));
			} else if (!used.contains(alpha.getKey())) {
				Set<String> aClass = new HashSet<String>();
				int num = Integer.parseInt(alpha.getKey().replace("a", ""));
				String a = "a" + num;
				do {
					aClass.add(a);
					used.add(a);
					num *= n;
					num %= ((int) Math.pow(n, exp) - 1);
					a = "a" + num;
				} while (!aClass.contains(a));

				List<String> elems = new ArrayList<>();
				for (String e : aClass) {
					elems.add(e);
				}

				classes.put(alpha.getKey(), elems);
			}
		}

		return classes;
	}

	public Map<String, Integer> getTraces(int[][] table, Map<String, List<String>> classes) {
		Map<String, Integer> traces = new TreeMap<String, Integer>(new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				Integer i1 = Integer.parseInt(o1.replace("a", ""));
				Integer i2 = Integer.parseInt(o2.replace("a", ""));
				return i1.compareTo(i2);
			}
		});

		Map<String, int[]> alphas = getAlphas(table);
		for (Entry<String, List<String>> entry : classes.entrySet()) {
			int[] sums = new int[exp];
			for (String alpha : entry.getValue()) {
				int[] nums = alphas.get(alpha);
				for (int i = 0; i < nums.length; i++)
					sums[i] += nums[i];
			}
			// System.out.println(Arrays.toString(sums));
			traces.put(entry.getKey(), sums[0] % n);
		}

		return traces;
	}

	public Map<String, int[]> getAlphas(int[][] table) {
		Map<String, int[]> map = new TreeMap<String, int[]>(new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				Integer i1 = Integer.parseInt(o1.replace("a", ""));
				Integer i2 = Integer.parseInt(o2.replace("a", ""));
				return i1.compareTo(i2);
			}
		});

		map.put("a-8", table[0]);
		for (int i = 1; i < table.length; i++) {
			map.put("a" + (i - 1), table[i]);
		}

		return map;
	}

	private List<String> getXs(int n) {
		List<String> xs = new LinkedList<>();
		for (int i = 0; i < exp; i++) {
			xs.add("x^" + i);
		}
		return xs;
	}

	private List<String> getPs(String polynomial) {
		List<String> ps = Arrays.asList(polynomial.split("\\+"));
		return ps;
	}

	private int[] getRegisterCoefs(List<String> xs, List<String> ps) {
		int[] coefs = new int[xs.size()];
		for (int i = 0; i < xs.size(); i++) {
			for (String p : ps) {
				if (p.contains(xs.get(i))) {
					String s = p.replace(xs.get(i), "");
					if (s.length() == 0) {
						coefs[i] = mod(-1, n);
					} else {
						coefs[i] = mod(Integer.parseInt("-" + s), n);
					}
				}
			}
		}
		return coefs;
	}

	private double[] getPolinomCoefs(String polynomial) {
		String[] elems = polynomial.split("\\+");
		double[] coefs = new double[elems.length];
		for (int i = 0; i < coefs.length; i++) {
			String elem = elems[i].replaceAll("x\\^\\d+", "");
			double coef = 0;
			if (elem.length() == 0) {
				coef = 1;
			} else {
				coef = Double.parseDouble(elem);
			}
			coefs[i] = coef;
		}
		return coefs;
	}

	private int mod(int n, int m) {
		n %= m;
		if (n < 0)
			n += m;
		return n;
	}

	public List<String> getXs() {
		return xs;
	}

	public List<String> getPs() {
		return ps;
	}

	public int[] getCoefs() {
		return coefs;
	}

	public int getN() {
		return n;
	}

	public int getExp() {
		return exp;
	}

	@Override
	public String toString() {
		return "GF(" + n + "^" + exp + ") [x] : g(x) = " + polynomial;
	}
}
