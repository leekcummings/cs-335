package djava;

public class Song {
	private String title;
	private String album;
	private String track;
	private String artist;
	
	public Song (String ti, String al, String tr, String ar) {
		this.title = ti;
		this.album = al;
		this.track = tr;
		this.artist = ar;
	}
	// Get functions
	public String getTitle() {
		return this.title;
	}
	public String getAlbum() {
		return this.album;
	}
	public String getTrack() {
		return this.track;
	}
	public String getArtist() {
		return this.artist;
	}
}
