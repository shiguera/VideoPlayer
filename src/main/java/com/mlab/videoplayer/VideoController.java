package com.mlab.videoplayer;

import java.awt.image.BufferedImage;

import com.mlab.patterns.Observer;
import com.mlab.vlc.VideoView;

/**
 * El <em>VideoController</em> se utiliza para manejar un 
 * {@link com.mlab.roadplayer.video.VideoModel}.
 * @author shiguera
 *
 */
public interface VideoController extends Observer {
	// VideoFile management
//	boolean setVideoFile(VideoFile videofile);
//	VideoFile getVideoFile();
	// MediaPlayer management
	void play();
	void stop();
	void pause();
	boolean isPlaying();
	long getTime();
	void go(long time);
	void goToBeginning();
	void goToEnd();
	void skipForward();
	void skipBack();
	long getVideoLength();
	/**
	 * Libera los recursos utilizados, quedando estos
	 * inutilizables salvo reinicialización. 
	 */
	void release();
	void resetVideoPlayer();
	// Snapshot
	BufferedImage takeSnapshot();
	// View management
	VideoView getVideoView();
	void setVideoView(VideoView videoview);
	void setVideoPanelSize(int width, int height);	
}
