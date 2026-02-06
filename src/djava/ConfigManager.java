package djava;

import java.io.File; //used for checking file and making file
import java.io.IOException;


public class ConfigManager {
	
	//instance
	static ConfigManager instance;
	static public OS opSys;
	
	String filePath;
	String directoryPath;
	
	File configFile;
	File programDir;
	
	//initializing method
	ConfigManager(){
		getOS(); //getting OS so that the config file can be made in the right spot
		setFileDirPath(); //sets the file path based on os\
		if(!dirExists()) {
			makeDir();
		} else {System.out.println("NOTE: Program directory already exists");}
		if(!fileExists()) { //check if file exists
			makeFile();
		} else {System.out.println("NOTE: Config file already exists");}
		
	}
	
	public //public classes below
	void edit() {
		//you will be able to edit the config file here
	}
	
	private //private classes below
	boolean dirExists() {
		programDir = new File(directoryPath);
		if(programDir.exists()) {
			System.out.println("NOTE: Program directory file exists.");
			return(true);
		} else {System.out.println("NOTE: Program directory does NOT exist."); 
		return(false); }
	}
	
	boolean fileExists() {
		//setting the path depending on what os the computer is
		configFile = new File(filePath); //making the file as a File to see if it exists
		if(configFile.exists()) {
			System.out.println("NOTE: Config file exists.");
			return(true);
		} else {System.out.println("NOTE: Config file does NOT exist."); 
		return(false); } //file isn't in this location/doesn't exist
	}
	
	void setFileDirPath() {
		if(opSys == OS.WINDOWS){ //windows
			filePath = System.getProperty("user.home") + "\\AppData\\Local\\cs335\\config.json";
			directoryPath = System.getProperty("user.home") + "\\AppData\\Local\\cs335";
		} else { filePath = System.getProperty("user.home") + "/.config/cs335/config.json";
		directoryPath = System.getProperty("user.home") + "/.config/cs335";} //unix
		System.out.println("ConfigFilePath: " + filePath);
	}
	
	void load() {
		//this is going to parse the things inside the config file
	}
	
	void makeFile() {
		try {
			configFile.createNewFile();
			System.out.println("NOTE: Config file made.");
			MusicDirectory.setDefaultDirectory(); //only want this to run
			//when the config file is first being made so that it doesn't
			//write over whenever they open the program b/c that would be annoying
			
			//default data in config file
			//NEED TO WRITE
			
		} catch (IOException e) {
			System.out.println("ERROR: Unable to make config file.");
		}
		
	}
	
	void makeDir() {
		programDir.mkdir();
		System.out.println("NOTE: Program directory made.");
	}
	
	void getOS() { //setting operating system (this should be able to be accesses throughout
				   //the entire program from this file after this file is run (which will be 1st)
		if(System.getProperty("os.name").matches("Windows [0-9].")) { //should work for any version of windows
			opSys = OS.WINDOWS;
			System.out.println("NOTE: System Type - Windows");
			}else {opSys = OS.UNIX; System.out.println("NOTE: System Type - Unix");}
	}
		
	
}
