package com.cfyj.data.structure.tree;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * TreeMap
 * @author chenfeng
 *
 */
public class TreeMapDemo {

	public static void main(String[] args) {
		TreeMap<Integer, String> map = new TreeMap();
		map.put(1, "1");
		map.put(2, "2");
		map.put(3, "3");
		map.put(4, "4");
		map.put(5, "5");
		map.put(6, "6");
		map.put(7, "7");
		SortedMap<Integer, String> subMap = map.subMap(3, 6);
		System.out.println(subMap.toString());
		
		
	}
	
	
}
