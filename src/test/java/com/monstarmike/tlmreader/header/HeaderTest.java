package com.monstarmike.tlmreader.header;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;

import com.monstarmike.tlmreader.datablock.HeaderBlock;
import com.monstarmike.tlmreader.datablock.HeaderNameBlock;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit tests for header blocks.
 */
public class HeaderTest extends TestCase {
	byte[] theBytes = null;

	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public HeaderTest(String testName) {
		super(testName);

		try {
			this.theBytes = FileUtils.readFileToByteArray(new File(
					"src/test/data/HeaderData.bin"));
		} catch (IOException e) {
			System.out.println("Yo, the header data needs to be in the src/test/data/HeaderData.bin file!");
			e.printStackTrace();
		}
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(HeaderTest.class);
	}

	/**
	 * Checks that the loaded data set for the tests is correct. All data in the
	 * dataset should be 36 byte header information.
	 */
	public void testDataSetIsGood() {
		assertTrue(this.theBytes.length % 36 == 0);
	}

	/**
	 * Tests the HeaderBlock.isHeaderBlock check.
	 */
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
	public void testHeaderNameBlockCheck() {
		byte[] headerBytes = Arrays.copyOfRange(this.theBytes, 0, 36);

		assertTrue(HeaderNameBlock.isHeaderBlock(headerBytes)
				&& HeaderNameBlock.isHeaderName(headerBytes));
	}

	public void testHeaderNameBlockModelName() {
		byte[] headerBytes = Arrays.copyOfRange(this.theBytes, 0, 36);

		HeaderNameBlock headerNameBlock = new HeaderNameBlock(headerBytes);
		assertEquals(headerNameBlock.get_modelName(), "Gracia Maxi3.5");
	}

}
