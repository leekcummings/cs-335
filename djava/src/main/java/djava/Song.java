package djava;

public class Song {
	private String title;
	private String album;
	private String artist;
	
	public Song (String t, String al, String ar) {
		this.title = t;
		this.album = al;
		this.artist = ar;
	}
	// Get functions
	public String getTitle() {
		return this.title;
	}
	public String getAlbum() {
		return this.album;
	}
	public String getArtist() {
		return this.artist;
	}
}
