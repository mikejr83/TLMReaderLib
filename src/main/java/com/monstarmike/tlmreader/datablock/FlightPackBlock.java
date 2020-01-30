package com.monstarmike.tlmreader.datablock;

import com.google.common.primitives.Ints;

public class FlightPackBlock extends DataBlock {
	
//	typedef struct
//	{
//		UINT8		identifier;														// Source device = 0x34
//		UINT8		sID;																	// Secondary ID
//		INT16		current_A;														// Instantaneous current, 0.1A (0-3276.6A)
//		INT16		chargeUsed_A;													// Integrated mAh used, 1mAh (0-32.766Ah)
//		UINT16		temp_A;															// Temperature, 0.1C (0-150C, 0x7FFF indicates not populated)
//		INT16		current_B;														// Instantaneous current, 0.1A (0-3276.6A)
//		INT16		chargeUsed_B;													// Integrated mAh used, 1mAh (0-32.766Ah)
//		UINT16		temp_B;															// Temperature, 0.1C (0-150C, 0x7FFF indicates not populated)
//		UINT16		spare;															// Not used
//	} STRU_TELE_FP_MAH;
	
	private int currentAInTenthOfAmps;
	private int chargeUsedAInMilliAmpsHours;
	private int temperatureAInTenthOfDegreeCelsius;
	private int currentBInTenthOfAmps;
	private int chargeUsedBInMilliAmpsHours;
	private int temperatureBInTenthOfDegreeCelsius;
	
	public FlightPackBlock(byte[] rawData) {
		super(rawData);
		decode(rawData);
		
		measurementNames.add("Current FPA");
		measurementNames.add("Capacity FPA");
		measurementNames.add("Temperature FPA");
		measurementNames.add("Current FPB");
		measurementNames.add("Capacity FPB");
		measurementNames.add("Temperature FPB");

		measurementUnits.add("A");
		measurementUnits.add("mAh");
		measurementUnits.add("°C");
		measurementUnits.add("A");
		measurementUnits.add("mAh");
		measurementUnits.add("°C");
 
		measurementFactors.add(0.1);
		measurementFactors.add(1.0);
		measurementFactors.add(0.1);
		measurementFactors.add(0.1);
		measurementFactors.add(1.0);
		measurementFactors.add(0.1);
	}
	
	private void decode(byte[] rawData) {
		currentAInTenthOfAmps = Ints.fromBytes((byte)0, (byte)0, rawData[7], rawData[6]);
		chargeUsedAInMilliAmpsHours = Ints.fromBytes((byte)0, (byte)0, rawData[9], rawData[8]);
		temperatureAInTenthOfDegreeCelsius = Ints.fromBytes((byte)0, (byte)0, rawData[11], rawData[10]);
		currentBInTenthOfAmps = Ints.fromBytes((byte)0, (byte)0, rawData[13], rawData[12]);
		chargeUsedBInMilliAmpsHours = Ints.fromBytes((byte)0, (byte)0, rawData[15], rawData[14]);
		temperatureBInTenthOfDegreeCelsius = Ints.fromBytes((byte)0, (byte)0, rawData[17], rawData[16]);

		measurementValues.add((int)getCurrentAInTenthOfAmps());
		measurementValues.add((int)getChargeUsedAInMilliAmpsHours());
		measurementValues.add((int)getTemperatureAInTenthOfDegreeCelsius());
		measurementValues.add((int)getCurrentBInTenthOfAmps());
		measurementValues.add((int)getChargeUsedBInMilliAmpsHours());
		measurementValues.add((int)getTemperatureBInTenthOfDegreeCelsius());
}

	@Override
	public boolean areValuesEquals(DataBlock block) {
		if (block instanceof FlightPackBlock) {
			FlightPackBlock fp = (FlightPackBlock) block;
			return fp.currentAInTenthOfAmps == currentAInTenthOfAmps
					&& fp.chargeUsedAInMilliAmpsHours == chargeUsedAInMilliAmpsHours
					&& fp.temperatureAInTenthOfDegreeCelsius == temperatureAInTenthOfDegreeCelsius
					&& fp.currentBInTenthOfAmps == currentBInTenthOfAmps
					&& fp.chargeUsedBInMilliAmpsHours == chargeUsedBInMilliAmpsHours
					&& fp.temperatureBInTenthOfDegreeCelsius == temperatureBInTenthOfDegreeCelsius;
		}
		return false;
	}

	/**
	 * @return the currentAInTenthOfAmps
	 */
	public int getCurrentAInTenthOfAmps() {
		return currentAInTenthOfAmps < 3000 ? currentAInTenthOfAmps : 0;
	}

	/**
	 * @return the chargeUsedAInMilliAmpsHours
	 */
	public int getChargeUsedAInMilliAmpsHours() {
		return chargeUsedAInMilliAmpsHours;
	}

	/**
	 * @return the temperatureAInTenthOfDegreeCelsius
	 */
	public int getTemperatureAInTenthOfDegreeCelsius() {
		return temperatureAInTenthOfDegreeCelsius != 0x7FFF ? temperatureAInTenthOfDegreeCelsius : 0;
	}

	/**
	 * @return the currentBInTenthOfAmps
	 */
	public int getCurrentBInTenthOfAmps() {
		return currentBInTenthOfAmps < 3000 ? currentBInTenthOfAmps : 0;
	}

	/**
	 * @return the chargeUsedBInMilliAmpsHours
	 */
	public int getChargeUsedBInMilliAmpsHours() {
		return chargeUsedBInMilliAmpsHours != 0x7FFF ? chargeUsedBInMilliAmpsHours : 0;
	}

	/**
	 * @return the temperatureBInTenthOfDegreeCelsius
	 */
	public int getTemperatureBInTenthOfDegreeCelsius() {
		return temperatureBInTenthOfDegreeCelsius != 0x7FFF ? temperatureBInTenthOfDegreeCelsius : 0;
	}

}
