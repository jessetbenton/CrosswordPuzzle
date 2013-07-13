import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


//["question"] [answer] [starting square] [length of word] [direction: ACROSS or DOWN] [end of line: #]

public class Parser {
	

	static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	static String DB_FILE = "CrosswordDB.txt";
	
	public static Puzzle parsePuzzle( File file ) throws Exception {
		Puzzle puzzle = new Puzzle();
		
		String[] data = readData(file, "#");
		//remove empty space at the end of each line
		for( int j = 0; j < data.length; j++ ) {
			data[j] = data[j].trim();
		}
		//Set Puzzle Size
		String size = data[0];
		puzzle.setSize(Integer.valueOf(size));
		
		//Set Puzzle Category
		puzzle.setGameCategory(data[1]);
		
		//Set Puzzle Number
		puzzle.setPuzzleNumber(data[2]);
		
		//Parse QuestionAndAnswer Section
		QuestionAndAnswer across[] = new QuestionAndAnswer[ puzzle.getSize() * puzzle.getSize() + 1 ];
		QuestionAndAnswer down[] = new QuestionAndAnswer[ puzzle.getSize() * puzzle.getSize() + 1 ];
		int i = 3;
		while( i < data.length && data[i] != null ) {
			parseQuestionAndAnswer(data[i], across, down);
			i++;
		}
		
		puzzle.setAcross(across);
		puzzle.setDown(down);
		puzzle.setPuzzleID();
		puzzle.setSolution();
		
		return puzzle;
	}
	
	
	//reads generic file, returns an array of strings which is split at the parameter 'delimiter'
	private static String[] readData(File file, String delimiter ) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader( file ));
		
		StringBuilder data = new StringBuilder();
		String line;
		while((line = in.readLine()) != null ){
			data.append(line);
		}
		
		return data.toString().split(delimiter);
	}
	
	
	
	//["question"] [answer] [starting square] [length of word] [direction: ACROSS or DOWN] [end of line: #]
	private static void parseQuestionAndAnswer( String line, QuestionAndAnswer[] across, QuestionAndAnswer[] down) {
		//pulls question from line, skip first " by starting substring from index 1
		String question = line.substring(1, line.indexOf("\"", 1));
		
		
		
		//split line without the question
		//get substring after length of question compensating for the two quotes and space after.
		line = line.substring(question.length() + 3);
		//the rest of the line minus the question
		String[] split = line.split(" ");
		
		String answer = split[0];
		int startingSquare = Integer.parseInt(split[1]);
		int length = Integer.parseInt(split[2]);
		String direction = split[3];
		//end of line char is at index 4
		
		if( direction.equals("ACROSS") ) {
			across[startingSquare] = new QuestionAndAnswer();
			across[startingSquare].setQuestion(question);
			across[startingSquare].setAnswer(answer);
			across[startingSquare].setStartingSquare(startingSquare);
			across[startingSquare].setLength(length);
			across[startingSquare].setDirection(direction);
		}
		else if( direction.equals("DOWN" ) ) {
			down[startingSquare] = new QuestionAndAnswer();
			down[startingSquare].setQuestion(question);
			down[startingSquare].setAnswer(answer);
			down[startingSquare].setStartingSquare(startingSquare);
			down[startingSquare].setLength(length);
			down[startingSquare].setDirection(direction);
		}
		
	}

	public static Game parseGame( String game ) {
		Game out = new Game();
		
		String split[] = game.split("~");
		String gameInfo[] = split[0].split(" ");
		String clock[] = split[1].split(" ");
		out.setClock(clock[0], clock[1], clock[2]);
		int size = Integer.parseInt(gameInfo[2]);
		String across[] = new String[size*size+1];
		String down[] = new String[size*size+1];

		//set puzzle name
		String puzzle_file_name = gameInfo[0] + gameInfo[1] + ".p"; 
		
		//set completed
		if( gameInfo[3].equals("true")) {
			out.setCompleted();
		}
		
		
		
		//set score
		out.setScore(Integer.parseInt(gameInfo[4]));
		try {
			out.setPuzzle( parsePuzzle( new File( puzzle_file_name ) ) );
		} 
		catch( FileNotFoundException f ) {
			System.out.println("File: " + puzzle_file_name + " NOT FOUND. UNABLE TO CONTINUE GAME");
		}
			
		catch (Exception e) {
			e.printStackTrace();
		}
		
		for( int i = 2; i < split.length; i++ ) {
			String[] line = split[i].split(" "); 
			int startingSquare = Integer.parseInt(line[0]);
			String direction = line[1];
			if( line.length > 2 ) {
				if( direction.equals("ACROSS") ) {
					across[startingSquare] = line[2];
				}
				else if( direction.equals("DOWN" ) ) {
					down[startingSquare] = line[2];
				}
			}
		}
		
		out.setAcross(across);
		out.setDown(down);
		
		return out;
	}
	
	public static Player readPlayerGames( String allGames ) throws ParseException {
		Player player = new Player();
		String data[] = allGames.split("~", 3);
		String playerLogin[] = data[0].split(" ");
		player.setUserName(playerLogin[0]);
		player.setPassword(playerLogin[1]);
		player.setRegistrationDate(Long.parseLong(data[1]));
		
		//check to see if player has any games
		if( data[2].length() > 0 ) {
			//divide up the games into games[]
			String games[] = data[2].split("#");
			ArrayList<Game> gamesList = new ArrayList<Game>();
			for( String game : games ) {
				gamesList.add(parseGame(game));
			}
			
			player.setGameHistory(gamesList);
		}
		return player;
	}
	
	public static Database parseDB() throws IOException {
		//read database into File obj
		File file = new File(DB_FILE);		
		
		Database out = new Database();
		
		
		//read data file into two parts Users and Global High Scores
		String[] data = readData(file, "###");
		//remove empty space at the end of each line
		for( int j = 0; j < data.length; j++ ) {
			data[j] = data[j].trim();
		}
		out.userSection = data[0];
		
		ArrayList<Player> formattedUsers = new ArrayList<Player>();
		//protect against Database with no User Data
		if( !data[0].equals("") ) {
			//USERS SECTION
			//split the string into different users
			String[] allUsers	= data[0].split("##");
			int i = 0;
			for( String user : allUsers ) {
				//split each user up into games
				try {
					formattedUsers.add( readPlayerGames( user ) );
				} catch (ParseException e) {
					e.printStackTrace();
				}
				i++;
			}
		}
		out.setPlayerData(formattedUsers);

		//GLOBAL STATS SECTION
		String lines[] = data[1].split("~");
		ScoreStructure[] playerScores = new ScoreStructure[lines.length];
		for( int p = 0; p < lines.length; p++ ) {
			playerScores[p] = new ScoreStructure(lines[p]);
		}
		
		GlobalHighScores highScores = new GlobalHighScores( playerScores );
		out.setGlobalHighScores( highScores );
		
		//keep full string, for good measure?
		out.globalSection = data[1];
	
		
		
		return out;
	}
	
	//Finds all files with the ".txt", in the directory of the program.
	public static File[] puzzleFinder(){

		File dir = new File("./");

		return dir.listFiles(new FilenameFilter() { 
			public boolean accept(File dir, String filename)
			{ return filename.endsWith(".p"); }
		} );

	}

	
}
