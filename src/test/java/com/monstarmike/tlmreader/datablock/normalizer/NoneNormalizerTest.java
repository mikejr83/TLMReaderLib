package com.monstarmike.tlmreader.datablock.normalizer;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.monstarmike.tlmreader.datablock.DataBlock;

@RunWith(MockitoJUnitRunner.class)
public class NoneNormalizerTest {

	@Mock
	List<DataBlock> blocks;
	
	@Test
	public void test() {
		NoneNormalizer noneNormalizer = new NoneNormalizer();
		noneNormalizer.normalize(blocks);
		Mockito.verifyZeroInteractions(blocks);
	}

}
