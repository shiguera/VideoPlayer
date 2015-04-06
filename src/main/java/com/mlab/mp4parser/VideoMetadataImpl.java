package com.mlab.mp4parser;

import java.net.URL;
import java.util.Date;

public class VideoMetadataImpl implements VideoMetadata {

	
	long length;
	double fps;
	Date dateOfCreation;
	URL url;
	
	VideoMetadataImpl() {
		length = -1l;
		fps = -1.0;
		dateOfCreation = null;
		url=null;
	}
	@Override
	public void setLength(long length) {
		this.length = length;
	}
	@Override
	public long getLength() {
		return length;
	}
	@Override
	public double getFps() {
		return fps;
	}
	@Override
	public void setFps(double fps) {
		this.fps = fps;
	}
	@Override
	public Date getDateOfCreation() {
		return dateOfCreation;
	}
	@Override
	public void setDateOfCreation(Date date) {
		dateOfCreation = new Date(date.getTime());
	}

	@Override
	public URL getUrl() {
		return url;
	}
	@Override
	public void setUrl(URL url) {
		this.url = url;
	}
}
