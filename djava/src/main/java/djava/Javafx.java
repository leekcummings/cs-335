// DO NOT RUN THIS FILE, RUN Starter.java TO LAUNCH GUI

package djava;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

public class Javafx extends Application {

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
    	
    	// Search bar (Doesn't work right now)
    	TextField searchBar = new TextField();    	
        searchBar.setPromptText("Search {CATEGORY}");
        
        // Buttons for help, settings, etc.
        Button helpButton = new Button("Help");
        Button settingsButton = new Button("Settings");
        
        topBar.getChildren().addAll(searchBar, helpButton, settingsButton);

        // Tabs for different categories of music
        TabPane tabPane = new TabPane();
        
        // Create all tabs and add to tabPane
    	tabPane.getTabs().add(createTab("Artist", new ArrayList<String>()));
    	tabPane.getTabs().add(createTab("Album", new ArrayList<String>()));
    	tabPane.getTabs().add(createTab("Song Title", new ArrayList<String>()));
    	tabPane.getTabs().add(createTab("All Categories", new ArrayList<String>()));
    	tabPane.getTabs().add(createTab("Playlist", new ArrayList<String>()));

        tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE); // Prevent user from closing tabs
        
        HBox playBar = new HBox();
        int playBarWidth = 400;
    	playBar.setMaxWidth(playBarWidth);
    	playBar.autosize();
    	Button playButton = new Button("Play");
    	Button pauseButton = new Button("Pause");
        String path = "C:\\Users\\bella\\Music\\Music\\DragonForce\\Reaching Into Infinity\\03 Judgement Day.mp3";
        Media media = new Media(new File(path).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        MediaView mediaView = new MediaView(mediaPlayer);
        playBar.getChildren().addAll(mediaView,playButton, pauseButton);
        
        
        // Add all elements to main window
        //border.getChildren().addAll(tabPane,topBar,playBar);  
    	mainWindow.getChildren().addAll(tabPane,topBar);        
//        VBox.setVgrow(tabPane, Priority.ALWAYS); // Allows tab pane to extend to bottom of screen
        StackPane.setAlignment(topBar, Pos.TOP_RIGHT); // Push search/buttons to the right
        BorderPane.setAlignment(playBar, Pos.BOTTOM_LEFT);
        BorderPane.setMargin(playBar, new Insets(10,10,10,10));
        StackPane.setMargin(topBar, new Insets(10,10,10,10));
        tabPane.setPadding(new Insets(10,10,10,10));
        topBar.setPadding(new Insets(5,5,5,5));
        tabPane.setTabMinHeight(28);
        
        border.setBottom(playBar);
        border.setCenter(mainWindow);
        
        //Scene scene = new Scene(mainWindow, 800, 600);
        Scene scene = new Scene(border, 800, 600);
        
        stage.setTitle("DJava Application (TEST)");
        stage.setScene(scene);
        stage.show();
        
        searchBar.setPrefWidth(topBarWidth - helpButton.getWidth() - settingsButton.getWidth());
        //scene.getStylesheets().add(getClass().getResource("default.css").toExternalForm());
    }
}