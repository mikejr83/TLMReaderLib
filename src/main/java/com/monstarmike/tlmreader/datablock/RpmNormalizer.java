package com.monstarmike.tlmreader.datablock;

import java.util.Iterator;
import java.util.List;

public class RpmNormalizer implements DataNormalizer {

	/**
	 * Remove all datablocks with too high rpm values (limit
	 */
	public void normalize(final List<DataBlock> dataBlocks) {
		Iterator<DataBlock> iterator = dataBlocks.iterator();
		while (iterator.hasNext()) {
			DataBlock dataBlock = iterator.next();
			if (dataBlock instanceof StandardBlock) {
				float rpm = ((StandardBlock) dataBlock).getRpm();
				if (rpm > 10000.0) {
					iterator.remove();
				}
			}
		}
	}
}
