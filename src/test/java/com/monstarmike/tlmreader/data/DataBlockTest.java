package com.monstarmike.tlmreader.data;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import com.google.common.io.Files;
import com.monstarmike.tlmreader.datablock.DataBlock;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class DataBlockTest extends TestCase {
	byte[] theBytes = null;

	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public DataBlockTest(String testName) {
		super(testName);

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
	public void testDataSetIsGood() {
		assertTrue(this.theBytes.length % 20 == 0);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(DataBlockTest.class);
	}

	/**
	 * Rigourous Test :-)
	 */
	public void testDataBlockTimestamp() {
		for (int i = 0; i < this.theBytes.length; i += 20) {
			byte[] dataBytes = Arrays.copyOfRange(this.theBytes, i, i + 20);
			DataBlock block = DataBlock.createDataBlock(dataBytes, null);
			// if (block != null)
			// System.out.println(block.get_timestamp());
		}
	}
}
