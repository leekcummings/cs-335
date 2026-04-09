// DO NOT RUN THIS FILE, RUN Starter.java TO LAUNCH GUI

package djava;

import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

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
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;

public class Javafx extends Application {
	Slider slider;
	Time time;
	Duration duration;
	static MediaPlayer mediaPlayer;
	static Media media;
	MediaView mediaView;
	static ArrayList<Song> queueList = new ArrayList<>();
	static ArrayList<TableView<Song>> tables = new ArrayList<>();
	TableView<Song> queue;
	Slider volumeSlider;
	int queueIndex = 0;
	Duration lastDet = new Duration(2000);
	ArrayList<Song> songs;
	
	// !!! CHANGE THIS VALUE TO BE A PART OF CONFIG FILE
	// THIS IS A DEFAULT VALUE FOR TESTING
	// This HashMap links the name of the column to the Song attribute
	// It is linked because it's also in order of priority
	static LinkedHashMap<String, String> songColNamesPriority = new LinkedHashMap<>(
			Map.of("Artist", "artist",
			"Album", "album",
			"#", "track",
			"Title", "title"));
	
    public static void main(String[] args) {
        launch(args);
    }
    
    /////////////////////////////////
    //==== QUEUE FUNCTIONALITY ====//
    /////////////////////////////////
   
    public void addToBack(Song song) {
    	if(queueList.size() == 0) {
    		playSong(song);
    	}
    	queueList.add(song);
    	queue.setItems((ObservableList<Song>) FXCollections.observableArrayList(queueList));
    	
    }
    
    public void addToFront(Song song) {
    	if(queueList.size() > 0) {
    		queueList.add(queueIndex+1,song);
    	}else {
    		queueList.add(queueIndex,song);
    		playSong(song);
    	}
    	queue.setItems((ObservableList<Song>) FXCollections.observableArrayList(queueList));
    }
    
    public void addManyToBack(FilteredList<Song> list) {
    	for(Song song: list) {
    		addToBack(song);
    	}
    }
    
    public static void playSong(Song song) {
    	mediaPlayer.stop();
    	media = new Media(new File(song.getPath()).toURI().toString());
    	mediaPlayer = new MediaPlayer(media);
    	mediaPlayer.play();
    }
    
    public void clearQueue() {
    	queueList.clear();
    	queue.setItems((ObservableList<Song>) FXCollections.observableArrayList(queueList));
    	queueIndex = 0;
    }

		////////////////////////////////////////
		//==== MUSIC PLAYER FUNCTIONALITY ====//
		////////////////////////////////////////
	
    public void playNext() {
    	if(queueIndex+1 > queueList.size()){
    		System.out.println("Out of range of queue" + queueList.size());
    	} else {
    		queueIndex++;
    		playSong(queueList.get(queueIndex));
    	}
    }
    
    public void playLast() {
    	if(mediaPlayer.getCurrentTime().lessThan(lastDet)) {
    		if(queueIndex-1 < 0){
        		System.out.println("Out of range of queue");
        	} else {
        		queueIndex--;
        		playSong(queueList.get(queueIndex));
        	}
    	} else {
    		playSong(queueList.get(queueIndex));
    	}
    }
    
    public void album(Song song){
    	String lowerCaseFilter = song.getAlbum().toLowerCase();
    	FilteredList<Song> filteredData = new FilteredList<Song>(FXCollections.observableArrayList(songs), p -> {
    		// If filter text is empty, display all persons.
    		// Compare first name and last name of every person with filter text.
    		//checks all relivant fields
    		if (p.getAlbum().toLowerCase().contains(lowerCaseFilter)) {
    			return true;
    			}return false; // Does not match.
    	});
		addManyToBack(filteredData);
		ArrayList<Song> sortedData = new ArrayList<>();
		for(Song s:filteredData) {
			for()
		}
    }
    
    public void albumNext(Song song) {
    	
    }
    
    public void artist(Song song) {
    	
    }
    
    public void artistNext(Song song) {
    	
    }
    
		
    //================THIS NO LONGER/CURRENTLY HAS USE===================//
    // Give it the row data of song info
    // Then give it the column that is the main one from songColNamesPriority
    // It'll handle the rest
//    public static TableView<Song> createTable(ObservableList<Song> rows, String priority) {
//    	TableView<Song> table = new TableView<>();
//    	//adding tables into an array (this makes it was easier later)
//    	tables.add(table);
//    	
//    	//this basically means that only one thing can be selected at a time
//    	table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
//    	ArrayList<TableColumn<Song,String>> sortOrder = new ArrayList<>();
//    	// Add songs to table
//    	table.setItems(rows);
//    	// Do priority column first
//    	TableColumn<Song,String> firstCol = new TableColumn(priority);
//    	firstCol.setCellValueFactory(new PropertyValueFactory<Song,String>(songColNamesPriority.get(priority)));
//    	table.getColumns().add(firstCol);
//    	table.getSortOrder().add(firstCol);
//		sortOrder.add(firstCol);
//    	// Create each column using arrays
//    	for (Entry<String, String> col : songColNamesPriority.entrySet()) {
//    		String colName = col.getKey();
//    		String songAttribute = col.getValue();
//    		// If we haven't already added the column first
//    		if (colName != priority) {
//    			TableColumn<Song,String> column = new TableColumn(colName);
//        		column.setCellValueFactory(new PropertyValueFactory<Song,String>(songAttribute));
//        		table.getColumns().add(column);
//        		table.getSortOrder().add(column);
//        		sortOrder.add(column);
//    		}
//    	}
//    	return table;
//    }
    
    
    public static Tab createTab(String title, TableView<Song> table) {
    	Tab tab = new Tab(title);
    	tab.setContent(table);
    	return tab;
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
    
    @Override
    public void start(Stage stage) {
    	
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
    	// Search bar (Doesn't work right now)
    	TextField searchBar = new TextField();    	
        searchBar.setPromptText("Search...");

        ComboBox comboBox = new ComboBox();
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
        Popup popup = new Popup();
        GridPane settingsGrid = createSettingsGrid();
        settingsGrid.getStyleClass().add("popup");
        popup.getContent().add(settingsGrid);
 
        // action event
        EventHandler<ActionEvent> settingsEvent = 
        new EventHandler<ActionEvent>() {
 
            public void handle(ActionEvent e)
            {
                if (!popup.isShowing())
                    popup.show(stage);
                else
                    popup.hide();
            }
        };
 
        // when button is pressed
        settingsButton.setOnAction(settingsEvent);
        //------------------------------------------------
        
	        
			    	/////////////////////////////////////////			
			    	// ===== SETTING TOP BAR ELEMETS ===== //
			    	/////////////////////////////////////////
        topBar.getChildren().addAll(searchBar, comboBox, helpButton, settingsButton);

        
					/////////////////////////////////////////////			
					// ===== TABLES FOR DISPLAYING MUSIC ===== //
					/////////////////////////////////////////////
        // Based on code from Oracle https://docs.oracle.com/javafx/2/ui_controls/table-view.htm   
    	// Create ArrayList to hold song data for TableView
        //=====================================================================================================================================
    	songs = new ArrayList<Song>();
    	for (Entry<String, Object> i : JsonManager.songMap.entrySet()) {
    		LinkedHashMap<String, String> song = (LinkedHashMap) i.getValue();
    		// Extract song name, title, artist from HashMap
    		songs.add(new Song(i.getKey(), song.get("albumTitle"), song.get("trackNumber"), song.get("artistName"), song.get("filePath")));  
    	}
    	// Turn the song array into an ObservableList (basically an array, but for Tables)
    	ObservableList<Song> rows = FXCollections.observableArrayList(songs);
    	//=====================================================================================================================================
    	
    	
    	
        // Tabs for different categories of music
        
        
        
//        for (Entry<String, String> tab : songColNamesPriority.entrySet()) {
//        	if (tab.getValue() != "track") {
//        		tabPane.getTabs().add(createTab(tab.getKey(), createTable(rows, tab.getKey())));
//        	}
//        }
            	
        
//        tabPane.getTabs().add(createTab("Albums", new TableView<Song>()));
//        tabPane.getTabs().add(createTab("Artists", new TableView<Song>()));
//    	tabPane.getTabs().add(createTab("Playlist", new TableView<Song>()));
    	
    	//========== TITLES ===========//
        //Tab t = createTab("Media", new TableView<Song>());
    	//mediaPane.getChildren().addAll(t);
    	
    	TableView<Song> tableView = new TableView<>();
    	//adding tables into an array (this makes it was easier later)
    	tables.add(tableView);
    	
    	//this basically means that only one thing can be selected at a time
    	tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    	//ArrayList<TableColumn<Song,String>> sortOrder = new ArrayList<>();
    	// Add songs to table
    	TableColumn<Song,String> title = new TableColumn("Title");
    	TableColumn<Song,Integer> track = new TableColumn("#");
    	TableColumn<Song,String> album = new TableColumn("Album");
    	TableColumn<Song,String> artist = new TableColumn("Artist");
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
		track.setSortable(false);
		title.setMinWidth(150);
		artist.setMinWidth(80);
		album.setMinWidth(100);
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
    	// Create each column using arrays
    		

    	//https://www.youtube.com/watch?v=1wxygyOGtlc
    	// Prevent user from closing tabs
        //tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE); 
        //t.setContent(tableView);
        
        
		//////////////////////////////////
		//==== SEARCH FUNCTIONALITY ====//
		//////////////////////////////////
		//https://codingtechroom.com/question/-javafx-tableview-search-textfield -- motified to fit the way that we are filtering (discovered FilteredList from this source)
		FilteredList<Song> filteredData = new FilteredList<Song>(FXCollections.observableArrayList(songs), p -> true);
		
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
		tableView.getSortOrder().removeAll();
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
        		addToBack(table.getSelectionModel().getSelectedItem());}
        	}
        });
        
        playNext.setOnAction(e -> {
        	for(TableView<Song> table: tables) {
        		if(table.getSelectionModel().getSelectedItem()!= null) {
        		addToFront(table.getSelectionModel().getSelectedItem());}
        	}
        });
        
        playNow.setOnAction(e -> {
        	for(TableView<Song> table: tables) {
        		System.out.print(table.getSelectionModel().getSelectedItem());
        		if(table.getSelectionModel().getSelectedItem()!= null) {
        			System.out.print(table.getSelectionModel().getSelectedItem() + "GOT GOT GOT");
        			playSong(table.getSelectionModel().getSelectedItem());
        		}
        	}
        });
        
        addAlbum.setOnAction(e -> {
        	for(TableView<Song> table: tables) {
        		System.out.print(table.getSelectionModel().getSelectedItem());
        		if(table.getSelectionModel().getSelectedItem()!= null) {
        			System.out.print(table.getSelectionModel().getSelectedItem() + "GOT GOT GOT");
        			album(table.getSelectionModel().getSelectedItem());
        		}
        	}
        });
        
        addAlbumNext.setOnAction(e -> {
        	for(TableView<Song> table: tables) {
        		System.out.print(table.getSelectionModel().getSelectedItem());
        		if(table.getSelectionModel().getSelectedItem()!= null) {
        			System.out.print(table.getSelectionModel().getSelectedItem() + "GOT GOT GOT");
        			albumNext(table.getSelectionModel().getSelectedItem());
        		}
        	}
        });
        
        addArtist.setOnAction(e -> {
        	for(TableView<Song> table: tables) {
        		System.out.print(table.getSelectionModel().getSelectedItem());
        		if(table.getSelectionModel().getSelectedItem()!= null) {
        			System.out.print(table.getSelectionModel().getSelectedItem() + "GOT GOT GOT");
        			artist(table.getSelectionModel().getSelectedItem());
        		}
        		
        	}
        });
        
        addArtistNext.setOnAction(e -> {
        	for(TableView<Song> table: tables) {
        		System.out.print(table.getSelectionModel().getSelectedItem());
        		if(table.getSelectionModel().getSelectedItem()!= null) {
        			System.out.print(table.getSelectionModel().getSelectedItem() + "GOT GOT GOT");
        			artistNext(table.getSelectionModel().getSelectedItem());
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
        			clearQueue();
        			addToFront(table.getSelectionModel().getSelectedItem());
        			playSong(table.getSelectionModel().getSelectedItem());	
        		}
        	});
        }

        
				///////////////////////
				// ===== QUEUE ===== //
				///////////////////////
        ObservableList<Song> data = (ObservableList<Song>) FXCollections.observableArrayList(queueList);
        System.out.println(queueList);
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
        int playBarWidth = 400;
    	playBar.setMaxWidth(playBarWidth);
    	playBar.autosize();
    	
    	//BUTTONS===============================
    	Button playButton = new Button("|>");
    	Button pauseButton = new Button("||");
    	Button nextButton = new Button(">>");
    	Button lastButton = new Button("<<");
    	Button clearQueueButton = new Button("Clear Queue");

    	
    	clearQueueButton.setOnAction(event -> {clearQueue();});
    	playButton.setOnAction(event -> {mediaPlayer.play();});
        pauseButton.setOnAction(event -> {mediaPlayer.pause();});
        nextButton.setOnAction(event -> {playNext();});
        lastButton.setOnAction(event -> {playLast();});

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
        mediaPlayer.setOnEndOfMedia( () -> {playNext();});
        //music plays on default, this is temp n for testing
        //mediaPlayer.setAutoPlay(true);
        //slider
        slider = new Slider();
        HBox.setHgrow(slider, Priority.ALWAYS);
        slider.setMinSize(300, 50);
        //button actions (PLAY N PAUSE)
        //idk if the mediaview there is necessary
        playBar.getChildren().addAll(lastButton,pauseButton,playButton,slider,nextButton,volumeLabel,volumeSlider,clearQueueButton);
        playBar.getStyleClass().add("buttonBar");
        
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
        
        stage.setTitle("DJava Application (TEST)");
        stage.setScene(scene);
        stage.show();
        searchBar.setPrefWidth(topBarWidth - helpButton.getWidth() - settingsButton.getWidth());
        //scene.getStylesheets().add(getClass().getResource("default.css").toExternalForm());
    
       
    }
}