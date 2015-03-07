package com.mlab.mp4parser;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

public class TestMp4MetadataReader {

	static VideoMetadataReader goproReader30fps, goproReader60fps, goproReader240fps, roadRecorderReader ;
	static VideoMetadata goproMeta30fps, goproMeta60fps, goproMeta240fps, rrMeta;
	
	@BeforeClass
	public static void setup() throws URISyntaxException, IOException {
		System.out.println("TestMp4MetadataReader.setup()");
		URL url = ClassLoader.getSystemResource("gopro30fps.mp4");
		goproReader30fps = new Mp4MetadataReader(url.getPath());
		goproMeta30fps = goproReader30fps.read();

		url = ClassLoader.getSystemResource("gopro60fps.mp4");
		goproReader60fps = new Mp4MetadataReader(url.getPath());
		goproMeta60fps = goproReader60fps.read();

		url = ClassLoader.getSystemResource("gopro240fps.mp4");
		goproReader240fps = new Mp4MetadataReader(url.getPath());
		goproMeta240fps = goproReader240fps.read();

		URL url2 = ClassLoader.getSystemResource("20130422_195242.mp4");
		roadRecorderReader = new Mp4MetadataReader(url2.getPath());			
		rrMeta = roadRecorderReader.read();
	}
	
	@Test
	public void mediaIsNotNullAfterCreation(){
		System.out.println("TestMp4MetadataReader.mediaIsNotNullAfterCreation()");
		Assert.assertNotNull(((Mp4MetadataReader)goproReader30fps).video);
		Assert.assertNotNull(((Mp4MetadataReader)goproReader60fps).video);
		Assert.assertNotNull(((Mp4MetadataReader)roadRecorderReader).video);
	}
	
	@Test
	public void readMethodReturnsVideoMetadataNotNull()  {
		System.out.println("TestMp4MetadataReader.readMethodReturnsVideoMetadataNotNull()");
		
		Assert.assertNotNull(goproReader30fps);
		Assert.assertNotNull(goproMeta30fps);
	
		Assert.assertNotNull(goproReader60fps);
		Assert.assertNotNull(goproMeta60fps);
	
		Assert.assertNotNull(roadRecorderReader);
		Assert.assertNotNull(rrMeta);

	}
	
	@Test
	public void lengthIsCorrect() {
		System.out.println("TestVideoMetadaImpl.lengthIsCorrect()");
		//System.out.println(goproMeta30fps.getLength());
		Assert.assertEquals(13513l, goproMeta30fps.getLength(),1l);

		//System.out.println(goproMeta60fps.getLength());
		Assert.assertEquals(23239l, goproMeta60fps.getLength(),1l);

		//System.out.println(rrMeta.getLength());		
		Assert.assertEquals(61283l, rrMeta.getLength(),1l); // N coincide exactamente con ffmpeg 
	}
	@Test
	public void fpsIsCorrect() {
		System.out.println("TestVideoMetadaImpl.fpsIsCorrect()");
		//System.out.println(goproMeta30fps.getFps());
		Assert.assertEquals(30.0, goproMeta30fps.getFps(), 0.3);

		//System.out.println(goproMeta60fps.getFps());
		Assert.assertEquals(60.0, goproMeta60fps.getFps(), 0.3);

		//System.out.println(goproMeta240fps.getFps());
		Assert.assertEquals(240.0, goproMeta240fps.getFps(), 0.3);


		//System.out.println(rrMeta.getFps());		
		Assert.assertEquals(30.0, rrMeta.getFps(), 0.3);  
	}

	@Test
	public void dateOfCreationIsCorrect() {
		System.out.println("TestVideoMetadaImpl.dateOfCreationIsCorrect()");
		Assert.assertEquals("2015-03-05 17:46:12", dateTimeToStringGpxFormat(goproMeta30fps.getDateOfCreation().getTime()));

		//System.out.println(goproMeta60fps.getFps());
		Assert.assertEquals("2015-03-06 15:03:30", dateTimeToStringGpxFormat(goproMeta60fps.getDateOfCreation().getTime()));

		//System.out.println(goproMeta240fps.getFps());
		Assert.assertEquals("2015-03-06 15:05:33", dateTimeToStringGpxFormat(goproMeta240fps.getDateOfCreation().getTime()));


		//System.out.println(rrMeta.getFps());		
		Assert.assertEquals("2013-04-22 19:53:46", dateTimeToStringGpxFormat(rrMeta.getDateOfCreation().getTime()));
	}

	private String dateTimeToStringGpxFormat(long t) {
    	SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
   		format.setTimeZone(TimeZone.getTimeZone("UTC"));
    	return format.format(new Date(t));
    }


}
