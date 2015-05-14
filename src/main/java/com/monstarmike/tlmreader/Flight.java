package com.monstarmike.tlmreader;

import java.util.ArrayList;

import org.joda.time.Duration;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import com.monstarmike.tlmreader.datablock.Block;
import com.monstarmike.tlmreader.datablock.DataBlock;
import com.monstarmike.tlmreader.datablock.HeaderBlock;
import com.monstarmike.tlmreader.datablock.HeaderDataBlock;
import com.monstarmike.tlmreader.datablock.HeaderNameBlock;

public class Flight {
	ArrayList<Block> data;
	ArrayList<HeaderBlock> headerData;
	ArrayList<DataBlock> dataBlockData;
	Duration duration = null;

	String modelName;

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
		this.data = new ArrayList<Block>();
		this.headerData = new ArrayList<HeaderBlock>();
		this.dataBlockData = new ArrayList<DataBlock>();
	}

	public void addBlock(HeaderNameBlock block) {
		if (block == null)
			return;
		this.modelName = block.get_modelName();
		this.data.add(block);
		this.headerData.add(block);
	}

	public void addBlock(HeaderDataBlock block) {
		if (block == null)
			return;
		this.data.add(block);
		this.headerData.add(block);
	}

	public void addBlock(DataBlock block) {
		if (block == null)
			return;
		this.data.add(block);
		this.dataBlockData.add(block);
	}

	@Override
	public String toString() {
		PeriodFormatter formatter = new PeriodFormatterBuilder().appendHours()
				.appendSuffix(":").appendMinutes().appendSuffix(":")
				.appendSeconds().appendSuffix(".").appendMillis().toFormatter();

		return this.modelName + " duration: "
				+ formatter.print(this.get_duration().toPeriod());
	}
}
