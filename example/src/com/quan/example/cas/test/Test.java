package com.quan.example.cas.test;

import java.util.Vector;

import com.quan.example.cas.LockFreeVector;

public class Test {

	public static void main(String[] args) {
		long start1 = System.currentTimeMillis();
		testVector();
		long end1 = System.currentTimeMillis();
		System.out.println("testVector():" + (end1 - start1));
		
		/*long start2 = System.currentTimeMillis();
		testLockFreeVector();
		long end2 = System.currentTimeMillis();
		System.out.println("testLockFreeVector():" + (end2 - start2));*/
	}
	
	private static void testVector() {
		Vector<String> v = new Vector<>();
		for(int i = 0;i < 2000000; i++) {
			v.add(i + "");
		}
	}
	
	private static void testLockFreeVector() {
		LockFreeVector<String> v = new LockFreeVector<>();
		for(int i = 0;i < 2000000; i++) {
			v.push_back(i + "");
		}
	}
	
}
