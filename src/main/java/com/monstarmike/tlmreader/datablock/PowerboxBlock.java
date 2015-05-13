package com.monstarmike.tlmreader.datablock;

import java.util.Arrays;

import com.monstarmike.tlmreader.util.PrimitiveUtils;

public class PowerboxBlock extends DataBlock {

	/*
	 * Address 0a: Powerbox. Very useful !
	 * 
	 * 0a,00,V1MSB,V1LSB,V2MSB,V2LSB,Cap1MSB,Cap1LSB,Cap2
	 * MSB,Cap2LSB,00,00,00,00,00,Alarm You can display 2 voltages and 2 used
	 * capacities. Voltage scale 1 unit is 0.01V, capacity 1 unit is 1mAh. Alarm
	 * has to be triggered by the sensor, it is transmitted to the radio with
	 * the last byte, the first 4 bits. The fist bit is alarm V1, the second V2,
	 * the third Capacity 1, the 4th capacity 2. This sensor is nearly perfect
	 * for a used capacity sensor, with the drawback that the alarm value must
	 * be set in the sensor . You can enable or disable an alarm for each value,
	 * but not set the value.
	 */

	Short voltageOne = null, voltageTwo = null, capacityOne = null,
			capacityTwo = null;

	public double get_voltageOne() {
		if (this.voltageOne == null) {
			this.voltageOne = new Short(PrimitiveUtils.toShort(Arrays
					.copyOfRange(this.rawData, 6, 8)));
		}
		return this.voltageOne * 0.01;
	}

	public double get_voltageTwo() {
		if (this.voltageTwo == null) {
			this.voltageTwo = new Short(PrimitiveUtils.toShort(Arrays
					.copyOfRange(this.rawData, 8, 10)));
		}
		return this.voltageTwo * 0.01;
	}

	public double get_capacityOne() {
		if (this.capacityOne == null) {
			this.capacityOne = new Short(PrimitiveUtils.toShort(Arrays
					.copyOfRange(this.rawData, 10, 12)));
		}
		return this.capacityOne;
	}

	public double get_capacityTwo() {
		if (this.capacityTwo == null) {
			this.capacityTwo = new Short(PrimitiveUtils.toShort(Arrays
					.copyOfRange(this.rawData, 12, 14)));
		}
		return this.capacityTwo;
	}

	public PowerboxBlock(byte[] rawData) {
		super(rawData);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "V1: " + this.get_voltageOne() + " Capacity 1: "
				+ this.get_capacityOne() + " - V2: " + this.get_voltageTwo()
				+ " Capacity 2: " + this.get_capacityTwo();
	}
}
