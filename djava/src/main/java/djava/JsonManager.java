package djava;

import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONObject;

//https://www.youtube.com/watch?v=s6QtjCKq_wA
public class JsonManager {
	
	static void makeJson() {
		JSONObject json = new JSONObject();
		json.put("song", MusicLib.getSongs());
		
		FileWriter file;
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
