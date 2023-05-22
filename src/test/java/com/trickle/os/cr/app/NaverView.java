package com.trickle.os.cr.app;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.util.Arrays;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import com.trickle.os.cr.AppView;
import com.trickle.os.cr.app.common.CrTool;
import com.trickle.os.cr.app.common.MenuGrid;
import com.trickle.os.cr.gui.Gui;
import com.trickle.os.cr.gui.panel.layout.GridBagPanel;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NaverView extends AppView{
	final NaverCr naverCr;

	JSpinner spinner;
	
	GridBagPanel gbPanel = new GridBagPanel();

	JTextField search = new JTextField("${d2}");
	
	@Override
	public void initRootPanel() {
		getPanel().removeAll();
		getPanel().setLayout(new BorderLayout());
		MenuGrid mg = new MenuGrid();
		getPanel().add(mg.panel,"Center");
		
		SpinnerModel spinnerModel = new SpinnerNumberModel(1, 1, 10, 1);
		spinner = new JSpinner(spinnerModel);
		
		getPanel().add(gbPanel.getPanel(), "South");
		
		gbPanel.gbc.fill = GridBagConstraints.HORIZONTAL;
		gbPanel.add(new JLabel("반복"), 1, 1);
		gbPanel.add(spinner, 2, 1);
		gbPanel.add(new JLabel("검색어"), 1, 2);
		gbPanel.add(search, 2,2);
		gbPanel.add(Gui.createButton("submit", b->{
			naverCr.submit(mg.getD2().getId(), search.getText().replace("${d2}", mg.getD2().getName()), (int) spinner.getValue());
		}), 1, 3);
		
		gbPanel.add(Gui.createButton("createRoot", b->{
			String text = JOptionPane.showInputDialog("RootName, Type, Menu1, Menu2...");
			String[] ts = text.split(",");
			
			if(text != null && ts.length >= 4) {
		        String[] params = Arrays.copyOfRange(ts, 3, ts.length);
		        for (int i = 0; i < params.length; i++) {
		            params[i] = params[i].trim();
		        }
				CrTool.createRoot(ts[0].trim(), ts[1].trim(), ts[2].trim(), params);
				initRootPanel();
			}
		}), 2, 3);
	}
}
