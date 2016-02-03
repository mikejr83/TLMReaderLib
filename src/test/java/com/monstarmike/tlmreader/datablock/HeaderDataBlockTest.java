package com.monstarmike.tlmreader.datablock;

import static org.junit.Assert.*;

import org.junit.Test;

import com.monstarmike.tlmreader.HeaderBlockBuilder;

public class HeaderDataBlockTest {
	private static final short VOLT_HEADER_MAKER = (short) 0x0101;
	private static final short TEMPERATURE_HEADER_MAKER = (short) 0x0202;
	private static final short AMPS_HEADER_MAKER = (short) 0x0303;
	private static final short POWERBOX_HEADER_MAKER = (short) 0x0A0A;
	private static final short SPEED_HEADER_MAKER = (short) 0x1111;
	private static final short ALTIMETER_HEADER_MAKER = (short) 0x1212;
	private static final short GFORCE_HEADER_MAKER = (short) 0x1414;
	private static final short JETCAT_HEADER_MAKER = (short) 0x1515;
	private static final short GPS_HEADER_MAKER = (short) 0x1616;
	private static final short TERMINATING_BLOCK_HEADER_MAKER = (short) 0x1717;
	private static final short UNKNOWN_HEADER_MAKER = (short) 0x5555;

	@Test
	public void testGetSensorTypeEnabledInvalidMarker() {
		byte[] testBlock = new HeaderBlockBuilder(0, 0x0102).get();
		HeaderDataBlock headerBlock = new HeaderDataBlock(testBlock);
		assertEquals("invalid information! 0x4 does not equal 0x5", headerBlock.get_sensorTypeEnabled());
	}

	@Test
	public void testGetSensorTypeEnabledVolt() {
		byte[] testBlock = new HeaderBlockBuilder(0, VOLT_HEADER_MAKER).get();
		HeaderDataBlock headerBlock = new HeaderDataBlock(testBlock);
		assertEquals("Volts", headerBlock.get_sensorTypeEnabled());
	}

	@Test
	public void testGetSensorTypeEnabledTemperature() {
		byte[] testBlock = new HeaderBlockBuilder(0, TEMPERATURE_HEADER_MAKER).get();
		HeaderDataBlock headerBlock = new HeaderDataBlock(testBlock);
		assertEquals("Temperature", headerBlock.get_sensorTypeEnabled());
	}

	@Test
	public void testGetSensorTypeEnabledAmps() {
		byte[] testBlock = new HeaderBlockBuilder(0, AMPS_HEADER_MAKER).get();
		HeaderDataBlock headerBlock = new HeaderDataBlock(testBlock);
		assertEquals("Amps", headerBlock.get_sensorTypeEnabled());
	}

	@Test
	public void testGetSensorTypeEnabledPowerBox() {
		byte[] testBlock = new HeaderBlockBuilder(0, POWERBOX_HEADER_MAKER).get();
		HeaderDataBlock headerBlock = new HeaderDataBlock(testBlock);
		assertEquals("PowerBox", headerBlock.get_sensorTypeEnabled());
	}

	@Test
	public void testGetSensorTypeEnabledSpeed() {
		byte[] testBlock = new HeaderBlockBuilder(0, SPEED_HEADER_MAKER).get();
		HeaderDataBlock headerBlock = new HeaderDataBlock(testBlock);
		assertEquals("Speed", headerBlock.get_sensorTypeEnabled());
	}

	@Test
	public void testGetSensorTypeEnabledAltimeter() {
		byte[] testBlock = new HeaderBlockBuilder(0, ALTIMETER_HEADER_MAKER).get();
		HeaderDataBlock headerBlock = new HeaderDataBlock(testBlock);
		assertEquals("Altimeter", headerBlock.get_sensorTypeEnabled());
	}

	@Test
	public void testGetSensorTypeEnabledGForce() {
		byte[] testBlock = new HeaderBlockBuilder(0, GFORCE_HEADER_MAKER).get();
		HeaderDataBlock headerBlock = new HeaderDataBlock(testBlock);
		assertEquals("G-Force", headerBlock.get_sensorTypeEnabled());
	}

	@Test
	public void testGetSensorTypeEnabledJetCat() {
		byte[] testBlock = new HeaderBlockBuilder(0, JETCAT_HEADER_MAKER).get();
		HeaderDataBlock headerBlock = new HeaderDataBlock(testBlock);
		assertEquals("JetCat", headerBlock.get_sensorTypeEnabled());
	}

	@Test
	public void testGetSensorTypeEnabledGPS() {
		byte[] testBlock = new HeaderBlockBuilder(0, GPS_HEADER_MAKER).get();
		HeaderDataBlock headerBlock = new HeaderDataBlock(testBlock);
		assertEquals("GPS", headerBlock.get_sensorTypeEnabled());
		assertFalse(headerBlock.isTerminatingBlock());
	}

	@Test
	public void testGetSensorTypeEnabledTerminatingBlock() {
		byte[] testBlock = new HeaderBlockBuilder(0, TERMINATING_BLOCK_HEADER_MAKER).get();
		HeaderDataBlock headerBlock = new HeaderDataBlock(testBlock);
		assertEquals("Terminating Block", headerBlock.get_sensorTypeEnabled());
		assertTrue(headerBlock.isTerminatingBlock());
	}

	@Test
	public void testGetSensorTypeEnabledUnknownHeaderMarker() {
		byte[] testBlock = new HeaderBlockBuilder(0, UNKNOWN_HEADER_MAKER).get();
		HeaderDataBlock headerBlock = new HeaderDataBlock(testBlock);
		assertEquals("Unknown Header Block", headerBlock.get_sensorTypeEnabled());
		assertFalse(headerBlock.isTerminatingBlock());
	}

}
