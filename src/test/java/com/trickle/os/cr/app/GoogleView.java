package com.trickle.os.cr.app;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;

import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import com.trickle.os.cr.AppView;
import com.trickle.os.cr.gui.panel.layout.GridBagPanel;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GoogleView extends AppView{
	final GoogleCr googleCr;

	JSpinner spinner;
	
	GridBagPanel gbPanel = new GridBagPanel();

	JTextField search = new JTextField("${d2}");
	
	@Override
	public void initRootPanel() {
		SpinnerModel spinnerModel = new SpinnerNumberModel(1, 1, 10, 1);
		spinner = new JSpinner(spinnerModel);
		
		getPanel().setLayout(new BorderLayout());
		getPanel().add(gbPanel.getPanel());
		gbPanel.gbc.fill = GridBagConstraints.HORIZONTAL;
		gbPanel.add(new JLabel("반복"), 1, 1);
		gbPanel.add(spinner, 2, 1);
		gbPanel.add(new JLabel("검색어"), 1, 2);
		gbPanel.add(search, 2,2);
		
	}
}
