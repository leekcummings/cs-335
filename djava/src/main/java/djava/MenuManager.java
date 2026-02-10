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
			if(inputNum == 1) {MusicLib.viewAlbums();}
			else if(inputNum == 2) {MusicLib.viewArtists();}
			else if(inputNum == 3) {MusicLib.viewSongs();}
			else {returnToMainMenu();}
			break;
		case MUSIC_LIBRARY:
			if(inputNum == 0) {musicLibrary();}
			else {MusicLib.getSong(inputNum-1);}
			break;
		case OPTIONS:
			if(inputNum == 1) {setDirectory();}
			else if(inputNum == 2) {reloadLib();}
			else if(inputNum == 3) {MusicLib.viewSongs();}
			else {returnToMainMenu();}
			break;
		case SONGS:
			break;
		case ALBUMS:
			break;
		case ARTISTS:
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
		MusicLib.viewSongs();
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
		
	}
	
	static void albums() {
		
	}
	
	static void artists() {
		
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
	
	static void returnToOptions() {
		options();
	}
	
	static void exitProgram() {
		//are you sure you want to exit?
	}
	
}
