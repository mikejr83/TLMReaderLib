package com.monstarmike.tlmreader.datablock;

import java.util.Arrays;

import com.monstarmike.tlmreader.util.PrimitiveUtils;

public class CurrentBlock extends DataBlock {
	/*
	 * Address 0x03: Current. Use it ! This sensor came with DX8 2.05, DX18
	 * 1.01.
	 * 
	 * 03,00,MSB,LSB,00,00,00,00,00,00,00,00,00,00,00,00 MSB and LSB are a 16bit
	 * value, 1 unit is 0.1967A, seems to be some sensor related value. You can
	 * display 4 digits, I tried up to 0x2000 (displaying 1612A). Please beware
	 * that the maximum alarm value you can set in the radio is 200A.
	 */
	Short current = null;

	public double get_Current() {
		if (this.current == null) {
			this.current = new Short(PrimitiveUtils.toShort(Arrays.copyOfRange(
					this.rawData, 6, 8)));
		}
		
		return this.current * 0.1967;
	}

	public CurrentBlock(byte[] rawData) {
		super(rawData);
	}
}
