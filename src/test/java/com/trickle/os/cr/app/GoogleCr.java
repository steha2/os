package com.trickle.os.cr.app;

import com.trickle.os.cr.AppView;
import com.trickle.os.cr.SubApp;

public class GoogleCr extends SubApp{
	GoogleView view = new GoogleView(this);
	
	public GoogleCr() {
		view.initRootPanel();
	}
	
	@Override
	public AppView requestView() {
		return view;
	}
	
}
