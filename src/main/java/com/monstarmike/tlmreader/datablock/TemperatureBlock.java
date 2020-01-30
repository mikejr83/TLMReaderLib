package com.monstarmike.tlmreader.datablock;

import com.google.common.primitives.Shorts;

public class TemperatureBlock extends DataBlock {
	
//	typedef struct
//	{
//		UINT8		identifier;														// Source device = 0x02
//		UINT8		sID;																	// Secondary ID
//		INT16		temperature;													// Temperature in degrees Fahrenheit
//	} STRU_TELE_TEMP;

	private short temperatureInFahrenheit;
	
	public TemperatureBlock(byte[] rawData) {
		super(rawData);
		decode(rawData);
		
		measurementNames.add("Temperature T");
		measurementUnits.add("°C");
		measurementFactors.add(0.1);
	}

	@Override
	public boolean areValuesEquals(DataBlock block) {
		if (block instanceof TemperatureBlock) {
			TemperatureBlock temp = (TemperatureBlock) block;
			return temp.temperatureInFahrenheit == temperatureInFahrenheit;
		}
		return false;
	}
	
	/**
	 * @return the temperatureInFahrenheit
	 */
	public short getTemperatureInFahrenheit() {
		return temperatureInFahrenheit;
	}
	
	/**
	 * @return the temperatureInFahrenheit
	 */
	public short getTemperatureInThenthGradCelsius() {
		return (short) ((temperatureInFahrenheit - 32) / 1.8f * 10);
	}

	private void decode(byte[] rawData) {
		temperatureInFahrenheit = Shorts.fromBytes(rawData[2], rawData[3]);
		
		measurementValues.add((int)getTemperatureInThenthGradCelsius());
	}

}
