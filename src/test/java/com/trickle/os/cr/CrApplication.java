package com.trickle.os.cr;

import javax.swing.Timer;

import com.trickle.os.cr.app.GoogleCr;
import com.trickle.os.cr.app.NaverCr;
import com.trickle.os.cr.app.Ocr;
import com.trickle.os.cr.gui.Gui;
import com.trickle.os.cr.test.Debug;

public class CrApplication {
	public static final String RES_PATH = "src/main/resources/";
	public static final String OS_IMG= "D:/resources/images";
	public static final String IMG_PATH = RES_PATH + "images/";
	
	public static void main(String[] args) {
		Gui.setLookAndFeel(Gui.NIMBUS);
		new CrApplication().run();
	}

	//앱 추가부분
	public void run() {
		long start = System.currentTimeMillis();
		Debug.sysout(getClass() +" start...");
		
		AppService service = AppService.instance();
		service.addSubApp(new NaverCr());
		service.addSubApp(new GoogleCr());
		service.addSubApp(new Ocr());
//		service.setAttr("member", new MemberDTO("dummy","1234","name1","",""));
//		service.setAttr("id", "dummy");
		service.start();

		//앱 시작시 로그인 창을 띄운다
		
		//전체 동작 타이머 시작
		new Timer(1000, e->service.update()).start();
	
		long end = System.currentTimeMillis();
		Debug.sysout("Run time: "+ (end-start)+" ms ");
	}
}
