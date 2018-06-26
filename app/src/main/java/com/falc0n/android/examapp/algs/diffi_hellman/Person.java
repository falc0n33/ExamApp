package com.falc0n.android.examapp.algs.diffi_hellman;

public class Person {
	int p, g;
	int a;
	String name;
	
	public Person(String name, int p, int g, int n) {
		this.name = name;
		this.p = p;
		this.g = g;
		this.a = n;
	}

	public Pair getPublicKey() {
		StringBuilder sb = new StringBuilder();
		sb.append(name + "\n");
		sb.append("PubKey = " + g + "^" + a + " mod " + p + "\n");
		String pub = Extra.fastMultiply(g, a, p);
		sb.append("PubKey = " + pub);
		
		Pair res = new Pair();
		res.setU(sb.toString());
		res.setV(pub);
		return res;
	}
	
	public Pair getPrivateKey(int n) {
		StringBuilder sb = new StringBuilder();
		sb.append(name + "\n");
		sb.append("PrivKey = " + n + "^" + a + " mod " + p + "\n");
		String priv = Extra.fastMultiply(n, a, p);
		sb.append("PrivKey = " + priv);
		
		Pair res = new Pair();
		res.setU(sb.toString());
		res.setV(priv);
		return res;
	}

	public int getP() {
		return p;
	}

	public int getG() {
		return g;
	}

	public int getA() {
		return a;
	}

	public String getName() {
		return name;
	}

}
