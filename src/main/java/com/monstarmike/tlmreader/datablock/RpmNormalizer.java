package com.monstarmike.tlmreader.datablock;

import java.util.Iterator;
import java.util.List;

public class RpmNormalizer implements DataNormalizer {

	private static final double LIMIT_RPM_VALUE = 10000.0;

	/**
	 * Remove all datablocks with too high rpm values
	 */
	public void normalize(final List<DataBlock> dataBlocks) {
		Iterator<DataBlock> iterator = dataBlocks.iterator();
		while (iterator.hasNext()) {
			DataBlock dataBlock = iterator.next();
			if (dataBlock instanceof StandardBlock) {
				float rpm = ((StandardBlock) dataBlock).getRpm();
				if (rpm > LIMIT_RPM_VALUE) {
					iterator.remove();
				}
			}
		}
	}
}
