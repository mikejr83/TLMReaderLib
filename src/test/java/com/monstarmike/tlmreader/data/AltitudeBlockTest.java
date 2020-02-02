package com.monstarmike.tlmreader.data;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import com.google.common.io.Files;
import com.monstarmike.tlmreader.datablock.AltitudeBlock;
import com.monstarmike.tlmreader.datablock.DataBlock;

public class AltitudeBlockTest {
	public static byte[] TEST_BLOCK = { (byte) 0xC3, (byte) 0x88, (byte) 0x00, (byte) 0x00, (byte) 0x12, (byte) 0x00,
			(byte) 0x07, (byte) 0xDF, (byte) 0x00, (byte) 0xF4, (byte) 0x00, (byte) 0xF4, (byte) 0x00, (byte) 0xF4,
			(byte) 0x00, (byte) 0xF4, (byte) 0x00, (byte) 0xF4, (byte) 0x00, (byte) 0xF4 };

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

	/**
	 * Use the static test block that we KNOW is altitude data to verify that
	 * the altitude block is created correctly.
	 */
	@Test
	public void testCreation() {
		DataBlock dataBlock = DataBlock.createDataBlock(TEST_BLOCK, null);
		assertTrue(dataBlock instanceof AltitudeBlock);
	}

	@Test
	public void testAltitudeValue() {
		AltitudeBlock block = (AltitudeBlock) DataBlock.createDataBlock(TEST_BLOCK, null);
		assertEquals(2015, block.getAltitudeInTenthOfMeter());
	}

	@Test
	public void testReadofAltitude() {
		int count = 0;
		for (int i = 0; i < this.theBytes.length; i += 20) {
			byte[] dataBytes = Arrays.copyOfRange(this.theBytes, i, i + 20);
			DataBlock block = DataBlock.createDataBlock(dataBytes, null);
			if (block instanceof AltitudeBlock) {
				count++;
			}
		}

		assertEquals(4751, count);
	}
}
