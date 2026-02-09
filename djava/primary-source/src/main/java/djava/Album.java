package djava;

import java.util.ArrayList;

public class Album extends Media{
	private
	ArrayList<Song> songs = new ArrayList<Song>();
	String albumTitle;
	String artist;
	String genre;
	Song song;
	
	public Album(Song song){
		this.albumTitle = song.getAlbum();
		this.artist = song.getArtist();
		this.genre = song.getGenre();
		this.setIdentifyingName(this.albumTitle);
		MusicLib.addToLibrary(this, MediaType.ALBUM);}
	
	public
	Song getSong() {return(this.song);}
	
	String getAlbumTitle() {return(this.albumTitle);}
	
	String getArtist() {return(this.artist);}
	
	void addSong(Song song) {
		songs.add(song);
	}
	
		
}
