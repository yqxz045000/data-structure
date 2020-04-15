package com.cfyj.data.structure.blockqueue;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 测试自己写的LikedBlockingQueue
 * 
 * @author chenfeng
 *
 */
public class MyLinkedBlockingQueueTest {
	public static AtomicInteger valueInteger = new AtomicInteger(0);
	public static volatile Set<Integer> set = Collections.synchronizedSet(new HashSet<>());
	
	public static void main(String[] args) {
		
		int threadNum = 50;
		CyclicBarrier cb = new CyclicBarrier(threadNum);
		MyLinkedBlockingQueue<Integer> queue = new MyLinkedBlockingQueue<Integer>();
		for (int i = 1; i <=threadNum; i++) {
			if (i % 2 == 0) {
				new ProductWorker(cb, queue).start();
				continue;
			}
			new ConsumeWorker(cb, queue).start();

		}
	}

}

class ProductWorker extends Thread {

	private CyclicBarrier cb;
	MyLinkedBlockingQueue<Integer> queue;

	public ProductWorker(CyclicBarrier cb, MyLinkedBlockingQueue<Integer> queue) {
		this.cb = cb;
		this.queue = queue;
	}

	@Override
	public void run() {
		try {
			cb.await();
			System.out.println(super.getName()+"阻塞失效,开始生产");
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < 50; i++) {
			try {
				queue.put(MyLinkedBlockingQueueTest.valueInteger.getAndIncrement());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("生产完毕");
	}
}

class ConsumeWorker extends Thread {

	private CyclicBarrier cb;
	MyLinkedBlockingQueue<Integer> queue;

	public ConsumeWorker(CyclicBarrier cb, MyLinkedBlockingQueue<Integer> queue) {
		this.cb = cb;
		this.queue = queue;
	}

	@Override
	public void run() {
		try {
			cb.await();
			System.out.println(super.getName()+"阻塞失效,开始消费");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < 50; i++) {
			try {
				Integer numInteger =queue.take();
//				System.out.println(super.getName() + ": value= " + numInteger);
				MyLinkedBlockingQueueTest.set.add(numInteger);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println(MyLinkedBlockingQueueTest.set.size());
	}
}
