package com.cfyj.data.structure.skiplist;

import lombok.Data;

/**
 * Skiplist的子节点
 * 
 * 包含属性：
 * 1.key
 * 2.value
 * 元素级别
 * 3.前向层级元素指针
 * 4.后向层级元素指针
 *
 * 
 * @author lius
 *
 * @param <V>
 */
@Data
public class MySkipListNode<V> {

	
	private long key ; 
	
	private V value ; 
	
	private int level; //元素所处层级
	
	private MySkipListNode<V> beforeNodes[] ; //前向引用，按层级排列
	
	private MySkipListNode<V> afterNodes[] ; //后向引用，按层级排列，当后向引用的第一层为null时，则说明该节点为尾部节点
	
	
	public MySkipListNode() {

	}


	public MySkipListNode(long key, V value, int level) {
		super();
		this.key = key;
		this.value = value;
		this.level = level;
	}


	public MySkipListNode(long key, V value, int level, MySkipListNode<V>[] beforeNodes) {
		super();
		this.key = key;
		this.value = value;
		this.level = level;
		this.beforeNodes = beforeNodes;
	}
	
	
	
	
}
