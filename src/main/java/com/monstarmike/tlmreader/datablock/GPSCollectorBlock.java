package com.monstarmike.tlmreader.datablock;

import java.util.List;

public class GPSCollectorBlock extends DataBlock implements IBlock {
	
	private static GPSCollectorBlock instance = null; 
	private GPSLocationBlock locationData;
	private GPSStatusBlock statusData;
	private boolean	isUpdateLocation = false;
	private boolean isUpdateStatus = false;


	private GPSCollectorBlock() {
		super();
		measurementNames.add("Altitude GPS");
		measurementNames.add("Latitude");
		measurementNames.add("Longitude");
		measurementNames.add("Speed GPS");
		measurementNames.add("Satellites GPS");
		measurementNames.add("Course");
		measurementNames.add("HDOP");
		measurementNames.add("GPSFix");
		measurementNames.add("UTC GPS");
		
		measurementUnits.add("m");
		measurementUnits.add("° '");
		measurementUnits.add("° '");
		measurementUnits.add("km/h");
		measurementUnits.add("#");
		measurementUnits.add("°");
		measurementUnits.add("");
		measurementUnits.add("");
		measurementUnits.add("");
		
		measurementFactors.add(0.1); //altitude 
		measurementFactors.add(1.0); //latitude
		measurementFactors.add(1.0); //longitude
		measurementFactors.add(0.1852); //speed knots -> km/h
		measurementFactors.add(1.0); //numSats
		measurementFactors.add(0.1); //course
		measurementFactors.add(0.1); //HDOP
		measurementFactors.add(1.0); //GPSFix
		measurementFactors.add(1.0); //UTC
	}
	
	public static GPSCollectorBlock getInstance() {
		if (instance == null)
			return instance = new GPSCollectorBlock();
		return instance;
	}

	/**
	 * update the GPS location information
	 * @param block the GPS location data
	 */
	public void updateLocation(GPSLocationBlock block) {
		locationData = new GPSLocationBlock(block);
		timestamp = locationData.getTimestamp();
		sequence = locationData.getSequence();
		isUpdateLocation = true;
	}

	/**
	 * update the GPS status information
	 * @param block the GPS status data
	 */
	public void updateStatus(GPSStatusBlock block) {
		statusData = new GPSStatusBlock(block);
		timestamp = statusData.getTimestamp();
		sequence = statusData.getSequence();
		isUpdateStatus = true;
	}
	
	/**
	 * @return true is both GPS data blocks are updated
	 */
	public boolean isUpdated() {
		return (locationData != null && statusData != null) && (isUpdateLocation || isUpdateStatus);
	}
		
	/**
	 * legacy interface method
	 */
	@Override
	public int getSize() {
		return 0;
	}

	/**
	 * method probably unused since GPSLocationBlock and GPSStatusBlock has such method to filter duplicates
	 * @param block byte array
	 * @return true|false if some value has been changed related to last collection
	 */
	@Override
	public boolean areValuesEquals(DataBlock block) {
		if (locationData != null && statusData != null) {
			if (block instanceof GPSLocationBlock) {
				return locationData.areValuesEquals(block);
			}
			else if (block instanceof GPSStatusBlock) {
				return statusData.areValuesEquals(block);
			}
		}
		return false;
	}

	/**
	 * @return list of measurement names of the dedicated sensor block
	 */
	@Override
	public List<String> getMeasurementNames() {
		return measurementNames;
	}

	@Override
	public List<String> getMeasurementUnits() {
		return measurementUnits;
	}

	@Override
	public List<Double> getMeasurementFactors() {
		return measurementFactors;
	}

	@Override
	public List<Integer> getMeasurementValues() {
		measurementValues.clear();
		int altitudeInTenthOfMeter = statusData.getAltitudeHightInMeter() * 10000 + locationData.getAltitudeLowInTenthOfMeter();
		measurementValues.add((int)altitudeInTenthOfMeter);
		measurementValues.add(locationData.getLatitude());
		measurementValues.add(((locationData.getFlags() & 0x04) == 0x04) ? 1000000000 + locationData.getLongitude() : locationData.getLongitude());
		measurementValues.add((int)statusData.getSpeedInTenthOfKnots());
		measurementValues.add((int)statusData.getNumSats());
		measurementValues.add((int)locationData.getCourseInTenthOfDegree());
		measurementValues.add((int)locationData.getHDOPInTenth());
		measurementValues.add((int)locationData.getGPSFix());
		measurementValues.add((int)statusData.getUTC());
		return measurementValues;
	}
	


}
