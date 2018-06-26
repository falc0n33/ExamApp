package com.falc0n.android.examapp.algs.legendre;

public class Fraction {

	private int a;
	private int b;

	public Fraction() {
		this(1, 1);
	}

	public Fraction(int a, int b) {
		this.a = a;
		this.b = b;
	}

	public int getA() {
		return a;
	}

	public void setA(int a) {
		this.a = a;
	}

	public int getB() {
		return b;
	}

	public void setB(int b) {
		this.b = b;
	}

	public String toString() {
		return a + "/" + b;
	}

}
