package jonas;

import java.lang.reflect.Array;

import jonas.gui.GUI;
import processing.core.*;
import processing.opengl.*;
import unlekker.modelbuilder.UGeometry;
import unlekker.modelbuilder.UVec3;
import krister.Ess.*;

public class SoundViz extends PApplet {

	private GUI 			_gui;
	private ObjectField 	_field;
	private AudioChannel	_myChannel;
	private FFT				_myFFT;
	private float 			count = 0;
	private SaveUtil 		_saver;
	
	public int 				steps = 16;
	public boolean			enableMesh;
	
	// editable properties
	public float 			cameraRotX = 0f;
	public float			waveStrength = 10f;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PApplet.main(new String[]{"jonas.SoundViz"});
	}
	
	public void setup()
	{
		size(900, 600, OPENGL);
		
		background(0);
		
		_gui = new GUI();
		_gui.initGUI(this);
		_gui.getNav().rot = new UVec3(1.134, 0.576, 0);
		_gui.getNav().trans = new UVec3(450, 300, 210);
		
		_field = new ObjectField(this);
		_field.build();
		
		Ess.start(this);
		_myChannel = new AudioChannel("nodiggity.aif");
		_myFFT = new FFT(512);
		_myFFT.damp(0.5f);
		_myChannel.play(Ess.FOREVER);
		
		_saver = new SaveUtil(this);
		
	}
	
	public void keyPressed() {
	  _saver.onKeyPressed(key);
	}
	
	private float _peak = 0;
	public void draw()
	{
		background(0);
		
		pushMatrix();
		
		lights();
		
		_gui.getNav().doTransforms();
		
		
		if(this.enableMesh){
			fill(155);
			noStroke();
			_field.getMesh().draw(this);
		}
		else{
			fill(255);
			stroke(0);
			drawMesh();
		}
		
		popMatrix();
		
		_gui.getNav().rot.y -= 0.01;
		_gui.getNav().rot.x = 1.134f + (float) (((Math.sin(count)) + 1) * cameraRotX);
	}
	
	public void build()
	{
		_field.build();
	}
	
	
	public void drawMesh()
	{
		int i = 0;
		
		_myFFT.getSpectrum(_myChannel);
		
		for ( int ix = 0; ix < 16; ix ++ ) {

			for ( int iy = 0; iy < 16; iy ++ ) {
				
				// 0 - 255
				float specVal = _myFFT.spectrum[i]*512;
				float temp = max(0,185-specVal);
				if(specVal > _peak) _peak = specVal;
				float diff = (_peak - specVal) / _peak;  
				  
				pushMatrix();
				float zCoeff = waveStrength;
				float scaleCoeff = 0.2f;
				float zVal = (float) (( Math.sin( ( ix + count ) * 0.3f ) * zCoeff ) + ( Math.sin( ( iy + count ) * 0.5f ) * zCoeff )) + specVal;
				translate(_field.getMesh().vert.v[i].x, _field.getMesh().vert.v[i].y, zVal);
				noStroke();
				sphereDetail(18);
				float colorVal = Math.min((1-diff) * 255 + 10, 255);
				float r = Math.min(colorVal + 20, 255);
				float g = Math.max(colorVal - 30, 0);
				float b = Math.max(colorVal - 30, 0);
				fill(r, g, b);
				box(5);
				//sphere(5);
				popMatrix();
				i++;
			}
		}

		count += 0.1;
	}
	
	public void stop() {
	  Ess.stop();
	  super.stop();
	}



}
