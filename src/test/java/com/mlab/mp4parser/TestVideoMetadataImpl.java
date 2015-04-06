package com.mlab.mp4parser;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class TestVideoMetadataImpl {

	VideoMetadata meta;
	
	@Before
	public void setup() throws URISyntaxException, IOException {
		System.out.println("TestVideoMetadaImpl.setup()");
		meta = new VideoMetadataImpl();
	}
	@Test
	public void metadataIsNotNullAfterCreation() {
		System.out.println("TestVideoMetadaImpl.metadataIsNotNullAfterCreation()");
		Assert.assertNotNull(meta);
	}
	


}
