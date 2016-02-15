package com.monstarmike.tlmreader.datablock.normalizer;

import java.util.List;

import com.monstarmike.tlmreader.datablock.DataBlock;

/**
 * This interface can be used, to normalize the values of DataBlocks. A
 * Implementation of this interface can be used to remove dataBlocks with Bad
 * value or to fix those.
 */
public interface DataNormalizer {
	void normalize(final List<DataBlock> dataBlocks);
}
