package djava;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import java.util.Collections;

public class MediaManager {
	static ArrayList<Song> queueList = new ArrayList<>();
	static int queueIndex = 0;
	
	/// ADD TO BACK
	/// Add one/many songs to the end of the queue
	public static void addToBack(Song song) {
    	if(queueList.size() == 0) {
    		Javafx.playSong(song);
    	}
    	queueList.add(song);
    	Javafx.queue.setItems((ObservableList<Song>) FXCollections.observableArrayList(queueList));
    }
	
    public static void addManyToBack(ArrayList<Song> list) {
    	for(Song song: list) {
    		addToBack(song);
    	}
    }
    
    /// ADD TO FRONT
    /// Add one/many songs as the FIRST item in the queue
    public static void addToFront(Song song) {
    	int queueIndex = 0;
    	if(queueList.size() > 0) {
    		queueList.add(queueIndex+1,song);
    	}else {
    		queueList.add(queueIndex,song);
    		Javafx.playSong(song);
    	}
    	Javafx.queue.setItems((ObservableList<Song>) FXCollections.observableArrayList(queueList));
    }
    
    public static void addManyToNext(ArrayList<Song> list) {
    	for(int i = 0; i<list.size();i++) {
    		addToFront(list.get(list.size()-1-i));
    	}
    }
    
    /// PLAY NEXT
    /// Play the next item in the queue, if there are any more items
    public static void playNext() {
    	if(queueIndex+1 > queueList.size()){
    		System.out.println("Out of range of queue" + queueList.size());
    	} else {
    		queueIndex++;
    		Javafx.playSong(queueList.get(queueIndex));
    	}
    }
    
    /// PLAY LAST
    /// Get the last item in the queue and play it
    public static void playLast() {
    	if(Javafx.mediaPlayer.getCurrentTime().lessThan(Javafx.lastDet)) {
    		if(queueIndex-1 < 0){
        		System.out.println("Out of range of queue");
        	} else {
        		queueIndex--;
        		Javafx.playSong(queueList.get(queueIndex));
        	}
    	} else {
    		Javafx.playSong(queueList.get(queueIndex));
    	}
    }
    
    /// I HAVE NO IDEA WHAT THESE ARE FOR
    public static void artist(Song song) {
    	addManyToBack(artistAdding(song));
    }
    
    public static void artistNext(Song song) {
    	addManyToNext(artistAdding(song));
    }
    
    public static void album(Song song){
    	MediaManager.addManyToBack(albumAdding(song));
    }
    
    public static void albumNext(Song song) {
    	MediaManager.addManyToNext(albumAdding(song));
    }
    
    public static ArrayList<Song> artistAdding(Song song) {
    	String lowerCaseFilter = song.getArtist().toLowerCase();
    	FilteredList<Song> filteredData = new FilteredList<Song>(FXCollections.observableArrayList(Javafx.songs), p -> {
    		// If filter text is empty, display all persons.
    		// Compare first name and last name of every person with filter text.
    		//checks all relivant fields
    		if (p.getArtist().toLowerCase().contains(lowerCaseFilter)) {
    			return true;
    			}return false; // Does not match.
    	});
		
		ArrayList<Song> sortedData = new ArrayList<>();
		for(Song s:filteredData) {
			if(sortedData.size() == 0) {
				sortedData.add(s);
			}
			for(Song data: sortedData) {
				if(s.getAlbum().compareTo(data.getAlbum()) == 0){
					if (s.getTrack() <= data.getTrack() ) {
						sortedData.add(sortedData.indexOf(data), s);
						break;
					}
				}
				else if(s.getAlbum().compareTo(data.getAlbum()) < 0) {
					sortedData.add(sortedData.indexOf(data), s);
					break;
				} else if (s.getAlbum().compareTo(data.getAlbum()) > 0 && s.getTrack() >= data.getTrack() && sortedData.indexOf(data) == sortedData.size()-1) {
					sortedData.add(s);
					break;
				}
			}
		}
		return(sortedData);
    }
    
    public static ArrayList<Song> albumAdding(Song song) {
    	String lowerCaseFilter = song.getAlbum().toLowerCase();
    	FilteredList<Song> filteredData = new FilteredList<Song>(FXCollections.observableArrayList(Javafx.songs), p -> {
    		// If filter text is empty, display all persons.
    		// Compare first name and last name of every person with filter text.
    		//checks all relivant fields
    		if (p.getAlbum().toLowerCase().contains(lowerCaseFilter)) {
    			return true;
    			}return false; // Does not match.
    	});
		
		ArrayList<Song> sortedData = new ArrayList<>();
		for(Song s:filteredData) {
			if(sortedData.size() == 0) {
				sortedData.add(s);
			}
			for(Song data: sortedData) {
				if (s.getTrack() <= data.getTrack()) {
					sortedData.add(sortedData.indexOf(data), s);
					break;
				} else if (s.getTrack() >= data.getTrack() && sortedData.indexOf(data) == sortedData.size()-1) {
					sortedData.add(s);
					break;
				}
			}
		}
		//System.out.println(sortedData);
		return(sortedData);	
    }
    //////////////////////
    
    /// CLEAR QUEUE
    /// Remove all items from current queue
    public static void clearQueue() {
    	queueList.clear();
    	Javafx.queue.setItems((ObservableList<Song>) FXCollections.observableArrayList(queueList));
    	queueIndex = 0;
    }
    
    public static void shuffleQueue() {
    	//ArrayList<Song> shuffleQueue = new ArrayList<>();
    	Song currentSong = queueList.get(queueIndex);
    	queueList.remove(queueIndex);
    	//shuffleQueue = shuffleQueue.shuffle();
    	Collections.shuffle(queueList);
    	queueList.add(0, currentSong);
    	
    	queueIndex = 0;
    	Javafx.queue.setItems((ObservableList<Song>) FXCollections.observableArrayList(queueList));
    	
    	
    }
}
