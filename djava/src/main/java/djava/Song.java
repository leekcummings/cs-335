package djava;

import java.lang.Integer;

public class Song {
	private String title;
	private String album;
	private int track;
	private String artist;
	private String path;
	private String coverArt;
	
	public Song (String ti, String al, String tr, String ar, String p, String c) {
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
		this.coverArt = c;
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
	
	public String getCoverArt() {
		return this.coverArt;
	}
}
