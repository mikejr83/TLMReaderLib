package com.monstarmike.tlmreader.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.google.common.io.Files;
import com.monstarmike.tlmreader.datablock.DataBlock;

public class DataBlockTest {
	byte[] theBytes = null;

	@Before
	public void setup() {

		try {
			this.theBytes = Files.toByteArray(new File("src/test/data/SensorData.bin"));
		} catch (IOException e) {
			System.out.println("Yo, the data block data needs to be in the src/test/data/SensorData.bin file!");
			e.printStackTrace();
		}
	}

	/**
	 * Checks that the loaded data set for the tests is correct. All data in the
	 * dataset should be 20 byte sensor data block information.
	 */
	@Test
	public void testDataSetIsGood() {
		assertTrue(this.theBytes.length % 20 == 0);
	}

	@Test
	public void testTimestampOfTheFirstDataBlock() {
		byte[] dataBytes = Arrays.copyOfRange(this.theBytes, 0, 20);
		DataBlock dataBlock = DataBlock.createDataBlock(dataBytes, null);
		assertEquals(6620, dataBlock.getTimestamp());
	}
	
	@Test
	public void testTimestampOfTheLastDataBlock() {
		byte[] dataBytes = Arrays.copyOfRange(this.theBytes, this.theBytes.length-20, this.theBytes.length);
		DataBlock dataBlock = DataBlock.createDataBlock(dataBytes, null);
		assertEquals(90516, dataBlock.getTimestamp());
	}
}
