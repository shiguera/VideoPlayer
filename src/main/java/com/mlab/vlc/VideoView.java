package com.mlab.vlc;

import javax.swing.JPanel;

public interface VideoView {
	
	JPanel getMainPanel();
	void setVideoPanelSize(int width, int height);

}
