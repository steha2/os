package com.trickle.os.cr;

import java.time.LocalDateTime;

public abstract class SubApp implements Comparable<SubApp>{
	protected int index;
	
	//--------------선택적 Override Methods -----------------
	public String getTitle() {
		//기본값은 클래스 이름입니다. 쉽게 알아볼수 있는 이름으로 Override 하기
		return getClass().getSimpleName();
	}
	
	public String getImagePath() {
		return CrApplication.IMG_PATH+getClass().getSimpleName()+".PNG";
	}
	
	public void update(LocalDateTime time) {
		//실행 중인 앱은 update() 1초마다 호출됨
		//시간흐름 관련 기능을 추가 하고싶으면 오버라이드해서 사용
	}
	
	/**
	 * SubApp 이 닫힐때 해야할 작업이 있다면 작성하세요 (예: 정보 저장)
	 * false 를 리턴할 경우 AppContainer 는 해당 앱을 닫지 않습니다. 
	 */
	public boolean close() {
		return true;
	}
	//-------------------------------------------------------
	
	@Override
	public int compareTo(SubApp another) {
		int c = another.index - this.index;
		if(c == 0) return this.getTitle().compareTo(another.getTitle());
		else return c;
	}
	
	public abstract AppView requestView();
}
