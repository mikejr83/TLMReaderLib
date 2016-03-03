package com.monstarmike.tlmreader.datablock.normalizer.processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.monstarmike.tlmreader.datablock.DataBlock;

public class RedundantDataBlocksProcessor extends AbstractProcessor<DataBlock> {

	private Map<String, ProcessBlockType> typeMap = new HashMap<>();

	@Override
	public void preprocess(DataBlock block) {
		if (!(block instanceof DataBlock)) {
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
		private List<DataBlock> redundantBlocks = new ArrayList<>();
		private int count = 0;
		private int currentPos = 0;

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

		public int getNumberOfRedundantBlocks() {
			return redundantBlocks.size();
		}

		/**
		 * The RedundantBlock List must be ordered by the sequence.<br>
		 * This Implementation is 2 to 3 times faster than the use of a map;
		 * 
		 * @param block
		 *            The checked blocks must also be ordered by sequence.
		 * @return Returns true, if the block can be found in the redundant
		 *         block list.
		 */
		public boolean isRedundantBlock(final DataBlock block) {
			if (currentPos >= redundantBlocks.size()) {
				return false;
			}
			while (redundantBlocks.get(currentPos).getSequence() < block.getSequence()) {
				currentPos++;
				if (currentPos >= redundantBlocks.size()) {
					return false;
				}
			}
			return redundantBlocks.get(currentPos).getSequence() == block.getSequence();
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
			redundantAll += blockType.getNumberOfRedundantBlocks();
			printRedundantBlocks(type, blockType.getCount(), blockType.getNumberOfRedundantBlocks());
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
		return typeMap.get(type).isRedundantBlock(block);
	}

	@Override
	public Class<? extends DataBlock> getClassOfDataBlock() {
		return DataBlock.class;
	}
}
