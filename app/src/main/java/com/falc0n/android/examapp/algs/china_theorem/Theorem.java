package com.falc0n.android.examapp.algs.china_theorem;

public class Theorem {
	public static Pair solve(long[]... parts) {
		Pair res = new Pair();
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < parts.length; i++) {
			long[] part = parts[i];
			sb.append("k" + (i + 1) + " = ");
			sb.append("(" + part[0] + ", " + part[1] + ")\n");
		}
		sb.append("\n");
		
		for (int i = 0; i < parts.length; i++) {
			sb.append("M' = " + parts[i][0] + " mod " + parts[i][1] + "\n");
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
			sb.append(mi[i] + " * " + mci[i] + " * " + parts[i][0]);
			mc += mi[i] * mci[i] * parts[i][0];
			n *= parts[i][1];
		}
		sb.append(" mod " + n + "\n");
		
		mc %= n;
		sb.append("M' = " + mc + "\n\n");
		
		res.setU(sb.toString());
		res.setV(Long.toString(mc));
		
		return res;
	}
}
