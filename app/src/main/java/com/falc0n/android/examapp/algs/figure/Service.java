package com.falc0n.android.examapp.algs.figure;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

public class Service {
	public static List<String>[] getOperations(String s) {
		List<String> chars = new ArrayList<>();
		List<String> states = new ArrayList<>();
		chars.add("e");
		states.add(s);

		int rots = s.length() - 1;
		String sRot = new String(s);
		for (int i = 0; i < rots; i++) {
			chars.add((char) (97 + i) + "");
			sRot = Extra.leftRot(sRot);
			states.add(sRot);
		}

		int offset = rots;
		for (int i = 0; i < s.length(); i++) {
			char c = (char) (97 + offset + i);
			if (c == 'e') {
				offset++;
				chars.add((char) (1 + c) + "");
			} else {
				chars.add(c + "");
			}
			states.add(Extra.mirror(s, (char) (97 + i - 32)));
		}

		if (s.length() % 2 == 0) {
			offset = (int) chars.get(chars.size() - 1).charAt(0);
			for (int i = 0, j = 1; i < s.length() / 2; i++, j += 2) {
				char c = (char) (offset + i + 1);
				chars.add(c + "");
				String state = Extra.halfMirror(s, j);
				states.add(state);
			}
		}

		return new List[] { chars, states };
	}

	public static List<String>[] getOperations2(String s) {
		List<String> chars = new ArrayList<>();
		List<String> states = new ArrayList<>();
		chars.add("e");
		states.add(s);

		int rots = s.length() - 1;
		String sRot = new String(s);
		for (int i = 0; i < rots; i++) {
			chars.add((char) (97 + i) + "");
			sRot = Extra.leftRot(sRot);
			states.add(sRot);
		}

		int offset = rots;
		int n = s.length();
		
		if (n % 2 == 0) {
			n /= 2;
		}
		
		for (int i = 0; i < n; i++) {
			char c = (char) (97 + offset + i);
			if (c == 'e') {
				offset++;
				chars.add((char) (1 + c) + "");
			} else {
				chars.add(c + "");
			}
			states.add(Extra.mirror(s, (char) (97 + i - 32)));
		}

		if (s.length() % 2 == 0) {
			offset = (int) chars.get(chars.size() - 1).charAt(0);
			for (int i = 0, j = 1; i < s.length() / 2; i++, j += 2) {
				char c = (char) (offset + i + 1);
				chars.add(c + "");
				String state = Extra.halfMirror(s, j);
				states.add(state);
			}
		}

		return new List[] { chars, states };
	}

	public static String getStringOperations(List<String>[] operations) {
		StringBuilder sb = new StringBuilder();
		String original = operations[1].get(0);
		for (int i = 0; i < operations[0].size(); i++) {
			sb.append(operations[0].get(i) + ": " + original + " => " + operations[1].get(i) + "\n");
		}
		return sb.toString();
	}

	public static String getCayleyTable(List<String>[] operations) {
		StringBuilder sb = new StringBuilder();

		sb.append("n\t");
		for (String c : operations[0]) {
			sb.append(c + "\t");
		}
		sb.append("\n\n");

		for (int i = 0; i < operations[0].size(); i++) {
			String c1 = operations[0].get(i);
			sb.append(c1 + "\t");

			for (int j = 0; j < operations[0].size(); j++) {
				String c2 = operations[0].get(j);
				sb.append(Service.twoOperations(operations, c1, c2) + "\t");
			}
			sb.append("\n\n");
		}

		return sb.toString();
	}

	public static String twoOperations(List<String>[] operations, String c1, String c2) {
		String s = operations[1].get(0);
		int n = s.length();
		if (n % 2 == 0) 
			n /= 2;
		int rots = s.length();

		int i1 = operations[0].indexOf(c1);
		int i2 = operations[0].indexOf(c2);
		String state = operations[1].get(i1);

		if (c1 == "e") {
			return c2;
		} else if (c2 == "e") {
			return c1;
		} else if (i2 < rots) {
			for (int j = 0; j < i2; j++)
				state = Extra.leftRot(state);
			int pos = operations[1].indexOf(state);
			return operations[0].get(pos);
		} else if (c1.equals(c2)) {
			return "e";
		} else if (i2 < rots + n) {
			char angle = (char) (i2 - rots + 65);
			state = Extra.mirror(state, angle);
			int pos = operations[1].indexOf(state);
			return operations[0].get(pos);
		} else {
			int count = i2 - rots - n + 1;
			int mod = 1;
			for (int i = 1; i < count; i++) {
				mod += 2;
			}
			state = Extra.halfMirror(state, mod);
			int pos = operations[1].indexOf(state);
			return operations[0].get(pos);
		}
	}

	public static List<String>[] getGroups(List<String>[] operations) {
		List<String> chars = operations[0];
		List<String> groups = new ArrayList<>();
		Set<String> set = new TreeSet<>();

		for (String c : chars) {
			String res = c;
			do {
				set.add(res);
				res = Service.twoOperations(operations, c, res);
			} while (!set.contains(res));
			groups.add(set.toString());
			set.clear();
		}
		return new List[] { chars, groups };
	}

	public static String getStringGroups(List<String>[] groups) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < groups[0].size(); i++) {
			sb.append("<" + groups[0].get(i) + "> = " + groups[1].get(i) + "\n");
		}

		return sb.toString();
	}

	public static String getLeftRightClass(List<String>[] operations, List<String>[] groups) {
		StringBuilder sb = new StringBuilder();
		Formatter formatter = new Formatter(sb, Locale.US);

		for (int i = 0; i < groups[0].size(); i++) {
			String c1 = groups[0].get(i);
			for (int j = 0; j < groups[0].size(); j++) {
				String c2 = groups[0].get(j);
				String group = groups[1].get(j);
				group = group.substring(1);
				group = group.substring(0, group.length() - 1);
				String[] arr = group.split(", ");
				Set<String> lClass = new TreeSet<>();
				Set<String> rClass = new TreeSet<>();
				for (String c3 : arr) {
					String lRes = Service.twoOperations(operations, c1, c3);
					lClass.add(lRes);
					String rRes = Service.twoOperations(operations, c3, c1);
					rClass.add(rRes);
				}

				formatter.format("%s * <%s> = %-35s <%s> * %s = %s\n", c1, c2, lClass.toString(), c2, c1,
						rClass.toString());
				// sb.append(c1 + " * <" + c2 + "> = " + lClass.toString() + "\t\t\t");
				// sb.append("<" + c2 + "> * " + c1 + " = " + rClass.toString() + "\n");
			}
		}

		return sb.toString();
	}

	public static String getChars(int n) {
		String s = "";
		for (int i = 0; i < n; i++) {
			s += (char) (65 + i);
		}
		return s;
	}
}
