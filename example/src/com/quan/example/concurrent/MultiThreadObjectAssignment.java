package com.quan.example.concurrent;

import java.util.ArrayList;
import java.util.List;

/**
 * 多线程下对象赋值问题
 * @author zhiguangquan
 *
 */
public class MultiThreadObjectAssignment {

	private List<String> list = new ArrayList<>();
	
	public MultiThreadObjectAssignment() {
		list.add("1");
		list.add("2");
	}
	
	public static void main(String[] args) throws Exception{
		
		MultiThreadObjectAssignment m1 = new MultiThreadObjectAssignment();
		m1.objectAssign();
		for(String s : m1.list) {
			System.out.println(s);
			Thread.sleep(20);
		}
		
		MultiThreadObjectAssignment m2 = new MultiThreadObjectAssignment();
		m2.objectOperate();
		for(String s : m2.list) {
			System.out.println(s);
			Thread.sleep(20);
		}
		
	}
	
	private void objectAssign() {
		
		List<String> temp = new ArrayList<>();
		temp.add("4");
		temp.add("5");
		temp.add("6");
		
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				//在子线程中对list执行赋值操作,
				//只是将原本指向list的指针改变为指向temp对象,
				//此时，已经根据引用地址找到了list原本指向的对象，所以在当前的执行循环中可以正常执行
				list = temp;
			}
		});
		
		t.start();
		
	}
	
	private void objectOperate() {
		
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				//在子线程中对list执行添加元素操作，
				//此时会影响到list的真实值，导致主线程中的便利出现异常
				list.add("3");
			}
		});
		
		t.start();
	}
	
}
