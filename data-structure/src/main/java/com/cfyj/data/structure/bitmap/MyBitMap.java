package com.cfyj.data.structure.bitmap;

/**
 * 位图数据结构
 * 思想: 对于整型来说就是在分布在0-N之间,那么就可以利用字节来存储数据,每个位代表一个数,1个字节能代表8个数,
 * 	就将整型值压缩来8倍处理,而对于元素本身来说,就是不断对1的累加,那么相邻两个元素就相差一位.
 * 最终: 高位定位桶,低位定位元素偏移位
 * 
 * 步骤:
 * 	1.构建一个字节数组
 * 	2.新增元素时,定位元素所在的桶: 每个桶表示8个值,那么对于元素所在桶来说,就是缩小8倍即可确定其桶位置,
 * 	3.确定元素在桶内的位置: 找元素其实在对应桶的bit位置,这个偏移位置为 元素0-8之间的值,
 * 	所以将元素与7进行比较,获取其在一个字节上的偏移量
 * 	4.将目标桶位置与偏移位做或运算,即可将目标值加到数组上
 * @author chenfeng
 *
 */
public class MyBitMap {

	private int capacity = Integer.MAX_VALUE ;
	
	private byte [] elements = new byte [capacity >> 3] ; 
	
	public void add(int nums) {
		int index = getIndex(nums);
		setValue(nums, index);
		
	}
	
	public boolean contains(int nums) {
		return (elements[getIndex(nums)] | 1 <<  (nums & 7)) == elements[getIndex(nums)] ? true:false;
	}
	
	public int getIndex(int nums) {
		return nums >> 3 ; 
	}
	
	public void setValue(int nums ,int index) {
		elements[index] |= 1 <<  (nums & 7);
	}
	
	public static void main(String[] args) {
		System.out.println(10>> 3);	//1

		
		MyBitMap bitMap = new MyBitMap();
		bitMap.add(1);
		System.out.println(bitMap.contains(1));
		bitMap.add(2);
		bitMap.add(3);
		bitMap.add(33333);
		System.out.println(bitMap.contains(33333));
	}
	
	
}
