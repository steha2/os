package com.trickle.os.cr.app;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.*;

import com.trickle.os.cr.AppView;
import com.trickle.os.cr.gui.Gui;
import com.trickle.os.cr.gui.panel.layout.GridBagPanel;

import lombok.RequiredArgsConstructor;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;

@RequiredArgsConstructor
public class OcrView extends AppView{
	JFrame frame;
	
	final Ocr ocrCr;
	GridBagPanel gbPanel = new GridBagPanel();

	JTextArea text = new JTextArea() {{ setLineWrap(true); }};
	JScrollPane sc = new JScrollPane(text);
	JButton button = Gui.createButton("Start", b->start());
	@Override
	public boolean close() {
		if(frame != null) frame.dispose();
		return true;
	}
	
	OcrFrame frame2;
	private void start() {
		text.setFont(Gui.createFont(20));
		if(button.getText().equals("Start")) {
			button.setText("OCR");
			// 스캔시작
			System.out.println(1);
			frame2 = new OcrFrame();
		}else {
			System.out.println(2);
			button.setText("Start");
			//스캔 종료
			frame2.removeHooker();;
			
			System.out.println(frame2.getMacroBounds());
			
			Robot robot;
			try {
				robot = new Robot();
				BufferedImage in = robot.createScreenCapture(frame2.getMacroBounds());;
				ITesseract instance = new Tesseract();
				text.setText( instance.doOCR(in));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			frame2.getFrame().dispose();
			
		}
	}

	@Override
	public void initRootPanel() {
		getPanel().setLayout(new BorderLayout());
		getPanel().add(sc,"Center");
		sc.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		getPanel().add(gbPanel.getPanel(),"South");
		gbPanel.gbc.fill = GridBagConstraints.HORIZONTAL;
		gbPanel.add(button, 1, 1);
	}
}
