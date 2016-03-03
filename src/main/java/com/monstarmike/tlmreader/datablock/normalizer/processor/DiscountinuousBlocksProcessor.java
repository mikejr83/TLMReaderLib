package com.monstarmike.tlmreader.datablock.normalizer.processor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.monstarmike.tlmreader.datablock.RXBlock;

public class DiscountinuousBlocksProcessor extends AbstractProcessor<RXBlock> {

	private static final int MAX_CHANGE_IN_ONE_HUNDERTH = 10;

	private Set<RXBlock> unclassifiedBlocks = new HashSet<>();
	private DataState dataState = DataState.UNKNOWN;
	private RXBlock lastBlock = null;
	private List<RXBlock> badBlocks = new ArrayList<>();

	public boolean isBad(RXBlock block) {
		return badBlocks.contains(block);
	}

	public void preprocessFinished() {
		if (DataState.BAD.equals(dataState)) {
			badBlocks.add(lastBlock);
		}
	}

	public void preprocess(RXBlock currentBlock) {
		if (currentBlock.hasValidDataLostPacketsReceiverA()) {
			if (lastBlock != null) {
				ChangeState currentChangeState = isContinuous(lastBlock, currentBlock);
				if (DataState.UNKNOWN.equals(dataState)) {
					if (ChangeState.CONTINUOUS.equals(currentChangeState)) {
						unclassifiedBlocks.add(lastBlock);
					} else if (ChangeState.TOO_LOW.equals(currentChangeState)) {
						unclassifiedBlocks.add(lastBlock);
						badBlocks.addAll(unclassifiedBlocks);
						unclassifiedBlocks.clear();
						dataState = DataState.OK;
					} else { // TOO_HIGH
						dataState = DataState.BAD;
					}
				} else if (DataState.BAD.equals(dataState)) {
					badBlocks.add(lastBlock);
					if (ChangeState.TOO_LOW.equals(currentChangeState)) {
						dataState = DataState.UNKNOWN;
					}
				} else { // DataState.OK.equals(dataState)
					if (ChangeState.TOO_HIGH.equals(currentChangeState)) {
						dataState = DataState.BAD;
					}
				}
			}
			lastBlock = currentBlock;
		}
	}

	public Class<RXBlock> getClassOfDataBlock() {
		return RXBlock.class;
	}

	private ChangeState isContinuous(RXBlock last, RXBlock current) {
		int timeDiff = current.getTimestamp() - last.getTimestamp();
		int valueDiff = current.getLostPacketsReceiverA() - last.getLostPacketsReceiverA();
		double change = (double) valueDiff / timeDiff;
		if (change > MAX_CHANGE_IN_ONE_HUNDERTH) {
			return ChangeState.TOO_HIGH;
		} else if (change < -MAX_CHANGE_IN_ONE_HUNDERTH) {
			return ChangeState.TOO_LOW;
		} else {
			return ChangeState.CONTINUOUS;
		}
	}

	private enum ChangeState {
		TOO_HIGH, TOO_LOW, CONTINUOUS
	}

	private enum DataState {
		OK, UNKNOWN, BAD
	}
}