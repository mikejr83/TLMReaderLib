package com.monstarmike.tlmreader.datablock.normalizer.processor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.monstarmike.tlmreader.datablock.RxBlock;

public class DiscountinuousBlocksProcessor extends AbstractProcessor<RxBlock> {

	private static final int MAX_CHANGE_IN_ONE_HUNDERTH = 10;

	private Set<RxBlock> unclassifiedBlocks = new HashSet<>();
	private DataState dataState = DataState.UNKNOWN;
	private RxBlock lastBlock = null;
	private List<RxBlock> badBlocks = new ArrayList<>();

	public boolean isBad(RxBlock block) {
		return badBlocks.contains(block);
	}

	public void preprocessFinished() {
		if (DataState.BAD.equals(dataState)) {
			badBlocks.add(lastBlock);
		}
	}

	public void preprocess(RxBlock currentBlock) {
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

	public Class<RxBlock> getClassOfDataBlock() {
		return RxBlock.class;
	}

	private ChangeState isContinuous(RxBlock last, RxBlock current) {
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