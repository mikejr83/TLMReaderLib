package com.monstarmike.tlmreader;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.Duration;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import com.monstarmike.tlmreader.datablock.DataBlock;
import com.monstarmike.tlmreader.datablock.HeaderBlock;
import com.monstarmike.tlmreader.datablock.HeaderNameBlock;
import com.monstarmike.tlmreader.datablock.HeaderRpmBlock;

public class FlightDefinition implements IFlight {
	private ArrayList<HeaderBlock> headerData = new ArrayList<>();
	private DataBlock firstDataBlock;
	private DataBlock lastDataBlock;
	private int numberOfDataBlocks;
	private Duration duration = null;

	String modelName;
	private HeaderRpmBlock rpmHeaderBlock;

	public Duration getDuration() {
		if (this.duration == null) {
			int start = 0;
			int end = 0;

			if (firstDataBlock != null && lastDataBlock != null) {
				start = firstDataBlock.getTimestamp();
				end = lastDataBlock.getTimestamp();
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
		PeriodFormatter formatter = new PeriodFormatterBuilder().appendHours().appendSuffix(":").appendMinutes()
				.appendSuffix(":").appendSeconds().appendSuffix(".").appendMillis().toFormatter();

		return this.modelName + " duration: " + formatter.print(this.getDuration().toPeriod());
	}

	public List<HeaderBlock> getHeaderBlocks() {
		return headerData;
	}

	public int getNumberOfDataBlocks() {
		return numberOfDataBlocks;
	}

}
