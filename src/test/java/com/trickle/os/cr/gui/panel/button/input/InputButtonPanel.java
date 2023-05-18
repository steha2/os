package com.trickle.os.cr.gui.panel.button.input;

import com.trickle.os.cr.gui.panel.button.AbstractButtonPanel;
import com.trickle.os.cr.gui.panel.input.InputComponent;

public abstract class InputButtonPanel extends AbstractButtonPanel implements InputComponent{
	private String name;
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public void setValue(Object value) {
		String val = value.toString();
		buttonList.forEach(b->{
			if(b.getName().equals(val)) {
				b.setSelected(true);
				return;
			}
		});
		if (val.matches("-?\\d+(\\.\\d+)?")) {
			int i = Integer.parseInt(val);
			if(i>=0 && i<buttonList.size()) 
				buttonList.get(i).setSelected(true);
        }
	}
	
	@Override
	public void setEditable(boolean editable) {
		buttonList.forEach(b->b.setEnabled(editable));
	}
}
