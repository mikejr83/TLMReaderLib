package com.monstarmike.tlmreader.datablock;

import static org.junit.Assert.*;

import org.junit.Test;

public class GpsLocationBlockTest {

	private static final byte[] GPS_BLOCK_DATA = new byte[] { (byte)0x0A, (byte)0xD5, (byte)0x09, (byte)0x00, (byte)0x16, (byte)0x00, (byte)0x01, (byte)0x04, (byte)0x76, (byte)0x28, (byte)0x38, (byte)0x48, (byte)0x04, (byte)0x95, (byte)0x58, (byte)0x08, (byte)0x49, (byte)0x32, (byte)0x05, (byte)0x3B};

	@Test
	public void testGetAltitude() {
		GPSLocationBlock block = new GPSLocationBlock(GPS_BLOCK_DATA);
		assertEquals(401*0.1 , block.getAltitudeLowInTenthOfMeter() * block.getMeasurementFactors().get(0), 0.01);
	}

	@Test
	public void testToString() {
		GPSLocationBlock block = new GPSLocationBlock(GPS_BLOCK_DATA);
		assertEquals("GPSLocationData:  644362 - Altitude low = 40.1, Latitude = 48.638126, Longitude = 8.982507, Course  = 324.9, HDOP = 0.5, GPSFix = 3, IS_NORTH = true, IS_EAST = true, LONGITUDE_GREATER_99 = false", 
				block.toString().trim()
				);
	}
}
