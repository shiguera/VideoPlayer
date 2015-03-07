package com.mlab.mp4parser;

import java.util.Date;

public class VideoMetadataImpl implements VideoMetadata {

	
	long length;
	double fps;
	Date dateOfCreation;
	
	VideoMetadataImpl() {
		
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

}
