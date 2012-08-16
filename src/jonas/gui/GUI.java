package jonas.gui;

import jonas.SoundViz;
import processing.core.PApplet;
import controlP5.*;
import unlekker.*;
import unlekker.modelbuilder.UNav3D;
import unlekker.util.USimpleGUI;

public class GUI {
	
	private UNav3D nav;
	private ControlP5 cp5;

	public void initGUI( SoundViz soundViz)
	{
		nav = new UNav3D(soundViz);
		nav.unregisterMouseEvents();
		nav.setTranslation(soundViz.width / 2, soundViz.height / 2, 0);
		
		setCp5(new ControlP5(soundViz));
		getCp5().addSlider("waveStrength", 0, 0).setRange(0f, 50f).setValue(10f);
	}

	public UNav3D getNav() {
		return nav;
	}

	public void setNav(UNav3D nav) {
		this.nav = nav;
	}

	public ControlP5 getCp5() {
		return cp5;
	}

	public void setCp5(ControlP5 cp5) {
		this.cp5 = cp5;
	}

}
