
package djava;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

public class MusicLib {
	private 
	//files in an arraylist that contains each mp3 file in the directory
//	static ArrayList<String> files = new ArrayList<>();
	
	static void directoryScan(File dir){
		//this function steps through the directory and adds them to a list
		if(dir.listFiles() == null) {System.out.println("Directory:" + dir.getAbsolutePath() + "is null");}
		else {
			for (File file : dir.listFiles()) { //iterating through each file
				if(file.isDirectory()) {directoryScan(file);}
				else {
					if(file.getAbsolutePath().matches(".*mp3")) { //this is just to make sure that we are only
						mp3ToJson(file);
						//files.add(file.getAbsolutePath());		  //adding mp3s to the list, to get rid of unnecessary
					}//headaches (again can be changed for flacs later)
					else if(file.getAbsolutePath().matches(".*flac")) {
						//make json for flac
						flacToJson(file);
					}
				}
			}
		}
	}
	
	//https://javarevisited.blogspot.com/2023/09/how-to-sort-arraylist-in-java-without.html
	//I used this as a base but then changed it a lot so that it would work with our type of data
//	static void quickSort(int low, int high) {
//        if (low < high) {
//        	// Partition the list into two sublists
//	        int pivotIndex = partition(low, high);
//	
//	        // Recursively sort each sublist
//	        quickSort(low, pivotIndex - 1);
//	        quickSort(pivotIndex + 1, high);
//        }
//    }
//
//	static int partition(int low, int high) {
//		String highData = getSongData(files.get(high)); //high is the pivot
//        int i = low - 1;
//        String jData = getSongData(files.get(low));
//        for (int j = low; j < high; j++) {
//        	jData = getSongData(files.get(j));
//            if (jData.compareToIgnoreCase(highData) < 0) {
//                i++;
//                // Swap elements at i and j
//                String temp = files.get(i);
//                files.set(i, files.get(j));
//                files.set(j, temp);
//            }
//        }
//	      //Swap the pivot element with the element at (i + 1)
//	      String temp = files.get(i+1);
//	      files.set(i + 1, files.get(high));
//	      files.set(high, temp);
//	      return i + 1;
//        // Swap the pivot element with the element at (i + 1)
//        String temp = files.get(i+1);
//        files.set(i + 1, files.get(high));
//        files.set(high, temp);
//        return i + 1;
//        
//        String[] highData = getMetadata(files.get(high)); //high is the pivot
//        int i = low - 1;
//        String[] jData = getMetadata(files.get(low));
//        for (int j = low; j < high; j++) {
//        	jData = getMetadata(files.get(j));
//            if (jData[0].compareToIgnoreCase(highData[0]) < 0) {
//                i++;
//                // Swap elements at i and j
//                String temp = files.get(i);
//                files.set(i, files.get(j));
//                files.set(j, temp);
//            }
//        }
//        // Swap the pivot element with the element at (i + 1)
//        String temp = files.get(i+1);
//        files.set(i + 1, files.get(high));
//        files.set(high, temp);
//        return i + 1;
    }
	
	public
//	static ArrayList<String> getFiles(){return files;}
	static void loadLibrary(){
		//this function recurses through the specified directory and adds all mp3 file paths to a list 
		//in the future this can also be flacs but for now it is ONLY mp3s
		directoryScan(new File(MusicDirectory.get()));
		JsonManager.writeToJson();
//		System.out.println(files);
//		//commenting this out for the time being because I simply don't want to do it right now
//		quickSort(0,files.size()-1);
	}
	
	static void mp3ToJson(File file) {
		JsonManager.newMapElement(song,album,artist,filepath);
	}
	
	static void flacToJson(File file) {
		JsonManager.newMapElement(song,album,artist,filepath);
		
	}
	
//	static String[] getMetadata(String fileLocation) {
//		//this function gets the set of title album and artist metadata and returns it as an array
//							// Title,     Album,    Artist
//		String[] metadata = {"Unknown", "Unknown", "Unknown"};
//		Mp3File mp3file;
//		//checking for which type of id3 data and grabbing the title accordingly
//		try {
//			//tries to make an mp3 file, checks what type it is and gets the title accordingly
//			mp3file = new Mp3File(fileLocation);
//			if(mp3file.hasId3v2Tag()) { //v2 is first because it is more common
//	 			ID3v2 id3v2Tag = mp3file.getId3v2Tag();
//	 			metadata[0] = id3v2Tag.getTitle();
//	 			metadata[1] = id3v2Tag.getAlbum();
//	 			metadata[2] = id3v2Tag.getArtist();
//	 		}else {
//	 			ID3v1 id3v1Tag = mp3file.getId3v1Tag();
//	 			metadata[0] = id3v1Tag.getTitle();
//	 			metadata[1] = id3v1Tag.getAlbum();
//	 			metadata[2] = id3v1Tag.getArtist();
//	 		}
//			//this for loop is to insure that we don't get any null values and its easier to check
//			//here at the end then every time we are getting each metadata
//			//this for loop can be debated if you want
//			for(int i = 0; i < 3; i++) {if(metadata[i] == null) {metadata[i] = "Unknown";}}
//		} catch (UnsupportedTagException | InvalidDataException | IOException e) {
//			// TODO Auto-generated catch block
//			//e.printStackTrace();
//			System.out.println("File not found.");
//		}						
//		return metadata;
//	}
//	
//	static ArrayList<String> getSongs(){
//		ArrayList<String> songData = new ArrayList<>();
//		for(String file: files) {
//			songData.add(getSongData(file));
//		}
//		return songData;
//	}
//	
//	static String getSongData(String fileLocation) {
//		String songData;
//		Mp3File mp3file;
//		//checking for which type of id3 data and grabbing the title accordingly
//		try {
//			//tries to make an mp3 file, checks what type it is and gets the title accordingly
//			mp3file = new Mp3File(fileLocation);
//			if(mp3file.hasId3v2Tag()) { //v2 is first because it is more common
//	 			ID3v2 id3v2Tag = mp3file.getId3v2Tag();
//	 			songData = id3v2Tag.getTitle();
//	 		}else {
//	 			ID3v1 id3v1Tag = mp3file.getId3v1Tag();
//	 			songData = id3v1Tag.getTitle();
//	 		}
//			//this for loop is to insure that we don't get any null values and its easier to check
//			//here at the end then every time we are getting each metadata
//			//this for loop can be debated if you want
//			if(songData == null) {return("Unknown");}
//		} catch (UnsupportedTagException | InvalidDataException | IOException e) {
//			// TODO Auto-generated catch block
//			//e.printStackTrace();
//			System.out.println("File unable to be parsed: " + fileLocation);
//			return("Unknown");
//		}						
//		return songData;
//	}
}
