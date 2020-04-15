package com.cfyj.data.structure.skiplist;

import java.util.Random;

/**
 * 跳跃链表:
 * 1.包含的属性
	 * 1.头节点
	 * 2.尾节点
	 * 3.元素个数
	 * 4.最低层链表
	 * 0为最低层
 * 
 * 2.包含的方法：
	 * 1.新增
	 * 2.标识查找
	 * 3.范围查找
	 * 4.删除
 * @author lius
 *
 */

public class MySkipList<V> {

	
	private int size = 0 ; //元素个数
	
	private MySkipListNode<V> list ; //维护链表
	
	private MySkipListNode<V> headNode ;  //头部元素
	
	private MySkipListNode<V> tailNode ;  //尾部元素
	
	private int maxLevel = 0 ;
	
	private Random random = new Random (); 
	
	private static final int CONFIG_MAXLEVEL = 4 ; //默认配置的最大层数，随机层数不能超过此限制。
	
	
	
	public MySkipList() {
		this.headNode = new MySkipListNode<V>();
		this.size = 0 ;
		this.maxLevel = 0 ;
	}

	public int  set(long key ,V value ) {
		
		/**
		 * 新增：
		 * 	1.判断元素是否存在,
		 * 	2.如果存在则只需更改元素的value，结束
		 * 	3.如果元素不存在，首先定位小于该元素的最后一个节点
		 *  4.生成随机层数
		 *  5.维护前后向层数引用指针
		 *  6.插入K-V
		 */
		int result =1 ;
		if(exist(key)) {
			result = update(key,value);
		}else {
			add(key ,value);
		}
		return result;
	}
	
	/**
	 * 新增元素
	 * @param key
	 * @param value
	 */
	@SuppressWarnings("rawtypes")
	private void add(long key, V value) {
		/**
		 * 1.判断链表是否为null，为null则初始化链表，并插入值
		 * 2.不为null则找到第一个比其小的元素.
		 */
		
		if(size==0) {		
			/**
			 *1.生成随机层数		1
			 *2.初始化前向链表	1
			 *3.维护第一层链表前向引用	1
			 *4.维护level层链表前向引用	1
			 *5.创建节点元素		1	
			 *5.维护header的第一层后向引用 				1		
			 *6.更新maxlevel和更新head的最大层数以及引用	1
			 */
			int level = randomLevel();
			MySkipListNode beforeNodes [] = new MySkipListNode[CONFIG_MAXLEVEL];
			beforeNodes[1] = this.headNode;
			beforeNodes[level] = this.headNode;
			MySkipListNode<V> newNode = new MySkipListNode<V>(key,value,level,beforeNodes);
			MySkipListNode afterNodes [] =  new MySkipListNode[CONFIG_MAXLEVEL]; 
			afterNodes[1] = newNode;		
			this.headNode.setAfterNodes(afterNodes);
			updateMaxLevel(level,newNode); 
			
		}else {
			/**
			 * 1.找到最后一个比其小的元素
			 * 2.生成随机层数 
			 * 3.维护其前向引用层数
			 * 4.维护其后向引用层数
			 * 5.初始化节点
			 * 6.更新maxlevel和更新head的最大层数以及引用
			 * 7.维护前向元素的后向引用：注意相同层数维护引用关系，不同层级之间无阻挡也维护引用关系，最近元素维护引用关系
			 * 8.维护后向元素的前向引用：
			 */
			
			MySkipListNode beforeNode = getLastBeforeNode(key);
			int level = randomLevel();
			
			
			
			
			
			
			
			
			
		}

	}
	
	
	private MySkipListNode getLastBeforeNode(long key ) {
		
		MySkipListNode node = null;
		if(exist(key)) {
			//存在则获取该元素的前向元素
		}else {		
			node = getLastBeforeNode( key ,this.headNode , this.maxLevel);
		}
		
		return node;
	}

	/**
	 * 初始化传入的参数是 head节点和 maxlevel
	 * 假设不会出现相等的情况，所以在执行时需要先判断该元素是否存在,存在则走另一种方式获取
	 * @param key
	 * @return
	 */
	private MySkipListNode getLastBeforeNode(long key ,MySkipListNode<V> afterNode , int level ) {
		/**
		 * 1.从head维护的最高层开始查找.
		 * 2.未找到则降层查找
		 */
		
		
		//先同层遍历，如果同层中有下沉，则降级遍历
		if( key> afterNode.getKey()) {
			
			if(afterNode.getAfterNodes()!=null && afterNode.getAfterNodes()[level]!=null ) {
				//还存在同层后向元素，同层遍历
				return getLastBeforeNode(key,afterNode.getAfterNodes()[level] , level );
			}else if(afterNode.getAfterNodes() ==null  ) {
				//不存在后向元素，则该位置即最后一个最小元素
				return afterNode;
			}else {
				/**
				 * 存在后向节点，可能出现的情况：
				 * 1.第0层，则判断0层元素是否存在,存在则递归，不存在则说明该位置即最后一个最小元素
				 * 2.非第0层，降层查找：只要有存在的降层元素则递归，不存在则说明该位置即最后一个最小元素
				 */
				
				//不存在后向元素，返回前一个节点降层查找			
				MySkipListNode<V>[] node_afterNodes = afterNode.getAfterNodes();
				if(level==0 && node_afterNodes[0]!=null ) {
					
					return getLastBeforeNode(key, node_afterNodes[0], level);				
				}else if(level==0 && node_afterNodes[0] == null ) {
					
					return afterNode;
				}
				
			
				for(int i =level-1 ; i>=0 ; i++  ) { 
					if(node_afterNodes[i]!=null) {
						return getLastBeforeNode(key,node_afterNodes[i],i);
					}else if( node_afterNodes[i] == null && i ==0){
						//已经降到最低层，后向无元素，该位置即最后一个最小元素
						return afterNode;
					}			
				}					
			}
			
		}else if(key < afterNode.getKey() ){
			/**
			 * 从前置层降层可能出现的结果：
			 * 1.如果已经是第一层，则直接判断后向是否存在，不存在则返回该元素，存在则继续迭代
			 * 2.如果不是第一层则降层查找。
			 */
			MySkipListNode<V>[] beforeNode_afterNodes = null;
			if(afterNode.getBeforeNodes()!=null) {
				//因为head为前向节点
				beforeNode_afterNodes = afterNode.getAfterNodes();	
			}else {
				beforeNode_afterNodes =  afterNode.getBeforeNodes()[level].getAfterNodes();
			}
			
			if(level==0 && beforeNode_afterNodes[level] != null) {
			
				return getLastBeforeNode(key, beforeNode_afterNodes[level], level);
			}else if(level==0 && beforeNode_afterNodes[level] == null ) {
				
				return 	beforeNode_afterNodes[level];
			}else {
				for(int i = level -1 ; i>0 ; i++  ) {
					if(beforeNode_afterNodes[i]!=null) {
						return getLastBeforeNode(key,beforeNode_afterNodes[i],i);
					}else if( beforeNode_afterNodes[i] == null && i ==0){
						//已经降到最低层，后向无元素，该位置即最后一个最小元素
						return afterNode;
					}			
				}								
			}					
		}
		
		return null;
	}

	/**
	 * 更新最大层级和更新head的最大层数以及引用。
	 * @param level
	 */
	private void updateMaxLevel(int level , MySkipListNode<V> node) {
		if(this.maxLevel<level) {
			this.maxLevel = level ; 
			this.headNode.setLevel(level);
			MySkipListNode<V>[] afterNodes = this.headNode.getAfterNodes();
			afterNodes[level] = node;
		}
	}

	/**
	 * 生成随机层数
	 * @return
	 */
	private int randomLevel() {
		return random.nextInt(CONFIG_MAXLEVEL);
	}

	/**
	 * 更新元素value
	 * @param key
	 * @param v
	 * @return
	 */
	private int update(long key, V v) {
		MySkipListNode<V> node = getRef(key);
		int flags = 0 ;
		if(node !=null) {
			node.setValue(v);	
			flags=1;
		}
		return flags;	
	}


	/**
	 * 检测元素是否存在
	 * @param key
	 * @return
	 */
	private boolean exist(long key) {		
		return get(key)!=null ? true : false; 
	}
	
	/**
	 * 通过key获取元素的引用指针，
	 * @param key
	 * @return
	 */
	public  MySkipListNode<V> getRef(long key){
		//TODO ; 
		return null;
	}
	


	public V get(long key) {	
		return getRef(key).getValue();
	}
	
	
	
	
	
	
	
	
	
	
	
}
