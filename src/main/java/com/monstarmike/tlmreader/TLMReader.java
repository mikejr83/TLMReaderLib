package com.monstarmike.tlmreader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import com.monstarmike.tlmreader.datablock.DataBlock;
import com.monstarmike.tlmreader.datablock.HeaderBlock;
import com.monstarmike.tlmreader.datablock.HeaderDataBlock;
import com.monstarmike.tlmreader.datablock.HeaderNameBlock;

public class TLMReader {
	ArrayList<Flight> flights;
	
	public TLMReader() {
		flights = new ArrayList<Flight>();
	}
	
	public void Read(String path) throws IOException {			
		byte[] theBytes = FileUtils.readFileToByteArray(new File(path));
		
		this.Read(theBytes);
		
		for(Flight f : this.flights) {
			System.out.println(f);
		}
	}
	
	public void Read(byte[] bytes) {
		int i = 0;
		Flight currentFlight = null;
		while((i + 4) < bytes.length) {
			byte[] headerTest = java.util.Arrays.copyOfRange(bytes, i, i + 4);
			if (HeaderBlock.isHeaderBlock(headerTest)) {
				byte[] headerBytes = java.util.Arrays.copyOfRange(bytes, i, i + 36);
				if(HeaderNameBlock.isHeaderName(headerBytes)) {
					currentFlight = new Flight();
					currentFlight.addBlock(new HeaderNameBlock(headerBytes));
					this.flights.add(currentFlight);
				} else {
					currentFlight.addBlock(new HeaderDataBlock(headerBytes));
				}
				
				i += 36;
			}
			else {
				DataBlock db = DataBlock.createDataBlock(java.util.Arrays.copyOfRange(bytes, i, i + 20));
				currentFlight.addBlock(db);
				i += 20;	
			}
		}
	}
}
