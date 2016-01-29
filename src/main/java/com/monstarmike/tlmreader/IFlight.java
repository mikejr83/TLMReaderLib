package com.monstarmike.tlmreader;

import java.util.Iterator;

import org.joda.time.Duration;

import com.monstarmike.tlmreader.datablock.DataBlock;
import com.monstarmike.tlmreader.datablock.HeaderBlock;
import com.monstarmike.tlmreader.datablock.HeaderNameBlock;
import com.monstarmike.tlmreader.datablock.HeaderRpmBlock;

public interface IFlight {

	Duration get_duration();

	void addHeaderNameBlock(HeaderNameBlock block);
	
	void addRpmHeaderBlock(HeaderRpmBlock rpmBlock);

	void addDataBlock(DataBlock block);

	HeaderRpmBlock getRpmHeader();
	
	Iterator<HeaderBlock> get_headerBlocks();
	
	int getNumberOfDataBlocks();

}