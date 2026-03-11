// DO NOT RUN THIS FILE, RUN Starter.java TO LAUNCH GUI

package djava;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Javafx extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
    	StackPane mainWindow = new StackPane(); // All modules will be appended to this mainWindow
    	
    	// TOP BAR ITEMS (DOESN'T INCLUDE TABS FOR MUSIC)
    	HBox topBar = new HBox();
    	int topBarWidth = 400;
    	topBar.setMaxWidth(topBarWidth);
    	topBar.setPrefWidth(Double.MAX_VALUE);
    	
    	// Search bar (Doesn't work right now)
    	TextField searchBar = new TextField();    	
        searchBar.setPromptText("Search {CATEGORY}");
        
        // Buttons for help, settings, etc.
        Button helpButton = new Button("Help");
        Button settingsButton = new Button("Settings");
        
        topBar.getChildren().addAll(searchBar, helpButton, settingsButton);

        // Tabs for different categories of music
        TabPane tabPane = new TabPane();
        
        // Create 1 tab for each category
        Tab tab1 = new Tab("Artist");
        ListView<String> artists = new ListView<>(); // Make content of tab ListView
        artists.getItems().addAll(MusicLib.artistsSorted);
    	tab1.setContent(artists);
    	
        Tab tab2 = new Tab("Album");
    	ListView<String> albums = new ListView<>();
    	albums.getItems().addAll(MusicLib.albumsSorted);
    	tab2.setContent(albums);
    	
        Tab tab3 = new Tab("Song Title");
        ListView<String> songs = new ListView<>();
    	songs.getItems().addAll(MusicLib.songsSorted);
    	tab3.setContent(songs);
    	
    	Tab tab4 = new Tab("Playlist");
        tab4.setContent(new Label("But there was nothing here..."));
        
        Tab tab5 = new Tab("All Categories");
        ListView<String> all = new ListView<>();
    	all.getItems().addAll(MusicLib.allSorted);
        tab5.setContent(all);
    	
    	// Add all created Tabs to TabPane
        tabPane.getTabs().addAll(tab1, tab2, tab3, tab4, tab5);
        tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE); // Prevent user from closing tabs
        
        // Add all elements to main window
    	mainWindow.getChildren().addAll(tabPane, topBar);        
//        VBox.setVgrow(tabPane, Priority.ALWAYS); // Allows tab pane to extend to bottom of screen
        StackPane.setAlignment(topBar, Pos.TOP_RIGHT); // Push search/buttons to the right
        
        Scene scene = new Scene(mainWindow, 800, 600);
        
        stage.setTitle("DJava Application (TEST)");
        stage.setScene(scene);
        stage.show();
        
        searchBar.setPrefWidth(topBarWidth - helpButton.getWidth() - settingsButton.getWidth());

    }
}