package com.falc0n.android.examapp.algs.elliptic_curve;

import java.util.Scanner;

public class Service {
	public static void main(String[] args) {
		EllipticCurve curve = new EllipticCurve("x^3+ax+b", 2, 4, 5);
		//EllipticCurve curve = new EllipticCurve("x^3+ax+b", 3, 3, 7);
		//EllipticCurve curve = new EllipticCurve("x^3+ax+b", 6, 4, 7);
		System.out.println("Discriminant = " + curve.getDiscriminant() + "\n");
		System.out.println(curve.tableToString() + "\n");
		calculate(curve);
		
	}
	
	public static void calculate(EllipticCurve curve) {
		Scanner sc = new Scanner(System.in);
		boolean flag = true;
		while (flag) {
			System.out.println("1. x2");
			System.out.println("2. sum");
			System.out.println("0. exit");
			int n = sc.nextInt();
			switch (n) {
			case 1:
				/*
				System.out.print("x1: ");
				int x = sc.nextInt();
				System.out.print("y1: ");
				int y = sc.nextInt();
				String p = curve.getP(new int[] {x, y});
				System.out.println("Answer = " + curve.dub(p) + "\n");
				*/
				System.out.print("p: ");
				String p = sc.next();
				System.out.println("Answer = " + curve.dub(p) + "\n");
				
				break;
			case 2:
				/*
				System.out.print("x1: ");
				int x1 = sc.nextInt();
				System.out.print("y1: ");
				int y1 = sc.nextInt();
				System.out.print("x2: ");
				int x2 = sc.nextInt();
				System.out.print("y2: ");
				int y2 = sc.nextInt();
				String p1 = curve.getP(new int[] {x1, y1});
				String p2 = curve.getP(new int[] {x2, y2});
				System.out.println("Answer = " + curve.sum(p1, p2) + "\n");
				*/
				System.out.print("p1: ");
				String p1 = sc.next();
				System.out.print("p2: ");
				String p2 = sc.next();
				System.out.println("Answer = " + curve.sum(p1, p2) + "\n");
				break;
			case 0:
				flag = false;
			}
		}
	}
}
