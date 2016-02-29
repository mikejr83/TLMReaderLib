package com.monstarmike.tlmreader.datablock.normalizer.processor;

import com.monstarmike.tlmreader.datablock.DataBlock;

/**
 * A implementation of {@link AbstractProcessor} is used, to describe a pattern
 * of invalid sensor data<br>
 * <br>
 * The implementation of the {@link AbstractProcessor} should be registered in
 * the {@link ProcessorEvaluator}.<br>
 *
 * @param <T>
 *            The Type T is the Datatype, the {@link AbstractProcessor} is
 *            workig with.
 */
public abstract class AbstractProcessor<T extends DataBlock> {
	/**
	 * This method is called from {@link ProcessorEvaluator} for all
	 * {@link DataBlock} and can be used to generate a internal state to give a
	 * adequate answer to the method 'isBad'
	 * 
	 * @param block
	 *            The datablock of the Type T
	 */
	void preprocess(T block) {
	}

	/**
	 * This method is called from {@link ProcessorEvaluator} once, all preprocess
	 * calls are done.<br>
	 * This can be used to finalize the internal state.
	 */
	void preprocessFinished() {

	}

	/**
	 * This method is called from {@link ProcessorEvaluator} for every
	 * {@link DataBlock} after the preprocessFinished is called.
	 * 
	 * @param block
	 *            The block of the type T which had to be checked
	 * @return Return true, if the DataBlock should be threatened as bad.
	 */
	boolean isBad(T block) {
		return false;
	}

	/**
	 * This method is used for the check, whether the preprocess and isBad
	 * should be called for a specific {@link DataBlock}
	 * 
	 * @return The Class of the expected {@link DataBlock} type.
	 */
	public abstract Class<? extends T> getClassOfDataBlock();
}
