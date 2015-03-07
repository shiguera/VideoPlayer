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

import com.coremedia.iso.IsoFile;
import com.coremedia.iso.boxes.CompositionTimeToSample;
import com.coremedia.iso.boxes.CompositionTimeToSample.Entry;
import com.coremedia.iso.boxes.MovieHeaderBox;
import com.coremedia.iso.boxes.TimeToSampleBox;
import com.googlecode.mp4parser.authoring.Movie;
import com.googlecode.mp4parser.authoring.Mp4TrackImpl;
import com.googlecode.mp4parser.authoring.container.mp4.MovieCreator;

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
		//model.addListener(this);
		//setVideoFile();
		
		//panel.add(model.getMediaPlayerComponent());
		//frame.getContentPane().add(panel);
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
		model.getMediaPlayerComponent().setBackground(Color.green);
		frame.getContentPane().add(model.getMediaPlayerComponent());
		Assert.assertTrue(model.getMediaPlayerComponent().isDisplayable());
		
		
		boolean result = setVideoFile();
		Assert.assertTrue(result);
		Assert.assertTrue(model.isMediaParsed());
				
		
		Assert.assertFalse(model.isPlayable());
		model.getMediaPlayer().start();
		Assert.assertTrue(model.isPlayable());

		//model.getMediaPlayerComponent().getMediaPlayer().stop();
		model.getMediaPlayer().pause();
		System.out.println("length: " + model.getMediaPlayer().getLength());
		System.out.println("fps: " + model.getMediaPlayer().getFps());
		System.out.println("time: " + model.getMediaPlayer().getTime());
		System.out.println("date: " + model.getMediaPlayer().getMediaMeta().getDate());
		System.out.println("date: " + model.getMediaPlayer().getMediaMeta().getDate());
		
		
		Assert.assertEquals(30.0f, model.getMediaPlayer().getFps(),0.1f);

		
	}

	private void assertInitializationIsQuicklyEnough(long elapsed) {
		System.out.println("TestVideoModel.assertInitializationIsQuicklyEnough()");
		if(elapsed > this.maxInizializationMilliseconds) {
			Assert.fail();
		}
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
		URL url = ClassLoader.getSystemResource("GOPR0037.MP4");
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
