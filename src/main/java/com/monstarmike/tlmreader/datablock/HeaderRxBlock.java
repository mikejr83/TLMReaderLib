package com.monstarmike.tlmreader.datablock;

import com.monstarmike.tlmreader.datablock.normalizer.DataNormalizer;
import com.monstarmike.tlmreader.datablock.normalizer.SignalNormalizer;

public class HeaderRxBlock extends HeaderBlock {
	
	/**
	 * If true, then spectrum Telemetry system, else Lemon Telemetry System
	 */
	private boolean spectrumTelemetrySystem;
	
	
	public HeaderRxBlock(byte[] rawData) {
		super(rawData);
		decode(rawData);
	}
	
	public static boolean isRxHeader(byte[] bytes) {
		return bytes.length > 6 && bytes[4] == (byte) 0x17 && bytes[5] == (byte) 0x17;		
	}


	private void decode(byte[] rawData) {
	}
	
	@Override
	public DataNormalizer getNormalizer() {
		return new SignalNormalizer(this);
	}
	
	public void setSpectrumTelemetrySystem(boolean spectrumTelemetrySystem) {
		this.spectrumTelemetrySystem = spectrumTelemetrySystem;
	}

	public boolean isSpectrumTelemetrySystem() {
		return spectrumTelemetrySystem;
	}
}
