package djava;

import java.io.File;
import java.io.IOException;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;

public class Driver {

	public static void main(String[] args) throws UnsupportedTagException, InvalidDataException, IOException {
		ConfigManager cm = new ConfigManager(); //this runs the config manager
		MusicDirectory.setDefaultDirectory();
		DirectoryScanner.readDirectory();
		
		System.out.println(MusicLib.songsSorted);
		System.out.println(MusicLib.albumsSorted);
		System.out.println(MusicLib.artistsSorted);
		
		MenuManager mm = new MenuManager();
	}

}