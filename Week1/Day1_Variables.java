package com.jeevlifeworks.internship;

import java.util.Scanner;

public class Day1_Variables {

	public static void main(String[] args) {
		int a, b;
		float c;
		char d;
		boolean e;
		String name;
		Scanner scan = new Scanner(System.in);

		System.out.print("Enter the first Integer:");
		a = scan.nextInt();
		System.out.print("Enter the second Integer:");

		b = scan.nextInt();
		System.out.print("Enter a floating-pont Integer:");
		c = scan.nextFloat();
		System.out.print("Enter a single character :");
		d = scan.next().charAt(0);
		System.out.print("Enter a boolean value (True / False) :");
		e = scan.nextBoolean();
		System.out.print("Enter Your Name :");
		name = scan.next();
		System.out.print("Sum of " + a + "and " + b + " is :" + (a + b));
		System.out.print("Difference between " + a + "and " + b + " is :" + (a - b));
		System.out.print("Product of " + a + "and " + b + " is :" + (a * b));
		System.out.print(c + " Multiplied by 2 is : " + (2 * c));
		System.out.print(" The Next character after " + d + " is:" + (char) (d + 1));
		System.out.print(" The opposite of " + e + " is :" + !e);
		System.out.print("Hello ," + name);

	}

}
