package com.mlab.vlc;

import java.awt.image.BufferedImage;

import org.apache.log4j.Logger;

import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

import com.mlab.patterns.Observable;
import com.mlab.videoplayer.VideoController;

/**
 * El BasicVideoController es un Controller para VideoView y VideoModel.<br/>
 * Consta de un VideoModel y un VideoView. No instancia el VideoView, lo recibe a través del método 
 * setVideoView().<br/>
 * 
 * @author shiguera
 *
 */
public class VideoControllerImpl implements VideoController {

	private final Logger LOG = Logger.getLogger(getClass().getName());
	VideoModel model;
	VideoView videoView;
	
	// Constructor
	public VideoControllerImpl(VideoModel videomodel) {
		LOG.info("Creating video trackMap");
		model = videomodel;
		videoView = null;
	}

	// Interface CompositeObserver
	@Override
	public void update() {
		
	}

	
	// VideoFile management
//	@Override
//	public VideoFile getVideoFile() {
//		return this.model.getVideoFile();
//	}
//	@Override
//	public boolean setVideoFile(VideoFile videofile) {
//		boolean result = ((VideoModel)model).setVideoFile(videofile);
//		if(result) {
//			this.play();
//			RPUtil.pause(100);
//			setPause(true);
//			this.goToBeginning();
//		}
//		return result;
//	}

	// Utility methods
	private void setPause(boolean pause) {
		getMediaPlayer().setPause(pause);
	}
	
	// Manejo del reproductor
	@Override
	public void play() {
		this.model.play();
	}
	@Override
	public void stop() {
		this.model.stop();
	}
	@Override
	public void pause() {
		this.model.pause();
	}
	@Override
	public boolean isPlaying() {
		return model.getMediaPlayerComponent().getMediaPlayer().isPlaying();
	}
	@Override
	public long getTime() {
		return model.getMediaPlayerComponent().getMediaPlayer().getTime();
	}
	@Override
	public void go(long time) {
		model.setTime(time);
	}
	@Override
	public void goToBeginning() {
		//TODO this.model.goToBeginning();
	}
	@Override
	public void goToEnd() {
		// TODO this.model.goToEnd();
	}
	@Override
	public long getVideoLength() {
		return this.model.getVideoLength();
	}

	@Override
	public void skipForward() {
		// TODO this.model.skipForward();
	}
	@Override
	public void skipBack() {
		// TODO this.model.skipBack();
	}
	/*
	 * (non-Javadoc)
	 * @see com.mlab.roadplayer.acore.video.VideoController#release()
	 */
	@Override
	public void release() {
		// TODO this.model.release();
	}
	@Override
	public void resetVideoPlayer() {
		LOG.debug("Resetting MediaPlayer");
		// TODO model.resetMediaPlayer();
	}
	// Snapshot
	@Override
	public BufferedImage takeSnapshot() {
		// TODO BufferedImage image = ((VideoModel)model).getSnapshot();
		return null; //image;
	}

	// Getters
	public EmbeddedMediaPlayer getMediaPlayer() {
		return ((VideoModel)model).getMediaPlayerComponent().getMediaPlayer();
	}

	// View management
	@Override
	public VideoView getVideoView() {
		return this.videoView;
	}
	@Override
	public void setVideoView(VideoView videoview) {
		videoView = videoview;
	}
	@Override
	public void setVideoPanelSize(int width, int height) {
		//LOG.debug(String.format("Setting View dimensions: %d, %d",width, height));
		model.setVideoPanelSize(width, height);
		// TODO videoView.getVideoPanel().revalidate();
		
	}

	
	public VideoView getView() {
		return this.videoView;
	}

	@Override
	public Observable getObservable() {
		return this.model;
	}





}
