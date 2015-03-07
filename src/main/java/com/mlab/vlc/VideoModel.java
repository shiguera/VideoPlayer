package com.mlab.vlc;

import java.awt.Dimension;
import java.io.File;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.MediaPlayer;

public interface VideoModel {
	
	// Gestion vlc MediaPlayerComponent
	public void setVideoPanelSize(int width, int height);
	EmbeddedMediaPlayerComponent getMediaPlayerComponent();
	MediaPlayer getMediaPlayer();
	boolean isMediaPlayerInitialized();
	boolean setVideoFile(File videofile);
	boolean isMediaPrepared();
	boolean isMediaParsed();
	boolean isPlayable();
	
	// Gestion de VideoModelListener
	boolean addListener(VideoModelListener listener);
	boolean removeListener(VideoModelListener listener);
	
	
}
