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

public class Flight implements IFlight {
	private ArrayList<HeaderBlock> headerData = new ArrayList<HeaderBlock>();
	private ArrayList<DataBlock> blockData = new ArrayList<DataBlock>();
	private Duration duration = null;

	String modelName;
	private HeaderRpmBlock rpmHeader;

	public Duration get_duration() {
		if (this.duration == null) {
			int start = 0, end = 0;

			if (this.blockData.size() > 0) {
				start = this.blockData.get(0).get_timestamp();
				end = this.blockData.get(this.blockData.size() - 1).get_timestamp();
			}
			this.duration = new Duration((end - start) * 10);
		}

		return this.duration;
	}

	public void addHeaderNameBlock(HeaderNameBlock block) {
		this.modelName = block.getModelName();
		this.headerData.add(block);
	}

	public void addRpmHeaderBlock(HeaderRpmBlock block) {
		this.headerData.add(block);
		this.rpmHeader = block;
	}

	public void addHeaderBlock(HeaderBlock block) {
		this.headerData.add(block);
	}

	public void addDataBlock(DataBlock block) {
		this.blockData.add(block);
	}

	public Iterator<HeaderBlock> get_headerBlocks() {
		return this.headerData.iterator();
	}

	public Iterator<DataBlock> get_dataBlocks() {
		return this.blockData.iterator();
	}

	@Override
	public String toString() {
		PeriodFormatter formatter = new PeriodFormatterBuilder().appendHours().appendSuffix(":").appendMinutes()
				.appendSuffix(":").appendSeconds().appendSuffix(".").appendMillis().toFormatter();

		return this.modelName + " duration: " + formatter.print(this.get_duration().toPeriod());
	}

	public HeaderRpmBlock getRpmHeader() {
		return rpmHeader;
	}

	public int getNumberOfDataBlocks() {
		return blockData.size();
	}
}
