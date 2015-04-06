package com.mlab.videoplayer.swing;

import java.awt.Dimension;

import javax.swing.JFrame;

public class Window1 {
	private final int DEFAULT_WIDTH = 600;
	private final int DEFAULT_HEIGHT = 400;
	
	private JFrame frame;

	public Window1(String title) {
		frame = new JFrame(title);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
	}
	public void show() {
		frame.pack();
		frame.setVisible(true);
	}
	public JFrame getFrame() {
		return frame;
	}

	
}
