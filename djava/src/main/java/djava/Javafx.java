// DO NOT RUN THIS FILE, RUN Starter.java TO LAUNCH GUI

package djava;

import java.io.File;
import java.sql.Time;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

public class Javafx extends Application {
	Slider slider;
	Time time;
	Duration duration;
	MediaPlayer mediaPlayer;
	MediaView mediaView;
	
	// !!! CHANGE THIS VALUE TO BE A PART OF CONFIG FILE
	// THIS IS A DEFAULT VALUE FOR TESTING
	// This HashMap links the name of the column to the Song attribute
	// It is linked because it's also in order of priority
	static LinkedHashMap<String, String> songColNamesPriority = new LinkedHashMap<>(
			Map.of("Artist", "artist",
			"Album", "album",
			"Title", "title"));
	
    public static void main(String[] args) {
        launch(args);
    }

    // Give it the row data of song info
    // Then give it the column that is the main one from songColNamesPriority
    // It'll handle the rest
    public static TableView<Song> createTable(ObservableList<Song> rows, String priority) {
    	TableView<Song> table = new TableView<>();
    	ArrayList<TableColumn<Song,String>> sortOrder = new ArrayList<>();
    	// Add songs to table
    	table.setItems(rows);
    	// Do priority column first
    	TableColumn<Song,String> firstCol = new TableColumn(priority);
    	firstCol.setCellValueFactory(new PropertyValueFactory<Song,String>(songColNamesPriority.get(priority)));
    	table.getColumns().add(firstCol);
		sortOrder.add(firstCol);
    	// Create each column using arrays
    	for (Entry<String, String> col : songColNamesPriority.entrySet()) {
    		String colName = col.getKey();
    		String songAttribute = col.getValue();
    		// If we haven't already added the column first
    		if (colName != priority) {
    			TableColumn<Song,String> column = new TableColumn(colName);
        		column.setCellValueFactory(new PropertyValueFactory<Song,String>(songAttribute));
        		table.getColumns().add(column);
        		sortOrder.add(column);
    		}
    	}
    	// Reverse the order of columns to sort correctly
    	// This broke idk why
    	Collections.reverse(sortOrder);
    	System.out.println(sortOrder);
    	for (TableColumn<Song, String> sort : sortOrder) {
    		System.out.println(sort.getText());
    		table.getSortOrder().add(sort);
    	}
    	return table;
    }
    public static Tab createTab(String title, TableView<Song> table) {
    	Tab tab = new Tab(title);
    	tab.setContent(table);
    	return tab;
    }
    
    @Override
    public void start(Stage stage) {
    	BorderPane border = new BorderPane();
    	StackPane mainWindow = new StackPane(); // All modules will be appended to this mainWindow
    	
    	// TOP BAR ITEMS (DOESN'T INCLUDE TABS FOR MUSIC)
    	HBox topBar = new HBox();
    	int topBarWidth = 400;
    	topBar.setMaxWidth(topBarWidth);
    	topBar.setPrefWidth(Double.MAX_VALUE);
    	topBar.getStyleClass().add("buttonBar");
    	
    	// Search bar (Doesn't work right now)
    	TextField searchBar = new TextField();    	
        searchBar.setPromptText("Search {CATEGORY}");
        
        // Buttons for help, settings, etc.
        Button helpButton = new Button("Help");
        helpButton.setMinWidth(50);
        Button settingsButton = new Button("Settings");
        settingsButton.setMinWidth(100);
        
        topBar.getChildren().addAll(searchBar, helpButton, settingsButton);

        // Based on code from Oracle https://docs.oracle.com/javafx/2/ui_controls/table-view.htm   
    	// Create ArrayList to hold song data for TableView
    	ArrayList<Song> songs = new ArrayList<Song>();
    	for (Entry<String, Object> i : JsonManager.songMap.entrySet()) {
    		LinkedHashMap<String, String> song = (LinkedHashMap) i.getValue();
    		// Extract song name, title, artist from HashMap
    		songs.add(new Song(i.getKey(), song.get("albumTitle"), song.get("trackNumber"), song.get("artistName")));    
    	}
    	// Turn the song array into an ObservableList (basically an array, but for Tables)
    	ObservableList<Song> rows = FXCollections.observableArrayList(songs);
        
        // Tabs for different categories of music
        TabPane tabPane = new TabPane();
        
        for (Entry<String, String> tab : songColNamesPriority.entrySet()) {
        	tabPane.getTabs().add(createTab(tab.getKey(), createTable(rows, tab.getKey())));
        }
        
        // I BORKED IT, this is the previous version
        // Create all tabs and add to tabPane
        // The createTable() function is stupid, please read note above
//    	tabPane.getTabs().add(createTab("Artist", createTable(rows,
//    			new ArrayList<>(List.of("Artist", "Album", "#", "Title")),
//    			new ArrayList<>(List.of("artist", "album", "track", "title")))));
//    	tabPane.getTabs().add(createTab("Album", createTable(rows,
//    			new ArrayList<>(List.of("Album", "Artist", "#", "Title")),
//    			new ArrayList<>(List.of("album", "artist", "track", "title")))));
//    	tabPane.getTabs().add(createTab("Song", createTable(rows,
//    			new ArrayList<>(List.of("Title", "Album", "#", "Artist")),
//    			new ArrayList<>(List.of("title", "album", "track", "artist")))));
    	
    	// All Categories and Playlist EMPTY for now
    	tabPane.getTabs().add(createTab("All Categories", new TableView<Song>()));
    	tabPane.getTabs().add(createTab("Playlist", new TableView<Song>()));

    	//https://www.youtube.com/watch?v=1wxygyOGtlc
    	
    	// Prevent user from closing tabs
        tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE); 
        
        //all of the stuff below is for the play bar
        HBox playBar = new HBox();
        int playBarWidth = 400;
    	playBar.setMaxWidth(playBarWidth);
    	playBar.autosize();
    	//buttons for playbar
    	Button playButton = new Button("Play");
    	
    	Button pauseButton = new Button("Pause");
    	Button nextButton = new Button("Next");
    	Button lastButton = new Button("Last");
    	playButton.setMinWidth(50);
    	pauseButton.setMinWidth(50);
    	nextButton.setMinWidth(50);
    	lastButton.setMinWidth(50);
    	//this is going to change, jsut temp for playing music n testing
        String path = "660452__seth_makes_sounds__free-commercial-song.wav";
        Media media = new Media(new File(path).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        //music plays on default, this is temp n for testing
        mediaPlayer.setAutoPlay(true);
        mediaView = new MediaView(mediaPlayer);
        //slider
        slider = new Slider();
        HBox.setHgrow(slider, Priority.ALWAYS);
        slider.setMinSize(300, 50);
        //button actions (PLAY N PAUSE)
        playButton.setOnAction(event -> {mediaPlayer.play();});
        pauseButton.setOnAction(event -> {mediaPlayer.pause();});
        //idk if the mediaview there is necessary
        playBar.getChildren().addAll(mediaView,lastButton,pauseButton,playButton,slider,nextButton);
        playBar.getStyleClass().add("buttonBar");
        
        // Add all elements to main window
        //border.getChildren().addAll(tabPane,topBar,playBar);  
    	mainWindow.getChildren().addAll(tabPane,topBar);        
//        VBox.setVgrow(tabPane, Priority.ALWAYS); // Allows tab pane to extend to bottom of screen
        StackPane.setAlignment(topBar, Pos.TOP_RIGHT); // Push search/buttons to the right
        BorderPane.setAlignment(playBar, Pos.BOTTOM_LEFT);
        //padding n margin stuff that can probably be added to css later
        BorderPane.setMargin(playBar, new Insets(10,10,10,10));
        StackPane.setMargin(topBar, new Insets(10,10,10,10));
        tabPane.setPadding(new Insets(10,10,10,10));
        topBar.setPadding(new Insets(5,5,5,5));
        tabPane.setTabMinHeight(28);
        
        //adding the elements to the borderpane, you have to do them
        //seperate like this for adding them to different regions else it
        //doesn't work
        border.setBottom(playBar);
        border.setCenter(mainWindow);
        
        //Scene scene = new Scene(mainWindow, 800, 600);
        Scene scene = new Scene(border, 800, 600);
        
        stage.setTitle("DJava Application (TEST)");
        stage.setScene(scene);
        stage.show();
        searchBar.setPrefWidth(topBarWidth - helpButton.getWidth() - settingsButton.getWidth());
        scene.getStylesheets().add(getClass().getResource("default.css").toExternalForm());
    }
}