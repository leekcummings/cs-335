package djava;

import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONObject;

//https://www.youtube.com/watch?v=s6QtjCKq_wA
public class JsonManager {
	
	static JSONObject json;
	
	static void makeJson() {
		json = new JSONObject();
	}
	
	static void newMapElement(String song, String album, String artist, String filepath) {
		//making an element to go in the map
	}
	
	static void writeToJson() {
		FileWriter file;
		//will write the information to the json file
		try {
			file = new FileWriter("musicData.json", false);
			file.write(json.toJSONString());
			file.close();
			System.out.println("file has been created");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
