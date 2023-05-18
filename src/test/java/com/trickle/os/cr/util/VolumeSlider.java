package com.trickle.os.cr.util;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.Port;
import javax.swing.JOptionPane;

public class VolumeSlider {
	// to get the aydiosystem gain control
	public void setGain(float ctrl) {
		try {
			Mixer.Info[] infos = AudioSystem.getMixerInfo();
			for (Mixer.Info info : infos) {
				Mixer mixer = AudioSystem.getMixer(info);
				if (mixer.isLineSupported(Port.Info.SPEAKER)) {
					Port port = (Port) mixer.getLine(Port.Info.SPEAKER);
					port.open();
					if (port.isControlSupported(FloatControl.Type.VOLUME)) {
						FloatControl volume = (FloatControl) port
								.getControl(FloatControl.Type.VOLUME);
						volume.setValue(ctrl);
					}
					port.close();
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro\n" + e);
		}
	}
	
	public float getGain(){
		try {
			Mixer.Info[] infos = AudioSystem.getMixerInfo();
			for (Mixer.Info info : infos) {
				Mixer mixer = AudioSystem.getMixer(info);
				if (mixer.isLineSupported(Port.Info.SPEAKER)) {
					Port port = (Port) mixer.getLine(Port.Info.SPEAKER);
					port.open();
					if (port.isControlSupported(FloatControl.Type.VOLUME)) {
						FloatControl volume = (FloatControl) port
								.getControl(FloatControl.Type.VOLUME);
						return volume.getValue();
					}
					port.close();
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro\n" + e);
		}
		return 0;
	}
}