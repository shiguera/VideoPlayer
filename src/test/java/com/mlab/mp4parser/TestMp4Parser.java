package com.mlab.mp4parser;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Date;

import org.junit.Test;

import com.coremedia.iso.IsoFile;
import com.coremedia.iso.boxes.MovieHeaderBox;
import com.googlecode.mp4parser.authoring.Movie;
import com.googlecode.mp4parser.authoring.container.mp4.MovieCreator;

public class TestMp4Parser {

	@Test
	public void canReadMovie() {
		System.out.println("TestMp4Parser.canReadMovie()");
		try {
			testISOParser();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void testISOParser() throws IOException, URISyntaxException {
		URL url = ClassLoader.getSystemResource("gopro30fps.mp4");
		IsoFile mp4file = new IsoFile(url.getPath());
		//IsoFile mp4file = new IsoFile("20130422_195242.mp4");
		MovieHeaderBox header = mp4file.getMovieBox().getMovieHeaderBox();
		Date date = header.getCreationTime();
		//System.out.println("date: " + date.toGMTString());
		//System.out.println("duration:" +header.getDuration());
		//System.out.println("rate:" +header.getRate());
		//System.out.println("time scale:" +header.getTimescale());
		//System.out.println("volume:" +header.getVolume());
		//System.out.println("size:" +header.getSize());

		//Movie video = MovieCreator.build("GOPR0039.MP4");
		
		url = ClassLoader.getSystemResource("20130422_195242.mp4");
		Movie video = MovieCreator.build(url.toURI().getPath());
		long duration = 0l;
		for(long d: video.getTracks().get(0).getSampleDurations()) {
			duration += d;
		}
        long durPerSample =  duration /  video.getTracks().get(0).getSamples().size();
        double fps = (double)video.getTracks().get(0).getTrackMetaData().getTimescale() / durPerSample;
        //System.out.println("fps:" + fps);
        //System.out.println("duration:" + video.getTracks().get(0).getDuration());
        long timescale = video.getTracks().get(0).getTrackMetaData().getTimescale();
        //System.out.println("time scale:" + timescale);
        //System.out.println("duration seconds:" + duration / timescale);
        
        
		mp4file.close();
	}

}
