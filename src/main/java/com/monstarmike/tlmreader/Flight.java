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
import com.monstarmike.tlmreader.datablock.normalizer.DataNormalizer;
import com.monstarmike.tlmreader.datablock.normalizer.processor.ProcessorEvaluator;
import com.monstarmike.tlmreader.datablock.normalizer.processor.RedundantDataBlocksProcessor;

public class Flight implements IFlight {
	private ArrayList<HeaderBlock> headerData = new ArrayList<HeaderBlock>();
	private ArrayList<DataBlock> blockData = new ArrayList<DataBlock>();
	private Duration duration = null;

	String modelName;
	private HeaderRpmBlock rpmHeader;

	public Duration getDuration() {
		if (this.duration == null) {
			int start = 0;
			int end = 0;

			if (!this.blockData.isEmpty()) {
				start = this.blockData.get(0).getTimestamp();
				end = this.blockData.get(this.blockData.size() - 1).getTimestamp();
			}
			this.duration = new Duration((end - start) * 10);
		}
		return this.duration;
	}

	public void normalizeDataBlocks() {
		List<HeaderBlock> headerBlocks = getHeaderBlocks();
		for (HeaderBlock headerBlock : headerBlocks) {
			DataNormalizer normalizer = headerBlock.getNormalizer();
			normalizer.normalize(getDataBlocks());
		}
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

	public List<HeaderBlock> getHeaderBlocks() {
		return this.headerData;
	}

	public List<DataBlock> getDataBlocks() {
		return this.blockData;
	}

	@Override
	public String toString() {
		PeriodFormatter formatter = new PeriodFormatterBuilder().appendHours().appendSuffix(":").appendMinutes()
				.appendSuffix(":").appendSeconds().appendSuffix(".").appendMillis().toFormatter();

		return this.modelName + " duration: " + formatter.print(this.getDuration().toPeriod());
	}

	public boolean hasRpmHeader() {
		return rpmHeader != null;
	}

	public HeaderRpmBlock getRpmHeader() {
		return rpmHeader;
	}

	public int getNumberOfDataBlocks() {
		return blockData.size();
	}
	
	public void removeRedundantDataBlocks() {
		ProcessorEvaluator<DataBlock> evaluator = new ProcessorEvaluator<DataBlock>();
		evaluator.registerProcessor(new RedundantDataBlocksProcessor());
		evaluator.process(this.getDataBlocks());
	}
}
