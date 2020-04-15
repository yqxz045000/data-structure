package com.cfyj.data.structure.hash;

import java.util.HashMap;

/**
 * HashMap源码解析--看影响笔记
 * @author chenfeng
 *
 */
public class HashMapDemo {
	
	public static void main(String[] args) {
		HashMap<String,String> map = new HashMap<>();
		map.put("key", "value");
		map.put("key", "value");
		map.get("key");
		for (int i = 0; i < 50000; i++) {
//			map.put("key"+i, "value");
			System.out.println(("key"+i).hashCode());
		}
		map.put("key", "value");
//		System.out.println(tableSizeFor(18));
//		System.out.println(toBinary(10));
		
	}
	
	
	
    static String toBinary(int num) {
        String str = "";
        while (num != 0) {
            str = num % 2 + str;
            num = num / 2;
        }
        return str;
    }
	
    static final int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= 1 << 30) ? 1 << 30 : n + 1;
    }
	
}
