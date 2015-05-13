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
			reader.Read("src/test/data/2015 - FSS 2 - day 2.TLM");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
