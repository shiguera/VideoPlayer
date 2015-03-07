package com.mlab.vlc;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class TestVideoModel {
	private final long maxInizializationMilliseconds = 500l;
	
	VideoModel model;
	private boolean listenerNotified = false;
	@Before
	public void initVideoModelAndVerifyListenersAreNotified() {
		System.out.println("TestVideoModel.initVideoModelAndVerifyListenersAreNotified()");
		long t0 = System.currentTimeMillis();
		model = new VideoModelImpl();
		model.addListener(new VideoModelListener() {
			@Override
			public void initializationDone() {
				listenerNotified = true;
			}
		});

		while(model.isMediaPlayerInitialized()==false) {
			try {
				Thread.sleep(50);
			} catch(Exception e) {
				Assert.fail();
			}
			continue;
		}
		long t1 = System.currentTimeMillis();
		long elapsed = t1-t0;
		assertInitializationIsQuicklyEnough(elapsed);
		Assert.assertTrue(model.isMediaPlayerInitialized());
		Assert.assertTrue(listenerNotified);
	}
	private void assertInitializationIsQuicklyEnough(long elapsed) {
		System.out.println("TestVideoModel.assertInitializationIsQuicklyEnough()");
		if(elapsed > this.maxInizializationMilliseconds) {
			Assert.fail();
		}
	}
	
	@Test 
	public void mediaPlayerComponentIsNotNullAfterInitialization() {
		System.out.println("TestVideoModel.mediaPlayerComponentIsNotNullAfterInitialization()");
		Assert.assertNotNull(model.getMediaPlayerComponent());
	}
	@Test 
	public void testSetVideoFileMethod() {
		System.out.println("TestVideoModel.testSetVideoFileMethod()");
		
		Assert.assertFalse(model.isMediaPrepared());
		Assert.assertFalse(model.isMediaParsed());
		Assert.assertFalse(model.isPlayable());
		
		boolean result = model.setVideoFile(getTestVideoFile());
		Assert.assertTrue(result);
		
		Assert.assertTrue(model.isMediaPrepared());
		Assert.assertTrue(model.isMediaParsed());
		Assert.assertFalse(model.isPlayable());

		
	}
	@Test 
	public void assertFpsIsZeroAfterSetVideoFile() {
		System.out.println("TestVideoModel.assertFpsIsZeroAfterSetVideoFile()");
		boolean result = model.setVideoFile(getTestVideoFile());
		Assert.assertTrue(result);
		
		Assert.assertEquals(0.0f, model.getMediaPlayer().getFps(),0.001f);
	}
	@Test 
	public void assertLengthIsMinusOneAfterSetVideoFile() {
		System.out.println("TestVideoModel.assertLengthIsZeroAfterSetVideoFile()");
		boolean result = model.setVideoFile(getTestVideoFile());
		Assert.assertTrue(result);
		
		Assert.assertEquals(-1.0f, model.getMediaPlayer().getLength(),0.001f);
	}

	private File getTestVideoFile() {
		URL url = ClassLoader.getSystemResource("testvideo.mp4");
		File videofile = null;
		try {
			videofile = new File(url.toURI());
			Assert.assertNotNull(videofile);
		} catch (URISyntaxException e) {
			Assert.fail();
		}
		return videofile;
	}
}
