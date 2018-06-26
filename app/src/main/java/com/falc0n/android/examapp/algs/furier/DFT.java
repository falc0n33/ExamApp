package com.falc0n.android.examapp.algs.furier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class DFT {
	private GaloisField field;
	private int[][] table;
	private Map<String, int[]> alphas;

	public DFT(int m, int exp, String polynomial) {
		this.field = new GaloisField(m, exp, polynomial);
		this.table = field.getTable();
		this.alphas = field.getAlphas(table);
	}

	public Pair directConvert(String... in) {
		StringBuilder sb = new StringBuilder();
		String[] vec = getVec(in);
		String[] res = new String[vec.length];
		int core = getCore(in.length, alphas.size() - 1);
		sb.append("a^(" + in.length + "*x) = a0 mod" + (alphas.size() - 1) + " => Core = a"  + core + "\n");
		for (int i = 0; i < vec.length; i++) {
			List<String> as = new ArrayList<>();
			for (int j = 0; j < vec.length; j++) {
				int n = 0;
				if (!vec[j].equals("a-8")) {
					n = (core * i * j) % (alphas.size() - 1);
					String a = "a" + n;
					as.add(multiplyAlphas(a, vec[j]));
				}
			}
			res[i] = sumAlphas(as.toArray(new String[0]));
			sb.append("V" + i + " = " + as + " = " + res[i] + "\n");
		}

		Pair p = new Pair();
		p.setU(sb.toString());
		p.setV(res);
		return p;
	}

	public Pair reverseConvert(String... in) {
		StringBuilder sb = new StringBuilder();
		String[] vec = getVec(in);
		String[] res = new String[vec.length];
		int core = getCore(in.length, alphas.size() - 1);
		sb.append("a^(" + in.length + "*x) = a0 mod" + (alphas.size() - 1) + " => Core = a"  + core + "\n");
		int reverse = reverse(in.length, field.getN());
		sb.append("1/" + (in.length) + " mod " + field.getN() + " = " + reverse + "\n");
		for (int i = 0; i < vec.length; i++) {
			List<String> as = new ArrayList<>();
			for (int j = 0; j < vec.length; j++) {
				int n = 0;
				if (!vec[j].equals("a-8")) {
					n = mod(core * -i * j, alphas.size() - 1);
					String a = "a" + n;
					as.add(multiplyAlphas(a, vec[j]));
				}
			}
			int[] alpha = alphas.get(sumAlphas(as.toArray(new String[0])));
			sb.append("V" + i + " = " + reverse + " * " + as + " = ");
			sb.append(reverse + " * " + getAlpha(alpha) + " = ");
			alpha = multiply(alpha, reverse, field.getN());
			res[i] = getAlpha(alpha);
			sb.append(res[i] + "\n");
		}

		Pair p = new Pair();
		p.setU(sb.toString());
		p.setV(res);
		return p;
	}

	public Pair testTimePolynomial(String... in) {
		StringBuilder sb = new StringBuilder();
		sb.append("In: " + Arrays.toString(in) + "\n");
		String[] res = new String[in.length];
		int core = getCore(in.length, alphas.size() - 1);
		sb.append("a^(" + in.length + "*x) = a0 mod" + (alphas.size() - 1) + " => Core = a"  + core + "\n");
		for (int i = 0; i < res.length; i++) {
			List<String> as = new ArrayList<>();
			for (int j = 0; j < in.length; j++) {
				int n = 0;
				if (!in[j].equals("a-8")) {
					n = (core * i * j) % (alphas.size() - 1);
					String a = "a" + n;
					as.add(multiplyAlphas(a, in[j]));
				}
			}
			res[i] = sumAlphas(as.toArray(new String[0]));
			sb.append("V(a" + (core * i) + ") = " + as + " = " + res[i] + "\n");
		}

		Pair p = new Pair();
		p.setU(sb.toString());
		p.setV(res);
		return p;
	}
	
	public Pair testSpectrePolynomial(String... in) {
		StringBuilder sb = new StringBuilder();
	    sb.append(Arrays.toString(in) + "\n");
		String[] res = new String[in.length];
		int core = getCore(in.length, alphas.size() - 1);
		sb.append("a^(" + in.length + "*x) = a0 mod" + (alphas.size() - 1) + " => Core = a"  + core + "\n");
		for (int i = 0; i < res.length; i++) {
			List<String> as = new ArrayList<>();
			for (int j = 0; j < in.length; j++) {
				int n = 0;
				if (!in[j].equals("a-8")) {
					n = mod((core * i * -j), (alphas.size() - 1));
					String a = "a" + n;
					as.add(multiplyAlphas(a, in[j]));
				}
			}
			res[i] = sumAlphas(as.toArray(new String[0]));
			sb.append("V(a" + (core * i) + ") = " + as + " = " + res[i] + "\n");
		}

		Pair p = new Pair();
		p.setU(sb.toString());
		p.setV(res);
		return p;
	}

	public static int mod(int n, int m) {
		n %= m;
		if (n < 0)
			n += m;
		return n;
	}

	public static int reverse(int n, int m) {
		int i = 1;
		while ((n * i) % m != 1)
			i += 1;
		return i;
	}
	
	public static int getCore(int n, int len) {
		int i = 1;
		while ((n * i) % len != 0)
			i += 1;
		return i;		
	}

	public String[] getVec(String... in) {
		String[] vec = new String[in.length];
		int n = table[0].length;
		for (int i = 0; i < vec.length; i++) {
			if (isDigit(in[i])) {
				int[] x = new int[n];
				x[0] = Integer.parseInt(in[i]);
				vec[i] = getAlpha(x);
			} else {
				vec[i] = in[i];
			}
		}
		return vec;
	}

	public int[] multiply(int[] as, int n, int m) {
		int[] alpha = Arrays.copyOf(as, as.length);
		for (int i = 0; i < alpha.length; i++) {
			alpha[i] *= n;
			alpha[i] %= m;
		}
		return alpha;
	}

	public String sumAlphas(String... as) {
		int[] x = new int[table[0].length];
		for (String a : as) {
			int[] ax = alphas.get(a);
			for (int i = 0; i < ax.length; i++) {
				x[i] += ax[i];
				x[i] %= field.getN();
			}
		}
		return getAlpha(x);
	}

	public String multiplyAlphas(String a1, String a2) {
		a1 = a1.replace("a", "");
		a2 = a2.replace("a", "");
		int i1 = Integer.parseInt(a1);
		int i2 = Integer.parseInt(a2);
		int n = (i1 + i2) % (alphas.size() - 1);
		return "a" + n;
	}

	public static boolean isDigit(String num) {
		try {
			Integer.parseInt(num);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	public String getAlpha(int[] x) {
		for (Entry<String, int[]> e : alphas.entrySet()) {
			if (Arrays.equals(e.getValue(), x)) {
				return e.getKey();
			}
		}
		return null;
	}

	public String getAlphasTable() {
		StringBuilder sb = new StringBuilder();
		for (Entry<String, int[]> e : alphas.entrySet()) {
			sb.append(e.getKey() + "\t" + Arrays.toString(e.getValue()) + "\n");
		}
		return sb.toString();
	}

	public int[][] getTable() {
		return table;
	}

	public Map<String, int[]> getAlphas() {
		return alphas;
	}

	public GaloisField getField() {
		return field;
	}

}
