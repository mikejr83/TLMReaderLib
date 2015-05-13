package com.monstarmike.tlmreader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import com.monstarmike.tlmreader.datablock.Block;
import com.monstarmike.tlmreader.datablock.HeaderBlock;

public class TLMReader {
	ArrayList<Block> blocks;
	
	public TLMReader() {
		blocks = new ArrayList<Block>();
	}
	
	public void Read(String path) throws IOException {			
		byte[] theBytes = FileUtils.readFileToByteArray(new File(path));
		
		this.Read(theBytes);
		
		for(Block b : this.blocks) {
			System.out.println(b.toString());
		}
	}
	
	public void Read(byte[] bytes) {
		int i = 0;
		while((i + 3) < bytes.length) {
			byte[] headerTest = java.util.Arrays.copyOfRange(bytes, i, i + 3);
			if (HeaderBlock.isHeaderBlock(headerTest)) {
				HeaderBlock headerBlock = new HeaderBlock(java.util.Arrays.copyOfRange(bytes, i, i + 18));
				this.blocks.add(headerBlock);
				i += 18;
			}
			else {
				i += 10;	
			}
		}
	}
}
