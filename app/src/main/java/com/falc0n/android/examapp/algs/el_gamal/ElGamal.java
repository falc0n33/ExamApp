package com.falc0n.android.examapp.algs.el_gamal;

public class ElGamal {
	private long p, g, x, k;
	private long y;

	public ElGamal(long p, long g, long x, long k) {
		this.p = p;
		this.g = g;
		this.x = x;
		this.k = k;
		this.y = Long.parseLong(Extra.fastMultiply(g, x, p));
	}

	public Pair encrypt(long m) {
		StringBuilder sb = new StringBuilder();

		sb.append("Message = " + m + "\n");

		long a = Long.parseLong(Extra.fastMultiply(g, k, p));
		sb.append("a = g^k mod p" + "\n");	
		sb.append("a = " + g + "^" + k + " mod " + p + "\n");
		sb.append("a = " + a + "\n");

		long mc = m % p;
		long b = Long.parseLong(Extra.fastMultiply(y, k, p));
		b = (mc * b) % p;
		sb.append("b = y^k * m mod p" + "\n");
		sb.append("b = " + y + "^" + k + " * " + m + " mod " + p + "\n");
		sb.append("b = " + b + "\n");

		long[] cipher = { a, b };
		sb.append("{a, b} = " + "{" + a + ", " + b + "}" + "\n");

		Pair res = new Pair();
		res.setU(sb.toString());
		res.setV(cipher);

		return res;
	}

	public Pair decrypt(long a, long b) {
		StringBuilder sb = new StringBuilder();
		sb.append("{a, b} = " + "{" + a + ", " + b + "}" + "\n");

		sb.append("M = b * (a^x)^-1 mod p" + "\n");
		sb.append("M = " + b + " * (" + a + "^" + x + ")^-1 mod " + p + "\n");
		sb.append("M = b * a^p-1-x mod p" + "\n");
		sb.append("M = " + b + " * " + a + "^" + (p - 1 - x) + " mod " + p + "\n");

		long m = b % p;
		long ac = Long.parseLong(Extra.fastMultiply(a, (p - 1 - x), p));
		m = (m * ac) % p;
		
		sb.append("Message = " + m + "\n");

		Pair res = new Pair();
		res.setU(sb.toString());
		res.setV(m);

		return res;
	}
	
	public Pair getSignature(long m) {
		StringBuilder sb = new StringBuilder();
		sb.append("Message = " + m + "\n");
		
		long r = Long.parseLong(Extra.fastMultiply(g, k, p));
		sb.append("r = g^k mod p" + "\n");
		sb.append("r = " + g + "^" + k + " mod " + p + "\n");
		sb.append("r = " + r + "\n");
		
		long mc = (m - x * r) % (p - 1);
		mc = mod(mc, (p - 1));
		long s = Long.parseLong(Extra.solveWithEuler(k, mc, (p - 1)).getV().toString());
		sb.append("s = (M - x * r) * k^-1 mod (p - 1)" + "\n");
		sb.append("s = " + (m - x * r) + " * " + k + "^-1 mod " + (p - 1) + "\n");
		sb.append("s = " + s + "\n");
		
		long[] signature = {r, s};
		sb.append("{r, s} = {" + r + ", " + s + "}\n");
		Pair res = new Pair();
		res.setU(sb.toString());
		res.setV(signature);
		
		return res;
	}
	
	public Pair verifySignature(long m, long[] rs) {
		StringBuilder sb = new StringBuilder();
		sb.append("{r, s} = {" + rs[0] + ", " + rs[1] + "}\n");
		
		long yc = Long.parseLong(Extra.fastMultiply(y, rs[0], p));
		long rc = Long.parseLong(Extra.fastMultiply(rs[0], rs[1], p));
		long left = (yc * rc) % p;
		sb.append("y^r * r^s mod p = ");
		sb.append(yc + " * " + rc + " mod " + p + " = ");
		sb.append(left + " mod " + p + "\n");
		
		long right = Long.parseLong(Extra.fastMultiply(g, m, p));
		sb.append("g^m mod p = " + right + " mod " + p + "\n");
		
		boolean verify = (left == right);
		if (verify)
			sb.append("right == left OK" + "\n");
		else 
			sb.append("NOT OK" + "\n");
		
		Pair res = new Pair();
		res.setU(sb.toString());
		res.setV(verify);
		
		return res;
	}
	
	private long mod(long a, long m) {
		long res = a % m;
		if (res < 0) 
			res += m;
		return res;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("p = " + p + "\n");
		sb.append("g = " + g + "\n");
		sb.append("x = " + x + "\n");
		sb.append("k = " + k + "\n");
		sb.append("y = " + g + "^" + x + " mod " + p + "\n");
		sb.append("y = " + y + "\n");

		return sb.toString();
	}

}
