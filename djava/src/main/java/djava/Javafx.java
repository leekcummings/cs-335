// DO NOT RUN THIS FILE, RUN Starter.java TO LAUNCH GUI

package djava;

import java.awt.event.ActionEvent;
import java.io.File;
import java.sql.Time;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

public class Javafx extends Application {
	Slider slider;
	Time time;
	Duration duration;
	MediaPlayer mediaPlayer;
	MediaView mediaView;
    public static void main(String[] args) {
        launch(args);
    }

    public static Tab createTab(String title, ArrayList<String> content) {
    	Tab tab = new Tab(title);
        ListView<String> tabContent = new ListView<>(); // Make content of tab ListView
        tabContent.getItems().addAll(content);
    	tab.setContent(tabContent);
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

        // Tabs for different categories of music
        TabPane tabPane = new TabPane();
        
        // Create all tabs and add to tabPane
    	tabPane.getTabs().add(createTab("Artist", new ArrayList<String>()));
    	tabPane.getTabs().add(createTab("Album", new ArrayList<String>()));
    	//this is a test for adding columns
    	Tab tab = new Tab("Songs");
    
        //https://codingtechroom.com/question/-javfx-treetableview-sorting-exception
    	
    	
    	
    	
  
    		
    	
    	TableView<Song> songTable = new TableView<>();
    		
    		//https://docs.oracle.com/en/java/java-components/javafx/25/docs/javafx.controls/javafx/scene/control/TableView.html
    		//this can be an arraylist probably later
    		
		//this will work with the json file but don't have that implemented yet
		List<Song> songs = List.of(new Song("Year Zero","Infesstisium","Ghost"),
				new Song("The Call","Black & Blue","The Backstreet Boys"));
		ObservableList<Song> rows = FXCollections.observableArrayList(songs);
		songTable.setItems(rows);
		TableColumn<Song,String> songTitle = new TableColumn<>("Title");
    	TableColumn<Song,String> songAlbum = new TableColumn<>("Album");
    	TableColumn<Song,String> songArtist = new TableColumn<>("Artist");
    	songTable.getColumns().addAll(songTitle, songAlbum, songArtist);

    	tab.setContent(songTable);
    	tabPane.getTabs().add(tab);
    	tabPane.getTabs().add(createTab("All Categories", new ArrayList<String>()));
    	tabPane.getTabs().add(createTab("Playlist", new ArrayList<String>()));

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