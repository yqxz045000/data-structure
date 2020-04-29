package com.cfyj.data.structure.bitmap;

import java.util.Random;

import lombok.extern.slf4j.Slf4j;

/**
 * bitMap
 * @author cf
 * @create 2020年4月17日
 */

@Slf4j
public class BitMap {

  
  private int capacity ;  //容量
  
  private byte[] table ; //存储桶
  
  private int maxValue ; //最大能存储的值
  
  BitMap(){
    this(Integer.MAX_VALUE);
  }
  
  BitMap(int maxValue){
    this.capacity = getNum(maxValue);
    this.maxValue = maxValue; 
  }
   
  /**
   * 获取元素所在table桶
   * @param value
   * @return
   */
  private int getNum(int value) {
    return value >> 3;
  }
  
  /**
   * 添加元素
   * @param value
   * @return
   */
  public boolean add(int value) {
    byte[] table ;
    if( (table = this.table)==null) {
      table = initTable();
    }   
    return setValue(table,value);
  }
  
  /**
   * 1.找到value所在桶-->缩小8倍
   * 2.找到value迁移量
   * 3.迁移value到指定bit
   * 4.运算更新bit
   * @param table
   * @param value
   * @return
   */
  private boolean setValue(byte[] table, int value) {   
    int oldValue = table[getNum(value)]; 
    int newValue = table[getNum(value)] |= 1 << getPosition(value);   
    return oldValue != newValue;
  }
  
  /**
   * 返回value在 table[index]中的偏移量，因为对于值而言，一直是从二进制的0起始点+1递增的，
   * 所以可以直接与 7运算,得出其在0-7之间的递增偏移量
   * @param value
   * @return
   */
  private int getPosition(int value) {
    return value & 0x07;
  }
  
  
  private byte[] initTable() {
    this.table = new byte[this.capacity];
    return this.table;
  }
  
  /**
   * 包含运算其实就是判断值是否存在，通过重运算方式去比较，当重定位运算后值发生改变则说明不存在
   * @param value
   * @return
   */
  public boolean contations(int value) {
   return  (table[getNum(value)] |  1 << getPosition(value)) == table[getNum(value)]; 
  }
  
  
  public static void main(String[] args) {
    
    BitMap bitMap = new BitMap();
    for(int i =5; i< 1000 ;i++) {
      bitMap.add(i);
    }
    
    Random random = new Random();
    for(int i =5; i< 100 ;i++) {
      int key = random.nextInt(2000) ; 
     boolean flag =  bitMap.contations( key);
     log.info("key:{} , contaions:{}" , key ,flag );
    }
  }
  
  
}
