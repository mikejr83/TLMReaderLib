package com.monstarmike.tlmreader.datablock;

import com.google.common.primitives.Shorts;

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

	short voltageOne, voltageTwo, capacityOne, capacityTwo;

	public double get_voltageOne() {
		return (double) voltageOne * 0.01;
	}

	public double get_voltageTwo() {
		return (double) voltageTwo * 0.01;
	}

	public double get_capacityOne() {
		return capacityOne;
	}

	public double get_capacityTwo() {
		return capacityTwo;
	}

	public PowerboxBlock(byte[] rawData) {
		super(rawData);
		decode(rawData);
	}

	private void decode(byte[] rawData) {
		voltageOne = Shorts.fromBytes(rawData[6], rawData[7]);
		voltageTwo = Shorts.fromBytes(rawData[8], rawData[9]);
		capacityOne = Shorts.fromBytes(rawData[10], rawData[11]);
		capacityTwo = Shorts.fromBytes(rawData[12], rawData[13]);
	}

	@Override
	public String toString() {
		return super.toString() + " - V1: " + get_voltageOne() + " Capacity 1: " + get_capacityOne() + " - V2: "
				+ get_voltageTwo() + " Capacity 2: " + get_capacityTwo();
	}
}
