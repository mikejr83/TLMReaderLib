package com.monstarmike.tlmreader;

import com.google.common.primitives.Ints;

public final class HeaderBlockBuilder {
	private final byte[] dataBlock = new byte[36];

	public HeaderBlockBuilder(final int timestamp, final int blockMarker) {
		System.arraycopy(Ints.toByteArray(timestamp), 0, dataBlock, 0, 4);
		setValueAtPosition(dataBlock, blockMarker, 4);
	}

	public HeaderBlockBuilder setValue(int value, int position) {
		setValueAtPosition(dataBlock, value, position);
		return this;
	}

	public HeaderBlockBuilder setSwitchedEndianValue(short value, int position) {
		setValueAtPosition(dataBlock, switchEndianOfShort(value), position);
		return this;
	}

	private short switchEndianOfShort(short value) {
		return (short) (((value & 0xFF00) >> 8) | ((value & 0xFF) << 8));
	}
	public HeaderBlockBuilder setValue(String value, int position) {
		byte[] bytes = value.getBytes();
		int len = bytes.length > 24 ? 24 : bytes.length;
		System.arraycopy(bytes, 0, dataBlock, 0x0C, len);
		return this;
	}

	public byte[] get() {
		return dataBlock;
	}

	private void setValueAtPosition(byte[] dataBlock, int value, int position) {
		dataBlock[position] = (byte) ((value & 0xFF00) >> 8);
		dataBlock[position + 1] = (byte) (value & 0xFF);
	}

}
