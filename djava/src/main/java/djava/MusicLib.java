package djava;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class MusicLib {
	// Bella set these up if you have any questions
	//HashMaps
	static HashMap<String, ArrayList<Song>> songsUnsorted = new HashMap<>(); //could be songs of the same name
	static HashMap<String, ArrayList<Album>> albumsUnsorted = new HashMap<>(); //list of albums because there def could be two albums of the same name
	static HashMap<String, Artist> artistsUnsorted = new HashMap<>();
	
	//ArrayLists
	//these are sorted so that you can do a binary search on them to know if one exists, and then get it from the
	//unsorted hashmaps that hold the same string that is stored here, along with the object (be it song, album or artist)
	static ArrayList<String> songsSorted = new ArrayList<>();
	static ArrayList<String> albumsSorted = new ArrayList<>();
	static ArrayList<String> artistsSorted = new ArrayList<>();
	
	static Scanner sc = new Scanner(System.in);
	MenuManager mm = new MenuManager();
	
	public
	static void addToLibrary(Media media, MediaType type) {
		switch(type) {
		
		//STRUCTURE OF EACH CASE | Bella
		//check if media is in respective hashmap
		//if it is
			//if its a song, add it to the list of objects under the song
			//if its an album, if the artist name is different from all other albums of the same name, then add it to the list of objects
			//if its an album or artist, delete the object if it isn't being used
		//else if it isn't
			//add media to respective list and hashmap
		
		//SONG CASE | Bella
		case SONG:
			Song song = (Song) media; //changing it from media to song
			if(songsUnsorted.containsKey(song.getSongTitle())) { //if the album name key already exists //FOR DEBUGGING
				//System.out.println("NOTE: Song title '" + song.getSongTitle() + "' exists in map.");
				songsUnsorted.get(song.getSongTitle()).add(song); //adds song object to the map list
				//this for loop is seeing if there is an album in it that doesn't share an artist with it
			}else { //if it doesn't exist it makes it exist
				//System.out.println("NOTE: Song title '" + song.getSongTitle() + "' does not exist in map.");// FOR DEBUGGING
				//addToList adds to array and does insertion sort
				songsSorted.add(song.getSongTitle()); //adding song to sorted list
				ArrayList<Song> songList = new ArrayList<>();
				songList.add(song);
				songsUnsorted.put(song.getSongTitle(), songList); //adding album to the map
			}
			//System.out.println("NOTE: " + song.getSongTitle() + " by " + song.getArtist() + " was added to song library.");// FOR DEBUGGING
			break;
		
			
		//ALBUM CASE | Bella
		case ALBUM:
			Album album = (Album) media;
			if(albumsUnsorted.containsKey(album.getAlbumTitle())) { //if the album name key already exists
				boolean exists = false;
				//this for loop is seeing if there is an album in it that doesn't share an artist with it
				for(Album existingAlbum : albumsUnsorted.get(album.getAlbumTitle())) {
					if (existingAlbum.getArtist().equals(album.getArtist())) {
						exists = true;
						existingAlbum.addSong(album.getSong());//if it exists add the song from current to this
					}}
				if(!exists) {
					albumsUnsorted.get(album.getAlbumTitle()).add(album); //adds album to the arraylist of albums at the key point in map
					//System.out.println("NOTE: " + album.getAlbumTitle() + " by " + album.getArtist() + "was added to album library."); //FOR DEBUGGING
				}else {album = null;} //else DELETE THE OBJECT (don't need all this floating around
			}else { //if it doesn't exist it makes it exist
				albumsSorted.add(album.getAlbumTitle());
				ArrayList<Album> albumList = new ArrayList<Album>();
				albumList.add(album);
				albumsUnsorted.put(album.getAlbumTitle(), albumList); //adding album to the map
				//System.out.println("NOTE: " + album.getAlbumTitle() + " by " + album.getArtist() + " was added to album library.");// FOR DEBUGGING
			}
			break;
		
			
		// ARTIST CASE | Bella
		case ARTIST:
			Artist artist = (Artist) media;
			if(artistsUnsorted.containsKey(artist.getName())) { //if the album name key already exists
				artistsUnsorted.get(artist.getName()).addAlbum(artist.getAlbum());
				artist = null; //delete object
				//this for loop is seeing if there is an album in it that doesn't share an artist with it
			}else { //if it doesn't exist it makes it exist
				artistsSorted.add(artist.getName());
				ArrayList<Artist> artistList = new ArrayList<>();
				artistList.add(artist);
				artistsUnsorted.put(artist.getName(), artist); //adding album to the map
				//System.out.println("NOTE: " + artist.getName() + " was added to artist library."); //FOR DEBUGGING
			}
			break;
		default:
			//probably good practice to put some kind of note here
			break;
		}
	}
	
	
	
	static void viewAlbums(String regex) {
		ArrayList<String> alike = new ArrayList<>();
		boolean chosen = false;
		int iterate = 0;
		int countUp = 1;
		while(!chosen) {
			if(albumsSorted.get(iterate).toLowerCase().contains(regex.toLowerCase())) {
				System.out.println(countUp + ". " + albumsSorted.get(iterate));
				alike.add(albumsSorted.get(iterate));
				countUp ++;
			}
			iterate++;
			
			if(countUp%25 == 0 || iterate >= albumsSorted.size()) {
				if(iterate >= albumsSorted.size()) {countUp = 1; iterate = 0;}
				System.out.println("Hit ENTER for more or type ALBUM NUMBER to access album. (or type 0 to return)");
				String input = sc.nextLine();
				if(input.compareTo("") != 0) {
					if(input.compareTo("0")==0) {MenuManager.parse(input); chosen = true;} //this SHOULD send you back?????????? (it doesn't)
					else {
					try {
						getAlbum(Integer.parseInt(input),alike);
						chosen = true;
						}catch(NumberFormatException e) {
						chosen = false;
						System.out.println("Could not parse input.");
			}
		}}}}
		
	}
	
	static void viewArtists(String regex) {
		ArrayList<String> alike = new ArrayList<>();
		boolean chosen = false;
		int iterate = 0;
		int countUp = 1;
		while(!chosen) {
			if(artistsSorted.get(iterate).toLowerCase().contains(regex.toLowerCase())) {
				System.out.println(countUp + ". " + artistsSorted.get(iterate));
				alike.add(artistsSorted.get(iterate));
				countUp ++;
			}
			iterate++;
			
			if(countUp%25 == 0 || iterate >= artistsSorted.size()) {
				if(iterate >= artistsSorted.size()) {countUp = 1; iterate = 0;}
				System.out.println("Hit ENTER for more or type ARTIST NUMBER to access artist. (or type 0 to return)");
				String input = sc.nextLine();
				if(input.compareTo("") != 0) {
					if(input.compareTo("0")==0) {MenuManager.parse(input); chosen = true;} //this SHOULD send you back?????????? (it doesn't)
					else {
					try {
						getArtist(Integer.parseInt(input),alike);
						chosen = true;
						}catch(NumberFormatException e) {
						chosen = false;
						System.out.println("Could not parse input.");
						}
					}
	}}}}
	
	static void viewSongs(String regex) {
		ArrayList<String> alike = new ArrayList<>();
		boolean chosen = false;
		int countUp = 1;
		int iterate = 0;
		while(!chosen) {
			if(songsSorted.get(iterate).toLowerCase().contains(regex.toLowerCase())) { //gets each one that is (its not even really a regex lol (we c
				System.out.println(countUp + ". " + songsSorted.get(iterate));
				alike.add(songsSorted.get(iterate));
				countUp ++;
			}
			iterate++;
			if(countUp%25 == 0 || iterate >= songsSorted.size()) {
				if(iterate >= songsSorted.size()) {countUp = 1; iterate = 0;}
				System.out.println("Hit ENTER for more or type SONG NUMBER to access song. (or type 0 to return)");
				String input = sc.nextLine();
				if(input.compareTo("") != 0) {
					if(input.compareTo("0")==0) {MenuManager.parse(input); chosen = true;} //this SHOULD send you back?????????? (it doesn't)
					else {
					try {
						getSong(Integer.parseInt(input),alike);
						chosen = true;
						}catch(NumberFormatException e) {
						chosen = false;
						System.out.println("Could not parse input.");
			}}}}}}


static void getSong(int index, ArrayList<String> list) {
	System.out.println("It is getting to getSong()"); //for debugging obv
	Song song = null;
	if(songsUnsorted.get(list.get(index-1)).size() == 1) {
		song = songsUnsorted.get(list.get(index-1)).get(0);
	} else {
		// this else case shouldn't come up often
		int countUp = 1;
		boolean chosen = false;
		while (!chosen){
			for(Song songsSameName: songsUnsorted.get(list.get(index-1))) {
				System.out.println(countUp + ". " + songsSameName);
				countUp ++;
				String input = sc.nextLine();
				int inputNum;
				try {
					inputNum = Integer.parseInt(input);
					if(inputNum <= songsUnsorted.get(list.get(index-1)).size()){
						song = songsUnsorted.get(list.get(index-1)).get(inputNum-1);
						chosen = true;
					}else {System.out.println("Input out of range.");}
				}catch(NumberFormatException e){
					inputNum = 0;
					System.out.println("Input not in valid format please input number of song you want to access.");
				}}}}
		// THIS SHOULD GET MADE INTO ITS OWN FUNCTION AND CHANGED
		//ONCE WE GET THE MP3S PLAYING - Bella
		boolean playing = true;
		do {
		System.out.println(song.getSongTitle());
		String input = sc.nextLine();
			playing = false;
			MenuManager.parse("-1"); //will return to song menu
		}while(playing);
	}
	
	static void getAlbum(int index, ArrayList<String> list) {
		Album album = null;
		if(albumsUnsorted.get(list.get(index)).size() == 1) {
			album = albumsUnsorted.get(list.get(index)).get(0);
		} else {
			// this else case shouldn't come up often
			int countUp = 1;
			boolean chosen = false;
			while (!chosen){
				for(Album albumsSameName: albumsUnsorted.get(list.get(index))) {
					System.out.println(countUp + ". " + albumsSameName);
					countUp ++;
					String input = sc.nextLine();
					int inputNum;
					try {
						inputNum = Integer.parseInt(input);
						if(inputNum <= albumsUnsorted.get(list.get(index)).size()){
							album = albumsUnsorted.get(list.get(index)).get(inputNum-1);
							chosen = true;
						}else {System.out.println("Input out of range.");}
					}catch(NumberFormatException e){
						chosen = false;
						System.out.println("Input not in valid format please input number of song you want to access.");
					}
				}
			}
		}
			// THIS SHOULD GET MADE INTO ITS OWN FUNCTION AND CHANGED
			//ONCE WE GET THE MP3S PLAYING - Bella
			boolean playing = true;
			do {
			System.out.println(album.getAlbumTitle());
			String input = sc.nextLine();
			int inputNum;
			try {
				inputNum = Integer.parseInt(input);
				playing = false;
				MenuManager.parse("-1"); //will return to song menu
			}catch(NumberFormatException e){
				inputNum = 0;
				System.out.println("Input not in valid format please input number of song you want to access.");
			}}while(playing);
		}
		
		static void getArtist(int index, ArrayList<String> list) {
			Artist artist = null;
			artist = artistsUnsorted.get(list.get(index));
			
			// THIS SHOULD GET MADE INTO ITS OWN FUNCTION AND CHANGED
			//ONCE WE GET THE MP3S PLAYING - Bella
			boolean playing = true;
			do {
			System.out.println(artist.getName());
			String input = sc.nextLine();
			int inputNum;								//this is actually going to just print out the names of the albums that they have
			try {
				inputNum = Integer.parseInt(input);
				playing = false;
				MenuManager.parse("-1"); //will return to song menu
			}catch(NumberFormatException e){
				inputNum = 0;
				System.out.println("Input not in valid format please input number of song you want to access.");
			}}while(playing);

			}
	
	static void sortLists() {
		songsSorted.sort(null); //sort songs
		albumsSorted.sort(null);; //sort albums
		artistsSorted.sort(null); //sort artists
	}
	
	ArrayList<String> searchLib(MediaType type, Media media) {
		ArrayList<String> results = new ArrayList<>();
		return(results);
	}	
}
