package com.falc0n.android.examapp.algs.mod;

import java.util.LinkedList;
import java.util.List;

public class Table {
	private List<Integer> qn;
	private List<Integer> pn;

	public Table(List<Integer> coefs) {
		qn = new LinkedList<>(coefs);
		qn.add(0, 0);
		pn = new LinkedList<>();
		pn.add(0, 1);
		calculate();
	}

	private void calculate() {
		for (int i = 1; i < qn.size(); i++) {
			int p = qn.get(i) * pn.get(i - 1);
			if (i > 1) 
				p += pn.get(i - 2);
			pn.add(p);
		}
	}
	
	public List<Integer> getQn() {
		return qn;
	}
	
	public List<Integer> getPn() {
		return pn;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		List<Integer> in = new LinkedList<>();
		
		int n = qn.size();
		String form = "";
		
		for (int i = 0; i < n; i++) {
			in.add(i);
			form += "%10s";
		}
		
		sb.append("i " + String.format(form, in.toArray()) + "\n");
		sb.append("q " + String.format(form, qn.toArray()) + "\n");
		sb.append("p " + String.format(form, pn.toArray()) + "\n");
		
		return sb.toString();
	}
}
