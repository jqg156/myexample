package com.quan.example.concurrent;

import java.util.ArrayList;
import java.util.List;
/**
 * 多线程下volatile的使用
 * @author 11084729
 *
 */
public class MultiThreadVolatile {

	private volatile List<String> vlist = new ArrayList<>();
	private List<String> list = new ArrayList<>();
	
	private volatile boolean vstop = false;
	private boolean stop = false;
	
	public static void main(String[] args) throws Exception{
		
		MultiThreadVolatile m = new MultiThreadVolatile();
		
		for(int i = 0; i < 200; i++) {
			m.list();
			Thread.sleep(10);
		}
		
	}
	
	@SuppressWarnings("unused")
	private void vlist() {
		vlist.clear();
		vlist.add("1");
		List<String> temp = new ArrayList<>();
		temp.add("1");
		temp.add("2");
		
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
				}
				vlist = temp;
			}
		});
		t.start();
		int i = 0;
		while(vlist.size() == 1) {
			i ++;
		}
		System.out.println(i);
	}
	
	@SuppressWarnings("unused")
	private void list() {
		list.clear();
		list.add("1");
		List<String> temp = new ArrayList<>();
		temp.add("1");
		temp.add("2");
		
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
				}
				list = temp;
			}
		});
		t.start();
		int i = 0;
		while(list.size() == 1) {
			//如果加上这一段空循环，代码就可以正常执行下去
			/*for(String s : list) {
				
			}*/
			i ++;
		}
		System.out.println(i);
	}
	
	@SuppressWarnings("unused")
	private void vstop() {
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
				}
				vstop = true;
			}
		});
		t.start();
		int i = 0;
		while(!vstop) {
			i ++;
		}
		System.out.println(i);
		stop = false;
	}
		
	@SuppressWarnings("unused")
	private void stop() {
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
				}
				stop = true;
			}
		});
		t.start();
		int i = 0;
		while(!stop) {
			i ++;
		}
		System.out.println(i);
		stop = false;
	}
	
}
