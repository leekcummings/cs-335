package djava;

import java.util.ArrayList;
import java.util.HashMap;

public class MusicLib {
	
	//HashMaps
	static HashMap<String, ArrayList<Song>> songsUnsorted = new HashMap<>(); //could be songs of the same name
	static HashMap<String, ArrayList<Album>> albumsUnsorted = new HashMap<>(); //list of albums because there def could be two albums of the same name
	static HashMap<String, Artist> artistsUnsorted = new HashMap<>();
	
	//ArrayLists
	static ArrayList<String> songsSorted = new ArrayList<>();
	static ArrayList<String> albumsSorted = new ArrayList<>();
	static ArrayList<String> artistsSorted = new ArrayList<>();
	
	
	public
	static void addToLibrary(Media media, MediaType type) {
		switch(type) {
		case SONG:
			break;
		case ALBUM:
			Album album = (Album) media;
			if(albumExists(album)) { //if the album name key already exists
				boolean exists = false;
				//this for loop is seeing if there is an album in it that doesn't share an artist with it
				for(Album existingAlbum : albumsUnsorted.get(album.getAlbumTitle())) {
					if (existingAlbum.getArtist().equals(album.getArtist())) {exists = true;}
				}
				if(!exists) {
					albumsUnsorted.get(album.getAlbumTitle()).add(album); //adds album to the arraylist of albums at the key point in map
				}else {album = null;} //else DELETE THE OBJECT (don't need all this floating around
			}else { //if it doesn't exist it makes it exist
				ArrayList<Album> albumList = new ArrayList<Album>();
				albumList.add(album);
				albumsUnsorted.put(album.getAlbumTitle(), albumList); //adding album to the map
			}
			break;
		case ARTIST:
			break;
		default:
			//probably good practice to put some kind of note here
			break;
		}
	}
	
	void searchLib(MediaType type) {
		
	}
	
	boolean checkLib(MediaType type, Media media) {
		switch(type) {
		case ALBUM: //has to be like this so its more efficient 
			if(checkBinarySearch(albumsSorted, media.getIdentifyingName())) {return(true);}
			else return(false);
		case ARTIST:
			if(checkBinarySearch(artistsSorted, media.getIdentifyingName())){return(true);}
			else return(false);
		default: return(false);
		}
	}
	
	boolean checkBinarySearch(ArrayList<String> sorted, String name) {
		int low = 0;
		int high = sorted.size()-1;
		while(low <= high) {
			int middleValueIndex = low + (high-low) / 2;
			int compared = sorted.get(middleValueIndex).compareTo(name);
				//BELOW EXPLAINS THE SWITCH CASE :P
				//for compareTo if it returns a 0 they are equal, if it returns
				//a -1 the first value is less than the second and if its a
				//1 then the first value is greater than the second
				// a a = 0 | a b = -1 | b a = 1 <-- example
			switch(compared) {
			//they are equal
			case 0: return(true);
			// first value(middle) is greater than name
			case 1: high = middleValueIndex - 1; break;
			// middle is less than name
			case -1: low = middleValueIndex + 1; break;
			default: break;}}
		return(false);
	}
	
	private
	static boolean albumExists(Album album) { return(checkLib(MediaType.ALBUM, album));}
	
	static boolean artistExists(Artist artist) { return(checkLib(MediaType.ARTIST, artist));}
	
	void addAlbum() {
		
	}
	
	void addArtist(){
		
	}
	
	
	
}


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
