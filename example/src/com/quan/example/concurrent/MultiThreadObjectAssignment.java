package com.quan.example.concurrent;

import java.util.ArrayList;
import java.util.List;

/**
 * ���߳��¶���ֵ����
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
				//�����߳��ж�listִ�и�ֵ����,
				//ֻ�ǽ�ԭ��ָ��list��ָ��ı�Ϊָ��temp����,
				//��ʱ���Ѿ��������õ�ַ�ҵ���listԭ��ָ��Ķ��������ڵ�ǰ��ִ��ѭ���п�������ִ��
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
				//�����߳��ж�listִ�����Ԫ�ز�����
				//��ʱ��Ӱ�쵽list����ʵֵ���������߳��еı��������쳣
				list.add("3");
			}
		});
		
		t.start();
	}
	
}
