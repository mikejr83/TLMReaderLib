package com.monstarmike.tlmreader.datablock;

public class HeaderDataBlock extends HeaderBlock {
	String sensorTypeEnabled = null;
	boolean terminatingBlock = false;

	public String get_sensorTypeEnabled() {
		if (this.sensorTypeEnabled == null) {
			if (this.rawData[0x4] != this.rawData[0x5]) {
				this.sensorTypeEnabled = "invalid information! 0x4 does not equal 0x5";
			} else {
				switch (this.rawData[0x4]) {
				case 0x1:
					this.sensorTypeEnabled = "Volts";
					break;

				case 0x2:
					this.sensorTypeEnabled = "Temperature";
					break;

				case 0x3:
					this.sensorTypeEnabled = "Amps";
					break;

				case 0x0A:
					this.sensorTypeEnabled = "PowerBox";
					break;

				case 0x11:
					this.sensorTypeEnabled = "Speed";
					break;

				case 0x12:
					this.sensorTypeEnabled = "Altimeter";
					break;

				case 0x14:
					this.sensorTypeEnabled = "G-Force";
					break;

				case 0x15:
					this.sensorTypeEnabled = "JetCat";
					break;

				case 0x16:
					this.sensorTypeEnabled = "GPS";
					break;

				case 0x17:
					this.sensorTypeEnabled = "Terminating Block";
					this.terminatingBlock = true;
					break;

				case 0x7E:
					this.sensorTypeEnabled = "RPM";
					break;
				}
			}
		}

		return this.sensorTypeEnabled;
	}
	
	public boolean isTerminatingBlock() {
		this.get_sensorTypeEnabled();
		return this.terminatingBlock;
	}

	public HeaderDataBlock(byte[] rawData) {
		super(rawData);
	}

	@Override
	public String toString() {
		return "DataHeader; type: " + get_sensorTypeEnabled();
	}

}
