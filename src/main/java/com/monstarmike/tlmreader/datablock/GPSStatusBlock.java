package com.monstarmike.tlmreader.datablock;

import java.util.Locale;

import com.monstarmike.tlmreader.util.StringUtils;

public class GPSStatusBlock extends DataBlock {

//	typedef struct
//	{
//		UINT8		identifier;												// Source device = 0x17
//		UINT8		sID;															// Secondary ID
//		UINT16	speed;														// BCD, knots, format 3.1
//		UINT32	UTC;															// BCD, format HH:MM:SS.S, format 6.1
//		UINT8		numSats;													// BCD, 0-99
//		UINT8		altitudeHigh;											// BCD, meters, format 2.0 (High order of altitude)
//	} STRU_TELE_GPS_STAT;
	private short speedInKnots;
	private int		UTC;
	private byte	numSats;
	private short altitudeHightInMeter;
	
	public GPSStatusBlock(byte[] rawData) {
		super(rawData);
		decode(rawData);
		
		measurementNames.add("Speed GPS");
		measurementNames.add("UTC GPS");
		measurementNames.add("Satellites GPS");
		measurementNames.add("Altitude GPShigh");

		measurementUnits.add("Knots");
		measurementUnits.add("");
		measurementUnits.add("#");
		measurementUnits.add("m");

		measurementFactors.add(0.1);
		measurementFactors.add(1.0);
		measurementFactors.add(1.0);
		measurementFactors.add(1.0);
	}
	
	public GPSStatusBlock(GPSStatusBlock that) {
		super(that);
		this.speedInKnots = that.speedInKnots;
		this.UTC = that.UTC;
		this.numSats = that.numSats;
		this.altitudeHightInMeter = that.altitudeHightInMeter;
	}

	private void decode(final byte[] rawData) {
		speedInKnots = Short.parseShort(StringUtils.bcdEncodeInverted(rawData, 6, 2));
		UTC = Integer.parseInt(StringUtils.bcdEncodeInverted(rawData, 8, 4));
		numSats = Byte.parseByte(StringUtils.bcdEncodeInverted(rawData, 12, 1));
		altitudeHightInMeter = Short.parseShort(StringUtils.bcdEncodeInverted(rawData, 13, 1));
		
		measurementValues.add((int)getSpeedInTenthOfKnots());
		measurementValues.add((int)getUTC());
		measurementValues.add((int)getNumSats());
		measurementValues.add((int)getAltitudeHightInMeter());
	}

	@Override
	public boolean areValuesEquals(DataBlock block) {
		if (block instanceof GPSStatusBlock) {
			GPSStatusBlock gpsStatus = (GPSStatusBlock) block;
			return gpsStatus.speedInKnots == speedInKnots
					&& gpsStatus.UTC == UTC
					&& gpsStatus.numSats == numSats
					&& gpsStatus.altitudeHightInMeter == altitudeHightInMeter;
		}
		return false;
	}

	/**
	 * @return the speedInKnots
	 */
	public short getSpeedInTenthOfKnots() {
		return speedInKnots;
	}

	/**
	 * @return the UTC
	 */
	public int getUTC() {
		return UTC;
	}

	/**
	 * @return the UTC as string representation
	 */
	public String getUTCString() {
		String utc = "" + UTC;
		return utc.length() == 8 ? utc.substring(0, 2) + ":" + utc.substring(2, 4) + ":" + utc.substring(4, 6) + "." + utc.substring(6, 8) : "00:00:00.00";
	}

	/**
	 * @return the numSats
	 */
	public byte getNumSats() {
		return numSats;
	}

	/**
	 * @return the altitudeHightInMeter
	 */
	public short getAltitudeHightInMeter() {
		return altitudeHightInMeter;
	}

	@Override
	public String toString() {
		return "GPSStatusData:    " + getTimestamp()
				+ " - speedInKnots = " + String.format(Locale.ENGLISH, "%.1f", getSpeedInTenthOfKnots() * 0.1)
				+ ", UTC = " + getUTCString()
				+ ", numSats = " + getNumSats()
				+ ", altitudeHightInMeter = " + getAltitudeHightInMeter();
	}

}
