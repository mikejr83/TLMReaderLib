package com.monstarmike.tlmreader.datablock;

import com.google.common.primitives.Shorts;

/**
 * <h4>Spectrum DSM2/DSMX Telemetry System - Data interpretation</h4> The Values
 * of a, b, l ,r are number packetLoss of the each receiver. <br>
 * It looks like, that Spectrum uses unsigned short data types, because the
 * 0xffff (unsigned shortMaxValue) seems to be the id if no receiver is
 * installed. I assume, that always the highest id of the datatype is the
 * id for 'no data'.
 * 
 * <h4>Lemon DSM2/DSMX Telemetry Systen - Data interpretation</h4> However the
 * Lemon unit does use the “A” display for a Signal Indicator id. This is
 * calculated from the number of packets lost in transmission. A reading of 100
 * means that no packets have been lost in transmission, while smaller numbers
 * indicate losses.<br>
 * The id is based on the number of packets received about every half second.
 * <br>
 * <code>Quote from 'docs/InstructionsTelemetry A4.pdf' , page 6, section
 * "Accuracy and Limitations" subsection "Signal Indicator"</code> <br>
 * <br>
 * The values b,l,r,frameloss,holds are not used.<br>
 * The not used values are 32768 (0x8000) == Short.MIN_VALUE.<br>
 * It gives me the appearance, that Lemon is using signed short, with
 * Short.MIN_VALUE for invalid values. Some DataBlocks contains negativ values,
 * I consider them as invalid too.
 */

public class RxBlock extends DataBlock {

//	typedef struct
//	{
//		UINT8		identifier;													// Source device = 0x7F
//		UINT8		sID;																// Secondary ID
//		UINT16		A;																// Internal/base receiver fades. 0xFFFF = "No data"
//		UINT16		B;																// Remote receiver fades. 0xFFFF = "No data"
//		UINT16		L;																// Third receiver fades. 0xFFFF = "No data"
//		UINT16		R;																// Fourth receiver fades. 0xFFFF = "No data"
//		UINT16		F;																// Frame losses. 0xFFFF = "No data"
//		UINT16		H;																// Holds. 0xFFFF = "No data"
//		UINT16		rxVoltage;												// Volts, .01V increment. 0xFFFF = "No data"
//	} STRU_TELE_QOS;
	
	private short lostPacketsReceiverA; 
	private short lostPacketsReceiverB;
	private short lostPacketsReceiverL;
	private short lostPacketsReceiverR; 
	private short frameLoss;
	private short holds; 
	private short voltageInHunderthOfVolts;

	public RxBlock(final byte[] rawData) {
		super(rawData);
		decode(rawData);
		
		measurementNames.add("LostPacketsReceiver A");
		measurementNames.add("LostPacketsReceiver B");
		measurementNames.add("LostPacketsReceiver L");
		measurementNames.add("LostPacketsReceiver R");
		measurementNames.add("FrameLoss");
		measurementNames.add("Holds");
		measurementNames.add("VoltageRx");

		measurementUnits.add("");
		measurementUnits.add("");
		measurementUnits.add("");
		measurementUnits.add("");
		measurementUnits.add("");
		measurementUnits.add("");
		measurementUnits.add("V");

		measurementFactors.add(1.0);
		measurementFactors.add(1.0);
		measurementFactors.add(1.0);
		measurementFactors.add(1.0);
		measurementFactors.add(1.0);
		measurementFactors.add(1.0);
		measurementFactors.add(0.01);
	}

	@Override
	public boolean areValuesEquals(DataBlock block) {
		if (block instanceof RxBlock) {
			RxBlock rx = (RxBlock) block;
			return rx.voltageInHunderthOfVolts == getVoltageInHunderthOfVolts()
					&& rx.lostPacketsReceiverA == getLostPacketsReceiverA()
					&& rx.lostPacketsReceiverB == getLostPacketsReceiverB()
					&& rx.lostPacketsReceiverL == getLostPacketsReceiverL()
					&& rx.lostPacketsReceiverR == getLostPacketsReceiverR()
					&& rx.frameLoss == getFrameLoss()
					&& rx.holds == getHolds();
		}
		return false;
	}
	
	private void decode(final byte[] rawData) {
		lostPacketsReceiverA = Shorts.fromBytes(rawData[0x06], rawData[0x07]);
		lostPacketsReceiverB = Shorts.fromBytes(rawData[0x08], rawData[0x09]);
		lostPacketsReceiverL = Shorts.fromBytes(rawData[0x0A], rawData[0x0B]);
		lostPacketsReceiverR = Shorts.fromBytes(rawData[0x0C], rawData[0x0D]);
		frameLoss = Shorts.fromBytes(rawData[0x0E], rawData[0x0F]);
		holds = Shorts.fromBytes(rawData[0x10], rawData[0x11]);
		voltageInHunderthOfVolts = Shorts.fromBytes(rawData[0x12], rawData[0x13]);
		
		measurementValues.add((int)getLostPacketsReceiverA());
		measurementValues.add((int)getLostPacketsReceiverB());
		measurementValues.add((int)getLostPacketsReceiverL());
		measurementValues.add((int)getLostPacketsReceiverR());
		measurementValues.add((int)getFrameLoss());
		measurementValues.add((int)getHolds());
		measurementValues.add((int)getVoltageInHunderthOfVolts());
	}

	private boolean isValidData(short value) {
		return value >= 0;
	}

	public boolean hasValidDataLostPacketsReceiverA() {
		return isValidData(lostPacketsReceiverA);
	}

	/**
	 * @return If Spectrum Telemetry System is used, the id shows
	 *         LostDataPackets on the receiver.<br>
	 *         If Lemon Telemetry System is used, the id is 100 if all is ok,
	 *         less if packets are lost.
	 */
	public short getLostPacketsReceiverA() {
		return (lostPacketsReceiverA  & 0xF000) < 1 ? lostPacketsReceiverA : 0;
	}

	public boolean hasValidDataLostPacketsReceiverB() {
		return isValidData(lostPacketsReceiverB);
	}

	/**
	 * @return If Spectrum Telemetry System is used, the id shows
	 *         LostDataPackets on the receiver.<br>
	 *         If Lemon Telemetry System is used, the id is not used
	 */
	public short getLostPacketsReceiverB() {
		return (lostPacketsReceiverB  & 0xF000) < 1 ? lostPacketsReceiverB : 0;
	}

	public boolean hasValidDataLostPacketsReceiverL() {
		return isValidData(lostPacketsReceiverL);
	}

	/**
	 * @return If Spectrum Telemetry System is used, the id shows
	 *         LostDataPackets on the receiver.<br>
	 *         If Lemon Telemetry System is used, the id is not used
	 */
	public short getLostPacketsReceiverL() {
		return (lostPacketsReceiverL  & 0xF000) < 1 ? lostPacketsReceiverL : 0;
	}

	public boolean hasValidDataLostPacketsReceiverR() {
		return isValidData(lostPacketsReceiverR);
	}

	/**
	 * @return If Spectrum Telemetry System is used, the id shows
	 *         LostDataPackets on the receiver.<br>
	 *         If Lemon Telemetry System is used, the id is not used
	 */
	public short getLostPacketsReceiverR() {
		return (lostPacketsReceiverR  & 0xF000) < 1 ? lostPacketsReceiverR : 0;
	}

	public boolean hasValidFrameLosssData() {
		return isValidData(frameLoss);
	}

	/**
	 * @return If Spectrum Telemetry System is used, the id shows lost
	 *         frames.<br>
	 *         If Lemon Telemetry System is used, the id is not used.
	 */
	public short getFrameLoss() {
		return (frameLoss  & 0xF000) < 1 ? frameLoss : 0;
	}

	public boolean hasValidHoldsData() {
		return isValidData(holds);
	}

	/**
	 * @return If Spectrum Telemetry System is used, the id shows holds.<br>
	 *         If Lemon Telemetry System is used, the id is not used.
	 */
	public short getHolds() {
		return (holds & 0xF000) < 1 ? holds : 0;
	}

	/**
	 * @return Returns the voltage in hunderth of Volt of the receiver.
	 */
	public short getVoltageInHunderthOfVolts() {
		return voltageInHunderthOfVolts;
	}

}
