package djava;

public class Media {
	//this only exists so that all media types (song, album, artist
	//can all exist and be treated as media, no shared attributes
	//at the moment :3
	
	private String identifyingName = "Unknown";
	
	public
	void setIdentifyingName(String newIdentName) {this.identifyingName = newIdentName;}
	
	String getIdentifyingName() {return(this.identifyingName);};
}
