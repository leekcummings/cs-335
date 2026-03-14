package djava;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

//Dhttps://www.baeldung.com/jackson-object-mapper-tutorial
//https://www.javathinking.com/blog/how-to-convert-hashmap-to-json-object-in-java/
//https://www.youtube.com/watch?v=s6QtjCKq_wA
public class JsonManager {
	
	static File json;
	static Map<String, Object> songMap;
	
	//this should only be called when the json doesn't exist
	//(alt. we can add an if else here to check if it exists)
	static void makeJson() {json = new File("path to where we want the json tbd");}
	
	//making a map, we only want one of these, it will get populated and then
	//it will, as one object, be written to the json file
	static void makeMap() {songMap = new HashMap<>();}
	
	static void newMapElement(String song, String album, String artist, String filePath) {
		//making an element to go in the map
		//map will look like this:
		/*
		 "The Call":{
		 songTitle: "The Call",
		 albumTitle: "Black & Blue",
		 artistName: "Backstreet Boys",
		 filePath: "home/usr/music/Backstreet Boys/The Call.mp3"
		 }
		 */
		songMap.put(song, Map.of("songTitle", song,
								 "albumTitle", album,
								 "artistName", artist,
								 "filePath", filePath));
	}
	
	static void writeToJson() {
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonObject = objectMapper.convertValue(songMap, JsonNode.class);
		try {
			String jsonString = objectMapper.writeValueAsString(jsonObject);
			System.out.println("JSON String: " + jsonString);
			try {
				//String filePath = checkJsonDir();
				FileWriter file = new FileWriter("media.json", false);
				file.write(jsonString);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("ERROR: Was not able to write to file");
			} //will write the information to the json file
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("ERROR: Was not able to write string from map");
		}
		//for now just printing it out
		System.out.println("JSON Object (JsonNode): " + jsonObject);  
		//then write the string to a file
		
		
	}
	
	//this function checks if the json directory already exists, if it doesn't
	//it makes the directory, then checks if the file exists, if it doesn't
	//it makes the file
	//this isn't working rn for some reason but i think i figured out the ignore thing
	//but im leaving this here for now
	static String checkJsonDir() {
		String dirPath;
		String filePath;
		//starts by checking the os to get the filePath it should look for for each
		if (ConfigManager.opSys == OS.WINDOWS) {
			//ALL OF THESE PATHS ARE SUBJECT TO CHANGE BUT ARE FINE FOR NOW!!!
			dirPath = "Desktop\\djavaFiles";
			filePath = "Desktop\\djavaFiles\\media.json";
		} else {
			dirPath = "/usr/local/djavaFiles";
			filePath = "/usr/local/djavaFiles/media.json";
		}
		File dir = new File(dirPath);
		if(!dir.exists()) {dir.mkdir();}
		File file = new File(filePath);
		if(!file.exists()) {try {
			file.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("ERROR: Unable to make Json File");
		}}
		return filePath;
	}
}
