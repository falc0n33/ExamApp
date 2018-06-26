package com.falc0n.android.examapp.algs.asmuth_bloom;

public class AsmuthBloom {
	
	public static Pair encrypt(long m, long n, long t, long p, long r, long... di) {
		Pair res = new Pair();
		StringBuilder sb = new StringBuilder();
		
		sb.append("M = " + m + "\n");
		sb.append("n = " + n + ", t = " + t + "\n");
		sb.append("p = " + p + "\n");
		for (int i = 0; i < di.length; i++) {
			if (i > 0) 
				sb.append(", ");
			sb.append("d" + (i + 1) + " = " + di[i]);
		}
		sb.append("\n\n");
		
		long mc = m + r * p;
		sb.append("M' = " + m + " + " + r + " * " + p + "\n");
		sb.append("M' = " + mc + "\n\n");
		
		long[][] parts = new long[di.length][3];
		for (int i = 0; i < di.length; i++) {
			long[] part = new long[3];
			part[0] = p;
			part[1] = di[i];
			part[2] = mc % di[i];
			parts[i] = part;
		}
		
		for (int i = 0; i < parts.length; i++) {
			long[] part = parts[i];
			sb.append("k" + (i + 1) + " = ");
			sb.append("(" + part[0] + ", " + part[1] + ", " + part[2] + ")\n");
		}
		
		res.setU(sb.toString());
		res.setV(parts);
		
		return res;
	}
	
	public static Pair decrypt(long[]... parts) {
		Pair res = new Pair();
		StringBuilder sb = new StringBuilder();
		
		long p = parts[0][0];
		sb.append("p = " + p + "\n\n");
		
		for (int i = 0; i < parts.length; i++) {
			long[] part = parts[i];
			sb.append("k" + (i + 1) + " = ");
			sb.append("(" + part[0] + ", " + part[1] + ", " + part[2] + ")\n");
		}
		sb.append("\n");
		
		for (int i = 0; i < parts.length; i++) {
			sb.append("M' = " + parts[i][2] + " mod " + parts[i][1] + "\n");
		}
		sb.append("\n");
		
		long[] mi = new long[parts.length];
		
		for (int i = 0; i < mi.length; i++) {
			long mul = 1;
			for (int j = 0; j < mi.length; j++) {
				if (j != i) 
					mul *= parts[j][1];
			}
			mi[i] = mul;
		}
		
		for (int i = 0; i < mi.length; i++) {
			sb.append("M" + (i + 1) + " = " + mi[i] + "\n");
		}
		sb.append("\n");
		
		long[] mci = new long[mi.length];
		
		for (int i = 0; i < mci.length; i++) {
			sb.append("M'" + (i + 1) + " * " + mi[i] + " = 1 mod " + parts[i][1] + "\n");
			Pair euler = Extra.solveWithEuler(mi[i], 1, parts[i][1]);
			sb.append(euler.getU() + "\n");
			mci[i] = Long.parseLong(euler.getV().toString());
		}
		
		for (int i = 0; i < mi.length; i++) {
			sb.append("M'" + (i + 1) + " = " + mci[i] + "\n");
		}
		sb.append("\n");
		
		long mc = 0;
		long n = 1;
		
		sb.append("M' = ");
		for (int i = 0; i < mi.length; i++) {
			if (i > 0) 
				sb.append(" + ");
			sb.append(mi[i] + " * " + mci[i] + " * " + parts[i][2]);
			mc += mi[i] * mci[i] * parts[i][2];
			n *= parts[i][1];
		}
		sb.append(" mod " + n + "\n");
		
		mc %= n;
		sb.append("M' = " + mc + "\n\n");
		
		long m = mc % p;
		sb.append("M = " + mc + " mod " + p + "\n");
		sb.append("M = " + m);
		
		res.setU(sb.toString());
		res.setV(Long.toString(m));
		
		return res;
	}
}
