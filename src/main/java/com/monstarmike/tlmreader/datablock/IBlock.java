package com.monstarmike.tlmreader.datablock;

import java.util.List;

public interface IBlock {

	String toString();
	
	/**
	 * @return data byte array size
	 */
	int getSize();

	/**
	 * @param block byte array
	 * @return true|false if some value has been changed related to last collection
	 */
	boolean areValuesEquals(DataBlock block);

	/**
	 * @return list of measurement names of the dedicated sensor block
	 */
	List<String> getMeasurementNames();

	/**
	 * @return list of measurement units of the dedicated sensor block
	 */
	List<String> getMeasurementUnits();

	/**
	 * @return list of measurement factors of the dedicated sensor block
	 */
	List<Double> getMeasurementFactors();
	
	/**
	 * @return list of measurement raw values of the dedicated sensor block
	 */
	List<Integer> getMeasurementValues();

}
