package com.mlab.vlc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class SimpleVideoView implements VideoView {

	JPanel mainPanel;
	VideoModel model;
	
	public SimpleVideoView(VideoModel model) {
		this.model = model;
		createMainPanel();
	}
	
	private void createMainPanel() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		mainPanel.add(model.getMediaPlayerComponent(), BorderLayout.CENTER);
	}

	
	@Override
	public JPanel getMainPanel() {
		return mainPanel;
	}

	@Override
	public void setVideoPanelSize(int width, int height) {
		model.setVideoPanelSize(width, height);
	}

}
