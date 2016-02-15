package com.monstarmike.tlmreader.datablock;

import com.google.common.primitives.Shorts;

/**
 * <h4>Spectrum DSM2/DSMX Telemetry System - Data interpretation</h4> The Values
 * of a, b, l ,r are number packetLoss of the each receiver. <br>
 * It looks like, that Spectrum uses unsigned short data types, because the
 * 0xffff (unsigned shortMaxValue) seems to be the value if no receiver is
 * installed. I assume, that always the highest value of the datatype is the
 * value for 'no data'.
 * 
 * <h4>Lemon DSM2/DSMX Telemetry Systen - Data interpretation</h4> However the
 * Lemon unit does use the “A” display for a Signal Indicator value. This is
 * calculated from the number of packets lost in transmission. A reading of 100
 * means that no packets have been lost in transmission, while smaller numbers
 * indicate losses.<br>
 * The value is based on the number of packets received about every half second.
 * <br>
 * <code>Quote from 'docs/InstructionsTelemetry A4.pdf' , page 6, section
 * "Accuracy and Limitations" subsection "Signal Indicator"</code> <br>
 * <br>
 * The values b,l,r,frameloss,holds are not used.<br>
 * The not used values are 32768 (0x8000) == Short.MIN_VALUE.<br>
 * It gives me the appearance, that Lemon is using signed short, with
 * Short.MIN_VALUE for invalid values.
 */

public class RXBlock extends DataBlock {

	private short lostPacketsReceiverA, lostPacketsReceiverB, lostPacketsReceiverL, lostPacketsReceiverR, frameLoss,
			holds, voltageInHunderthOfVolts;

	public RXBlock(final byte[] rawData) {
		super(rawData);
		decode(rawData);
	}

	private void decode(final byte[] rawData) {
		lostPacketsReceiverA = Shorts.fromBytes(rawData[0x06], rawData[0x07]);
		lostPacketsReceiverB = Shorts.fromBytes(rawData[0x08], rawData[0x09]);
		lostPacketsReceiverL = Shorts.fromBytes(rawData[0x0A], rawData[0x0B]);
		lostPacketsReceiverR = Shorts.fromBytes(rawData[0x0C], rawData[0x0D]);
		frameLoss = Shorts.fromBytes(rawData[0x0E], rawData[0x0F]);
		holds = Shorts.fromBytes(rawData[0x10], rawData[0x11]);
		voltageInHunderthOfVolts = Shorts.fromBytes(rawData[0x12], rawData[0x13]);
	}

	private boolean isValidData(short value) {
		return value >= 0;
	}

	public boolean hasValidDataLostPacketsReceiverA() {
		return isValidData(getLostPacketsReceiverA());
	}

	/**
	 * @return If Spectrum Telemetry System is used, the value shows
	 *         LostDataPackets on the receiver.<br>
	 *         If Lemon Telemetry System is used, the value is 100 if all is ok,
	 *         less if packets are lost.
	 */
	public short getLostPacketsReceiverA() {
		return lostPacketsReceiverA;
	}

	public boolean hasValidDataLostPacketsReceiverB() {
		return isValidData(getLostPacketsReceiverB());
	}

	/**
	 * @return If Spectrum Telemetry System is used, the value shows
	 *         LostDataPackets on the receiver.<br>
	 *         If Lemon Telemetry System is used, the value is not used
	 */
	public short getLostPacketsReceiverB() {
		return lostPacketsReceiverB;
	}

	public boolean hasValidDataLostPacketsReceiverL() {
		return isValidData(getLostPacketsReceiverL());
	}

	/**
	 * @return If Spectrum Telemetry System is used, the value shows
	 *         LostDataPackets on the receiver.<br>
	 *         If Lemon Telemetry System is used, the value is not used
	 */
	public short getLostPacketsReceiverL() {
		return lostPacketsReceiverL;
	}

	public boolean hasValidDataLostPacketsReceiverR() {
		return isValidData(getLostPacketsReceiverR());
	}

	/**
	 * @return If Spectrum Telemetry System is used, the value shows
	 *         LostDataPackets on the receiver.<br>
	 *         If Lemon Telemetry System is used, the value is not used
	 */
	public short getLostPacketsReceiverR() {
		return lostPacketsReceiverR;
	}

	public boolean hasValidFrameLosssData() {
		return isValidData(getFrameLoss());
	}

	/**
	 * @return If Spectrum Telemetry System is used, the value shows
	 *         lost frames.<br>
	 *         If Lemon Telemetry System is used, the value is not used.
	 */
	public short getFrameLoss() {
		return frameLoss;
	}

	public boolean hasValidHoldsData() {
		return isValidData(getHolds());
	}

	/**
	 * @return If Spectrum Telemetry System is used, the value shows
	 *         holds.<br>
	 *         If Lemon Telemetry System is used, the value is not used.
	 */
	public short getHolds() {
		return holds;
	}

	/**
	 * @return Returns the voltage in hunderth of Volt of the receiver.
	 */
	public short getVoltageInHunderthOfVolts() {
		return voltageInHunderthOfVolts;
	}

	@Override
	public String toString() {
		return "RxData; LostPacketsReceiver A: " + getLostPacketsReceiverA() + " (" + hasValidDataLostPacketsReceiverA()
				+ "), B: " + getLostPacketsReceiverB() + " (" + hasValidDataLostPacketsReceiverB() + "), L: "
				+ getLostPacketsReceiverL() + " (" + hasValidDataLostPacketsReceiverL() + "), R: "
				+ getLostPacketsReceiverR() + " (" + hasValidDataLostPacketsReceiverR() + "), FrameLoss: "
				+ getFrameLoss() + "(" + hasValidFrameLosssData() + ") , Holds: " + getHolds() + "("
				+ hasValidHoldsData() + "), Volts: " + getVoltageInHunderthOfVolts();
	}

}
