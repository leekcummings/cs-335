package djava;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;

public class MenuManager {
	static Menu currentMenu = Menu.MAIN;
	static Scanner sc = new Scanner(System.in);
	
	MenuManager() {
		try {
			mainMenu();
		} catch (UnsupportedTagException | InvalidDataException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static
	void parse(String input) throws UnsupportedTagException, InvalidDataException, IOException {
		int inputNum;
		try {
			inputNum = Integer.parseInt(input);
		}catch(NumberFormatException e){
			parseError(input);
			inputNum = 0; //idk if this should be here
		}
		switch(currentMenu){
		case MAIN: //you are on the main menu
			if(inputNum == 1) {musicLibrary();}
			else if(inputNum == 2) {searchMenu();}
			else if(inputNum == 3) {options();}
			else if(inputNum == 4) {exitProgram();}
			else {
				System.out.println("Could not parse input");
				returnToMainMenu();
			}
			break;
	
		case SEARCH:
			if(inputNum == 1) {searchAll();}
			else if(inputNum == 2) {searchAlbums();}
			else if(inputNum == 3) {searchArtists();}
			else if(inputNum == 4) {searchSongs();}
			else {
				System.out.println("Could not parse input");
				returnToMusicLibrary();
			}
			break;
			
		case MUSIC_LIBRARY:
			if(inputNum == 1) {browseAlbums();}
			else if(inputNum == 2){browseArtists();}
			else if(inputNum == 3){browseSongs();}
			else if(inputNum == 4) {returnToMainMenu();}
			else {
				System.out.println("Could not parse input");
				returnToMusicLibrary();
			}
			break;
			
		case OPTIONS:
			if(inputNum == 1) {setDirectory();}
			else if(inputNum == 2) {reloadLib();}
			else {returnToMainMenu();}
			break;
			
		case S_SONGS:
			if(inputNum == 0) {returnToSongsSearch();}
			break;
		case S_ALBUMS:
			if(inputNum == 0) {returnToAlbumsSearch();}
			break;
		case S_ARTISTS:
			if(inputNum == 0) {returnToArtistsSearch();}
			break;
		case ML_SONGS:
			if(inputNum == 0) {returnToMusicLibrary();}
			else {MusicLib.getMedia(input, MediaType.SONG);}
			break;
		case ML_ALBUMS:
			if(inputNum == 0) {returnToMusicLibrary();}
			else {MusicLib.getMedia(input, MediaType.ALBUM);}
			break;
		case ML_ARTISTS:
			if(inputNum == 0) {returnToMusicLibrary();}
			else {MusicLib.getMedia(input, MediaType.ARTIST);}
			break;
		default:
			break;
			}
		
	}
	
	private static
	void parseError(String input) {
		System.out.println("Unable to parse input " + input);
	}
	static void mainMenu() throws UnsupportedTagException, InvalidDataException, IOException {
		System.out.println("1. View Music Library" +
							"\n2. Search for Music" +
							"\n3. Options" +
							"\n4. Exit Program");
		currentMenu = Menu.MAIN;
		parse(sc.nextLine());
	}
	
	static void musicLibrary() throws UnsupportedTagException, InvalidDataException, IOException {
		currentMenu = Menu.MUSIC_LIBRARY;
		System.out.println("1. Sort by Album" +
				"\n2. Sort by Artist" +
				"\n3. Sort by Song Name" +
				"\n4. Return to previous page");
		parse(sc.nextLine());
	}
	
	static void browseSongs() {
		currentMenu = Menu.ML_SONGS;
		MusicLib.browseMedia(MediaType.SONG);
	}
	
	static void browseAlbums() {
		currentMenu = Menu.ML_ALBUMS;
		MusicLib.browseMedia(MediaType.ALBUM);
	}
	
	static void browseArtists() {
		currentMenu = Menu.ML_ARTISTS;
		MusicLib.browseMedia(MediaType.ARTIST);
	}
	
	static void searchMenu() throws UnsupportedTagException, InvalidDataException, IOException {
		System.out.println("1. Search by All Catagories"
				+ "2. Search by Album" +
				"\n3. Search by Artist" +
				"\n4. Search by Song Name" +
				"\n5. Return to previous page");
		currentMenu = Menu.SEARCH;
		parse(sc.nextLine());
		
	}
	 //for searching, they are their own menus so that you can return to them
	static void searchAll() {
		if(currentMenu != Menu.MUSIC_LIBRARY) {currentMenu = Menu.S_ALL;}
		System.out.println("Search: ");
		MusicLib.searchMedia(sc.next(), MediaType.ALL);
	}
	
	static void searchSongs() {
		if(currentMenu != Menu.MUSIC_LIBRARY) {currentMenu = Menu.S_SONGS;}
		System.out.println("Search: ");
		MusicLib.searchMedia(sc.next(), MediaType.SONG);
	}
	
	static void searchAlbums() {
		if(currentMenu != Menu.MUSIC_LIBRARY) {currentMenu = Menu.S_ALBUMS;}
		System.out.println("Search: ");
		MusicLib.searchMedia(sc.next(), MediaType.ALBUM);
	}
	
	static void searchArtists() {
		if(currentMenu != Menu.MUSIC_LIBRARY) {currentMenu = Menu.S_ARTISTS;}
		System.out.println("Search: ");
		MusicLib.searchMedia(sc.next(), MediaType.ARTIST);
	}
	
	static void options() throws UnsupportedTagException, InvalidDataException, IOException {
		System.out.println("1. Change directory" +
				"\n2. Reload directory" +
				"\n3. Return to previous page");
		currentMenu = Menu.OPTIONS;
		parse(sc.nextLine());
	}
	
	static void reloadLib() throws UnsupportedTagException, InvalidDataException, IOException {
		// add a "are you sure"
		DirectoryScanner.readDirectory();
	}
	
	static void setDirectory() {
		//add a "are you sure" (also check to make sure directory is real)
		//get the directory and then set it give brief instruction on how
		String newDirectory = null;
		//get input
		MusicDirectory.set(newDirectory);
	}
	
	//this is just for ease of reading the code but this can be changed
	static void returnToMainMenu() throws UnsupportedTagException, InvalidDataException, IOException {
		mainMenu();
	}
	
	static void returnToSearchMenu() throws UnsupportedTagException, InvalidDataException, IOException {
		searchMenu();
	}
	
	static void returnToMusicLibrary() throws UnsupportedTagException, InvalidDataException, IOException {
		musicLibrary();
	}
	
	static void returnToSongsSearch() {
		searchSongs();
	}
	
	static void returnToAlbumsSearch() {
		searchAlbums();
	}
	
	static void returnToArtistsSearch() {
		searchArtists();
	}
	
	static void returnToSongsBrowse() {
		browseSongs();
	}
	
	static void returnToAlbumsBrowse() {
		browseAlbums();
	}
	
	static void returnToArtistsBrowse() {
		browseArtists();
	}
	
	static void returnToOptionsSearch() throws UnsupportedTagException, InvalidDataException, IOException {
		options();
	}
	
	static void exitProgram() {
		//are you sure you want to exit?
	}
	
}
