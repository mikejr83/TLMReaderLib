package com.monstarmike.tlmreader.datablock.normalizer.util;

import java.util.Iterator;
import java.util.Map.Entry;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.monstarmike.tlmreader.datablock.normalizer.util.MyFrequency;

public class MyFrequencyTest {

	private MyFrequency frequency;

	@Before
	public void setup() {
		frequency = new MyFrequency();
		frequency.addValue(2);
		frequency.addValue(2);
		frequency.addValue(1);
		frequency.addValue(1);
		frequency.addValue(1);
		frequency.addValue(1);
		frequency.addValue(2);
		frequency.addValue(5);
	}

	@Test
	public void testAddValue() {
		Assert.assertEquals(8, frequency.getTotalCount());
	}

	@Test
	public void testEntrySetIterator() {
		Iterator<Entry<Integer, Integer>> entrySetIterator = frequency.entrySetIterator();
		Assert.assertTrue(entrySetIterator.hasNext());
		Entry<Integer, Integer> next = entrySetIterator.next();
		Assert.assertEquals(1, next.getKey().intValue());
		Assert.assertEquals(4, next.getValue().intValue());

		Assert.assertTrue(entrySetIterator.hasNext());
		next = entrySetIterator.next();
		Assert.assertEquals(2, next.getKey().intValue());
		Assert.assertEquals(3, next.getValue().intValue());

		Assert.assertTrue(entrySetIterator.hasNext());
		next = entrySetIterator.next();
		Assert.assertEquals(5, next.getKey().intValue());
		Assert.assertEquals(1, next.getValue().intValue());
	}

	@Test
	public void testGetCumFreq() {
		Assert.assertEquals(0, frequency.getCumFreq(0));
		Assert.assertEquals(4, frequency.getCumFreq(1));
		Assert.assertEquals(7, frequency.getCumFreq(2));
		Assert.assertEquals(7, frequency.getCumFreq(3));
		Assert.assertEquals(7, frequency.getCumFreq(4));
		Assert.assertEquals(8, frequency.getCumFreq(5));
		Assert.assertEquals(8, frequency.getCumFreq(6));
	}
	
	@Test
	public void testGetCumPct() {
		Assert.assertEquals(0.0, frequency.getCumPct(0), 0.001);
		Assert.assertEquals(0.5, frequency.getCumPct(1), 0.001);
		Assert.assertEquals(0.875, frequency.getCumPct(2), 0.001);
		Assert.assertEquals(0.875, frequency.getCumPct(3), 0.001);
		Assert.assertEquals(0.875, frequency.getCumPct(4), 0.001);
		Assert.assertEquals(1, frequency.getCumPct(5), 0.001);
		Assert.assertEquals(1, frequency.getCumPct(6), 0.001);
	}

}
