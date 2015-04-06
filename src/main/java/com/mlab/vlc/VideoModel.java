package com.mlab.vlc;

import java.io.File;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.MediaPlayer;

import com.mlab.mp4parser.VideoMetadata;
import com.mlab.patterns.Observable;

public interface VideoModel extends Observable {
	
	// Gestion vlc MediaPlayerComponent
	public void setVideoPanelSize(int width, int height);
	EmbeddedMediaPlayerComponent getMediaPlayerComponent();
	MediaPlayer getMediaPlayer();
	boolean isMediaPlayerInitialized();
	
	// Video file
	boolean setVideoFile(File videofile);
	File getVideoFile();

	// Video Metadata
	long getVideoLength();
	long getVideoDateOfCreation();
	double getVideoFps();
	
	// Status
	long getTime();
	boolean isDisplayable();
	boolean isMediaPrepared();
	boolean isMediaParsed();
	boolean isPlayable();
	
	// Video playing controll
	void play();
	void stop();
	void pause();
	void setTime(long time);
		
	// Gestion de VideoModelListener
	boolean addListener(VideoModelListener listener);
	boolean removeListener(VideoModelListener listener);
	
	
	
}
