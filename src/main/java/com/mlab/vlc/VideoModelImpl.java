package com.mlab.vlc;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingWorker;

import org.apache.log4j.Logger;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.MediaPlayer;

import com.mlab.mp4parser.Mp4MetadataReader;
import com.mlab.mp4parser.VideoMetadata;
import com.mlab.mp4parser.VideoMetadataReader;
import com.mlab.patterns.Observer;

public class VideoModelImpl implements VideoModel {

	private Logger LOG = Logger.getLogger(VideoModelImpl.class);
	
	protected EmbeddedMediaPlayerComponent mediaPlayerComponent;
	protected VideoMetadata videoMetadata;
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
				
		videoMetadata = null;
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
		if(videofile == null) {
			return false;
		}
		videoMetadata = readMetadata(videofile);		
		if(videoMetadata == null) {
			LOG.error("setVideoFile() ERROR: videoMetadata is null"); 
			return false;
		}
		
		isMediaPrepared = mediaPlayerComponent.getMediaPlayer().prepareMedia(
			videofile.getPath(), "");
		if(isMediaPrepared) {
			getMediaPlayer().parseMedia();
		}
		return isMediaPrepared;
	}
	private VideoMetadata readMetadata(File file) {
		try {
			VideoMetadataReader reader = new Mp4MetadataReader(file.getPath());
			VideoMetadata data = reader.read();
			return data;
		} catch (IOException e) {
			LOG.error("readMetadata() ERROR IOException: " +e.getMessage()); 
			return null;
		}
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
	
	@Override
	public boolean registerObserver(Observer o) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean removeObserver(Observer o) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void notifyObservers() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void stopNotifications() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void startNotifications() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void play() {
		if (isDisplayable()) {
			mediaPlayerComponent.getMediaPlayer().play();
		}
		LOG.warn("play() WARNING: mediaPlayerComponent.isDisplayable() == false");
	}
	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setTime(long time) {
		if(isDisplayable()) {
			mediaPlayerComponent.getMediaPlayer().setTime(time);
		}
	}
	@Override
	public long getVideoLength() {
		if (videoMetadata != null) {
			return videoMetadata.getLength();
		}
		return 0l;
	}
	@Override
	public File getVideoFile() {
		if (videoMetadata != null) {
			File file = new File(videoMetadata.getUrl().getFile());
			return file;
		}
		return null;
	}
	@Override
	public long getVideoDateOfCreation() {
		if (videoMetadata != null) {
			return videoMetadata.getDateOfCreation().getTime();
		}
		return 0l;
	}
	@Override
	public double getVideoFps() {
		if (videoMetadata != null) {
			return videoMetadata.getFps();
		}
		return -1.0;
	}
	
	// Status
	@Override
	public long getTime() {
		if (mediaPlayerComponent != null && mediaPlayerComponent.getMediaPlayer() != null) {
			return mediaPlayerComponent.getMediaPlayer().getTime();
		}
		return -1l;
	}
	@Override
	public boolean isDisplayable() {
		if (mediaPlayerComponent != null && mediaPlayerComponent.getMediaPlayer() != null && 
				mediaPlayerComponent.isDisplayable()) {
			return true;
		}		
		return false;
	}

}
