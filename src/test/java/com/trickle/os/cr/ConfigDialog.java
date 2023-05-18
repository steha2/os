package com.trickle.os.cr;

import static com.trickle.os.cr.CrApplication.*;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.border.Border;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.trickle.os.cr.gui.Gui;
import com.trickle.os.cr.gui.panel.button.ButtonPanel;
import com.trickle.os.cr.gui.panel.layout.GridBagPanel;
import com.trickle.os.cr.test.Debug;
import com.trickle.os.cr.util.Style;
import com.trickle.os.cr.util.VolumeSlider;

public class ConfigDialog {
	private JDialog dialog;
	private AppContainer container;
	private int width = 200, height = 150, t = 2;
	
	private Border lineBorder = BorderFactory.createLineBorder(Color.RED, t);
	private Border emptyBorder = BorderFactory.createEmptyBorder(t, t, t, t);
	
	private ButtonPanel styleBtnPanel = new ButtonPanel();
	private Gson gson = new GsonBuilder().setPrettyPrinting().create();
	private VolumeSlider volumnSlider = new VolumeSlider();
	private JProgressBar volumnBar = new JProgressBar();
	
	public ConfigDialog(AppContainer appContainer) {
		this.container = appContainer;
		initDialog();
	}
	
	public void initDialog() {
		dialog = new JDialog(container.getFrame(), "Setting");
		dialog.setResizable(false);
		dialog.setAlwaysOnTop(true);
		dialog.setIconImage(Gui.getResizedImage(IMG_PATH+"config.png", 30, 30));
		GridBagPanel gbPanel = new GridBagPanel();
		gbPanel.setBackground(new Color(22,22,22));
		dialog.setContentPane(gbPanel.getPanel());
		
		gbPanel.add(Gui.createLabel(" Volume", Color.WHITE, 17, JLabel.CENTER), 1, 1, 1, 1);
		gbPanel.add(volumnBar, 2, 1, 2, 1);
		gbPanel.add(Gui.createLabel(" Style", Color.WHITE, 17, JLabel.CENTER), 1, 2 ,1 ,1);
		styleBtnPanel.setButtonSize(22, 22);
		styleBtnPanel.setBackground(Color.DARK_GRAY);
		gbPanel.add(styleBtnPanel, 2, 2, 1, 1);

		gbPanel.alignCenter();
		ButtonPanel botBtnPanel = new ButtonPanel();
		botBtnPanel.setBackground(gbPanel.getPanel().getBackground());
		botBtnPanel.setColor(Gui.DARK_BLUE, Color.WHITE);
		botBtnPanel.addButton("Apply&Close", b->applyAndClose());
		botBtnPanel.addButton("Cancel", b->cancel());
		gbPanel.addNextRow(botBtnPanel);
		
		controlVolume(volumnSlider.getGain());
		volumnBar.addMouseMotionListener(new MouseAdapter() { 
		    @Override
			public void mouseDragged(MouseEvent e) { 
		    	controlVolume((float) e.getX() / volumnBar.getWidth()); }});
	}
	
	private void controlVolume(float v) {
		if(v < 0 || v > 1) return;
		volumnSlider.setGain(v);
		volumnBar.setValue((int) (v * 100));
	}
	
	private void cancel() {
		loadStyle();
		container.setStyle();
		dialog.setVisible(false);
	}

	public void loadStyle() {
		try {
			JsonReader jsonReader = new JsonReader(new FileReader(CrApplication.RES_PATH+"Style.json"));
			container.style = gson.fromJson(jsonReader, Style.class);
			styleBtnPanel.removeAll();
			int styleCount = container.style.colors.size();
			dialog.setSize(width + styleCount * 20, height);

			for(int i=0; i < Math.min(styleCount,8); i++) {
				final int s = i;
				styleBtnPanel.addButton(new ImageIcon(IMG_PATH+"n"+i+".PNG"), b->settingStyle(s));
				styleBtnPanel.getButton(i).setBorder(emptyBorder);
				styleBtnPanel.getButton(i).setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			Debug.sysout("++++++++++++Style.json++++++++++++\n", gson.toJson(container.style),"\n------------style------------");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void settingStyle(int s) {
		container.style.num = s;
		container.setStyle();
		styleBtnPanel.getButtonList().forEach
		(b->b.setBorder(styleBtnPanel.getButtonList().indexOf(b) == s ? lineBorder : emptyBorder)); 
	}

	public void open() {
		settingStyle(container.style.num);
		Gui.placeSubWindow(container.getFrame(), dialog, 1);
		dialog.setModal(true);
		dialog.setVisible(true);
	}
	
	public void applyAndClose() {
		try {
			JsonWriter jsonWriter = new JsonWriter(new FileWriter(CrApplication.RES_PATH+"Style.json"));
			jsonWriter.setIndent("  ");
			gson.toJson(container.style, Style.class, jsonWriter);
			jsonWriter.flush();
			jsonWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		dialog.setVisible(false);
	}
	
	public JDialog getDialog() {
		return dialog;
	}
	
//	public static void main(String[] args) {
//		configDialog d =  new configDialog(new AppContainer());
//		d.loadStyle();
//		d.applyAndClose();
//	}
}
