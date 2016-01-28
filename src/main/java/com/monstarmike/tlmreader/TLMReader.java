package com.monstarmike.tlmreader;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import com.monstarmike.tlmreader.datablock.DataBlock;
import com.monstarmike.tlmreader.datablock.HeaderBlock;
import com.monstarmike.tlmreader.datablock.HeaderDataBlock;
import com.monstarmike.tlmreader.datablock.HeaderNameBlock;
import com.monstarmike.tlmreader.datablock.HeaderRpmBlock;
import com.monstarmike.tlmreader.datablock.HeaderVoltBlock;

public class TLMReader implements Iterable<Flight> {
	ArrayList<Flight> flights;
	private int byteCounter;

	public TLMReader() {
		flights = new ArrayList<Flight>();
	}

	public void Read(String path) throws IOException {
		this.Read(new FileInputStream(new File(path)));
	}

	public void Read(InputStream inputStream) throws IOException {
		BufferedInputStream bufferedStream = new BufferedInputStream(inputStream);
		Flight currentFlight = null;
		byte[] headerTest = new byte[4];
		bufferedStream.mark(4);
		while (bufferedStream.read(headerTest, 0, 4) == 4) {
			bufferedStream.reset();
			if (HeaderBlock.isHeaderBlock(headerTest)) {
				byte[] headerBytes = new byte[36];
				byteCounter += bufferedStream.read(headerBytes, 0, 36);
				if (HeaderNameBlock.isHeaderName(headerBytes)) {
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
			} else {
				byte[] dataBytes = new byte[20];
				byteCounter += bufferedStream.read(dataBytes, 0, 20);
				DataBlock db = DataBlock.createDataBlock(dataBytes, currentFlight);
				currentFlight.addBlock(db);
			}
			bufferedStream.mark(4);
		}
	}

	public Iterator<Flight> iterator() {
		return this.flights.iterator();
	}

	public boolean hasFlights() {
		return flights.size() > 0;
	}

	public int getNumberOfBytesRead() {
		return byteCounter;
	}
}
