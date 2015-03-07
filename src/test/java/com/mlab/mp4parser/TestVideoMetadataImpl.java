package com.mlab.mp4parser;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class TestVideoMetadataImpl {

	VideoMetadata goproMeta, rrMeta;
	
	@Before
	public void setup() throws URISyntaxException, IOException {
		System.out.println("TestVideoMetadaImpl.setup()");
	}
	@Test
	public void metadataIsNotNullAfterCreation() {
		System.out.println("TestVideoMetadaImpl.metadataIsNotNullAfterCreation()");
		Assert.assertNotNull(goproMeta);
	}
	
	@Test
	public void metadataIsNotNull() {
		System.out.println("TestVideoMetadaImpl.metadataIsNotNull()");
		Assert.assertNotNull(goproMeta);
		Assert.assertNotNull(rrMeta);		
	}


}
