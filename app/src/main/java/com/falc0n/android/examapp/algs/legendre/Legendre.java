package com.falc0n.android.examapp.algs.legendre;

public class Legendre {

	private Fraction frac;
	private Fraction res;

	public Legendre(Fraction frac) {
		this.frac = frac;
		this.res = new Fraction(frac.getA(), frac.getB());
	}

	public Legendre(int a, int b) {
		this(new Fraction(a, b));
	}

	public void setFrac(Fraction frac) {
		this.frac = frac;
		this.res = new Fraction(frac.getA(), frac.getB());
	}

	public Fraction getFrac() {
		return frac;
	}

	public int getResult() {
		return res.getA();
	}

	public String solve() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(frac.toString());
		while (!isSolved(res)) {
			simplify(res);
			sb.append(" = " + res);
		}
		sb.append(" = " + getResult());
		
		return sb.toString();
	}

	private boolean isSolved(Fraction frac) {
		if (frac.getA() == 1 || frac.getA() == -1 || frac.getA() == 0)
			return true;
		else
			return false;
	}

	private void twist(Fraction frac) {
		boolean negative = (frac.getA() < 0);

		int b = Math.abs(frac.getA());
		int a = (negative) ? frac.getB() * -1 : frac.getB();

		frac.setA(a);
		frac.setB(b);
	}

	private void changeSign(Fraction frac) {
		frac.setA(frac.getA() * -1);
	}

	private void simplify(Fraction frac) {
		if (Math.abs(frac.getA()) >= frac.getB()) {
			frac.setA(frac.getA() % frac.getB());
		} else if (frac.getA() % 2 == 0) {
			int mod = frac.getB() % 8;
			frac.setA(frac.getA() / 2);
			if (mod != 1 && mod != 7)
				changeSign(frac);
		} else if (frac.getA() % 2 != 0 && frac.getB() % 2 != 0) {
			int modA = Math.abs(frac.getA()) % 4;
			int modB = frac.getB() % 4;
			twist(frac);
			//System.out.println("MOD " + modA + " " + modB);
			if ((modA == 3 && modB == 3))
				changeSign(frac);
		}
	}

}
