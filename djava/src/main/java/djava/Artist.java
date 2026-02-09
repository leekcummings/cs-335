package djava;

import java.util.ArrayList;

public class Artist extends Media{
	private
	ArrayList<Album> albums = new ArrayList<Album>();
	String name;
	Album album;
	
	public
	Artist(Album album){
		this.name = album.getArtist();
		this.album = album;
		this.setIdentifyingName(this.name);
		MusicLib.addToLibrary(this, MediaType.ARTIST);
	}
	
	String getName() {return(this.name);}
	
	void addAlbum(Album album) {
		albums.add(album);
	}
	
	Album getAlbum() {return(this.album);}

}
