package com.mlab.vlc;

import java.awt.Dimension;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingWorker;

import org.apache.log4j.Logger;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.MediaPlayer;

public class VideoModelImpl implements VideoModel {

	private Logger LOG = Logger.getLogger(VideoModelImpl.class);
	
	protected EmbeddedMediaPlayerComponent mediaPlayerComponent;
	/**
	 * El MediaPlayer de vlc tarda en inicializarse. 
	 * Se hace el proceso en asíncrono y se establece
	 * isInitialized a true cuando acaba la inicialización
	 */
	private boolean isInitialized;
	/**
	 * Al cargar un nuevo fichero de vídeo hay que hacer el prepareMedia()
	 * esta variable guarda el resultado del proceso
	 */
	private boolean isMediaPrepared;

	protected List<VideoModelListener> listeners;
	
	public VideoModelImpl() {
		
		listeners = new ArrayList<VideoModelListener>();
		
		isInitialized = false;
		MediaPlayerCreator loader = new MediaPlayerCreator();
		loader.execute();
		try {
			loader.get();
		} catch (Exception e) {
			LOG.error("VideoModelImpl() ERROR in initialization"); 
		}
				
		isMediaPrepared = false;

	}
	private void initialized(EmbeddedMediaPlayerComponent mediaPComponent) {
		LOG.debug("initialized()");
		mediaPlayerComponent = mediaPComponent;
		isInitialized = true;
		for(VideoModelListener listener: listeners) {
			listener.initializationDone();
		}
	}
	@Override
	public EmbeddedMediaPlayerComponent getMediaPlayerComponent() {
		return mediaPlayerComponent;
	}
	
	class MediaPlayerCreator extends SwingWorker<EmbeddedMediaPlayerComponent, Object> {

		EmbeddedMediaPlayerComponent mediaPComponent;
		@Override
		protected EmbeddedMediaPlayerComponent doInBackground() throws Exception {
			mediaPComponent = new EmbeddedMediaPlayerComponent();
			return mediaPComponent;
		}
		@Override
		protected void done() {
			LOG.debug("done()");
			initialized(mediaPComponent);
		}
		
	}

	@Override
	public boolean isMediaPlayerInitialized() {
		return isInitialized;
	}
	@Override
	public boolean addListener(VideoModelListener listener) {
		return listeners.add(listener);
	}
	@Override
	public boolean removeListener(VideoModelListener listener) {
		return listeners.remove(listener);
	}
	@Override
	public boolean setVideoFile(File videofile) {
		isMediaPrepared = mediaPlayerComponent.getMediaPlayer().prepareMedia(
			videofile.getPath(), "");
		if(isMediaPrepared) {
			getMediaPlayer().parseMedia();
		}
		return isMediaPrepared;
	}
	@Override
	public MediaPlayer getMediaPlayer() {
		if(mediaPlayerComponent != null) {
			return mediaPlayerComponent.getMediaPlayer();			
		} else {
			return null;
		}
	}
	@Override
	public boolean isMediaParsed() {
		if(getMediaPlayer() != null && getMediaPlayer().getMediaState() != null) {
			return getMediaPlayer().isMediaParsed();			
		}
		return false;
	}
	@Override
	public boolean isPlayable() {
		if(getMediaPlayer() != null) {
			return getMediaPlayer().isPlayable();
		} else {
			return false;
		}
	}	
	@Override
	public boolean isMediaPrepared() {
		
		return isMediaPrepared;
	}
	@Override
	public void setVideoPanelSize(int width, int height) {
		mediaPlayerComponent.setPreferredSize(new Dimension(width, height));
	}
}
