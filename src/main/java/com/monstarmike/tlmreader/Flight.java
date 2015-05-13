package com.monstarmike.tlmreader;

import java.util.ArrayList;

import com.monstarmike.tlmreader.datablock.Block;
import com.monstarmike.tlmreader.datablock.DataBlock;
import com.monstarmike.tlmreader.datablock.HeaderDataBlock;
import com.monstarmike.tlmreader.datablock.HeaderNameBlock;

public class Flight {
	ArrayList<Block> data;

	String modelName;

	public Flight() {
		data = new ArrayList<Block>();
	}

	public void addBlock(HeaderNameBlock block) {
		this.modelName = block.get_modelName();
		this.data.add(block);
	}

	public void addBlock(HeaderDataBlock block) {
		this.data.add(block);
	}

	public void addBlock(DataBlock block) {
		this.data.add(block);
	}

	@Override
	public String toString() {
		return this.modelName;
	}
}
