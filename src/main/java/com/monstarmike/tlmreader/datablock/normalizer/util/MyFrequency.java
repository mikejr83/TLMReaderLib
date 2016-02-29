package com.monstarmike.tlmreader.datablock.normalizer.util;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;


/**
 * This class implements a subset of similar methods inspired from the Frequency
 * class from org.apache.commons-math3. <br>
 * The aim of this own implementation is, to not include the whole commons-math3
 * library.
 */
public class MyFrequency {

	private Map<Integer, Integer> map = new TreeMap<Integer, Integer>();
	private int totalCount = 0;

	public int getTotalCount() {
		return totalCount;
	}
	
	public void addValue(int value) {
		Integer key = Integer.valueOf(value);
		int count = 1;
		if (map.containsKey(key)) {
			count += map.get(key).intValue();
		}
		map.put(key, count);
		totalCount++;
	}

	public Iterator<Entry<Integer, Integer>> entrySetIterator() {
		return map.entrySet().iterator();
	}

	public int getCumFreq(Integer v) {
		int cumulativCount = 0;
		for (Entry<Integer, Integer> entry : map.entrySet()) {
			if (entry.getKey() <= v) {
				cumulativCount += entry.getValue();
			} else {
				return cumulativCount;
			}
		}
		return cumulativCount;
	}

	public double getCumPct(Integer v) {
		return (double) getCumFreq(v) / totalCount;
	}
}
