package com.monstarmike.tlmreader;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        TLMReader reader = new TLMReader();
        try {
			reader.Read("/home/mgardner/Desktop/2015-05-11.TLM");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
