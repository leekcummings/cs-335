package djava;

import java.io.File; //used for checking file and making file
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


public class ConfigManager {
	
	//instance
	static ConfigManager instance;
	static public OS opSys;
	
	static String filePath;
	static String directoryPath;
	static String musicDirectoryPath;
	static String mediaPath;
	
	File configFile;
	File programDir;
	File mediaFile;
	
	static Map<String, String> config;
	
	//initializing method
	ConfigManager(){
		getOS(); //getting OS so that the config file can be made in the right spot
		if(opSys == OS.WINDOWS) {
			musicDirectoryPath = System.getProperty("user.home") + "\\Music"; //this used to be ("user.home") + "\\{USERNAME}\\Music"
		}else {musicDirectoryPath = System.getProperty("user.home") + "/Music";} //but that doesn't work on windows, corrected here
		System.out.println("MusicDirectoryPath: " + musicDirectoryPath);

		setFileDirPath(); //sets the file path based on os\
		if(!dirExists()) {
			makeDir();
		} else {System.out.println("NOTE: Program directory already exists");}
		if(!fileExists()) { //check if file exists
			makeFile();
		} else {
			System.out.println("NOTE: Config file already exists");
			load();
		}
		if(!mediaFileExists()) {
			makeMediaFile();
		}
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
	
	boolean mediaFileExists() {
		mediaFile = new File(mediaPath);
		System.out.println(mediaFile);
		if (mediaFile.exists()) {
			System.out.println("NOTE: media.json file exists.");
			return(true);
		} else {System.out.println("NOTE: media.json file does NOT exist."); 
		return(false); }
	}
	
	void setFileDirPath() {
		if(opSys == OS.WINDOWS){ //windows
			filePath = System.getProperty("user.home") + "\\AppData\\Local\\djava\\config.json";
			directoryPath = System.getProperty("user.home") + "\\AppData\\Local\\djava";
			mediaPath = System.getProperty("user.home") + "\\AppData\\Local\\djava\\media.json";
		} else { filePath = System.getProperty("user.home") + "/.config/djava/config.json";
		directoryPath = System.getProperty("user.home") + "/.config/djava";
		mediaPath = System.getProperty("user.home") + "/.config/djava/media.json";
		} //unix
		
		System.out.println("ConfigFilePath: " + filePath);
	}
	
	void load() {
		ObjectMapper objectMapper = new ObjectMapper();
		File file = new File(filePath);
		String abPath = file.getAbsolutePath();
		Path filePath = Path.of(abPath);
		String json = null;
		try {
			json = Files.readString(filePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(json);
		Map<String, String> map = null;
		try {
			map = objectMapper.readValue(json, new TypeReference<Map<String,String>>(){});
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		config = map;
		//this is going to parse the things inside the config file
	}
	
	void makeFile() {
		try {
			File config = new File(filePath);
			System.out.println("NOTE: Config file made.");
						
			ObjectMapper objectMapper = new ObjectMapper();
			Map<String, String> map = Map.of(
					"musicDirectoryPath", musicDirectoryPath);
			objectMapper.writeValue(config, map);
			load();
			MusicDirectory.setDefaultDirectory(); //only want this to run
			
		} catch (IOException e) {
			System.out.println("ERROR: Unable to make config file.");
		}
	}
	
	static void changeConfig(String key, String value) {
		config.put(key, value);
		// Code taken from Geeks for Geeks https://www.geeksforgeeks.org/java/delete-file-using-java/
        File file = new File(filePath);
      	// Delete the File
        if (file.delete()) {
            System.out.println("File deleted successfully");
        }
        else {
            System.out.println("Failed to delete the file");
        }
        System.out.println("NOTE: Config file changed.");
        ObjectMapper objectMapper = new ObjectMapper();
		try {
			objectMapper.writeValue(file, config);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	void makeMediaFile() {
		try {
			mediaFile.createNewFile();
			System.out.println("NOTE: media.json file made.");
			MusicDirectory.setDefaultDirectory(); //only want this to run
			//when the config file is first being made so that it doesn't
			//write over whenever they open the program b/c that would be annoying
			
			//default data in config file
			//NEED TO WRITE
			
		} catch (IOException e) {
			System.out.println("ERROR: Unable to make media.json file.");
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
