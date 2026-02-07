package djava;

public class MenuManager {
	Menus currentMenu = Menus.MAIN;
	
	public
	void parse(String input) {
		int inputNum;
		try {
			inputNum = Integer.parseInt(input);
		}catch(NumberFormatException e){
			System.out.println("ERROR: Unable to parse input.");
			inputNum = 0;
		}
		switch(currentMenu){
		case MAIN:
			switch(inputNum) {
			case 1: //music library
				musicLibrary();
				break;
			case 2: //search for music
				searchMenu();
				break;
			case 3: //options
				options();
				break;
			case 4: //exit program
				exitProgram();
				break;
			default:
				break;
			}
			break;
		case SEARCH:
			switch(inputNum) {
			case 1: //sort by album
				//not made yet
				break;
			case 2: //sort by artist
				//not made yet
				break;
			case 3: //sort by song
				//not made yet
				break;
			case 4: //back
				returnToMainMenu();
				break;
			default:
				break;
			}
			break;
		case MUSIC_LIBRARY:
			switch(inputNum) {
			case 1: //change directory
				returnToSearchMenu();
				break;
			default:
				//will be handled elsewhere
				break;
			}
			break;
		case OPTIONS:
			switch(inputNum) {
			case 1: //change directory
				//not made yet
				break;
			case 2: //reload directory
				//not made yet
				break;
			case 3: //back
				returnToMainMenu();
				break;
			default:
				break;
			}
			break;
			}
		
	}
	
	private
	void mainMenu() {
		
	}
	
	void musicLibrary() {
		
	}
	
	void searchMenu() {
		
	}
	
	void options() {
		
	}
	
	void reloadLib() {
		
	}
	
	void setDirectory() {
		//get the directory and then set it give brief instruction on how
		String newDirectory;
		//get input
		MusicDirectory.set(newDirectory);
	}
	
	void returnToMainMenu() {
		
	}
	
	void returnToSearchMenu() {

	}
	
	void returnToOptions() {
		
	}
	
	void exitProgram() {
		//are you sure you want to exit?
	}
	
}
