package djava;

import java.util.ArrayList;
import java.util.HashMap;

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
	
	static void sortLists() {
		sortList(songsSorted); //sort songs
		sortList(albumsSorted); //sort albums
		sortList(artistsSorted); //sort artists
	}
	
	ArrayList<String> searchLib(MediaType type, Media media) {
		ArrayList<String> results = new ArrayList<>();
		return(results);
	}	
	
	private
	static void sortList(ArrayList<String> list) {
		
	}
		
	}


//								         OLD CODE
//---------------------------------------------------------------------------------------------
//					 BELLA STAFFIER
//---------------------------------------------------------------------------------------------
//i was very hungry when i wrote this and its very inefficient but i'm just going to keep
//it here for now lmfao, this is a binary search that is not very good
//	int middleValueIndex = sorted.size()/2;
//	int compared = sorted.get(middleValueIndex).compareTo(name);
//		//BELOW EXPLAINS THE SWITCH CASE :P
//		//for compareTo if it returns a 0 they are equal, if it returns
//		//a -1 the first value is less than the second and if its a
//		//1 then the first value is greater than the second
//		// a a = 0 | a b = -1 | b a = 1 <-- example
//	switch(compared) {
//	case 0: //they are equal
//		return(true);
//	case 1: // first value(middle) is greater than name
//		ArrayList<String> firstHalf = new ArrayList<>();
//		for(int x = 0; x < middleValueIndex-1; x ++) {firstHalf.add(sorted.get(x));}
//		checkBinarySearch(firstHalf, name);
//		break;
//	case -1: // middle is less than name
//		ArrayList<String> secondHalf = new ArrayList<>();
//		for(int x = middleValueIndex+1; x < sorted.size(); x ++) {secondHalf.add(sorted.get(x));}
//		checkBinarySearch(secondHalf, name);
//		break;
//	default: break;



//below wasn't working because compareTo doesn't work like i thought it would
//and i am switch/case happy :3 lolz
//System.out.println("NOTE: Binary Search check for '" + name + "' started.");
//if(sorted.isEmpty()) {return(false);}
//if(sorted.size()==1){if (sorted.get(0).compareTo(name) == 0) {return(true);} else {return(false);}}
//int low = 0;
//int high = sorted.size();
//while(low <= high) {
//	int middleValueIndex = low + (high-low) / 2;
//	System.out.println("low= " + low + "mid= " + middleValueIndex + "high= " + high);
//	int compared = sorted.get(middleValueIndex).compareTo(name);
//		//BELOW EXPLAINS THE SWITCH CASE :P  
//		//for compareTo if it returns a 0 they are equal, if it returns
//		//a -1 the first value is less than the second and if its a
//		//1 then the first value is greater than the second
//		// a a = 0 | a b = -1 | b a = 1 <-- example
//	switch(compared) {
//	//they are equal
//	case 0: return(true);
//	// first value(middle) is greater than name
//	case 1: low = middleValueIndex + 1; break;
//	// middle is less than name
//	case -1: high = middleValueIndex - 1; break;
//	default: break;}}
//return(false);


//long story short i found out searching hashmaps is O(1) time :|
//static boolean songExists(Song song) {return(checkLib(MediaType.SONG, song));}
//
//static boolean albumExists(Album album) { return(checkLib(MediaType.ALBUM, album));}
//
//static boolean artistExists(Artist artist) { return(checkLib(MediaType.ARTIST, artist));}

////adds sorted list for checkbinary search to check if its there, keeps things cleaner (might be able to just add
//	//to another function but for now we will keep these separate)
//	static boolean checkLib(MediaType type, Media media) {
//		switch(type) {
//		case SONG:
//			if(checkBinarySearch(songsSorted, media.getIdentifyingName())) {return(true);}
//			else return(false);
//		case ALBUM: //has to be like this so its more efficient 
//			if(checkBinarySearch(albumsSorted, media.getIdentifyingName())) {return(true);}
//			else return(false);
//		case ARTIST:
//			if(checkBinarySearch(artistsSorted, media.getIdentifyingName())){return(true);}
//			else return(false);
//		default: return(false);
//		}
//	}
//	
//	//binary search that returns a boolean to check if its in there at all
//	static boolean checkBinarySearch(ArrayList<String> sorted, String name) {
//		System.out.println("NOTE: Binary Search check for '" + name + "' started.");
//		if(sorted.isEmpty()) {return(false);}
//		if(sorted.size()==1){if (sorted.get(0).compareTo(name) == 0) {return(true);} else {return(false);}}
//		int low = 0;
//		int high = sorted.size();
//		while(low <= high) {
//			int middleValueIndex = low + (high-low) / 2;
//			if(sorted.get(middleValueIndex).compareTo(name) == 0) {
//				System.out.println("NOTE: Binary Search check for '" + name + "' concluded - FOUND.");
//				return(true);}
//			if(sorted.get(middleValueIndex).compareTo(name) < 0) { //if mid value is greater than name
//				high = middleValueIndex - 1; break;
//			}else {
//				low = middleValueIndex + 1; break;
//			}}
//		System.out.println("NOTE: Binary Search check for '" + name + "' concluded - NOT FOUND.");
//		return(false);
//	}

//private
//static void addToList(ArrayList<String> list, String item) {
//	int current = 0;
//	if(list.isEmpty()) {
//		list.add(item);
//	}else { //might add an if else here to check if its bigger than the last one for speed reasons
//		//while loop inserting or seeing if it needs to go at the end
//		while(list.get(current).compareTo(item) == 1 || current > list.size()) { //while item is less than current
//			current++;
//		}
//		list.add(current,item);
//	}
//---------------------------------------------------------------------------------------------
