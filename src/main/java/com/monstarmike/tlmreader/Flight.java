package com.monstarmike.tlmreader;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.joda.time.Duration;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import com.monstarmike.tlmreader.datablock.DataBlock;
import com.monstarmike.tlmreader.datablock.HeaderBlock;
import com.monstarmike.tlmreader.datablock.HeaderNameBlock;
import com.monstarmike.tlmreader.datablock.HeaderRpmBlock;

public class Flight {
	private ArrayList<HeaderBlock> headerData;
	private ArrayList<DataBlock> dataBlockData;
	private Duration duration = null;

	String modelName;
	private HeaderRpmBlock rpmHeader;

	public Duration get_duration() {
		if (this.duration == null) {
			int start = 0, end = 0;

			if (this.dataBlockData.size() > 0) {
				start = this.dataBlockData.get(0).get_timestamp();
				end = this.dataBlockData.get(this.dataBlockData.size() - 1)
						.get_timestamp();
			}
			this.duration = new Duration((end - start) * 10);
		}

		return this.duration;
	}

	public Flight() {
		this.headerData = new ArrayList<HeaderBlock>();
		this.dataBlockData = new ArrayList<DataBlock>();
	}

	public void addBlock(HeaderNameBlock block) {
		if (block == null)
			return;
		this.modelName = block.get_modelName();
		this.headerData.add(block);
	}

	public void addRpmHeaderBlock(HeaderRpmBlock block) {
		if (block == null)
			return;
		this.headerData.add(block);
		this.rpmHeader = block;
	}

	public void addBlock(HeaderBlock block) {
		if (block == null)
			return;
		this.headerData.add(block);
	}

	public void addBlock(DataBlock block) {
		if (block == null)
			return;
		this.dataBlockData.add(block);
	}
	
	public Iterator<HeaderBlock> get_headerBlocks() {
		return this.headerData.iterator();
	}
	
	public Iterator<DataBlock> get_dataBlocks(){
		return this.dataBlockData.iterator();
	}

	@Override
	public String toString() {
		PeriodFormatter formatter = new PeriodFormatterBuilder().appendHours()
				.appendSuffix(":").appendMinutes().appendSuffix(":")
				.appendSeconds().appendSuffix(".").appendMillis().toFormatter();

		return this.modelName + " duration: "
				+ formatter.print(this.get_duration().toPeriod());
	}

	public HeaderRpmBlock getRpmHeader() {
		return rpmHeader;
	}
}
