package djava;

public class MusicDirectory {
	
	private static String directory;

	public
	//this function is only called when config file is getting made
	static void setDefaultDirectory(){ //gets operating sys from config
								//and sets the correct default path
		if(ConfigManager.opSys == OS.WINDOWS) {
		directory = System.getProperty("user.home") + "\\{USERNAME}\\Music";
		}else {directory = System.getProperty("user.home") + "/Music";}
		System.out.println("MusicDirectoryPath: " + directory);
	}
	
	static void set(String newFileDirectoryPath) {
		directory = newFileDirectoryPath;
	}
	
	static String get() {
		return(directory);
	}
}
