package com.monstarmike.tlmreader;

import java.util.ArrayList;
import java.util.Iterator;

import org.joda.time.Duration;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import com.monstarmike.tlmreader.datablock.DataBlock;
import com.monstarmike.tlmreader.datablock.HeaderBlock;
import com.monstarmike.tlmreader.datablock.HeaderNameBlock;
import com.monstarmike.tlmreader.datablock.HeaderRpmBlock;

public class FlightDefinition implements IFlight {
	private ArrayList<HeaderBlock> headerData = new ArrayList<HeaderBlock>();
	private DataBlock firstDataBlock;
	private DataBlock lastDataBlock;
	private int numberOfDataBlocks;
	private Duration duration = null;

	String modelName;
	private HeaderRpmBlock rpmHeaderBlock;

	public Duration get_duration() {
		if (this.duration == null) {
			int start = 0, end = 0;

			if (firstDataBlock != null && lastDataBlock != null) {
				start = firstDataBlock.get_timestamp();
				end = lastDataBlock.get_timestamp();
			}
			this.duration = new Duration((end - start) * 10);
		}

		return this.duration;
	}

	public void addHeaderNameBlock(HeaderNameBlock block) {
		headerData.add(block);
	}
	
	public void addRpmHeaderBlock(HeaderRpmBlock rpmBlock) {
		this.rpmHeaderBlock = rpmBlock;
		headerData.add(rpmBlock);
	}
	
	public HeaderRpmBlock getRpmHeader() {
		return rpmHeaderBlock;
	}

	public void addDataBlock(DataBlock block) {
		numberOfDataBlocks++;
		if (firstDataBlock == null) {
			firstDataBlock = block;
			lastDataBlock = block;
		} else {
			lastDataBlock = block;
		}
	}

	@Override
	public String toString() {
		PeriodFormatter formatter = new PeriodFormatterBuilder().appendHours()
				.appendSuffix(":").appendMinutes().appendSuffix(":")
				.appendSeconds().appendSuffix(".").appendMillis().toFormatter();

		return this.modelName + " duration: "
				+ formatter.print(this.get_duration().toPeriod());
	}

	
	public Iterator<HeaderBlock> get_headerBlocks() {
		return headerData.iterator();
	}

	public int getNumberOfDataBlocks() {
		return numberOfDataBlocks;
	}

}
