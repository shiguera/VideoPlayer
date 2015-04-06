package com.mlab.videoplayer;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestVideoPlayerMain {

	private final Logger LOG = Logger.getLogger(TestVideoPlayerMain.class);
	@BeforeClass
	public static void setup() {
		PropertyConfigurator.configure("log4j.properties");
	}
	@Test
	public void existsLogPropertiesFile() {
		System.out.println("existsLogPropertiesFile()");
		File file = new File("log4j.properties");
		Assert.assertTrue(file.exists());
	}
	@Test
	public void existsLogFile() {
		System.out.println("existsLogFile()");
		LOG.info("existsLogFile()");
		File file = new File("videoplayer.log");
		Assert.assertTrue(file.exists());
	}
	@Test
	public void mainWindowIsNotNullAfterStart() throws InvocationTargetException, InterruptedException {
		System.out.println("mainWindowIsNotNullAfterStart()");
		VideoPlayerMain vpm = new VideoPlayerMain();
		Assert.assertNotNull(vpm);
		Assert.assertNotNull(vpm.mainWindow);
	}
}
