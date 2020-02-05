package com.monstarmike.tlmreader.datablock;

import com.google.common.primitives.Shorts;

public class CurrentBlock extends DataBlock {
	/*
	 * Address 0x03: Current. Use it ! This sensor came with DX8 2.05, DX18
	 * 1.01.
	 * 
	 * 03,00,MSB,LSB,00,00,00,00,00,00,00,00,00,00,00,00 MSB and LSB are a 16bit
	 * id, 1 unit is 0.1967A, seems to be some sensor related id. You can
	 * display 4 digits, I tried up to 0x2000 (displaying 1612A). Please beware
	 * that the maximum alarm id you can set in the radio is 200A.
	 */
	private short current;

	public CurrentBlock(byte[] rawData) {
		super(rawData);
		decode(rawData);
		measurementNames.add("Current C");
		measurementUnits.add("A");
		measurementFactors.add(0.1967);
	}

	@Override
	public boolean areValuesEquals(DataBlock block) {
		if (block instanceof CurrentBlock) {
			CurrentBlock current = (CurrentBlock) block;
			return current.current == this.current;
		}
		return false;
	}

	public short getCurrent() {
		return current;
	}

	private void decode(byte[] rawData) {
		current = Shorts.fromBytes(rawData[6], rawData[7]);
		measurementValues.add((int)getCurrent());
	}

}
