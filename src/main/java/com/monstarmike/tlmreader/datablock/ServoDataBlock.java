package com.monstarmike.tlmreader.datablock;

import java.util.HashMap;
import java.util.Map;

/**
 * <b>Address 0x0906</b> Servo Data Block (if "Include Servo Data?" is on)<br>
 * <br>
 * Each channel(servo) is stored in 2 bytes<br>
 * Per datablock, there are 6 11-Bit channels und one 9 Bit channel decoded. The
 * Datablocks contains only a subset of the channels.<br>
 * <br>
 * The channels 0-11 are stored with 11 Bit per channel. These channels are
 * encoded in each other datablock<br>
 * The channels 12-16 (12-18(20)*) are stored with 9 Bit per channel. In every
 * datablock, there is one of these channels encoded.<br>
 * * With my DX9, I have no possiblity to check how many 9 Bit channels are
 * decoded.
 * 
 * <h3>Some examples (recorded with a DX9):</h3>
 * <ul>
 * <li>0x09, 0x06, Cha2, Cha3, Cha5, Cha6, Cha7, Cha11, Cha13</li>
 * <li>0x09, 0x06, Cha1, Cha8, Cha4, Cha9, Cha10, Cha12, Cha13 or Cha17</li>
 * <li>0x09, 0x06, Cha2, Cha3, Cha5, Cha6, Cha7, Cha11, Cha14</li>
 * <li>0x09, 0x06, Cha1, Cha8, Cha4, Cha9, Cha10, Cha12, Cha14 or Cha18</li>
 * <li>0x09, 0x06, Cha2, Cha3, Cha5, Cha6, Cha7, Cha11, Cha15</li>
 * <li>0x09, 0x06, Cha1, Cha8, Cha4, Cha9, Cha10, Cha12, Cha15 or Cha19 (not
 * existing?)</li>
 * <li>0x09, 0x06, Cha2, Cha3, Cha5, Cha6, Cha7, Cha11, Cha16</li>
 * <li>0x09, 0x06, Cha1, Cha8, Cha4, Cha9, Cha10, Cha12, Cha16 or Cha20 (not
 * existing?)</li>
 * </ul>
 * <h3>11 Bit Channel-Encoding</h3>
 * <ul>
 * <li>Bit 0 (most significant bit): meaning not clear. Perhaps used for the
 * rotated 9 Bit Channels?**
 * <li>Bit 0x1-0x4: The channelnumber</li>
 * <li>Bit 0x5-0xF: The value of the channel (11 Bit, 2048 steps, middle
 * position 1024 = 0%, range +-150%)</li>
 * </ul>
 * 
 * <h3>9 Bit Channel-Encoding</h3>
 * <ul>
 * <li>Bit 0: Always zero
 * <li>Bit 0x1-0x4: The channelnumber</li>
 * <li>Bit 0x5-0x6: The subChannelnumber, these bits are rotating in each
 * datablock.**</li>
 * <li>Bit 0x7-0xF: The value of the channel (9 Bit, 512 steps, middle position
 * 256 = 0%, range +-150%)</li>
 * </ul>
 * * With this rotation, we can only decode 16 channel. The first Bit of the
 * first 11-Bit channel in a datablock has a Bit which is set accordingly to the
 * channels in the datablock. Perhaps, this one is the key for the channels
 * 17-18(20) <br>
 */
public class ServoDataBlock extends DataBlock {

	Map<Integer, Short> channelValues;

	public boolean hasChannel(int channelNumber) {
		if (channelValues == null) {
			decodeAllChannels();
		}
		return channelValues.containsKey(channelValues);
	}

	private void decodeAllChannels() {
		channelValues = new HashMap<Integer, Short>();
		decode11BitChannel(this.rawData[0x06], this.rawData[0x07]);
		decode11BitChannel(this.rawData[0x08], this.rawData[0x09]);
		decode11BitChannel(this.rawData[0x0A], this.rawData[0x0B]);
		decode11BitChannel(this.rawData[0x0C], this.rawData[0x0D]);
		decode11BitChannel(this.rawData[0x0E], this.rawData[0x0F]);
		decode11BitChannel(this.rawData[0x10], this.rawData[0x11]);
		decode9BitChannel(this.rawData[0x06], this.rawData[0x12], this.rawData[0x13]);
	}

	private void decode11BitChannel(byte first, byte second) {
		int channelNumber = (first & 0x78) >> 3;
		short channelValue = (short) (((first & 0x07) << 8) + (second & 0xFF));
		channelValues.put(channelNumber, channelValue);
	}

	private void decode9BitChannel(byte blockByte, byte first, byte second) {
		int channelNumber = (first & 0x78) >> 3;
		// always 12 on a 9Bit Channel
		int channelSubNumber = ((blockByte & 0x80) >> 5) + ((first & 0x06) >> 1);
		short channelValue = (short) (((first & 0x01) << 8) + (second & 0xFF));
		channelValues.put(channelNumber + channelSubNumber, channelValue);
	}

	public Short getChannelValue(int channelNumber) {
		if (channelValues == null) {
			decodeAllChannels();
		}
		return channelValues.get(channelNumber);
	}

	public ServoDataBlock(byte[] rawData) {
		super(rawData);
		decodeAllChannels();
	}
}
