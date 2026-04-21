package djava;

import java.io.IOException;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;

public class Starter {
        public static void main(final String[] args) throws UnsupportedTagException, InvalidDataException, IOException {
        	// Taken from original Driver file
        	ConfigManager cm = new ConfigManager(); //this runs the config manager
        	MusicDirectory.setDefaultDirectory();
    		MusicLib.loadLibrary();
            Javafx.main(args);
        }
}
