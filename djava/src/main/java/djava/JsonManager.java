package djava;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

//Dhttps://www.baeldung.com/jackson-object-mapper-tutorial
//https://www.javathinking.com/blog/how-to-convert-hashmap-to-json-object-in-java/
//https://www.youtube.com/watch?v=s6QtjCKq_wA
public class JsonManager {
	static String path = ConfigManager.mediaPath;
	
	//static File json;
	static Map<String, Object> songMap;
	
	//this should only be called when the json doesn't exist
	//(alt. we can add an if else here to check if it exists)
	//static void makeJson() {json = new File("path to where we want the json tbd");}
	
	//making a map, we only want one of these, it will get populated and then
	//it will, as one object, be written to the json file
	static void makeMap() {songMap = new HashMap<>();}
	
	static void newMapElement(String song, String album, String artist, String filePath) {
		//making an element to go in the map
		//map will look like this:
		/*
		 "The Call":{
		 albumTitle: "Black & Blue",
		 artistName: "Backstreet Boys",
		 filePath: "home/usr/music/Backstreet Boys/The Call.mp3"
		 }
		 */
		songMap.put(song, Map.of("albumTitle", album,
								 "artistName", artist,
								 "filePath", filePath));
	}
	
	static void readFromJson() {
		//i am profoundly lost right now
//		ObjectMapper objectMapper = new ObjectMapper();
//        JsonNode jsonNode = objectMapper.readTree(new File("media.json"));
        
	
		ObjectMapper objectMapper = new ObjectMapper();
		File file = new File(path);
//		System.out.println(path);
//		System.out.println(file.getAbsolutePath());
		String abPath = file.getAbsolutePath();
		Path filePath = Path.of(abPath);
		String json = null;
		try {
			json = Files.readString(filePath);
//			System.out.println(json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(json);
		Map<String, Object> map = null;
		try {
			map = objectMapper.readValue(json, new TypeReference<Map<String,Object>>(){});
		} catch (StreamReadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DatabindException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		songMap = map;
	}
//	
//	static ArrayList<String> allSongsFromJson() {
//		ArrayList<String> songs = new ArrayList<>();
//		Map<String, Object> map = readFromJson();
//		Set<String> keys = map.keySet();
//		System.out.println(map.keySet());
//		return songs;
//	}
		
		
	
	static void writeToJson() {
		ObjectMapper objectMapper = new ObjectMapper();
		System.out.println(songMap);
		//JsonNode jsonObject = objectMapper.convertValue(songMap, JsonNode.class);
//		try {
//			String jsonString = objectMapper.writeValueAsString(jsonObject);
//			System.out.println("JSON String: " + jsonString);
			try {
				
				//used to be writing a string to the JSON now using
				//object mapper to map the map to the JSON file instead, doesn't have
				//to then worry about handling the string in-between and then the string
				//being to long
				
				//String filePath = checkJsonDir();
				//FileWriter file = new FileWriter(path, false);
				//String jsonString = objectMapper.writeValueAsString(jsonObject);
				//System.out.println(jsonString);
				//file.write(jsonString);
				File file = new File(path);
				objectMapper.writeValue(file, songMap);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("ERROR: Was not able to write to file");
			} //will write the information to the json file
//		} catch (JsonProcessingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			System.out.println("ERROR: Was not able to write string from map");
//		}
		//for now just printing it out
//		System.out.println("JSON Object (JsonNode): " + jsonObject);  
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
