package com.monstarmike.tlmreader.datablock.normalizer.processor;

import java.util.HashSet;
import java.util.Set;

import com.monstarmike.tlmreader.datablock.DataBlock;

public abstract class RedundantDataBlocksProcessor<T extends DataBlock> extends AbstractProcessor<T> {

	private T lastBlock = null;
	private boolean lastStateEquals = false;
	private Set<T> redundantBlocks = new HashSet<T>();
	private int count = 0;

	@Override
	public void preprocess(T block) {
		count++;
		if (lastBlock != null) {
			boolean currentStateEquals = block.areValuesEquals(lastBlock);
			if (currentStateEquals && lastStateEquals) {
				redundantBlocks.add(lastBlock);
			}
			lastStateEquals = currentStateEquals;
		}
		lastBlock = block;
	}

	@Override
	void preprocessFinished() {
		System.out.println("Count redundant Blocks: " + (double) redundantBlocks.size() / count * 100 + "%");
	}

	@Override
	public boolean isBad(T block) {
		return redundantBlocks.contains(block);
	}
}
