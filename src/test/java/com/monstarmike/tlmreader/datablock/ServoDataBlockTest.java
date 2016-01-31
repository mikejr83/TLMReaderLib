package com.monstarmike.tlmreader.datablock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

public class ServoDataBlockTest {

	private static final int CHANNEL_1 = 0;
	private static final int VALUE_CHANNEL_1 = 1347;
	private static final int CHANNEL_2 = 1;
	private static final int VALUE_CHANNEL_2 = 478;
	private static final int CHANNEL_3 = 2;
	private static final int VALUE_CHANNEL_3 = 137;
	private static final int CHANNEL_4 = 3;
	private static final int VALUE_CHANNEL_4 = 1567;
	private static final int CHANNEL_5 = 4;
	private static final int VALUE_CHANNEL_5 = 346;
	private static final int CHANNEL_6 = 5;
	private static final int VALUE_CHANNEL_6 = 1789;
	private static final int CHANNEL_7 = 6;
	private static final int VALUE_CHANNEL_7 = 1379;
	private static final int CHANNEL_8 = 7;
	private static final int VALUE_CHANNEL_8 = 1963;
	private static final int CHANNEL_9 = 8;
	private static final int VALUE_CHANNEL_9 = 2032;
	private static final int CHANNEL_10 = 9;
	private static final int VALUE_CHANNEL_10 = 766;
	private static final int CHANNEL_11 = 10;
	private static final int VALUE_CHANNEL_11 = 1333;
	private static final int CHANNEL_12 = 11;
	private static final int VALUE_CHANNEL_12 = 1333;
	private static final int CHANNEL_13 = 12;
	private static final int VALUE_CHANNEL_13 = 381;
	private static final int CHANNEL_14 = 13;
	private static final int VALUE_CHANNEL_14 = 198;
	private static final int CHANNEL_15 = 14;
	private static final int VALUE_CHANNEL_15 = 334;
	private static final int CHANNEL_16 = 15;
	private static final int VALUE_CHANNEL_16 = 487;
	private static final int CHANNEL_17 = 16;
	private static final int VALUE_CHANNEL_17 = 369;
	private static final int CHANNEL_18 = 17;
	private static final int VALUE_CHANNEL_18 = 256;
	private static final int CHANNEL_19 = 18;
	private static final int VALUE_CHANNEL_19 = 352;
	private static final int CHANNEL_20 = 19;
	private static final int VALUE_CHANNEL_20 = 455;

	private static final int POSITION_1_IN_DATABLOCK = 0;
	private static final int POSITION_2_IN_DATABLOCK = 1;
	private static final int POSITION_3_IN_DATABLOCK = 2;
	private static final int POSITION_4_IN_DATABLOCK = 3;
	private static final int POSITION_5_IN_DATABLOCK = 4;
	private static final int POSITION_6_IN_DATABLOCK = 5;
	private static final int POSITION_7_IN_DATABLOCK = 6;

	private ServoDataBlock servoDataBlock1With13;
	private ServoDataBlock servoDataBlock1With14;
	private ServoDataBlock servoDataBlock1With15;
	private ServoDataBlock servoDataBlock1With16;
	private ServoDataBlock servoDataBlock2With17;
	private ServoDataBlock servoDataBlock2With18;
	private ServoDataBlock servoDataBlock2With19;
	private ServoDataBlock servoDataBlock2With20;

	@Before
	public void setup() {

		// ServoDataBlock with channels: 2, 3, 5, 6, 7, 11, 13|14|15|16
		servoDataBlock1With13 = (ServoDataBlock) DataBlock
				.createDataBlock(createServoTestBlock1(CHANNEL_13, VALUE_CHANNEL_13), null);
		servoDataBlock1With14 = (ServoDataBlock) DataBlock
				.createDataBlock(createServoTestBlock1(CHANNEL_14, VALUE_CHANNEL_14), null);
		servoDataBlock1With15 = (ServoDataBlock) DataBlock
				.createDataBlock(createServoTestBlock1(CHANNEL_15, VALUE_CHANNEL_15), null);
		servoDataBlock1With16 = (ServoDataBlock) DataBlock
				.createDataBlock(createServoTestBlock1(CHANNEL_16, VALUE_CHANNEL_16), null);

		// ServoDataBlock with channels: 1, 8, 4, 9, 10, 12, 17|18|19|20
		servoDataBlock2With17 = (ServoDataBlock) DataBlock
				.createDataBlock(createServoTestBlock2(CHANNEL_17, VALUE_CHANNEL_17), null);
		servoDataBlock2With18 = (ServoDataBlock) DataBlock
				.createDataBlock(createServoTestBlock2(CHANNEL_18, VALUE_CHANNEL_18), null);
		servoDataBlock2With19 = (ServoDataBlock) DataBlock
				.createDataBlock(createServoTestBlock2(CHANNEL_19, VALUE_CHANNEL_19), null);
		servoDataBlock2With20 = (ServoDataBlock) DataBlock
				.createDataBlock(createServoTestBlock2(CHANNEL_20, VALUE_CHANNEL_20), null);
	}

	@Test
	public void testChannel1() {
		assertFalse(servoDataBlock1With13.hasChannel(CHANNEL_1));
		assertFalse(servoDataBlock1With14.hasChannel(CHANNEL_1));
		assertFalse(servoDataBlock1With15.hasChannel(CHANNEL_1));
		assertFalse(servoDataBlock1With16.hasChannel(CHANNEL_1));
		assertEquals(VALUE_CHANNEL_1, servoDataBlock2With17.getChannelValue(CHANNEL_1));
		assertEquals(VALUE_CHANNEL_1, servoDataBlock2With18.getChannelValue(CHANNEL_1));
		assertEquals(VALUE_CHANNEL_1, servoDataBlock2With19.getChannelValue(CHANNEL_1));
		assertEquals(VALUE_CHANNEL_1, servoDataBlock2With20.getChannelValue(CHANNEL_1));
	}

	@Test
	public void testChannel2() {
		assertEquals(VALUE_CHANNEL_2, servoDataBlock1With13.getChannelValue(CHANNEL_2));
		assertEquals(VALUE_CHANNEL_2, servoDataBlock1With14.getChannelValue(CHANNEL_2));
		assertEquals(VALUE_CHANNEL_2, servoDataBlock1With15.getChannelValue(CHANNEL_2));
		assertEquals(VALUE_CHANNEL_2, servoDataBlock1With16.getChannelValue(CHANNEL_2));
		assertFalse(servoDataBlock2With17.hasChannel(CHANNEL_2));
		assertFalse(servoDataBlock2With18.hasChannel(CHANNEL_2));
		assertFalse(servoDataBlock2With19.hasChannel(CHANNEL_2));
		assertFalse(servoDataBlock2With20.hasChannel(CHANNEL_2));
	}

	@Test
	public void testChannel3() {
		assertEquals(VALUE_CHANNEL_3, servoDataBlock1With13.getChannelValue(CHANNEL_3));
		assertEquals(VALUE_CHANNEL_3, servoDataBlock1With14.getChannelValue(CHANNEL_3));
		assertEquals(VALUE_CHANNEL_3, servoDataBlock1With15.getChannelValue(CHANNEL_3));
		assertEquals(VALUE_CHANNEL_3, servoDataBlock1With16.getChannelValue(CHANNEL_3));
		assertFalse(servoDataBlock2With17.hasChannel(CHANNEL_3));
		assertFalse(servoDataBlock2With18.hasChannel(CHANNEL_3));
		assertFalse(servoDataBlock2With19.hasChannel(CHANNEL_3));
		assertFalse(servoDataBlock2With20.hasChannel(CHANNEL_3));
	}

	@Test
	public void testChannel4() {
		assertFalse(servoDataBlock1With13.hasChannel(CHANNEL_4));
		assertFalse(servoDataBlock1With14.hasChannel(CHANNEL_4));
		assertFalse(servoDataBlock1With15.hasChannel(CHANNEL_4));
		assertFalse(servoDataBlock1With16.hasChannel(CHANNEL_4));
		assertEquals(VALUE_CHANNEL_4, servoDataBlock2With17.getChannelValue(CHANNEL_4));
		assertEquals(VALUE_CHANNEL_4, servoDataBlock2With18.getChannelValue(CHANNEL_4));
		assertEquals(VALUE_CHANNEL_4, servoDataBlock2With19.getChannelValue(CHANNEL_4));
		assertEquals(VALUE_CHANNEL_4, servoDataBlock2With20.getChannelValue(CHANNEL_4));
	}

	@Test
	public void testChannel5() {
		assertEquals(VALUE_CHANNEL_5, servoDataBlock1With13.getChannelValue(CHANNEL_5));
		assertEquals(VALUE_CHANNEL_5, servoDataBlock1With14.getChannelValue(CHANNEL_5));
		assertEquals(VALUE_CHANNEL_5, servoDataBlock1With15.getChannelValue(CHANNEL_5));
		assertEquals(VALUE_CHANNEL_5, servoDataBlock1With16.getChannelValue(CHANNEL_5));
		assertFalse(servoDataBlock2With17.hasChannel(CHANNEL_5));
		assertFalse(servoDataBlock2With18.hasChannel(CHANNEL_5));
		assertFalse(servoDataBlock2With19.hasChannel(CHANNEL_5));
		assertFalse(servoDataBlock2With20.hasChannel(CHANNEL_5));
	}

	@Test
	public void testChannel6() {
		assertEquals(VALUE_CHANNEL_6, servoDataBlock1With13.getChannelValue(CHANNEL_6));
		assertEquals(VALUE_CHANNEL_6, servoDataBlock1With14.getChannelValue(CHANNEL_6));
		assertEquals(VALUE_CHANNEL_6, servoDataBlock1With15.getChannelValue(CHANNEL_6));
		assertEquals(VALUE_CHANNEL_6, servoDataBlock1With16.getChannelValue(CHANNEL_6));
		assertFalse(servoDataBlock2With17.hasChannel(CHANNEL_6));
		assertFalse(servoDataBlock2With18.hasChannel(CHANNEL_6));
		assertFalse(servoDataBlock2With19.hasChannel(CHANNEL_6));
		assertFalse(servoDataBlock2With20.hasChannel(CHANNEL_6));
	}

	@Test
	public void testChannel7() {
		assertEquals(VALUE_CHANNEL_7, servoDataBlock1With13.getChannelValue(CHANNEL_7));
		assertEquals(VALUE_CHANNEL_7, servoDataBlock1With14.getChannelValue(CHANNEL_7));
		assertEquals(VALUE_CHANNEL_7, servoDataBlock1With15.getChannelValue(CHANNEL_7));
		assertEquals(VALUE_CHANNEL_7, servoDataBlock1With16.getChannelValue(CHANNEL_7));
		assertFalse(servoDataBlock2With17.hasChannel(CHANNEL_7));
		assertFalse(servoDataBlock2With18.hasChannel(CHANNEL_7));
		assertFalse(servoDataBlock2With19.hasChannel(CHANNEL_7));
		assertFalse(servoDataBlock2With20.hasChannel(CHANNEL_7));
	}

	@Test
	public void testChannel8() {
		assertFalse(servoDataBlock1With13.hasChannel(CHANNEL_8));
		assertFalse(servoDataBlock1With14.hasChannel(CHANNEL_8));
		assertFalse(servoDataBlock1With15.hasChannel(CHANNEL_8));
		assertFalse(servoDataBlock1With16.hasChannel(CHANNEL_8));
		assertEquals(VALUE_CHANNEL_8, servoDataBlock2With17.getChannelValue(CHANNEL_8));
		assertEquals(VALUE_CHANNEL_8, servoDataBlock2With18.getChannelValue(CHANNEL_8));
		assertEquals(VALUE_CHANNEL_8, servoDataBlock2With19.getChannelValue(CHANNEL_8));
		assertEquals(VALUE_CHANNEL_8, servoDataBlock2With20.getChannelValue(CHANNEL_8));
	}

	@Test
	public void testChannel9() {
		assertFalse(servoDataBlock1With13.hasChannel(CHANNEL_9));
		assertFalse(servoDataBlock1With14.hasChannel(CHANNEL_9));
		assertFalse(servoDataBlock1With15.hasChannel(CHANNEL_9));
		assertFalse(servoDataBlock1With16.hasChannel(CHANNEL_9));
		assertEquals(VALUE_CHANNEL_9, servoDataBlock2With17.getChannelValue(CHANNEL_9));
		assertEquals(VALUE_CHANNEL_9, servoDataBlock2With18.getChannelValue(CHANNEL_9));
		assertEquals(VALUE_CHANNEL_9, servoDataBlock2With19.getChannelValue(CHANNEL_9));
		assertEquals(VALUE_CHANNEL_9, servoDataBlock2With20.getChannelValue(CHANNEL_9));
	}

	@Test
	public void testChannel10() {
		assertFalse(servoDataBlock1With13.hasChannel(CHANNEL_10));
		assertFalse(servoDataBlock1With14.hasChannel(CHANNEL_10));
		assertFalse(servoDataBlock1With15.hasChannel(CHANNEL_10));
		assertFalse(servoDataBlock1With16.hasChannel(CHANNEL_10));
		assertEquals(VALUE_CHANNEL_10, servoDataBlock2With17.getChannelValue(CHANNEL_10));
		assertEquals(VALUE_CHANNEL_10, servoDataBlock2With18.getChannelValue(CHANNEL_10));
		assertEquals(VALUE_CHANNEL_10, servoDataBlock2With19.getChannelValue(CHANNEL_10));
		assertEquals(VALUE_CHANNEL_10, servoDataBlock2With20.getChannelValue(CHANNEL_10));
	}

	@Test
	public void testChannel11() {
		assertEquals(VALUE_CHANNEL_11, servoDataBlock1With13.getChannelValue(CHANNEL_11));
		assertEquals(VALUE_CHANNEL_11, servoDataBlock1With14.getChannelValue(CHANNEL_11));
		assertEquals(VALUE_CHANNEL_11, servoDataBlock1With15.getChannelValue(CHANNEL_11));
		assertEquals(VALUE_CHANNEL_11, servoDataBlock1With16.getChannelValue(CHANNEL_11));
		assertFalse(servoDataBlock2With17.hasChannel(CHANNEL_11));
		assertFalse(servoDataBlock2With18.hasChannel(CHANNEL_11));
		assertFalse(servoDataBlock2With19.hasChannel(CHANNEL_11));
		assertFalse(servoDataBlock2With20.hasChannel(CHANNEL_11));
	}

	@Test
	public void testChannel12() {
		assertFalse(servoDataBlock1With13.hasChannel(CHANNEL_12));
		assertFalse(servoDataBlock1With14.hasChannel(CHANNEL_12));
		assertFalse(servoDataBlock1With15.hasChannel(CHANNEL_12));
		assertFalse(servoDataBlock1With16.hasChannel(CHANNEL_12));
		assertEquals(VALUE_CHANNEL_12, servoDataBlock2With17.getChannelValue(CHANNEL_12));
		assertEquals(VALUE_CHANNEL_12, servoDataBlock2With18.getChannelValue(CHANNEL_12));
		assertEquals(VALUE_CHANNEL_12, servoDataBlock2With19.getChannelValue(CHANNEL_12));
		assertEquals(VALUE_CHANNEL_12, servoDataBlock2With20.getChannelValue(CHANNEL_12));
	}

	@Test
	public void testChannel13() {
		assertEquals(VALUE_CHANNEL_13, servoDataBlock1With13.getChannelValue(CHANNEL_13));
		assertFalse(servoDataBlock1With14.hasChannel(CHANNEL_13));
		assertFalse(servoDataBlock1With15.hasChannel(CHANNEL_13));
		assertFalse(servoDataBlock1With16.hasChannel(CHANNEL_13));
		assertFalse(servoDataBlock2With17.hasChannel(CHANNEL_13));
		assertFalse(servoDataBlock2With18.hasChannel(CHANNEL_13));
		assertFalse(servoDataBlock2With19.hasChannel(CHANNEL_13));
		assertFalse(servoDataBlock2With20.hasChannel(CHANNEL_13));
	}

	@Test
	public void testChannel14() {
		assertFalse(servoDataBlock1With13.hasChannel(CHANNEL_14));
		assertEquals(VALUE_CHANNEL_14, servoDataBlock1With14.getChannelValue(CHANNEL_14));
		assertFalse(servoDataBlock1With15.hasChannel(CHANNEL_14));
		assertFalse(servoDataBlock1With16.hasChannel(CHANNEL_14));
		assertFalse(servoDataBlock2With17.hasChannel(CHANNEL_14));
		assertFalse(servoDataBlock2With18.hasChannel(CHANNEL_14));
		assertFalse(servoDataBlock2With19.hasChannel(CHANNEL_14));
		assertFalse(servoDataBlock2With20.hasChannel(CHANNEL_14));
	}

	@Test
	public void testChannel15() {
		assertFalse(servoDataBlock1With13.hasChannel(CHANNEL_15));
		assertFalse(servoDataBlock1With14.hasChannel(CHANNEL_15));
		assertEquals(VALUE_CHANNEL_15, servoDataBlock1With15.getChannelValue(CHANNEL_15));
		assertFalse(servoDataBlock1With16.hasChannel(CHANNEL_15));
		assertFalse(servoDataBlock2With17.hasChannel(CHANNEL_15));
		assertFalse(servoDataBlock2With18.hasChannel(CHANNEL_15));
		assertFalse(servoDataBlock2With19.hasChannel(CHANNEL_15));
		assertFalse(servoDataBlock2With20.hasChannel(CHANNEL_15));
	}

	@Test
	public void testChannel16() {
		assertFalse(servoDataBlock1With13.hasChannel(CHANNEL_16));
		assertFalse(servoDataBlock1With14.hasChannel(CHANNEL_16));
		assertFalse(servoDataBlock1With15.hasChannel(CHANNEL_16));
		assertEquals(VALUE_CHANNEL_16, servoDataBlock1With16.getChannelValue(CHANNEL_16));
		assertFalse(servoDataBlock2With17.hasChannel(CHANNEL_16));
		assertFalse(servoDataBlock2With18.hasChannel(CHANNEL_16));
		assertFalse(servoDataBlock2With19.hasChannel(CHANNEL_16));
		assertFalse(servoDataBlock2With20.hasChannel(CHANNEL_16));
	}

	@Test
	public void testChannel17() {
		assertFalse(servoDataBlock1With13.hasChannel(CHANNEL_17));
		assertFalse(servoDataBlock1With14.hasChannel(CHANNEL_17));
		assertFalse(servoDataBlock1With15.hasChannel(CHANNEL_17));
		assertFalse(servoDataBlock1With16.hasChannel(CHANNEL_17));
		assertEquals(VALUE_CHANNEL_17, servoDataBlock2With17.getChannelValue(CHANNEL_17));
		assertFalse(servoDataBlock2With18.hasChannel(CHANNEL_17));
		assertFalse(servoDataBlock2With19.hasChannel(CHANNEL_17));
		assertFalse(servoDataBlock2With20.hasChannel(CHANNEL_17));
	}

	@Test
	public void testChannel18() {
		assertFalse(servoDataBlock1With13.hasChannel(CHANNEL_18));
		assertFalse(servoDataBlock1With14.hasChannel(CHANNEL_18));
		assertFalse(servoDataBlock1With15.hasChannel(CHANNEL_18));
		assertFalse(servoDataBlock1With16.hasChannel(CHANNEL_18));
		assertFalse(servoDataBlock2With17.hasChannel(CHANNEL_18));
		assertEquals(VALUE_CHANNEL_18, servoDataBlock2With18.getChannelValue(CHANNEL_18));
		assertFalse(servoDataBlock2With19.hasChannel(CHANNEL_18));
		assertFalse(servoDataBlock2With20.hasChannel(CHANNEL_18));
	}

	@Test
	public void testChannel19() {
		assertFalse(servoDataBlock1With13.hasChannel(CHANNEL_19));
		assertFalse(servoDataBlock1With14.hasChannel(CHANNEL_19));
		assertFalse(servoDataBlock1With15.hasChannel(CHANNEL_19));
		assertFalse(servoDataBlock1With16.hasChannel(CHANNEL_19));
		assertFalse(servoDataBlock2With17.hasChannel(CHANNEL_19));
		assertFalse(servoDataBlock2With18.hasChannel(CHANNEL_19));
		assertEquals(VALUE_CHANNEL_19, servoDataBlock2With19.getChannelValue(CHANNEL_19));
		assertFalse(servoDataBlock2With20.hasChannel(CHANNEL_19));
	}

	@Test
	public void testChannel20() {
		assertFalse(servoDataBlock1With13.hasChannel(CHANNEL_20));
		assertFalse(servoDataBlock1With14.hasChannel(CHANNEL_20));
		assertFalse(servoDataBlock1With15.hasChannel(CHANNEL_20));
		assertFalse(servoDataBlock1With16.hasChannel(CHANNEL_20));
		assertFalse(servoDataBlock2With17.hasChannel(CHANNEL_20));
		assertFalse(servoDataBlock2With18.hasChannel(CHANNEL_20));
		assertFalse(servoDataBlock2With19.hasChannel(CHANNEL_20));
		assertEquals(VALUE_CHANNEL_20, servoDataBlock2With20.getChannelValue(CHANNEL_20));
	}

	private byte[] createServoTestBlock1(int channelNumberOf9BitChannel, int channelValue) {
		byte[] testBlock = new byte[20];
		testBlock[4] = (byte) 0x09; // marker for servoDataBlock
		testBlock[5] = (byte) 0x06; // marker for servoDataBlock
		set11BitChannelValueInDataBlock(testBlock, CHANNEL_2, (short) VALUE_CHANNEL_2, POSITION_1_IN_DATABLOCK);
		set11BitChannelValueInDataBlock(testBlock, CHANNEL_3, (short) VALUE_CHANNEL_3, POSITION_2_IN_DATABLOCK);
		set11BitChannelValueInDataBlock(testBlock, CHANNEL_5, (short) VALUE_CHANNEL_5, POSITION_3_IN_DATABLOCK);
		set11BitChannelValueInDataBlock(testBlock, CHANNEL_6, (short) VALUE_CHANNEL_6, POSITION_4_IN_DATABLOCK);
		set11BitChannelValueInDataBlock(testBlock, CHANNEL_7, (short) VALUE_CHANNEL_7, POSITION_5_IN_DATABLOCK);
		set11BitChannelValueInDataBlock(testBlock, CHANNEL_11, (short) VALUE_CHANNEL_11, POSITION_6_IN_DATABLOCK);
		set9BitChannelValueInDataBlock(testBlock, channelNumberOf9BitChannel, (short) channelValue,
				POSITION_7_IN_DATABLOCK);
		return testBlock;
	}

	private byte[] createServoTestBlock2(int channelNumberOf9BitChannel, int channelValue) {
		byte[] testBlock = new byte[20];
		testBlock[4] = (byte) 0x09; // marker for servoDataBlock
		testBlock[5] = (byte) 0x06; // marker for servoDataBlock
		set11BitChannelValueInDataBlock(testBlock, CHANNEL_1, (short) VALUE_CHANNEL_1, POSITION_1_IN_DATABLOCK);
		set11BitChannelValueInDataBlock(testBlock, CHANNEL_8, (short) VALUE_CHANNEL_8, POSITION_2_IN_DATABLOCK);
		set11BitChannelValueInDataBlock(testBlock, CHANNEL_4, (short) VALUE_CHANNEL_4, POSITION_3_IN_DATABLOCK);
		set11BitChannelValueInDataBlock(testBlock, CHANNEL_9, (short) VALUE_CHANNEL_9, POSITION_4_IN_DATABLOCK);
		set11BitChannelValueInDataBlock(testBlock, CHANNEL_10, (short) VALUE_CHANNEL_10, POSITION_5_IN_DATABLOCK);
		set11BitChannelValueInDataBlock(testBlock, CHANNEL_12, (short) VALUE_CHANNEL_12, POSITION_6_IN_DATABLOCK);
		set9BitChannelValueInDataBlock(testBlock, channelNumberOf9BitChannel, (short) channelValue,
				POSITION_7_IN_DATABLOCK);
		return testBlock;
	}

	private void set11BitChannelValueInDataBlock(byte[] dataBlock, int channelNumber, short channelValue,
			int channelPositionInDataBlock) {
		short data = (short) (setChannelNumber(channelNumber) | set11BitChannelValue(channelValue));
		dataBlock[4 + 2 + channelPositionInDataBlock * 2] = (byte) ((data & 0xFF00) >> 8);
		dataBlock[4 + 2 + channelPositionInDataBlock * 2 + 1] = (byte) (data & 0xFF);
	}

	private void set9BitChannelValueInDataBlock(byte[] dataBlock, int channelNumber, short channelValue,
			int channelPositionInDataBlock) {
		short data = (short) (setChannelNumber(12) | setSubChannelNumber(channelNumber - 12)
				| set9BitChannelValue(channelValue));
		short subChannelGroupBit = setSubChannelGroup(channelNumber / 16);
		// only set, if the channelNumber is 16 or higher
		dataBlock[4 + 2] |= (byte) ((subChannelGroupBit & 0xFF00) >> 8);
		dataBlock[4 + 2 + channelPositionInDataBlock * 2] = (byte) ((data & 0xFF00) >> 8);
		dataBlock[4 + 2 + channelPositionInDataBlock * 2 + 1] = (byte) (data & 0xFF);
	}

	private short setChannelNumber(int channelNumber) {
		// set the channelNumber from bit 11 to 14
		return (short) ((channelNumber & 0x0F) << 11);
	}

	private short setSubChannelNumber(int subChannelNumber) {
		// set the subChannelNumber from bit 9 to 10
		return (short) ((subChannelNumber & 0x03) << 9);
	}

	private short setSubChannelGroup(int subChannelGroup) {
		// set the subChannelGroup at bit 15
		// if set, I exptected that we need to add 4 to the channelNumber of the
		// 9-Bit Channel (Last 2 Bytes in the DataBlock)
		return (short) ((subChannelGroup & 0x01) << 15);
	}

	private short set11BitChannelValue(int channelValue) {
		// set the channelNumber from bit 0 to 10
		return (short) (channelValue & 0x07FF);
	}

	private short set9BitChannelValue(int channelValue) {
		// set the channelNumber from bit 0 to 8
		return (short) (channelValue & 0x01FF);
	}
}
