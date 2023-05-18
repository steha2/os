package com.trickle.os.cr.util;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.trickle.os.cr.gui.Gui;
import com.trickle.os.vo.MenuVo;
import com.trickle.os.vo.RootVo;

public class MenuGrid {
	public JPanel panel = new JPanel();

	MenuVo sd2;
	
	public MenuGrid() {
		List<RootVo> ml = CrTool.MC.getMenus();
		System.out.println(ml);
		ml.forEach(r->r.getChilds().forEach(d1->{
			d1.getChilds().forEach(d2->{
				JButton button = Gui.createButton(d2.getName() + " (" + d2.getId() + ")",b-> {
					sd2 = d2;
					System.out.println("Selected menu id : " + d2);
				});
				System.out.println(button + "added" + d2.getId());
				button.setToolTipText(d2.getPathName()  + "  " + d2.getPath());
				panel.add(button);
			});
		}));
	}

	public MenuVo getD2() {
		return sd2;
	}
}
