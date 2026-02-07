package djava;

import java.io.File;

public class Song{ //got rid of parent class because these objects are too different
	String filePath;
	File file;
	String songTitle;
	String album;
	String artist;
	String year;
	String trackNum;
	
	Song(File songFile){
		file = songFile; 
		filePath = songFile.getPath(); 
		
		//this is where we have to do meta data things :(
	}
	
	public
	File getFile() {return(this.file);}
	
	String getFilePath() {return(this.filePath);}
	
	String getSongTitle() {return(this.songTitle);}
	
	String getAlbum() {return(this.album);}
	
	String getArtist() {return(this.artist);}
	
	String getYear() {return(this.year);}
	
	String getTrackNum() {return(this.trackNum);}

	private
	void parseMetaData() {
		
	}
	
}
