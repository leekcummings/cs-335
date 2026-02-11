package djava;

import java.io.File;
import java.util.Scanner;

public class MenuManager {
	static Menu currentMenu = Menu.MAIN;
	static Scanner sc = new Scanner(System.in);
	
	MenuManager(){
		mainMenu();
	}
	
	public static
	void parse(String input) {
		int inputNum;
		try {
			inputNum = Integer.parseInt(input);
		}catch(NumberFormatException e){
			parseError(input);
			inputNum = 0;
		}
		if(inputNum != 0) {
			
		}
		switch(currentMenu){
		case MAIN:
			if(inputNum == 1) {musicLibrary();}
			else if(inputNum == 2) {searchMenu();}
			else if(inputNum == 3) {options();}
			else if(inputNum == 4) {exitProgram();}
			else {returnToSearchMenu();}
			break;
	
		case SEARCH:
			if(inputNum == 1) {albums();}
			else if(inputNum == 2) {artists();}
			else if(inputNum == 3) {songs();}
			else {returnToMainMenu();}
			break;
		case MUSIC_LIBRARY:
			if(inputNum == -1) {musicLibrary();}
			if(inputNum == 0){mainMenu();}
			break;
		case OPTIONS:
			if(inputNum == 1) {setDirectory();}
			else if(inputNum == 2) {reloadLib();}
			else {returnToMainMenu();}
			break;
		case SONGS:
			if(inputNum == 0) {returnToSongs();}
			if(inputNum == -1){returnToSongs();}
			break;
		case ALBUMS:
			if(inputNum == 0) {returnToAlbums();}
			if(inputNum == -1){returnToAlbums();}
			break;
		case ARTISTS:
			if(inputNum == 0) {returnToArtists();}
			if(inputNum == -1){returnToArtists();}
			break;
			}
		
	}
	
	private static
	void parseError(String input) {
		System.out.println("Unable to parse input " + input);
	}
	static void mainMenu() {
		System.out.println("1. View Music Library" +
							"\n2. Search for Music" +
							"\n3. Options" +
							"\n4. Exit Program");
		currentMenu = Menu.MAIN;
		parse(sc.nextLine());
	}
	
	static void musicLibrary() {
		currentMenu = Menu.MUSIC_LIBRARY;
		MusicLib.viewSongs(""); //searches for everything
	}
	
	static void searchMenu() {
		System.out.println("1. Sort by Album" +
				"\n2. Sort by Artist" +
				"\n3. Sort by Song Name" +
				"\n4. Return to previous page");
		currentMenu = Menu.SEARCH;
		parse(sc.nextLine());
		
	}
	 //for searching, they are their own menus so that you can return to them
	static void songs() {
		if(currentMenu != Menu.MUSIC_LIBRARY) {currentMenu = Menu.SONGS;}
		System.out.println("1. Search\n2. Browse");
		String input = sc.nextLine();
		if(input.equals("1")) {
			System.out.println("Search: ");
			MusicLib.viewSongs(sc.next());
		}else {MusicLib.viewSongs("");}
		
	}
	
	static void albums() {
		if(currentMenu != Menu.MUSIC_LIBRARY) {currentMenu = Menu.ALBUMS;}
		System.out.println("1. Search\n2. Browse");
		String input = sc.nextLine();
		if(input.equals("1")) {
			System.out.println("Search: ");
			MusicLib.viewAlbums(sc.next());
		}else {MusicLib.viewAlbums("");}
	}
	
	static void artists() {
		if(currentMenu != Menu.MUSIC_LIBRARY) {currentMenu = Menu.ARTISTS;}
		System.out.println("1. Search\n2. Browse");
		String input = sc.nextLine();
		if(input.equals("1")) {
			System.out.println("Search: ");
			MusicLib.viewArtists(sc.next());
		}else {MusicLib.viewArtists("");}
	}
	
	static void options() {
		System.out.println("1. Change directory" +
				"\n2. Reload directory" +
				"\n3. Return to previous page");
		currentMenu = Menu.OPTIONS;
		parse(sc.nextLine());
	}
	
	static void reloadLib() {
		// add a "are you sure"
		MediaLoader.readDirectory(new File( MusicDirectory.get()));
	}
	
	static void setDirectory() {
		//add a "are you sure" (also check to make sure directory is real)
		//get the directory and then set it give brief instruction on how
		String newDirectory = null;
		//get input
		MusicDirectory.set(newDirectory);
	}
	
	//this is just for ease of reading the code but this can be changed
	static void returnToMainMenu() {
		mainMenu();
	}
	
	static void returnToSearchMenu() {
		searchMenu();
	}
	
	static void returnToSongs() {
		searchMenu();
	}
	
	static void returnToAlbums() {
		searchMenu();
	}
	
	static void returnToArtists() {
		searchMenu();
	}
	
	static void returnToOptions() {
		options();
	}
	
	static void exitProgram() {
		//are you sure you want to exit?
	}
	
}
