package djava;

import java.io.File;

public class Driver {

	public static void main(String[] args) {
		ConfigManager cm = new ConfigManager(); //this runs the config manager
		MediaLoader ml = new MediaLoader();
		MusicDirectory.setDefaultDirectory();
		MusicDirectory.set("/home/izzygrl/Desktop/test");
		ml.readDirectory(new File(MusicDirectory.get()));
		
		System.out.println(MusicLib.albumsUnsorted);
	}

}
