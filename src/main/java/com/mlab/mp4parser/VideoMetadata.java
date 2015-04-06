package com.mlab.mp4parser;

import java.net.URL;
import java.util.Date;

public interface VideoMetadata {

	URL getUrl();
	void setUrl(URL url);
	
	/**
	 * Duración del vídeo en milisegundos
	 * @return
	 */
	public long getLength();
	void setLength(long length);
	
	public double getFps();
	public void setFps(double fps);
	
	public Date getDateOfCreation();
	public void setDateOfCreation(Date date);
}
