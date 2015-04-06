package com.mlab.videoplayer;

import java.awt.EventQueue;
import java.lang.reflect.InvocationTargetException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.mlab.videoplayer.swing.Window1;


public class VideoPlayerMain {

	private static final Logger LOG = Logger.getLogger(VideoPlayerMain.class);
	private static final String MAIN_WINDOW_TITLE = "VideoPlayerMain";
	
	Window1 mainWindow;
	
	public static void main(String[] args) {
		PropertyConfigurator.configure("log4j.properties");
		LOG.info("main()");		
		try {
			new VideoPlayerMain();
		} catch (InvocationTargetException e) {
			LOG.error(e.getMessage());
		} catch (InterruptedException e) {
			LOG.error(e.getMessage());
		}
	}
	class Starter implements Runnable {
		@Override
		public void run() {
			createMainWindow();
		}
	}
	
	public VideoPlayerMain() throws InvocationTargetException, InterruptedException {
		LOG.info("VideoPlayerMain()");
		Starter starter = new Starter();
		EventQueue.invokeAndWait(starter);
	}
	private void createMainWindow() {
		LOG.info("CreateMainWindow()");		
		mainWindow = new Window1(MAIN_WINDOW_TITLE);
		mainWindow.show();
		
	}
	
	

}
