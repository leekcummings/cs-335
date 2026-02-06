package djava;

public class MusicDirectory {
	
	private static String directory;

	public
	//this function is only called when config file is getting made
	static void setDefaultDirectory(){ //gets operating sys from config
								//and sets the correct default path
		if(ConfigManager.opSys == OS.WINDOWS) {
		directory = "C:\\Users\\{USERNAME}\\Music";
		}else {directory = "$HOME/Music";}
	}
	
	static void set(String newFileDirectoryPath) {
		directory = newFileDirectoryPath;
	}
	
	static String get() {
		return(directory);
	}
}
