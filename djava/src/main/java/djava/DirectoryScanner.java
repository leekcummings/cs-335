package djava;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

public class DirectoryScanner {

	static void readDirectory() throws UnsupportedTagException, InvalidDataException, IOException{ //recursive method that reads in all files in music directory	
		Path start = Paths.get(MusicDirectory.get()); //https://medium.com/@AlexanderObregon/javas-files-walk-method-explained-570d8a67247d <- got help from here (for efficiency sake)

        try {
            Files.walk(start)
                 .filter(path -> path.toString().endsWith(".mp3"))
                 .forEach(path -> {
                     try {
                         loadSong(path);
                     } catch (IOException | UnsupportedTagException | InvalidDataException e) {
                         e.printStackTrace();
                     }
                 });
        } catch (IOException e) {
            e.printStackTrace();
        }
        MusicLib.sortLists();
	}
	
	static ArrayList<String> searchDir(String mediaName, MediaType type) {
		ArrayList<String> files = searchDir(mediaName, type, new File(MusicDirectory.get()));
		return(files);
	}
	
	static ArrayList<String> searchDir(String mediaName, MediaType type, File dir) {
		ArrayList<String> media = new ArrayList<>();
		for (File file : dir.listFiles()) { //iterating through each file
			if(file.isDirectory()) {searchDir(mediaName, type, file);} //if file is directory it recurses
			else {
				if(type == MediaType.SONG || type == MediaType.ALL) {
                	String title;
             		Mp3File mp3file;
					mp3file = new Mp3File(file.getAbsolutePath());
             		if(mp3file.hasId3v2Tag()) { //v2 is first because it is more common
             			ID3v2 id3v2Tag = mp3file.getId3v2Tag();
             			title = id3v2Tag.getTitle();
             		}else {
             			ID3v1 id3v1Tag = mp3file.getId3v1Tag();
             			title = id3v1Tag.getTitle();
             		}
             		// adding to songs list
             		if(title.equals(mediaName)) {
             			media.add(file.getAbsolutePath());
             		}
				}
				if(type == MediaType.ALBUM || type == MediaType.ALL) {
					String album;
             		Mp3File mp3file = null;
					try {
						mp3file = new Mp3File(file.getAbsolutePath());
					} catch (UnsupportedTagException | InvalidDataException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
             		if(mp3file.hasId3v2Tag()) { //v2 is first because it is more common
             			ID3v2 id3v2Tag = mp3file.getId3v2Tag();
             			album = id3v2Tag.getAlbum();
             		}else {
             			ID3v1 id3v1Tag = mp3file.getId3v1Tag();
             			album = id3v1Tag.getTitle();
             		}
             		// adding to songs list
             		if(album.equals(mediaName)) {
             			media.add(file.getAbsolutePath());
             		}
				} 
				if(type == MediaType.ARTIST || type == MediaType.ALL){
					String artist;
//					String album;
             		Mp3File mp3file = null;
					try {
						mp3file = new Mp3File(file.getAbsolutePath());
					} catch (UnsupportedTagException | InvalidDataException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
             		if(mp3file.hasId3v2Tag()) { //v2 is first because it is more common
             			ID3v2 id3v2Tag = mp3file.getId3v2Tag();
             			artist = id3v2Tag.getAlbum();
//             			album = id3v2Tag.getAlbum();
             		}else {
             			ID3v1 id3v1Tag = mp3file.getId3v1Tag();
             			artist = id3v1Tag.getTitle();
//             			album = id3v1Tag.getTitle();
             		}
             		// adding to artist list | add some kind of way to tell if the album is already listed (or do this later)
//             		boolean found = false;
//             		int iterate = 0;
//             		if(artist.equals(mediaName)) {
//             			while(!found || iterate < media.size())
//             			media.add(file.getAbsolutePath());
//             		}
				} 
			}
		}
		if(media.isEmpty()) {
			media.add("NOT_FOUND");
		}
		return(media);
	}
		
	private
	static void loadSong(Path file) throws UnsupportedTagException, InvalidDataException, IOException { //loads all three things (can add genre here later)
		MusicLib.addToIndex(file);		
	}
}
