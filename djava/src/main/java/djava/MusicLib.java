
package djava;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.flac.FlacTag;

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
					if(file.getAbsolutePath().matches(".*mp3") ) { //this is just to make sure that we are only
//						|| file.getAbsolutePath().matches(".*wav")
//						if(ConfigManager.opSys == OS.UNIX && file.getAbsolutePath().matches(".*mp3")) {
//							continue;
//						} else {
//							mp3WavToJson(file);
//						}
						mp3WavToJson(file);
						//files.add(file.getAbsolutePath());		  //adding mp3s to the list, to get rid of unnecessary
					}//headaches (again can be changed for flacs later)
//					else if(file.getAbsolutePath().matches(".*flac")) {
//						//make json for flac
//						flacToJson(file);
//					}
				}
			}
		}
	}
	
	public
//	static ArrayList<String> getFiles(){return files;}
	static void loadLibrary(){
		//this function recurses through the specified directory and adds all mp3 file paths to a list 
		//in the future this can also be flacs but for now it is ONLY mp3s
//		if(!new File(filepath).exists()) {
//			//if the json file doesn't exist then we make it here\
//			//making it here bc its use is with this class specifically
//			JsonManager.makeJson();
//		}
		
//			JsonManager.readFromJson();

		JsonManager.makeMap();
		directoryScan(new File(MusicDirectory.get()));
		JsonManager.writeToJson();
		JsonManager.readFromJson();
		
	}
	
	//these two parsers might be able to be the same but I am unsure its a bit confusing
	static void mp3WavToJson(File file) {
		AudioFile m;
		try {
			m = AudioFileIO.read(file);
			Tag tag = m.getTag();
			//what this if statement does it basically for if a file doens't have ANY meta data
			//if it has none it won't throw the exception but it WILL crash when it tries to getFirst(...) from
			//the null instance so this is just fixing this
			if(tag != null) {
				String song = tag.getFirst(FieldKey.TITLE);
				String album = tag.getFirst(FieldKey.ALBUM);
				String artist = tag.getFirst(FieldKey.ARTIST);
				String track = tag.getFirst(FieldKey.TRACK);
				String coverArt = tag.getFirst(FieldKey.COVER_ART);
//				System.out.println(coverArt);
				String filePath = file.getAbsolutePath();
				JsonManager.newMapElement(song,album,artist,track,filePath,coverArt);
			}
			else {
				System.out.println("Tag is null for file: " + file);
			}
		} catch (CannotReadException | IOException | TagException | ReadOnlyFileException
				| InvalidAudioFrameException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("Problem with file:" + file);
		}
	}
	
	static void flacToJson(File file) {
		AudioFile f;
		try {
			f = AudioFileIO.read(file);
			FlacTag tag = (FlacTag) f.getTag();
			if(tag != null) {
				String song = tag.getFirst(FieldKey.TITLE);
				String album = tag.getFirst(FieldKey.ALBUM);
				String artist = tag.getFirst(FieldKey.ARTIST);
				String track = tag.getFirst(FieldKey.TRACK);
				String coverArt = tag.getFirst(FieldKey.COVER_ART);
				String filePath = file.getAbsolutePath();
				JsonManager.newMapElement(song,album,artist,track,filePath,coverArt);
			}
			else {
				System.out.println("Tag is null for file: " + file);
			}
		} catch (CannotReadException | IOException | TagException | ReadOnlyFileException
				| InvalidAudioFrameException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("Problem with file:" + file);
		}	
	}

	
// 					THIS IS ALL OLD STUFF!!!!!!!!! WILL BE DELETED ONCE IK WE DON'T NEED IT
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

