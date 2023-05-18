package com.trickle.os.cr.app;

import com.trickle.os.cr.AppView;
import com.trickle.os.cr.SubApp;

public class Ocr extends SubApp{
	OcrView view = new OcrView(this);
	
	public Ocr() {
		view.initRootPanel();
	}
	
	
	
	@Override
	public AppView requestView() {
		return view;
	}
	
}
