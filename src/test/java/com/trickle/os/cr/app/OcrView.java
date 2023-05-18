package com.trickle.os.cr.app;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import com.trickle.os.cr.AppView;
import com.trickle.os.cr.gui.Gui;
import com.trickle.os.cr.gui.panel.layout.GridBagPanel;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OcrView extends AppView{
	JFrame frame;
	
	final Ocr ocrCr;

	GridBagPanel gbPanel = new GridBagPanel();

	JTextField text = new JTextField();
	JButton button = Gui.createButton("시작", b->start());
	@Override
	public boolean close() {
		if(frame != null) frame.dispose();
		return true;
	}
	
	private void start() {
		if(button.getText().equals("Start")) {
			button.setText("OCR");
			// 스캔시작
		}else {
			button.setText("Start");
			//스캔 종료
		}
	}

	@Override
	public void initRootPanel() {
		getPanel().setLayout(new BorderLayout());
		getPanel().add(text,"Center");
		getPanel().add(gbPanel.getPanel(),"South");
		gbPanel.gbc.fill = GridBagConstraints.HORIZONTAL;
		gbPanel.add(button, 1, 1);
	}
}
