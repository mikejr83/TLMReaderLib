package com.monstarmike.tlmreader.datablock;

import static org.junit.Assert.*;

import org.junit.Test;

import com.monstarmike.tlmreader.DataBlockBuilder;

public class GpsStatusBlockTest {

	private static final short GPS_DATABLOCK_MAKER = (short) 0x1700;
	private static final byte[] GPS_BLOCK_DATA = new byte[] { (byte)0x11, (byte)0xD5, (byte)0x09, (byte)0x00, (byte)0x17, (byte)0x00, (byte)0x64, (byte)0x01, (byte)0x00, (byte)0x34, (byte)0x38, (byte)0x14, (byte)0x12, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00};

	@Test
	public void testGetAltitude() {
		byte[] testBlock = new DataBlockBuilder(0, GPS_DATABLOCK_MAKER).setValue(2, 12).get();
		GPSStatusBlock block = (GPSStatusBlock) DataBlock.createDataBlock(testBlock, null);
		assertEquals(2, block.getAltitudeHightInMeter() * block.getMeasurementFactors().get(3), 0);
	}

	@Test
	public void testToString() {
		GPSStatusBlock block = new GPSStatusBlock(GPS_BLOCK_DATA);
		assertEquals("GPSStatusData:    644369 - speedInKnots = 16.4, UTC = 14:38:34.00, numSats = 12, altitudeHightInMeter = 0", 
				block.toString().trim()
				);
	}
}
