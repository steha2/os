package com.trickle.os.cr.app;

import com.trickle.os.cr.AppView;
import com.trickle.os.cr.SubApp;
import com.trickle.os.cr.util.CrTool;

public class NaverCr extends SubApp{
	NaverView view = new NaverView(this);
	
	public NaverCr() {
		view.initRootPanel();
	}
	
	@Override
	public AppView requestView() {
		return view;
	}

	public void submit(long d2id, String search, int r) {
		CrTool.cr2(d2id, search, r);
	}
}
