package djava;

import java.io.File;

public class MediaLoader {
	
	MediaLoader(){
		System.out.println("NOTE: Media Loader initalized.");
	}
	
	public
	void getLib(File dir) {
		readDirectory(dir);
		MusicLib.sortLists();
	}
	void readDirectory(File dir){ //recursive method that reads in all files in music directory
		for (File file : dir.listFiles()) { //iterating through each file
			if(file.isDirectory()) {readDirectory(file);} //if file is directory it recurses
			else {
				debugLog(MediaType.SONG,file.getName());
				loadSong(file); //load in song
				 //
			}
		}
	}
	
	private
	void loadSong(File file) { //loads all three things (can add genre here later)
		if(file.getName().matches(".*\\.mp3")) { //seeing if its an mp3 file
			System.out.println("NOTE: " + file.getName() + " is an mp3.");
			Song s = new Song(file);
			Album al = new Album(s);
			Artist ar = new Artist(al);
		}
//		
	}
	
	void debugLog(MediaType type, String name) {
		System.out.println("NOTE: " + name + " read in.");
	}
}
