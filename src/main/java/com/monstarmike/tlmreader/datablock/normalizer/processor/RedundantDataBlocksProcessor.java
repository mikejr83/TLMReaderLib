package com.monstarmike.tlmreader.datablock.normalizer.processor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.monstarmike.tlmreader.datablock.DataBlock;
import com.monstarmike.tlmreader.datablock.ServoDataBlock;

public class RedundantDataBlocksProcessor extends AbstractProcessor<DataBlock> {

	private Map<String, ProcessBlockType> typeMap = new HashMap<String, ProcessBlockType>();

	@Override
	public void preprocess(DataBlock block) {
		if (!(block instanceof ServoDataBlock)) {
			return;
		}
		String type = getType(block);
		if (!typeMap.containsKey(type)) {
			typeMap.put(type, new ProcessBlockType());
		}
		typeMap.get(type).preprocess(block);
	}

	private class ProcessBlockType {
		private DataBlock lastBlock;
		private boolean lastStateEquals;
		private Set<DataBlock> redundantBlocks = new HashSet<DataBlock>();
		private int count = 0;

		public void preprocess(DataBlock block) {
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

		public int getCount() {
			return count;
		}

		public Set<DataBlock> getRedundantBlocks() {
			return redundantBlocks;
		}
	}

	private String getType(DataBlock block) {
		return block.getClass().getSimpleName();
	}

	@Override
	void preprocessFinished() {
		int countAll = 0;
		int redundantAll = 0;
		for (Entry<String, ProcessBlockType> entry : typeMap.entrySet()) {
			String type = entry.getKey();
			ProcessBlockType blockType = entry.getValue();
			countAll += blockType.getCount();
			redundantAll += blockType.getRedundantBlocks().size();
			printRedundantBlocks(type, blockType.getCount(), blockType.getRedundantBlocks().size());
		}
		printRedundantBlocks("TOTAL", countAll, redundantAll);
	}

	private void printRedundantBlocks(String type, int totalBlocks, int redundantBlocks) {
		System.out.println("[" + type + "] redundant Blocks: " + redundantBlocks + ", total Blocks: " + totalBlocks
				+ ", redundant Blocks in percent: " + (double) redundantBlocks / totalBlocks * 100 + "%");
	}

	@Override
	public boolean isBad(DataBlock block) {
		String type = getType(block);
		if (!typeMap.containsKey(type)) {
			return false;
		}
		return typeMap.get(type).getRedundantBlocks().contains(block);
	}

	@Override
	public Class<? extends DataBlock> getClassOfDataBlock() {
		return DataBlock.class;
	}
}
