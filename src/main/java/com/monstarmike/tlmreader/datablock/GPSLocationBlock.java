package com.monstarmike.tlmreader.datablock;

import com.monstarmike.tlmreader.util.StringUtils;

public class GPSLocationBlock extends DataBlock {
	
//	typedef struct
//	{
//		UINT8		identifier;											// Source device = 0x16
//		UINT8		sID;														// Secondary ID
//		UINT16	altitudeLow;										// BCD, meters, format 3.1 (Low order of altitude)
//		UINT32	latitude;												// BCD, format 4.4, Degrees * 100 + minutes, less than 100 degrees
//		UINT32	longitude;											// BCD, format 4.4 , Degrees * 100 + minutes, flag indicates > 99 degrees
//		UINT16	course;													// BCD, 3.1
//		UINT8		HDOP;														// BCD, format 1.1
//		UINT8		GPSflags;												// see definitions below
//	} STRU_TELE_GPS_LOC;

// GPS flags definitions:
//#define	GPS_INFO_FLAGS_IS_NORTH_BIT					(0)
//#define	GPS_INFO_FLAGS_IS_NORTH						(1 << GPS_INFO_FLAGS_IS_NORTH_BIT)
//#define	GPS_INFO_FLAGS_IS_EAST_BIT					(1)
//#define	GPS_INFO_FLAGS_IS_EAST						(1 << GPS_INFO_FLAGS_IS_EAST_BIT)
//#define	GPS_INFO_FLAGS_LONGITUDE_GREATER_99_BIT		(2)
//#define	GPS_INFO_FLAGS_LONGITUDE_GREATER_99			(1 << GPS_INFO_FLAGS_LONGITUDE_GREATER_99_BIT)
//#define	GPS_INFO_FLAGS_GPS_FIX_VALID_BIT			(3)
//#define	GPS_INFO_FLAGS_GPS_FIX_VALID				(1 << GPS_INFO_FLAGS_GPS_FIX_VALID_BIT)
//#define	GPS_INFO_FLAGS_GPS_DATA_RECEIVED_BIT		(4)
//#define	GPS_INFO_FLAGS_GPS_DATA_RECEIVED			(1 << GPS_INFO_FLAGS_GPS_DATA_RECEIVED_BIT)
//#define	GPS_INFO_FLAGS_3D_FIX_BIT					(5)
//#define	GPS_INFO_FLAGS_3D_FIX						(1 << GPS_INFO_FLAGS_3D_FIX_BIT)
//#define GPS_INFO_FLAGS_NEGATIVE_ALT_BIT				(7)
//#define GPS_INFO_FLAGS_NEGATIVE_ALT					(1 << GPS_INFO_FLAGS_NEGATIVE_ALT_BIT)

	private int 	altitudeLowInTenthOfMeter;
	private int		latitude;
	private int		longitude;
	private short	courseInTenthOfDegree;
	private byte	HDOPInTenth;
	private byte	flags;


	public GPSLocationBlock(byte[] rawData) {
		super(rawData);
		decode(rawData);
		
		measurementNames.add("Altitude low");
		measurementNames.add("Latitude");
		measurementNames.add("Longitude");
		measurementNames.add("Course");
		measurementNames.add("HDOPInTenth");
		measurementNames.add("GPSFix");

		measurementUnits.add("m");
		measurementUnits.add("° '");
		measurementUnits.add("° '");
		measurementUnits.add("°");
		measurementUnits.add("");
		measurementUnits.add("");

		measurementFactors.add(0.1);
		measurementFactors.add(1.0);
		measurementFactors.add(1.0);
		measurementFactors.add(0.1);
		measurementFactors.add(0.1);
		measurementFactors.add(1.0);
	}

	public GPSLocationBlock(GPSLocationBlock that) {
		super(that);
		this.altitudeLowInTenthOfMeter = that.altitudeLowInTenthOfMeter;
		this.latitude = that.latitude;
		this.longitude = that.longitude;
		this.courseInTenthOfDegree = that.courseInTenthOfDegree;
		this.HDOPInTenth = that.HDOPInTenth;
		this.flags = that.flags;
	}

	/**
	 * @return the altitudeLowInMeter
	 */
	public int getAltitudeLowInTenthOfMeter() {
		return altitudeLowInTenthOfMeter;
	}

	/**
	 * @return the latitude
	 */
	public int getLatitude() {
		return latitude;
	}

	/**
	 * @return the latitude converted in decimals
	 */
	public float getLatitudeInDecimals() {
		int grad = latitude / 1000000;
		float minuten = (latitude - (grad * 1000000)) / 10000.0f;
		return grad + minuten / 60.0f;
	}

	/**
	 * @return the longitude converted in decimals
	 */
	public float getLongitudeInDecimals() {
		int grad = longitude / 1000000;
		float minuten = (longitude - (grad * 1000000)) / 10000.0f;
		return grad + minuten / 60.0f;
	}

	/**
	 * @return the longitude
	 */
	public int getLongitude() {
		return longitude;
	}

	/**
	 * @return the courseInTenthOfDegree
	 */
	public short getCourseInTenthOfDegree() {
		return courseInTenthOfDegree;
	}

	/**
	 * @return the HDOPInTenth
	 */
	public byte getHDOPInTenth() {
		return HDOPInTenth;
	}

	/**
	 * @return the flags
	 */
	public byte getFlags() {
		return flags;
	}

	/**
	 * @return the GPS fix, 2 for 2D, 3 for 3D fix
	 */
	public int getGPSFix() {
		if (((flags & 0x08) == 0x08) && ((flags & 0x20) == 0x20))
			return 3;
		else if ((flags & 0x08) == 0x08)
			return 2;
		return 0;
		//return ((flags & 0x20) == 0x20) ? 3 : ((flags & 0x08) == 0x08) ? 2 : 0;
	}

	private void decode(final byte[] rawData) {
		altitudeLowInTenthOfMeter = Integer.parseInt(StringUtils.bcdEncodeInverted(rawData, 6, 2));
		latitude = Integer.parseInt(StringUtils.bcdEncodeInverted(rawData, 8, 4));
		longitude = Integer.parseInt(StringUtils.bcdEncodeInverted(rawData, 12, 4));
		courseInTenthOfDegree = Short.parseShort(StringUtils.bcdEncodeInverted(rawData, 16, 2));
		HDOPInTenth = Byte.parseByte(StringUtils.bcdEncodeInverted(rawData, 18, 1));
		flags = rawData[19];
		
		measurementValues.add(((getFlags() & 0x80) == 0x80) ? -1 * getAltitudeLowInTenthOfMeter() : getAltitudeLowInTenthOfMeter());
		measurementValues.add(getLatitude());
		measurementValues.add(((getFlags() & 0x04) == 0x04) ? 1000000000 + getLongitude() : getLongitude());
		measurementValues.add((int)getCourseInTenthOfDegree());
		measurementValues.add((int)getHDOPInTenth());
		measurementValues.add(getGPSFix());
	}

	@Override
	public boolean areValuesEquals(DataBlock block) {
		if (block instanceof GPSLocationBlock) {
			GPSLocationBlock gpsLoc = (GPSLocationBlock) block;
			return gpsLoc.altitudeLowInTenthOfMeter == altitudeLowInTenthOfMeter
					&& gpsLoc.latitude == latitude
					&& gpsLoc.longitude == longitude
					&& gpsLoc.courseInTenthOfDegree == courseInTenthOfDegree
					&& gpsLoc.HDOPInTenth == HDOPInTenth
					&& gpsLoc.flags == flags;
		}
		return false;
	}

	@Override
	public String toString() {
		return "GPSLocationData:  " + getTimestamp()
				+ " - Altitude low = " + (((getFlags() & 0x80) == 0x80) ? -1 * getAltitudeLowInTenthOfMeter() : getAltitudeLowInTenthOfMeter()) / 10.0
				+ ", Latitude = " + getLatitudeInDecimals()
				+ ", Longitude = " + getLongitudeInDecimals()
				+ ", Course  = " + getCourseInTenthOfDegree() / 10.0
				+ ", HDOP = " + (getHDOPInTenth() & 0xFF) / 10.0
				+ ", GPSFix = " + getGPSFix()
				+ ", IS_NORTH = " + ((getFlags() & 0x01) == 0x01)
				+ ", IS_EAST = " + ((getFlags() & 0x02) == 0x02)
				+ ", LONGITUDE_GREATER_99 = " + ((getFlags() & 0x04) == 0x04)
				//+ ", GPS fix = " + ((getFlags() & 0x08) == 0x08)
				//+ ", GPS_DATA_RECEIVED = " + ((getFlags() & 0x10) == 0x10)
				//+ ", GPS 3D fix = " + ((getFlags() & 0x20) == 0x20)
				//+ ", NEGATIVE_ALT = " + ((getFlags() & 0x80) == 0x80)
				;
	}
}
