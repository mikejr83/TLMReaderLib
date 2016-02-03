package com.monstarmike.tlmreader;

import com.google.common.primitives.Ints;

public final class DataBlockBuilder {
	private final byte[] dataBlock = new byte[20];

	public DataBlockBuilder(final int timestamp, final int blockMarker) {
		System.arraycopy(Ints.toByteArray(timestamp), 0, dataBlock, 0, 4);
		setValueAtPosition(dataBlock, blockMarker, 4);
	}

	public DataBlockBuilder setValue(int value, int position) {
		setValueAtPosition(dataBlock, value, position);
		return this;
	}

	public DataBlockBuilder setSwitchedEndianValue(short value, int position) {
		setValueAtPosition(dataBlock, switchEndianOfShort(value), position);
		return this;
	}

	private short switchEndianOfShort(short value) {
		return (short) (((value & 0xFF00) >> 8) | ((value & 0xFF) << 8));
	}

	public DataBlockBuilder setValueWithScale2(float value, int position) {
		short shortValue = (short) Math.round(value * 100);
		return setValue(shortValue, position);
	}

	public byte[] get() {
		return dataBlock;
	}

	private void setValueAtPosition(byte[] dataBlock, int value, int position) {
		dataBlock[position] = (byte) ((value & 0xFF00) >> 8);
		dataBlock[position + 1] = (byte) (value & 0xFF);
	}

}
