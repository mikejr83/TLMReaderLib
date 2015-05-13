package com.monstarmike.tlmreader.datablock;

public abstract class DataBlock extends Block {

	public DataBlock(byte[] rawData) {
		super(rawData);
		// TODO Auto-generated constructor stub
	}

	public static DataBlock createDataBlock(byte[] bytes) {
		DataBlock block = null;
		switch(bytes[4]) {
		case 0:
			//unknown
			break;
			
		case 16:
			//gps
			break;
			
		case 17:
			//speed or rpm? 
			break;
			
		case 18:
			//altitude
			block = new AltitudeBlock(bytes);
			break;
			
		case 20:
			//not sure - deals with x,y,z data
			break;
			
		case 126:
			
			break;
			
		case 127:
			
			break;
		}
		return block;
	}
	
	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return 20;
	}
}
