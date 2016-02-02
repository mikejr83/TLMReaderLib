package com.monstarmike.tlmreader.datablock;

import com.google.common.primitives.Ints;

public abstract class DataBlock extends Block {

	public int get_timestamp() {
		return Ints.fromBytes(this.rawData[3], this.rawData[2], this.rawData[1], this.rawData[0]);
	}

	public DataBlock(byte[] rawData) {
		super(rawData);
	}

	public static DataBlock createDataBlock(byte[] bytes, HeaderRpmBlock rpmHeaderBlock) {
		DataBlock block = null;
		short tm1100 = (short) (bytes[4] & 0xFF);
		if (tm1100 == 0xFE || tm1100 == 0xFF) {
			switch (tm1100) {
			case 0xFE:
				block = new StandardBlock(bytes, rpmHeaderBlock);
				break;

			case 0xFF:
				block = new RXBlock(bytes);
				break;
			}
		} else if (bytes[4] == 0x09 && bytes[5] == 0x06) {
			block = new ServoDataBlock(bytes);
		} else {
			switch (bytes[4]) {
			case 0:
				// unknown
				break;

			case 0x01:
				// voltmeter - appears to be legacy.
				break;

			case 0x02:
				// temperature - appears to be legacy.
				break;

			case 0x03:
				// current (amperage) usage
				block = new CurrentBlock(bytes);
				break;

			case 0x0a:
				// powerbox
				block = new PowerboxBlock(bytes);
				break;

			case 0x11:
				// airspeed
				block = new AirspeedBlock(bytes);
				break;

			case 0x12:
				// altitude
				block = new AltitudeBlock(bytes);
				break;

			case 0x14:
				// gforce
				block = new GForceBlock(bytes);
				break;

			case 0x40:
				// vario
				block = new VarioBlock(bytes);
				break;

			case 0x7E:
				// rpm, temperature, rx volts
				block = new StandardBlock(bytes, rpmHeaderBlock);
				break;

			case 0x7F:
				// signal info
				block = new RXBlock(bytes);
				break;
			}
		}
		return block;
	}

	@Override
	public int getSize() {
		return 20;
	}
}
