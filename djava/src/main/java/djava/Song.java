package djava;

import java.lang.Integer;

public class Song {
	private String title;
	private String album;
	private int track;
	private String artist;
	private String path;
	
	public Song (String ti, String al, String tr, String ar, String p) {
		this.title = ti;
		this.album = al;
		try {
			this.track = Integer.parseInt(tr);
		}
		catch (Exception e){
			this.track = 0;
		}
		this.artist = ar;
		this.path = p;
	}
	
	// Get functions
	public String getTitle() {
		return this.title;
	}
	public String getAlbum() {
		return this.album;
	}
	public int getTrack() {
		return this.track;
	}
	public String getArtist() {
		return this.artist;
	}
	public String getPath() {
		return this.path;
	}
}
