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
	String filePath = null;
	File file = null;
	String songTitle = null;
	String album = null;
	String artist = null;
	String year = null;
	String trackNum = null;
	String genre = null;
	
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
	void parseMetaData(String file) throws UnsupportedTagException, InvalidDataException, IOException {
		Mp3File mp3file = new Mp3File(file);
		if(mp3file.hasId3v1Tag()) {
			ID3v1 id3v1Tag = mp3file.getId3v1Tag();
			this.songTitle = id3v1Tag.getTitle();
			this.album = id3v1Tag.getAlbum();
			this.artist = id3v1Tag.getArtist();
			this.year = id3v1Tag.getYear();	//might be null for some but its okay (please account for)
			this.trackNum = id3v1Tag.getTrack(); //might be null for some but its okay (please account for)
			this.genre = id3v1Tag.getGenreDescription();
			
			this.setIdentifyingName(this.songTitle); //for sorting purposes and efficiency
			
		}else if(mp3file.hasId3v2Tag()) {
			ID3v2 id3v2Tag = mp3file.getId3v2Tag();
			this.songTitle = id3v2Tag.getTitle();
			this.album = id3v2Tag.getAlbum();
			this.artist = id3v2Tag.getArtist();
			this.year = id3v2Tag.getYear();	//might be null for some but its okay (please account for)
			this.trackNum = id3v2Tag.getTrack(); //might be null for some but its okay (please account for)
			this.genre = id3v2Tag.getGenreDescription();
			
			this.setIdentifyingName(this.songTitle); //for sorting purposes and efficiency (used in library for sorting and searching)
			
		}else {System.out.println("ERROR: Tag type not detectible/doesn't exist.");}
	}
	
}
