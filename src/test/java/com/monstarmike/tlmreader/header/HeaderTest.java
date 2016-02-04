package com.monstarmike.tlmreader.header;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.google.common.io.Files;
import com.monstarmike.tlmreader.datablock.HeaderBlock;
import com.monstarmike.tlmreader.datablock.HeaderNameBlock;


/**
 * Unit tests for header blocks.
 */
public class HeaderTest {
	byte[] theBytes = null;

	
	@Before
	public void setup() {
		try {
			this.theBytes = Files.toByteArray(new File(
					"src/test/data/HeaderData.bin"));
		} catch (IOException e) {
			System.out.println("Yo, the header data needs to be in the src/test/data/HeaderData.bin file!");
			e.printStackTrace();
		}
	}

	/**
	 * Checks that the loaded data set for the tests is correct. All data in the
	 * dataset should be 36 byte header information.
	 */
	@Test
	public void testDataSetIsGood() {
		assertTrue(this.theBytes.length % 36 == 0);
	}

	/**
	 * Tests the HeaderBlock.isHeaderBlock check.
	 */
	@Test
	public void testHeaderBlockCheck() {
		boolean allAreHeaders = true;
		for (int i = 0; i < this.theBytes.length; i += 36) {
			byte[] headerBytes = Arrays.copyOfRange(this.theBytes, i, i + 36);
			allAreHeaders = allAreHeaders
					&& HeaderBlock.isHeaderBlock(headerBytes);
		}
		assertTrue(allAreHeaders);
	}

	/**
	 * In our data set the first 36 bytes are a header name block. Use these 36
	 * bytes to test the HeaderNameBlock.isHeaderName() method.
	 */
	@Test
	public void testHeaderNameBlockCheck() {
		byte[] headerBytes = Arrays.copyOfRange(this.theBytes, 0, 36);

		assertTrue(HeaderNameBlock.isHeaderBlock(headerBytes)
				&& HeaderNameBlock.isHeaderName(headerBytes));
	}

	@Test
	public void testHeaderNameBlockModelName() {
		byte[] headerBytes = Arrays.copyOfRange(this.theBytes, 0, 36);

		HeaderNameBlock headerNameBlock = new HeaderNameBlock(headerBytes);
		assertEquals(headerNameBlock.getModelName(), "Gracia Maxi3.5");
	}

}
