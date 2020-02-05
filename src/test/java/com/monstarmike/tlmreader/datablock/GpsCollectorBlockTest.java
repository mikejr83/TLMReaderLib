package com.monstarmike.tlmreader.datablock;

import static org.junit.Assert.*;

import org.junit.Test;

public class GpsCollectorBlockTest {

	private static final byte[] LOC_BLOCK_DATA = new byte[] { (byte)0x0A, (byte)0xD5, (byte)0x09, (byte)0x00, (byte)0x16, (byte)0x00, (byte)0x01, (byte)0x04, (byte)0x76, (byte)0x28, (byte)0x38, (byte)0x48, (byte)0x04, (byte)0x95, (byte)0x58, (byte)0x08, (byte)0x49, (byte)0x32, (byte)0x05, (byte)0x3B};
	private static final byte[] STAT_BLOCK_DATA = new byte[] { (byte)0x11, (byte)0xD5, (byte)0x09, (byte)0x00, (byte)0x17, (byte)0x00, (byte)0x64, (byte)0x01, (byte)0x00, (byte)0x34, (byte)0x38, (byte)0x14, (byte)0x12, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00};


	@Test
	public void testToString() {
		GPSLocationBlock locBlock = new GPSLocationBlock(LOC_BLOCK_DATA);
		GPSCollectorBlock.getInstance().updateLocation(locBlock);
		GPSStatusBlock statBlock = new GPSStatusBlock(STAT_BLOCK_DATA);
		GPSCollectorBlock.getInstance().updateStatus(statBlock);
		assertEquals("GPSCollectorBlock 644369 - Altitude GPS = 40.1 m; Latitude = 48382876 ° '; Longitude = 8589504 ° '; Speed GPS = 30.4 km/h; Satellites GPS = 12 #; Course = 324.9 °; HDOP = 0.5 ; GPSFix = 3 ; UTC GPS = 14383400 ;", 
				GPSCollectorBlock.getInstance().toString().trim()
				);
	}
}
