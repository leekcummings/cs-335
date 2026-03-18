package djava;
// https://www.youtube.com/watch?v=GNsBTP2ZXrU

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


//THIS COULD THEORETICALLY BE USED BUT IDK IF WE WANNA DO THAT, I AM JUST LEAVING
//THIS HERE FOR NOW BUT DON'T WORRY ABT IT FOR THE MOMENT
public class TableViewManager {
//	//Song tab
//	//these are the colums in the table for the song tab
//	private static TableColumn<Song,String> songTitle = new TableColumn<>();
//	
//	private static TableColumn<Song,String> songAlbum = new TableColumn<>();
//	
//	private static TableColumn<Song,String> songArtist = new TableColumn<>();
//	
//	private static TableView<Song> songTable = new TableView<>();
//	
//	//https://docs.oracle.com/en/java/java-components/javafx/25/docs/javafx.controls/javafx/scene/control/TableView.html
//	//this can be an arraylist probably later
//	static ObservableList<Song> initalData(){
//		//this will work with the json file but don't have that implemented yet
//		Song s1 = new Song("Year Zero","Infesstisium","Ghost");
//		Song s2 = new Song("The Call","Black & Blue","The Backstreet Boys");
//		ObservableList<Song> rows = FXCollections.observableArrayList(s1,s2);
//		return rows;
//	}
//	
//	public static TableView<Song> initalize() {
//		songTitle.setCellValueFactory(new PropertyValueFactory<Song,String>("Title"));
//		songAlbum.setCellValueFactory(new PropertyValueFactory<Song,String>("Album"));
//		songArtist.setCellValueFactory(new PropertyValueFactory<Song,String>("Artist"));
//		
//		songTable.setItems(initalData());
//		return(songTable);
//	}
}
