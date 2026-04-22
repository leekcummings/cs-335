// DO NOT RUN THIS FILE, RUN Starter.java TO LAUNCH GUI

package djava;


import java.io.File;
import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.stage.DirectoryChooser;

public class Javafx extends Application {
	Time time;
	Duration duration;
	static MediaPlayer mediaPlayer;
	static Media media;
	static TableView<Song> queue;
	MediaView mediaView;
	static ArrayList<TableView<Song>> tables = new ArrayList<>();
	Slider volumeSlider;
	static Duration lastDet = new Duration(2000);
	static ArrayList<Song> songs;
	Label songLabel = new Label();
	TableColumn<Song,String> title;
	TableColumn<Song,Integer> track;
	TableColumn<Song,String> album;
	TableColumn<Song,String> artist;
	Stage filePickerStage;
	TableView<Song> tableView;
	TextField searchBar;    	
    ComboBox<String> comboBox;
    FilteredList<Song> filteredData;
    static Label currentSongInfo;
    static Slider musicSlider;
    static Label currentDuration;
    static Label maxDuration;
    static Song currentSong;
	
    public static void main(String[] args) {
        launch(args);
    }
   
    /// PLAY SONG WITH MEDIA PLAYER
    public static void playSong(Song song) {
    	mediaPlayer.stop();
    	media = new Media(new File(song.getPath()).toURI().toString());
    	// Update playbar label
    	updatePlayBarText(song);
    	mediaPlayer = new MediaPlayer(media);
    	mediaPlayer.play();    	
    }
    
    /// UPDATE THE LABEL THAT SHOWS THE CURRENT SONG
    public static void updatePlayBarText(Song song) {
    	currentSong = song;
    	// Get title and artist
    	String title = song.getTitle();
    	String artist = song.getArtist();
    	String formattedInfo = title + " - " + artist;
    	// Update the current info
    	currentSongInfo.setText(formattedInfo);
    	// Slider stuff
    	musicSlider.setMin(0.0);
    	// Based on this: https://stackoverflow.com/questions/3046669/how-do-i-get-a-mp3-files-total-time-in-java
    	File file = new File(currentSong.getPath());
	    AudioInputStream audioInputStream = null;
		try {
			audioInputStream = AudioSystem.getAudioInputStream(file);
		} catch (UnsupportedAudioFileException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    AudioFormat format = audioInputStream.getFormat();
	    long frames = audioInputStream.getFrameLength();
	    // Get duration as double, then set label+max
	    int durationInSeconds = (int) ((frames + 0.0) / format.getFrameRate());
	    maxDuration.setText(String.valueOf(durationInSeconds));
	    musicSlider.setMax(durationInSeconds);
	    //  Set current value to beginning
	    musicSlider.setValue(0.0);
    }

    public static void updateCurrentDuration() throws UnsupportedAudioFileException, IOException {
      if (mediaPlayer.getStatus() == Status.PLAYING) {
    	// Get duration of song as string
    	String currentTime = mediaPlayer.getCurrentTime().toString();
      	// Convert to float using this BS
      	float convertedDuration = Float.parseFloat(currentTime.substring(0, currentTime.length() - 3));
      	
      	// Set current value to beginning
      	musicSlider.setValue(convertedDuration);
      	// Update duration of song in GUI
      	int minutes = (int) mediaPlayer.getCurrentTime().toMinutes();
      	int seconds = (int) mediaPlayer.getCurrentTime().toSeconds() % 60;
  
      	currentDuration.setText(minutes + ":" + String.format("%02d", seconds));
      }
    }
		////////////////////////////////////////
		//==== MUSIC PLAYER FUNCTIONALITY ====//
		////////////////////////////////////////
    @SuppressWarnings("unchecked")
	public void setSongs() {
    	songs.clear();
    	for (Entry<String, Object> i : JsonManager.songMap.entrySet()) {
    		LinkedHashMap<String, String> song = (LinkedHashMap<String, String>) i.getValue();
    		// Extract song name, title, artist from HashMap
    		songs.add(new Song(i.getKey(), song.get("albumTitle"), song.get("trackNumber"), song.get("artistName"), song.get("filePath")));  
    	}
    	// Turn the song array into an ObservableList (basically an array, but for Tables)
    	ObservableList<Song> rows = FXCollections.observableArrayList(songs);
  
    	tableView.setItems(rows);
    	tableView.getSortOrder().removeAll(album, track);
		tableView.getSortOrder().addAll(album,track);
		tableView.sort();
		tableView.refresh();
    	// Create each column using arrays
    	
		filteredData = new FilteredList<Song>(FXCollections.observableArrayList(songs), p -> true);

    	//https://www.youtube.com/watch?v=1wxygyOGtlc
    	// Prevent user from closing tabs
        
    }
    
    public static GridPane createSettingsGrid() {
    	GridPane grid = new GridPane();
    	grid.setAlignment(Pos.CENTER);
    	grid.setHgap(10);
    	grid.setVgap(10);
    	grid.setPadding(new Insets(25, 25, 25, 25));
    	
    	Label musicDirectory = new Label("Music Directory: " + MusicDirectory.get());
    	grid.add(musicDirectory, 0, 1);
    	
    	Button musicDirButton = new Button("Change Music Directory");
    	grid.add(musicDirButton, 1, 1);

		return grid;
    }
    
    
    public void musicDirectoryChange(DirectoryChooser directoryChooser, Stage stage) {
    	Label directoryLabel = new Label("no files selected");
    	Button button = new Button("Show");
    	//EventHandler<ActionEvent> event = new EventHandler<ActionEvent>(
    	//		); // closes event handler 
    	
    	button.setOnAction(event -> {
    			directoryChooser.setTitle("Open Resource File");
    			File file = directoryChooser.showDialog(new Stage());
    			if(file != null) {
    				directoryLabel.setText(file.toString());
    			}
    			
    	});
    	
    	VBox vbox = new VBox(30, directoryLabel, button);
    	vbox.setAlignment(Pos.CENTER);
    	Scene scene = new Scene(vbox, 500, 300);
         
        stage.setTitle("DJava Application (TEST)");
        stage.setScene(scene);
        stage.show();
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception{

			    	///////////////////////			
			    	// ===== PANES ===== //
			    	///////////////////////
    	BorderPane border = new BorderPane();
    	StackPane mainWindow = new StackPane(); // All modules will be appended to this mainWindow
        //------------------------------------------------
    	
			    	///////////////////////			
			    	// ===== H-BOX ===== //
			    	///////////////////////
    	/// // TOP BAR ITEMS (DOESN'T INCLUDE TABS FOR MUSIC)
    	HBox topBar = new HBox();
    	int topBarWidth = 400;
    	topBar.setMaxWidth(topBarWidth);
    	topBar.setPrefWidth(Double.MAX_VALUE);
    	topBar.getStyleClass().add("buttonBar");
        //------------------------------------------------

    	
			    	////////////////////////////			
			    	// ===== SEARCH BAR ===== //
			    	////////////////////////////
    	searchBar = new TextField();    	
        searchBar.setPromptText("Search...");

        comboBox = new ComboBox();
        comboBox.setMinWidth(100);
        comboBox.getItems().addAll("All","Title","Album","Artist");
        comboBox.setEditable(false); 
        comboBox.setValue("All");

        //------------------------------------------------
        
        
			    	/////////////////////////////////////////	
			    	// ===== HELP N SETTINGS BUTTONS ===== //
			    	////////////////////////////////////////
        // Buttons for help, settings, etc.
        Button helpButton = new Button("Help");
        helpButton.setMinWidth(50);
        Button settingsButton = new Button("Settings");
        settingsButton.setMinWidth(100);
        
        // Based on https://www.geeksforgeeks.org/java/javafx-popup-class/
        // create a popup
        //Popup popup = new Popup();
        GridPane settingsGrid = createSettingsGrid();
        settingsGrid.getStyleClass().add("popup");
        //popup.getContent().add(settingsGrid);
 
        // action event
        EventHandler<ActionEvent> settingsEvent = 
        new EventHandler<ActionEvent>() {
 
            public void handle(ActionEvent e)
            {
                if (!filePickerStage.isShowing()) {
                    filePickerStage.show();
                } else {
                	filePickerStage.hide();}
            }
        };
 
        // when button is pressed
        settingsButton.setOnAction(settingsEvent);
        //------------------------------------------------
        

				//////////////////////////////		
				// ===== FILE CHOOSER===== //
				/////////////////////////////
		
        DirectoryChooser directoryChooser = new DirectoryChooser();
        filePickerStage = new Stage();
        Label directoryLabel = new Label(MusicDirectory.get());
    	Button dirButton = new Button("Change Directory");
    	Button refButton = new Button("Refresh Music Library");
    	//EventHandler<ActionEvent> event = new EventHandler<ActionEvent>(
    	//		); // closes event handler 
    	
    	dirButton.setOnAction(event -> {
    			directoryChooser.setTitle("Open Resource File");
    			File file = directoryChooser.showDialog(filePickerStage);
    			if(file != null) {
    				directoryLabel.setText(file.toString());
    				MusicDirectory.set(file.toString());
    			}
    			MusicLib.loadLibrary();
    			setSongs();
    			String text = searchBar.getText();
    	    	if (text != null) {
    	    		searchBar.setText(" ");
    	    		searchBar.setText(text);
    	    	}
    			//File file = directoryChooser.showDialog();
    			//if (file != null) {
    				//directoryLabel.setText(file.getAbsolutePath() + "selected");
    			 // closes file != null
    		// closes handle action event
    	});
    	
    	refButton.setOnAction(event -> {
			MusicLib.loadLibrary();
			setSongs();
			String text = searchBar.getText();
	    	if (text != null) {
	    		searchBar.setText(" ");
	    		searchBar.setText(text);
	    	}
			
	});
    	
    	VBox vbox = new VBox(30, directoryLabel, dirButton, refButton);
    	vbox.setAlignment(Pos.CENTER);
    	Scene fScene = new Scene(vbox, 500, 300);
         
    	filePickerStage.setTitle("DJava Application (TEST)");
    	filePickerStage.setScene(fScene);
    	//filePickerStage.show();
        //musicDirectoryChange(directoryChooser, filePickerStage);
	        

			    	/////////////////////////////////////////			
			    	// ===== SETTING TOP BAR ELEMETS ===== //
			    	/////////////////////////////////////////
        topBar.getChildren().addAll(searchBar, comboBox, helpButton, settingsButton);


        
					/////////////////////////////////////////////			
					// ===== TABLES FOR DISPLAYING MUSIC ===== //
					/////////////////////////////////////////////
       
        
        songs = new ArrayList<Song>();
    	for (Entry<String, Object> i : JsonManager.songMap.entrySet()) {
    		LinkedHashMap<String, String> song = (LinkedHashMap) i.getValue();
    		// Extract song name, title, artist from HashMap
    		songs.add(new Song(i.getKey(), song.get("albumTitle"), song.get("trackNumber"), song.get("artistName"), song.get("filePath")));  
    	}
    	// Turn the song array into an ObservableList (basically an array, but for Tables)
    	ObservableList<Song> rows = FXCollections.observableArrayList(songs);
    	
    	tableView = new TableView<>();
    	//adding tables into an array (this makes it was easier later)
    	tables.remove(tableView);
    	tables.add(tableView);
    	
    	//this basically means that only one thing can be selected at a time
    	tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    	//ArrayList<TableColumn<Song,String>> sortOrder = new ArrayList<>();
    	// Add songs to table
    	title = new TableColumn("Title");
    	track = new TableColumn("#");
    	album = new TableColumn("Album");
    	artist = new TableColumn("Artist");
    	tableView.setItems(rows);
    	// Do priority column first
    	title.setCellValueFactory(new PropertyValueFactory<Song,String>("title"));
    	album.setCellValueFactory(new PropertyValueFactory<Song,String>("album"));
    	artist.setCellValueFactory(new PropertyValueFactory<Song,String>("artist"));
    	track.setCellValueFactory(new PropertyValueFactory<Song,Integer>("track"));
    	tableView.getColumns().addAll(album,track,title,artist);
		tableView.getSortOrder().addAll(album,track);
		tableView.setItems(rows);
		track.setMinWidth(25);
		track.setMaxWidth(25);
//		track.setSortable(false);
		title.setMinWidth(150);
		artist.setMinWidth(80);
		album.setMinWidth(100);
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    	
        
		//////////////////////////////////
		//==== SEARCH FUNCTIONALITY ====//
		//////////////////////////////////
		//https://codingtechroom.com/question/-javafx-tableview-search-textfield -- motified to fit the way that we are filtering (discovered FilteredList from this source)
		filteredData = new FilteredList<Song>(FXCollections.observableArrayList(songs), p -> true);
		
		searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
		filteredData.setPredicate(song -> {
		// If filter text is empty, display all persons.
		if (newValue == null || newValue.isEmpty()) {
		return true;
		}
		
		// Compare first name and last name of every person with filter text.
		String lowerCaseFilter = newValue.toLowerCase();
		
		String value = comboBox.getValue().toString();
		//checks all relivant fields
		if(value.compareTo("All") == 0) {
			if (song.getTitle().toLowerCase().contains(lowerCaseFilter)) {
				return true; // Filter matches first name.
				} else if (song.getAlbum().toLowerCase().contains(lowerCaseFilter)) {
				return true; // Filter matches last name.
				} else if (song.getArtist().toLowerCase().contains(lowerCaseFilter)) {
				return true; // Filter matches last name.
				}return false; // Does not match.
				//only checks these fields
		} else if(value.compareTo("Title") == 0) {
			if (song.getTitle().toLowerCase().contains(lowerCaseFilter)) {
				return true; // Filter matches first name.
				}return false; // Does not match.
		} else if(value.compareTo("Album") == 0) {
			if (song.getAlbum().toLowerCase().contains(lowerCaseFilter)) {
				return true;
				}return false; // Does not match.
		} else if(value.compareTo("Artist") == 0) {
			if (song.getArtist().toLowerCase().contains(lowerCaseFilter)) {
				return true;
				}return false; 
		}// Does not match.
		return false;
		});
		tableView.setItems(FXCollections.observableArrayList(filteredData));
		// I FEEL LIKE THIS SHOULD BE WORKING BUT IT ISNTTTT
		tableView.getSortOrder().removeAll(album, track);
		tableView.getSortOrder().addAll(album,track);
		tableView.sort();
		tableView.refresh();
		});
		
		
        // Whenever the comboBox is changed (like from All to Title), it sets the TextField to blank then back to what it was
		//this basically just resets the function method that is above ^^^^ so it searches again but based now on the new comboBox contents
        comboBox.setOnAction(event ->{
        	String text = searchBar.getText();
        	if (text != null) {
        		searchBar.setText(" ");
        		searchBar.setText(text);
        	}
        });
        

        
				////////////////////////////////////
				// ===== RIGHT CLICK  MENU ===== //
				///////////////////////////////////
        ContextMenu contextMenu = new ContextMenu();
        MenuItem addToQueue = new MenuItem("Add To Queue");
        MenuItem playNext = new MenuItem("Play Next");
        MenuItem playNow = new MenuItem("Play");
        MenuItem addAlbum = new MenuItem("Add Album To Queue");
        MenuItem addAlbumNext = new MenuItem("Add Album Next");
        MenuItem addArtist = new MenuItem("Add Artist To Queue");
        MenuItem addArtistNext = new MenuItem("Add Artist Next");
        contextMenu.getItems().addAll(addToQueue, playNext, addAlbum, addAlbumNext, addArtist, addArtistNext, playNow);
        
        
        //maybe i dont need the for loop but i cant check that till i have t he displaying fixed
        addToQueue.setOnAction(e -> {
        	for(TableView<Song> table: tables) {
        		if(table.getSelectionModel().getSelectedItem()!= null) {
        		MediaManager.addToBack(table.getSelectionModel().getSelectedItem());}
        	}
        });
        
        playNext.setOnAction(e -> {
        	for(TableView<Song> table: tables) {
        		if(table.getSelectionModel().getSelectedItem()!= null) {
        		MediaManager.addToFront(table.getSelectionModel().getSelectedItem());}
        	}
        });
        
        playNow.setOnAction(e -> {
        	for(TableView<Song> table: tables) {
        		System.out.print(table.getSelectionModel().getSelectedItem());
        		if(table.getSelectionModel().getSelectedItem()!= null) {
        			System.out.print(table.getSelectionModel().getSelectedItem() + "GOT GOT GOT");
        			MediaManager.clearQueue();
        			MediaManager.addToFront(table.getSelectionModel().getSelectedItem());
        			playSong(table.getSelectionModel().getSelectedItem());
        		}
        	}
        });
        
        addAlbum.setOnAction(e -> {
        	for(TableView<Song> table: tables) {
        		System.out.print(table.getSelectionModel().getSelectedItem());
        		if(table.getSelectionModel().getSelectedItem()!= null) {
        			System.out.print(table.getSelectionModel().getSelectedItem() + "GOT GOT GOT");
        			MediaManager.album(table.getSelectionModel().getSelectedItem());
        		}
        	}
        });
        
        addAlbumNext.setOnAction(e -> {
        	for(TableView<Song> table: tables) {
        		System.out.print(table.getSelectionModel().getSelectedItem());
        		if(table.getSelectionModel().getSelectedItem()!= null) {
        			System.out.print(table.getSelectionModel().getSelectedItem() + "GOT GOT GOT");
        			MediaManager.albumNext(table.getSelectionModel().getSelectedItem());
        		}
        	}
        });
        
        addArtist.setOnAction(e -> {
        	for(TableView<Song> table: tables) {
        		System.out.print(table.getSelectionModel().getSelectedItem());
        		if(table.getSelectionModel().getSelectedItem()!= null) {
        			System.out.print(table.getSelectionModel().getSelectedItem() + "GOT GOT GOT");
        			MediaManager.artist(table.getSelectionModel().getSelectedItem());
        		}
        		
        	}
        });
        
        addArtistNext.setOnAction(e -> {
        	for(TableView<Song> table: tables) {
        		System.out.print(table.getSelectionModel().getSelectedItem());
        		if(table.getSelectionModel().getSelectedItem()!= null) {
        			System.out.print(table.getSelectionModel().getSelectedItem() + "GOT GOT GOT");
        			MediaManager.artistNext(table.getSelectionModel().getSelectedItem());
        		}
        	}
        });
        
        //adding it to the tab pane only
        tableView.setContextMenu(contextMenu);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        	

				//////////////////////////
				// ===== CLICKING ===== //
				//////////////////////////
		
        for(TableView<Song> table: tables) {
        	//table.autosize();
        	table.setOnMouseClicked(event ->{
        		if(event.getClickCount() == 2)  {
        			//basically start playing the song
        			MediaManager.clearQueue();
        			MediaManager.addToFront(table.getSelectionModel().getSelectedItem());
        			playSong(table.getSelectionModel().getSelectedItem());	
        		}
        	});
        }


        
				///////////////////////
				// ===== QUEUE ===== //
				///////////////////////
        ObservableList<Song> data = (ObservableList<Song>) FXCollections.observableArrayList(MediaManager.queueList);
        System.out.println(MediaManager.queueList);
        queue = new TableView<>();
        queue.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        TableColumn<Song, String> nameColumn = new TableColumn("Title");
        nameColumn.setCellValueFactory(new PropertyValueFactory<Song, String>("title"));
        TableColumn<Song, String> albumColumn = new TableColumn("Album");
        albumColumn.setCellValueFactory(new PropertyValueFactory<Song, String>("album"));
        TableColumn<Song, String> artistColumn = new TableColumn("Artist");
        artistColumn.setCellValueFactory(new PropertyValueFactory<Song, String>("artist"));
        nameColumn.setMinWidth(50);
        albumColumn.setMinWidth(50);
        artistColumn.setMinWidth(50);
        nameColumn.setPrefWidth(200);
        queue.getColumns().addAll(nameColumn, albumColumn, artistColumn);
        queue.setItems(data);
        queue.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        queue.setMinWidth(300);
        queue.setMaxWidth(600);
        queue.setPrefWidth(450);


				//////////////////////////		
				// ===== PLAY BAR ===== //
				//////////////////////////
        //all of the stuff below is for the play bar
        HBox playBar = new HBox();
        int playBarWidth = 1000;
    	playBar.setMaxWidth(playBarWidth);
    	playBar.autosize();
    	
    	//BUTTONS===============================
    	Button playButton = new Button("|>");
    	Button pauseButton = new Button("||");
    	Button nextButton = new Button(">>");
    	Button lastButton = new Button("<<");
    	Button clearQueueButton = new Button("Clear Queue");

    	
    	clearQueueButton.setOnAction(event -> {MediaManager.clearQueue();});
    	playButton.setOnAction(event -> {mediaPlayer.play();});
        pauseButton.setOnAction(event -> {mediaPlayer.pause();});
        nextButton.setOnAction(event -> {MediaManager.playNext();});
        lastButton.setOnAction(event -> {MediaManager.playLast();});

    	playButton.setMinWidth(50);
    	pauseButton.setMinWidth(50);
    	nextButton.setMinWidth(35);
    	lastButton.setMinWidth(35);
    	clearQueueButton.setMinWidth(80);
    	
    	
    	Label volumeLabel = new Label("Vol: ");
    	volumeLabel.setMinWidth(20);
    	
    	volumeSlider = new Slider();        
    	volumeSlider.setPrefWidth(100);
    	volumeSlider.setMaxWidth(130);
    	volumeSlider.setMinWidth(80);
    	
    	
    	//=======================================
    	
    	//this is going to change, jsut temp for playing music n testing
        String path = "660452__seth_makes_sounds__free-commercial-song.wav";
        media = new Media(new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
	    
        //look into this!!!!!!!!!!!!!!!
        mediaPlayer.setOnEndOfMedia( () -> {MediaManager.playNext();});
        //music plays on default, this is temp n for testing
        //mediaPlayer.setAutoPlay(true);
        currentDuration = new Label("0:00");
        maxDuration = new Label("0:00");
        //slider
        musicSlider = new Slider();
        HBox.setHgrow(musicSlider, Priority.ALWAYS);
        musicSlider.setMinSize(300, 50);
        // Song Title/Artist Name
        currentSongInfo = new Label("No Song Playing");
        // Layout for song playback
        VBox songPlaybackLayout = new VBox();
        songPlaybackLayout.getChildren().addAll(currentSongInfo, musicSlider);
        songPlaybackLayout.setAlignment(Pos.TOP_CENTER);

        //button actions (PLAY N PAUSE)
        //idk if the mediaview there is necessary
        playBar.getChildren().addAll(songLabel, lastButton,pauseButton,playButton,currentDuration,songPlaybackLayout,maxDuration,nextButton,volumeLabel,volumeSlider,clearQueueButton);
        playBar.getStyleClass().add("buttonBar");
        playBar.setAlignment(Pos.CENTER);
     
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
                	try {
						updateCurrentDuration();
					} catch (UnsupportedAudioFileException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                })
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    	
        
        
//        volumeSlider.valueProperty().addListener(new InvalidationListener() {
//    	    public void invalidated(Observable ov) {
//    	       if (volumeSlider.isValueChanging()) {
//    	           mediaPlayer.setVolume(volumeSlider.getValue() / 100.0);
//    	       }
//    	    }
//    	});
        
		//////////////////////////////////////////////			
		// ===== ADDING ELEMENTS TO MAIN WIND ===== //
		//////////////////////////////////////////////
		/// 
        HBox mediaPane = new HBox();
        HBox.setHgrow(mainWindow, Priority.ALWAYS);
        mediaPane.getChildren().addAll(mainWindow,queue);
        // Add all elements to main window
        //border.getChildren().addAll(tabPane,topBar,playBar);  
    	mainWindow.getChildren().addAll(tableView);        
//        VBox.setVgrow(tabPane, Priority.ALWAYS); // Allows tab pane to extend to bottom of screen
        StackPane.setAlignment(topBar, Pos.TOP_RIGHT); // Push search/buttons to the right
        BorderPane.setAlignment(playBar, Pos.BOTTOM_LEFT);
        //padding n margin stuff that can probably be added to css later
        BorderPane.setMargin(playBar, new Insets(10,10,10,10));
        StackPane.setMargin(topBar, new Insets(10,10,10,10));
        //tabPane.setPadding(new Insets(10,10,10,10));
        topBar.setPadding(new Insets(5,5,5,5));
        queue.minWidth(2000);
        queue.maxWidth(2000);
        queue.prefWidth(2000);
        //tabPane.setTabMinHeight(28);
        
        //adding the elements to the borderpane, you have to do them
        //seperate like this for adding them to different regions else it
        //doesn't work
        //border.setRight(queue);
        border.setBottom(playBar);
        border.setCenter(mediaPane);
        border.setTop(topBar);
        
        //Scene scene = new Scene(mainWindow, 800, 600);
        Scene scene = new Scene(border, 800, 600);
        
        primaryStage.setTitle("DJava Application (TEST)");
        primaryStage.setScene(scene);
        primaryStage.show();
        searchBar.setPrefWidth(topBarWidth - helpButton.getWidth() - settingsButton.getWidth());
        scene.getStylesheets().add(getClass().getResource("default.css").toExternalForm());
    }
}