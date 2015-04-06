package com.mlab.vlc;

import com.mlab.patterns.Observer;

public interface VideoModelListener extends Observer {

	void initializationDone();
}
