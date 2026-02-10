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
	}static void viewAlbums() {
		boolean chosen = false;
		int countUp = 1;
		while(!chosen) {
			System.out.println(countUp + ". " + albumsSorted.get(countUp-1));
			countUp ++;
			if(countUp%25 == 0 || countUp > albumsSorted.size()) {
				if(countUp >= albumsSorted.size()) {countUp = 1;}
				System.out.println("Hit ENTER for more or type ALBUM NUMBER to access album. (or type 0 to return)");
				String input = sc.nextLine();
				if(input.compareTo("") != 0) {
					MenuManager.parse(input);
					chosen = true;
				}
			}
		}
		
	}
	
	static void viewArtists() {
		boolean chosen = false;
		int countUp = 1;
		while(!chosen) {
			System.out.println(countUp + ". " + artistsSorted.get(countUp-1));
			countUp ++;
			if(countUp%25 == 0 || countUp > artistsSorted.size()) {
				if(countUp >= artistsSorted.size()) {countUp = 1;}
				System.out.println("Hit ENTER for more or type ARTIST NUMBER to access artist. (or type 0 to return)");
				String input = sc.nextLine();
				if(input.compareTo("") != 0) {
					MenuManager.parse(input);
					chosen = true;
				}
			}
		}
		
	}
	
	static void viewSongs() {
		boolean chosen = false;
		int countUp = 1;
		while(!chosen) {
			System.out.println(countUp + ". " + songsSorted.get(countUp-1));
			countUp ++;
			if(countUp%25 == 0 || countUp > songsSorted.size()) {
				if(countUp >= songsSorted.size()) {countUp = 1;}
				System.out.println("Hit ENTER for more or type SONG NUMBER to access song. (or type 0 to return)");
				String input = sc.nextLine();
				if(input.compareTo("") != 0) {
					MenuManager.parse(input);
					chosen = true;
				}
			}
		}
		
	}
	
	
	static void getSong(int index) {
		Song song = null;
		if(songsUnsorted.get(songsSorted.get(index)).size() == 1) {
			song = songsUnsorted.get(songsSorted.get(index)).get(0);
		} else {
			// this else case shouldn't come up often
			int countUp = 1;
			boolean chosen = false;
			while (!chosen){
				for(Song songsSameName: songsUnsorted.get(songsSorted.get(index))) {
					System.out.println(countUp + ". " + songsSameName);
					countUp ++;
					String input = sc.nextLine();
					int inputNum;
					try {
						inputNum = Integer.parseInt(input);
						if(inputNum <= songsUnsorted.get(songsSorted.get(index)).size()){
							song = songsUnsorted.get(songsSorted.get(index)).get(inputNum-1);
							chosen = true;
						}else {System.out.println("Input out of range.");}
					}catch(NumberFormatException e){
						inputNum = 0;
						System.out.println("Input not in valid format please input number of song you want to access.");
					}
				}
			}
		}
		System.out.println(song.getSongTitle());
	}
	
	static void sortLists() {
		songsSorted.sort(null);
		albumsSorted.sort(null);; //sort albums
		artistsSorted.sort(null); //sort artists
	}
	
	ArrayList<String> searchLib(MediaType type, Media media) {
		ArrayList<String> results = new ArrayList<>();
		return(results);
	}	
}
