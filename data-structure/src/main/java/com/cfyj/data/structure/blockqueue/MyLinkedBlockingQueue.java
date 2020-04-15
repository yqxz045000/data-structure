package com.cfyj.data.structure.blockqueue;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 自己实现linkedBlockingQueue 单向 、FIFO、读写不阻塞队列
 * 
 * @author chenfeng
 *
 */
public class MyLinkedBlockingQueue<V> {

	private int capacity;

	private AtomicInteger count;

	private ReentrantLock putLock = new ReentrantLock();

	private Condition notPullCondition = putLock.newCondition();

	private final int pullWaitTime = 1000;

	private final int takeWaitTime = 1000;

	private ReentrantLock takeLock = new ReentrantLock();;

	private Condition notEmptyCondition = takeLock.newCondition();

	private Node<V> headNode;

	private Node<V> tailNode;

	class Node<V> {
		V itemV;
		MyLinkedBlockingQueue<V>.Node<V> nextNode;

		public V getItemV() {
			return itemV;
		}

		public MyLinkedBlockingQueue<V>.Node<V> getNextNode() {
			return nextNode;
		}

		public Node(V itemV, MyLinkedBlockingQueue<V>.Node<V> nextNode) {
			super();
			this.itemV = itemV;
			this.nextNode = nextNode;
		}

	}

	public MyLinkedBlockingQueue() {
		capacity = Integer.MAX_VALUE;
		count = new AtomicInteger(0);
		headNode = tailNode = new Node(null, null);
	}

	public void put(V value) throws InterruptedException {
		/**
		 * 1.加锁 2.判断容量是否已满,已满则加入到notpull中 3.维护元素 4.自增 5.检查当前长度,小于容量则唤醒notpull生产 6.解锁
		 * 7.检查容量,有元素则唤醒notempty消费
		 */

		putLock.lockInterruptibly();

		Node<V> addNode = new Node(value, null);
		try {
			while (count.get() >= capacity) { // capacity 可以不加valotile吗? 可以不加,因为capacity不会发生变化
				notPullCondition.await();
			}

			enqueue(addNode);
			count.incrementAndGet();
			if (count.get()-1 < capacity) {
				notPullCondition.signal();
			}

		} finally {
			putLock.unlock();
		}

		if (count.get() > 0) {
			signallEmpty();
		}

	}

	private void signallEmpty() throws InterruptedException {
		takeLock.lockInterruptibly();
		try {
			notEmptyCondition.signalAll();

		} finally {
			takeLock.unlock();
		}
	}

	private void enqueue(MyLinkedBlockingQueue<V>.Node<V> addNode) {
		tailNode = tailNode.nextNode = addNode;
	}

	public V take() throws InterruptedException {
		/**
		 * 1.加锁 2.判断长度,无元素则阻塞 3.消费,维护链表 4.自减 5.长度大于0则唤醒继续消费 6.长度未满则唤醒生产
		 */

		V value = null;
		takeLock.lockInterruptibly();

		try {
			while (count.get() == 0) {
				notEmptyCondition.await();	//这里之前写成了wait(),要注意wait是Object的,所以抛出IllegalMonitorStateException异常,即不在监视器下的wait.
			}

			value = dequeue();
			count.decrementAndGet();
			if (count.get() > 1) {
				notEmptyCondition.signal();
				;
			}

		} finally {
			takeLock.unlock();
		}

		if (count.get() < capacity) {
			signallPut();
		}

		return value;
	}

	private void signallPut() throws InterruptedException {
		putLock.lockInterruptibly();
		try {
			notPullCondition.signal();
		} finally {
			putLock.unlock();
		}
	}

	private V dequeue() {
		/**
		 * 2.获取head.next节点 3.设置head节点为next节点 4.返回next.item的值
		 */
		Node<V> tempNode = headNode.getNextNode();
		headNode = null;
		V value = tempNode.getItemV();
		tempNode.itemV = null;
		headNode = tempNode;
		return value;
	}

	public int size() {
		return count.get();
	}

}
