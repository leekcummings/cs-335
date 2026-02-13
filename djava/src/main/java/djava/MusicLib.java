package djava;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

public class MusicLib {
	static ArrayList<String> songsSorted = new ArrayList<>();
	static ArrayList<String> albumsSorted = new ArrayList<>();
	static ArrayList<String> artistsSorted = new ArrayList<>();
	static ArrayList<String> allSorted = new ArrayList<>();
	//these arraylists will be scanned in from json at start
	
	static Scanner sc = new Scanner(System.in);
	MenuManager mm = new MenuManager();
	
	public
	static void addToIndex(Path file) throws UnsupportedTagException, InvalidDataException, IOException {
		String title;
		String album;
		String artist;
		Mp3File mp3file = new Mp3File(file);
		if(mp3file.hasId3v2Tag()) { //v2 is first because it is more common
			ID3v2 id3v2Tag = mp3file.getId3v2Tag();
			title = id3v2Tag.getTitle();
			album = id3v2Tag.getAlbum();
			artist = id3v2Tag.getArtist();
		}else {
			ID3v1 id3v1Tag = mp3file.getId3v1Tag();
			title = id3v1Tag.getTitle();
			album = id3v1Tag.getAlbum();
			artist = id3v1Tag.getArtist();
		}
		
		if(title == null) {title = "Unknown";}
		if(album == null) {album = "Unknown";}
		if(artist == null) {artist = "Unknown";}
		
		// adding to songs list
		if(!songsSorted.contains(title)) {
			songsSorted.add(title);
			allSorted.add(title);
		}
		//adding to albums list
		if(!albumsSorted.contains(album)) {
			albumsSorted.add(album);
			allSorted.add(title);
		}
		//adding to artists list
		if(!artistsSorted.contains(artist)) {
			artistsSorted.add(artist);
			allSorted.add(title);
		}
	}
	
	//				BROWSING
	static void browseMedia(MediaType type) {
		System.out.println("browseMedia");
		if(type == MediaType.SONG) {browseThisMedia(songsSorted, type);}
		else if(type == MediaType.ALBUM) {browseThisMedia(albumsSorted, type);}
		else {browseThisMedia(artistsSorted, type);}
	}
	
	static void browseThisMedia(ArrayList<String> mediaSorted, MediaType type) {
		System.out.println("browseThisMedia");
		boolean chosen = false;
		int countUp = 1; //what number to display with
		while(!chosen) { //printing out everything that matches
			System.out.println(countUp + ". " + mediaSorted.get(countUp-1));
			countUp ++; //for counting up selected songs
		
			//i think possibly this 25 number should be set able but prob won't matter once we have GUI
			if(countUp%25 == 0 || countUp >= mediaSorted.size()) {
				if(countUp >= mediaSorted.size()) {countUp = 1;}
				System.out.println("Hit ENTER for more, type number of media, or type 0 to return");
				String input = sc.nextLine();
				if(!input.equals("")) {
					try {
						MenuManager.parse(input);
					} catch (UnsupportedTagException | InvalidDataException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					chosen = true;
				}
				
				
			}
		}
	}
	
	//				SEARCHING
	static void searchMedia(String search, MediaType type) {
		if(type == MediaType.SONG) {searchThisMedia(search, songsSorted, type);}
		else if(type == MediaType.ALBUM) {searchThisMedia(search, albumsSorted, type);}
		else if(type == MediaType.ARTIST) {searchThisMedia(search, artistsSorted, type);}
		else {searchThisMedia(search, allSorted, type);}
	}
	
	static void searchThisMedia(String search, ArrayList<String> mediaSorted, MediaType type) {
		ArrayList<String> alike = new ArrayList<>();
		boolean chosen = false;
		int iterate = 0; //counting where we are in the list
		int countUp = 1; //what number to display with
		while(!chosen) { //printing out everything that matches
			if(mediaSorted.get(iterate) == null) {
				//do nothing lol
			}
			else if(mediaSorted.get(iterate).toLowerCase().contains(search.toLowerCase())) {
				System.out.println(countUp + ". " + mediaSorted.get(iterate));
				alike.add(mediaSorted.get(iterate));
				countUp ++; //for counting up selected songs
			}
			iterate++; //loop break condition
			
			//i think possibly this 25 number should be set able but prob won't matter once we have GUI
			if(countUp%25 == 0 || iterate >= mediaSorted.size()) {
				if(iterate >= mediaSorted.size()) {countUp = 1; iterate = 0; alike.removeAll(alike);}
				System.out.println("Hit ENTER for more, type number of media, or type 0 to return");
				String input = sc.nextLine();
				if(input.compareTo("") != 0) { //simply reads out the next bunch
					if(input.compareTo("0")==0) {
						try {
							MenuManager.parse(input);
						} catch (UnsupportedTagException | InvalidDataException | IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} chosen = true;} //this SHOULD send you back?????????? (it doesn't)
					else {
					try {
						getMedia(alike.get(Integer.parseInt(input)), type); //if input wasn't a number it sends the loop back around again
						chosen = true;
						}catch(NumberFormatException e) {
						chosen = false;
						System.out.println("Could not parse input '" + input + "'.");
						}
					}
					}
			}
		}
	}
	
	static void getMedia(String mediaName, MediaType type) {
		System.out.println(DirectoryScanner.searchDir(mediaName, type));
		try {
			MenuManager.parse(sc.nextLine());
		} catch (UnsupportedTagException | InvalidDataException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	static void sortLists() {
		songsSorted.sort(null); //sort songs
		albumsSorted.sort(null);; //sort albums
		artistsSorted.sort(null); //sort artists
	}
	
//	ArrayList<String> searchLib(MediaType type, Media media) {
//		ArrayList<String> results = new ArrayList<>();
//		return(results);
//	}	
}
