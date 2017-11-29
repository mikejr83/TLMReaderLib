package com.monstarmike.tlmreader;

import java.io.BufferedInputStream;
import java.io.IOException;

import com.monstarmike.tlmreader.datablock.HeaderBlock;
import com.monstarmike.tlmreader.datablock.HeaderNameBlock;

/**
 * 
 */
public abstract class TlmParser {
	
	int byteCounter;

	/**
	 * @return The size of the TLM file.
	 */
	public int getNumberOfBytesRead() {
		return byteCounter;
	}

	protected void parseStream(BufferedInputStream bufferedStream) throws IOException {
		int flightNumber = -1;
		byte[] headerTest = new byte[4];
		bufferedStream.mark(4);
		while (bufferedStream.read(headerTest, 0, 4) == 4) {
			bufferedStream.reset();
			if (HeaderBlock.isHeaderBlock(headerTest)) {
				byte[] headerBytes = new byte[36];
				byteCounter += bufferedStream.read(headerBytes, 0, 36);
				if (HeaderNameBlock.isHeaderName(headerBytes)) {
					flightNumber++;
				}
				handleHeaderBlock(headerBytes, flightNumber);
			} else {
				byte[] dataBytes = new byte[20];
				byteCounter += bufferedStream.read(dataBytes, 0, 20);
				handleDataBlock(dataBytes, flightNumber);
			}
			bufferedStream.mark(4);
		}
	}

	protected abstract void handleDataBlock(byte[] dataBytes, int flightNumber);

	protected abstract void handleHeaderBlock(byte[] headerBytes, int flightNumber);
}