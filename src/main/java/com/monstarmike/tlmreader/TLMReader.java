package com.monstarmike.tlmreader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import com.google.common.io.Files;
import com.monstarmike.tlmreader.datablock.DataBlock;
import com.monstarmike.tlmreader.datablock.HeaderBlock;
import com.monstarmike.tlmreader.datablock.HeaderDataBlock;
import com.monstarmike.tlmreader.datablock.HeaderNameBlock;
import com.monstarmike.tlmreader.datablock.HeaderRpmBlock;
import com.monstarmike.tlmreader.datablock.HeaderVoltBlock;

public class TLMReader implements Iterable<Flight> {
	ArrayList<Flight> flights;
	
	public TLMReader() {
		flights = new ArrayList<Flight>();
	}
	
	public void Read(String path) throws IOException {		
		
		byte[] theBytes = Files.toByteArray(new File(path));
		
		this.Read(theBytes);
	}
	
	public void Read(byte[] bytes) {
		int i = 0;
		Flight currentFlight = null;
		while((i + 4) < bytes.length) {
			byte[] headerTest = Arrays.copyOfRange(bytes, i, i + 4);
			if (HeaderBlock.isHeaderBlock(headerTest)) {
				byte[] headerBytes = Arrays.copyOfRange(bytes, i, i + 36);
				if(HeaderNameBlock.isHeaderName(headerBytes)) {
					currentFlight = new Flight();
					currentFlight.addBlock(new HeaderNameBlock(headerBytes));
					this.flights.add(currentFlight);
				} else if (HeaderRpmBlock.isRpmHeader(headerBytes)) {
					currentFlight.addRpmHeaderBlock(new HeaderRpmBlock(headerBytes));
				} else if (HeaderVoltBlock.isVoltHeader(headerBytes)) {
					currentFlight.addBlock(new HeaderVoltBlock(headerBytes));
				} else {
					currentFlight.addBlock(new HeaderDataBlock(headerBytes));
				}
				
				i += 36;
			}
			else {
				DataBlock db = DataBlock.createDataBlock(Arrays.copyOfRange(bytes, i, i + 20), currentFlight);
				currentFlight.addBlock(db);
				i += 20;	
			}
		}
	}

	public Iterator<Flight> iterator() {
		return this.flights.iterator();
	}
}
