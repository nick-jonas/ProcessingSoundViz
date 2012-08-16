package jonas;

import processing.core.PApplet;
import processing.video.*;
import unlekker.util.UIO;

public class SaveUtil {
	
	private PApplet 	_main;
	private String 		filename;
	private Capture 	quicktime;
	private boolean 	saveQuicktime;

	public SaveUtil( PApplet main )
	{
		_main = main;
	}
	
	public void onKeyPressed( char key )
	{
		if( key == 's') {
			if(quicktime!=null) saveQuicktime=false;
			else saveQuicktime=true;
		}
	}

	public void checkQuicktime() {
	  if(saveQuicktime && quicktime==null) startSave();
	  if(quicktime!=null) {
	    _main.loadPixels();
	    //quicktime.addFrame(_main.pixels, _main.width, _main.height);
	  }
	  if(!saveQuicktime && quicktime!=null) endSave();
	}

	private void startSave() {
	  // get auto-incrementing filename 
	  // filename uses sketch name as filename pattern.
	  filename=UIO.getIncrementalFilename(
	  this.getClass().getSimpleName()+
	    " ###.mov", _main.sketchPath);

	  _main.println("Start saving "+filename);
	    
	  //quicktime = new MovieMaker(_main, _main.width, _main.height, filename, 30, MovieMaker.H264, MovieMaker.BEST);	
	  
	}

	private void endSave() {
	 // quicktime.finish();
	  quicktime = null;
	  _main.println(filename+" saved.");
	}

}
