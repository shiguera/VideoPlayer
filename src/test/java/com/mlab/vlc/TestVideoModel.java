package com.mlab.vlc;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import junit.framework.Assert;

import org.apache.log4j.PropertyConfigurator;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mlab.patterns.Observable;

public class TestVideoModel {
	private static final long maxInizializationMilliseconds = 7000l;
	
	static VideoModel model;
	static private boolean listenerNotified = false;
	@BeforeClass
	public static void setupVideoModelAndVerifyListenersAreNotified() {
		System.out.println("TestVideoModel.setupVideoModelAndVerifyListenersAreNotified()");
		PropertyConfigurator.configure("log4j.properties");
		long t0 = System.currentTimeMillis();
		model = new VideoModelImpl();
		model.addListener(new VideoModelListener() {
			@Override
			public void initializationDone() {
				listenerNotified = true;
			}

			@Override
			public Observable getObservable() {
				return model;
			}

			@Override
			public void update() {
				// TODO Auto-generated method stub				
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
	private static void assertInitializationIsQuicklyEnough(long elapsed) {
		System.out.println("TestVideoModel.assertInitializationIsQuicklyEnough()");
		if(elapsed > maxInizializationMilliseconds) {
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
	public void methodSetVideoFileReadsMetadata() {
		System.out.println("TestVideoModel.methodSetVideoFileReadsMetadata()");	
		
		boolean result = model.setVideoFile(getTestVideoFile());
		Assert.assertTrue(result);
		Assert.assertEquals(30.0, model.getVideoFps(), 0.01);
		Assert.assertEquals(9102l, model.getVideoLength(), 0l);
		System.out.println(model.getVideoDateOfCreation());
		Assert.assertEquals(1403784173000l,model.getVideoDateOfCreation(), 0l);
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

	@Test
	public void setTimeMethodSetsTime() {
		System.out.println("TestVideoModel.setTimeMethodSetsTime()");
		Assert.assertTrue(model.setVideoFile(getTestVideoFile()));
		model.play();
		model.setTime(1000l);
		System.out.println(model.getTime());
		Assert.assertTrue(model.getTime()>1000l);
		
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
