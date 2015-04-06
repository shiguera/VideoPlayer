package com.mlab.vlc;

import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Date;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import junit.framework.Assert;

import org.apache.log4j.PropertyConfigurator;
import org.junit.Test;

import com.coremedia.iso.IsoFile;
import com.coremedia.iso.boxes.CompositionTimeToSample;
import com.coremedia.iso.boxes.CompositionTimeToSample.Entry;
import com.coremedia.iso.boxes.MovieHeaderBox;
import com.coremedia.iso.boxes.TimeToSampleBox;
import com.googlecode.mp4parser.authoring.Movie;
import com.googlecode.mp4parser.authoring.Mp4TrackImpl;
import com.googlecode.mp4parser.authoring.container.mp4.MovieCreator;
import com.mlab.patterns.Observable;

public class SwingTestVideoModel implements VideoModelListener {
	
	private final long maxInizializationMilliseconds = 700l;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new SwingTestVideoModel();
			}
		});
	}
	VideoModel model;
	JFrame frame;
	JPanel panel;
	long t0;
	public SwingTestVideoModel() {
		PropertyConfigurator.configure("log4j.properties");
		
		frame = new JFrame("SwingTestVideoModel");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(600,400));
		
		panel = new JPanel();

		initVideoModel();

//		VideoView view = new SimpleVideoView(model);
//		view.setVideoPanelSize(400, 300);
//		frame.add(view.getMainPanel());
		
		frame.setLocation(200,200);
		frame.pack();
		frame.setVisible(true);
		
			

	}
	private void initVideoModel() {
		System.out.println("TestVideoModel.initVideoModel()");
		t0 = System.currentTimeMillis();
		model = new VideoModelImpl();
		model.addListener(this);		
	}
	// Interface VideoModelListener
	@Override
	public void initializationDone() {
		long t1 = System.currentTimeMillis();
		long elapsed = t1-t0;
		assertInitializationIsQuicklyEnough(elapsed);
		Assert.assertTrue(model.isMediaPlayerInitialized());

		
		Assert.assertFalse(model.getMediaPlayerComponent().isDisplayable());
		boolean result = setVideoFile();
		Assert.assertTrue(result);
		Assert.assertTrue(model.isMediaParsed());
				

		
		VideoView view = new SimpleVideoView(model);
		
		frame.getContentPane().add(view.getMainPanel());
		Assert.assertTrue(model.getMediaPlayerComponent().isDisplayable());
		
		
		
		Assert.assertFalse(model.isPlayable());
		model.getMediaPlayer().start();
		Assert.assertTrue(model.isPlayable());

		//model.getMediaPlayerComponent().getMediaPlayer().stop();
		//model.getMediaPlayer().pause();
		System.out.println("length: " + model.getVideoLength());
		System.out.println("fps: " + model.getVideoFps());
		System.out.println("time: " + model.getTime());
		System.out.println("date: " + model.getVideoDateOfCreation());
				
		Assert.assertEquals(60.0f, model.getMediaPlayer().getFps(),0.1f);

		testSetTimeMethod();
		
	}
	@Override
	public Observable getObservable() {
		return model;
	}
	@Override
	public void update() {
		// TODO
	}

	private void assertInitializationIsQuicklyEnough(long elapsed) {
		System.out.println("TestVideoModel.assertInitializationIsQuicklyEnough()");
		if(elapsed > this.maxInizializationMilliseconds) {
			Assert.fail();
		}
	}
	public void testSetTimeMethod() {
		System.out.println("SwingTestVideoModel.setTimeMethodSetsTime()");
		model.setTime(1000l);
		//System.out.println(model.getTime());
		Assert.assertTrue(model.getTime()>=1000l);
		
		
	}
	public boolean setVideoFile() {
		System.out.println("TestVideoModel.setVideoFile()");
		
		Assert.assertFalse(model.isMediaPrepared());
		Assert.assertFalse(model.isMediaParsed());
		Assert.assertFalse(model.isPlayable());
		
		boolean result = model.setVideoFile(getTestVideoFile());
		Assert.assertTrue(result);
		
		Assert.assertTrue(model.isMediaPrepared());
		Assert.assertTrue(model.isMediaParsed());
		Assert.assertFalse(model.isPlayable());
		
		return result;
	}

	private File getTestVideoFile() {
		//URL url = ClassLoader.getSystemResource("testvideo.mp4");
		URL url = ClassLoader.getSystemResource("gopro60fps.mp4");
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
