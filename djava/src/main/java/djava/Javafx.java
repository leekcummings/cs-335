package djava;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TextField;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Javafx extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
    	VBox mainWindow = new VBox(); // All modules will be appended to this mainWindow
    	
    	// Search bar (Doesn't work right now)
    	TextField searchBar = new TextField();
        searchBar.setPromptText("Search {CATEGORY}");
        
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
    	
    	// Add all created Tabs to TabPane
        tabPane.getTabs().addAll(tab1, tab2, tab3, tab4);
        tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE); // Prevent user from closing tabs
        VBox.setVgrow(tabPane, Priority.ALWAYS); // Allows tab pane to extend to bottom of screen
        
        // Add all elements to main window
    	mainWindow.getChildren().addAll(searchBar, tabPane);

        Scene scene = new Scene(mainWindow, 800, 600);

        stage.setTitle("DJava Application (TEST)");
        stage.setScene(scene);
        stage.show();
    }
}