package com.trickle.os.cr;

import java.time.LocalDateTime;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import com.trickle.os.cr.gui.Gui;
import com.trickle.os.cr.util.Style;

public abstract class AppView {
	protected JPanel rootPanel = new JPanel();
	private SubApp parentApp;
	private String title;
	private ImageIcon icon;
	
	public abstract void initRootPanel();
	
	public AppView() {
		this(null, null);
	}
	
	public AppView(String title) {
		this(title, null);
	}

	public AppView(SubApp parentApp) {
		this(null, parentApp);
	}
	
	public AppView(String title, SubApp parentApp) {
		this.title = title == null || title.isEmpty() ? getClass().getSimpleName() : title;
		if(parentApp != null) {
			this.parentApp = parentApp;
			setImageIcon(Gui.getResizedIcon(parentApp.getImagePath(), CrApplication.IMG_PATH+"defaultimg.png", 35, 35));
		}
	}
	
	public final JPanel getPanel() {
		return rootPanel;
	}
	
	//--------------선택적 Override Methods -----------------
	public String getTitle() {
		return title;
	}
	
	public void setImageIcon(ImageIcon icon) {
		this.icon = icon;
	}
	
	public ImageIcon getImageIcon() {
		return icon;
	}
	
	public void update(LocalDateTime time) {
		//실행 중인 앱은 update() 1초마다 호출됨
		//시간흐름 관련 기능을 추가 하고싶으면 오버라이드해서 사용
	}
	
	/**
	 * @param style.json의 스타일 을 참조합니다. 적용하고 싶으면 오버라이드
	 */
	public void setStyle(Style style) {
		
	}
	
	/**
	 * SubApp 이 닫힐때 해야할 작업이 있다면 작성하세요 (예: 정보 저장)
	 * false 를 리턴할 경우 AppContainer 는 해당 앱을 닫지 않습니다. 
	 */
	public boolean close() {
		return true;
	}
	
	public SubApp parentApp() {
		return parentApp;
	}
	
	//-------------------------------------------------------
}
