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

	private short voltageOneInHunderthOfVolts;
	private short voltageTwoInHunderthOfVolts;
	private short capacityOneInmAh; 
	private short capacityTwoInmAh;

	public PowerboxBlock(byte[] rawData) {
		super(rawData);
		decode(rawData);
	}
	
	@Override
	public boolean areValuesEquals(DataBlock block) {
		if (block instanceof PowerboxBlock) {
			PowerboxBlock powerbox = (PowerboxBlock) block;
			if (powerbox.getVoltageOneInHunderthOfVolts() != voltageOneInHunderthOfVolts) {
				return false;
			}
			if (powerbox.getVoltageOneInHunderthOfVolts() != voltageTwoInHunderthOfVolts) {
				return false;
			}
			if (powerbox.getCapacityOneInmAh() != capacityOneInmAh) {
				return false;
			}
			if (powerbox.getCapacityTwoInmAh() != capacityTwoInmAh) {
				return false;
			}
			return true;
		}
		return false;
	}

	public short getVoltageOneInHunderthOfVolts() {
		return voltageOneInHunderthOfVolts;
	}

	public short getVoltageTwoInHunderthOfVolts() {
		return voltageTwoInHunderthOfVolts;
	}

	public short getCapacityOneInmAh() {
		return capacityOneInmAh;
	}

	public short getCapacityTwoInmAh() {
		return capacityTwoInmAh;
	}

	private void decode(byte[] rawData) {
		voltageOneInHunderthOfVolts = Shorts.fromBytes(rawData[6], rawData[7]);
		voltageTwoInHunderthOfVolts = Shorts.fromBytes(rawData[8], rawData[9]);
		capacityOneInmAh = Shorts.fromBytes(rawData[10], rawData[11]);
		capacityTwoInmAh = Shorts.fromBytes(rawData[12], rawData[13]);
	}

	@Override
	public String toString() {
		return super.toString() + " - V1: " + getVoltageOneInHunderthOfVolts() + " Capacity 1: " + getCapacityOneInmAh() + " - V2: "
				+ getVoltageTwoInHunderthOfVolts() + " Capacity 2: " + getCapacityTwoInmAh();
	}
}
