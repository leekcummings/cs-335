package djava;

import java.util.ArrayList;

public class Album extends Media{
	ArrayList<Song> songs = new ArrayList<Song>();
	String albumTitle;
	String artist;
	String genre;
	
	Album(Song song){
		this.albumTitle = song.getAlbum();
		this.artist = song.getArtist();
		this.genre = song.getGenre();
		MusicLib.addToLibrary(this, MediaType.ALBUM);}
	
	public
	
	String getAlbumTitle() {return(this.albumTitle);}
	
	String getArtist() {return(this.artist);}
	
		
}
