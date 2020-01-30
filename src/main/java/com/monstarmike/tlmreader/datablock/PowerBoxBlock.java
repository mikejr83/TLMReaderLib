package com.monstarmike.tlmreader.datablock;

import com.google.common.primitives.Shorts;

public class PowerBoxBlock extends DataBlock {

//	typedef struct
//	{
//		UINT8		identifier;														// Source device = 0x0A
//		UINT8		sID;																	// Secondary ID
//		UINT16	volt1;																// Volts, 0.01v
//		UINT16	volt2;																// Volts, 0.01v
//		UINT16	capacity1;														// mAh, 1mAh
//		UINT16	capacity2;														// mAh, 1mAh
//		UINT16	spare16_1;
//		UINT16	spare16_2;
//		UINT8		spare;
//		UINT8		alarms;																// Alarm bitmask (see below)
//	} STRU_TELE_POWERBOX;
//
	/*
	 * Address 0a: Powerbox. Very useful !
	 * 
	 * 0a,00,V1MSB,V1LSB,V2MSB,V2LSB,Cap1MSB,Cap1LSB,Cap2
	 * MSB,Cap2LSB,00,00,00,00,00,Alarm You can display 2 voltages and 2 used
	 * capacities. Voltage scale 1 unit is 0.01V, capacity 1 unit is 1mAh. Alarm
	 * has to be triggered by the sensor, it is transmitted to the radio with
	 * the last byte, the first 4 bits. The fist bit is alarm V1, the second V2,
	 * the third Capacity 1, the 4th capacity 2. This sensor is nearly perfect
	 * for a used capacity sensor, with the drawback that the alarm id must
	 * be set in the sensor . You can enable or disable an alarm for each id,
	 * but not set the id.
	 */

	//private static byte	TELE_PBOX_ALARM_VOLTAGE_1		=		0x01;
	//private static byte	TELE_PBOX_ALARM_VOLTAGE_2		=		0x02;
	//private static byte	TELE_PBOX_ALARM_CAPACITY_1 	=		0x04;
	//private static byte	TELE_PBOX_ALARM_CAPACITY_2 	=		0x08;
	//private static byte	TELE_PBOX_ALARM_RPM					=		0x10;
	//private static byte	TELE_PBOX_ALARM_TEMPERATURE =		0x20;
	//private static byte	TELE_PBOX_ALARM_RESERVED_1	=		0x40;
	//private static byte	TELE_PBOX_ALARM_RESERVED_2	=		0x80;

	private short voltageOneInHunderthOfVolts;
	private short voltageTwoInHunderthOfVolts;
	private short capacityOneInmAh; 
	private short capacityTwoInmAh;
	private byte	alarms;

	public PowerBoxBlock(byte[] rawData) {
		super(rawData);
		decode(rawData);

		measurementNames.add("Voltage PB1");
		measurementNames.add("Capacity PB1");
		measurementNames.add("Voltage PB2");
		measurementNames.add("Capacity PB2");
		measurementNames.add("Alarms PB");

		measurementUnits.add("V");
		measurementUnits.add("mAh");
		measurementUnits.add("V");
		measurementUnits.add("mAh");
		measurementUnits.add("");

		measurementFactors.add(0.01);
		measurementFactors.add(1.0);
		measurementFactors.add(0.01);
		measurementFactors.add(1.0);
		measurementFactors.add(1.0);
	}
	
	@Override
	public boolean areValuesEquals(DataBlock block) {
		if (block instanceof PowerBoxBlock) {
			PowerBoxBlock powerbox = (PowerBoxBlock) block;
			return powerbox.voltageOneInHunderthOfVolts == voltageOneInHunderthOfVolts
					&& powerbox.voltageTwoInHunderthOfVolts == voltageTwoInHunderthOfVolts
					&& powerbox.capacityOneInmAh == capacityOneInmAh
					&& powerbox.capacityTwoInmAh == capacityTwoInmAh
					&& powerbox.getAlarms() == (short)(alarms & 0xFF);
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

	public short getAlarms() {
		return (short) (alarms & 0xFF);
	}

	private void decode(byte[] rawData) {
		voltageOneInHunderthOfVolts = Shorts.fromBytes(rawData[6], rawData[7]);
		voltageTwoInHunderthOfVolts = Shorts.fromBytes(rawData[8], rawData[9]);
		capacityOneInmAh = Shorts.fromBytes(rawData[10], rawData[11]);
		capacityTwoInmAh = Shorts.fromBytes(rawData[12], rawData[13]);
		alarms = rawData[15];
		
		measurementValues.add((int)getVoltageOneInHunderthOfVolts());
		measurementValues.add((int)getCapacityOneInmAh());
		measurementValues.add((int)getVoltageTwoInHunderthOfVolts());
		measurementValues.add((int)getCapacityTwoInmAh());
		measurementValues.add((int)getAlarms());
	}

}
