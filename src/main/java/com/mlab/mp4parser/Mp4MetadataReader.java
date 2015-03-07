package com.mlab.mp4parser;

import java.io.IOException;
import java.util.Date;

import com.googlecode.mp4parser.authoring.Movie;
import com.googlecode.mp4parser.authoring.container.mp4.MovieCreator;

public class Mp4MetadataReader implements VideoMetadataReader {

	Movie video;
	
	public Mp4MetadataReader(String filepath) throws IOException {
		video = MovieCreator.build(filepath);
	}
	
	@Override
	public VideoMetadata read() {
		VideoMetadata meta = new VideoMetadataImpl(); 
		meta.setLength(getDuration());
		meta.setFps(getFps());
		meta.setDateOfCreation(getDateOfCreation());
		return meta;
	}
	
	private long getDuration() {
		if(video != null) {
			long duration = video.getTracks().get(0).getDuration();
			long timescale = video.getTracks().get(0).getTrackMetaData().getTimescale();
			return (long)((double)duration/timescale*1000);
		}
		return -1l;
	}
	
	private double getFps() {
		if(video != null) {
			long duration = video.getTracks().get(0).getDuration();
			long durPerSample = duration / video.getTracks().get(0).getSamples().size();
			long timescale = video.getTracks().get(0).getTrackMetaData().getTimescale();
			return (double)timescale/durPerSample;
		}
		return -1l;
	}
	
	private Date getDateOfCreation() {
		if(video != null) {
			return video.getTracks().get(0).getTrackMetaData().getCreationTime();
		}
		return null;
	}
	

}
