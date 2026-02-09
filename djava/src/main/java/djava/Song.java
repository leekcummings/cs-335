package djava;

import java.io.File;
import java.io.IOException;

import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

public class Song extends Media{ //got rid of parent class because these objects are too different
	
	private
	String filePath = "Unknown";
	File file = null;
	String songTitle = "Unknown";
	String album = "Unknown";
	String artist = "Unknown";
	String year = "Unknown";
	String trackNum = "Unknown";
	String genre = "Unknown";
	
	public
	Song(File songFile){
		file = songFile; 
		filePath = songFile.getPath(); 
		
		//this is where we have to do meta data things :(
		try {
			parseMetaData(filePath);
		} catch (UnsupportedTagException | InvalidDataException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("NOTE: " + file.getName() + " metadata successfully parsed."); //FOR DEBUGGING
		MusicLib.addToLibrary(this, MediaType.SONG);
	}
	
	public
	File getFile() {return(this.file);}
	
	String getFilePath() {return(this.filePath);}
	
	String getSongTitle() {return(this.songTitle);}
	
	String getAlbum() {return(this.album);}
	
	String getArtist() {return(this.artist);}
	
	String getYear() {return(this.year);}
	
	String getTrackNum() {return(this.trackNum);}
	
	String getGenre() {return(this.genre);}

	private 
	void parseMetaData(String file) throws UnsupportedTagException, InvalidDataException, IOException { // Bella
		Mp3File mp3file = new Mp3File(file);
		if(mp3file.hasId3v1Tag()) {
			ID3v1 id3v1Tag = mp3file.getId3v1Tag();
			//System.out.println("NOTE: " + id3v1Tag.getTitle() + " has id3v1Tag metadata."); //FOR DEBUGGING
			//if statements are to insure that no metadata will be saved as "null"
			//if they are null they will stay as the default "Unknown"
			if(id3v1Tag.getTitle() != null) {this.songTitle = id3v1Tag.getTitle();}
			if(id3v1Tag.getAlbum() != null) {this.album = id3v1Tag.getAlbum();}
			if(id3v1Tag.getArtist() != null) {this.artist = id3v1Tag.getArtist();}
			if(id3v1Tag.getYear() != null) {this.year = id3v1Tag.getYear();}	//might be null for some but its okay (please account for)
			if(id3v1Tag.getTrack() != null) {this.trackNum = id3v1Tag.getTrack();} //might be null for some but its okay (please account for)
			if(id3v1Tag.getGenreDescription() != null) {this.genre = id3v1Tag.getGenreDescription();}
			
			this.setIdentifyingName(this.songTitle); //for sorting purposes and efficiency
			
		}else if(mp3file.hasId3v2Tag()) {
			ID3v2 id3v2Tag = mp3file.getId3v2Tag();
			//System.out.println("NOTE: " + id3v2Tag.getTitle() + " has id3v2Tag metadata."); //FOR DEBUGGING
			if(id3v2Tag.getTitle() != null) {this.songTitle = id3v2Tag.getTitle();}
			if(id3v2Tag.getAlbum() != null) {this.album = id3v2Tag.getAlbum();}
			if(id3v2Tag.getArtist() != null) {this.artist = id3v2Tag.getArtist();}
			if(id3v2Tag.getYear() != null) {this.year = id3v2Tag.getYear();}	//might be null for some but its okay (please account for)
			if(id3v2Tag.getTrack() != null) {this.trackNum = id3v2Tag.getTrack();} //might be null for some but its okay (please account for)
			if(id3v2Tag.getGenreDescription() != null) {this.genre = id3v2Tag.getGenreDescription();}
			
			this.setIdentifyingName(this.songTitle); //for sorting purposes and efficiency (used in library for sorting and searching)
			
		}else {System.out.println("ERROR: Tag type not detectible/doesn't exist.");}
	}
	
}
