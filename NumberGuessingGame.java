import java.util.Scanner;

// method to play the game

public class NumberGuessingGame {

	private int correctNumber;
	private int guesses;
	private int highestNumber;
//	private char guessType;
	
	Scanner kb = new Scanner(System.in);
	
	/**
	 * Default constructor handles setting the game up which requires user input
	 */
	public NumberGuessingGame() {
		String gameLevel = readGameLevel();
		String gameType = readGameType();
		
		// Tested
		if (gameLevel.equals("EASY")) {
			correctNumber = getNumber(10);
			guesses = 5;
			highestNumber = 9;
		}
		else if (gameLevel.equals("MEDIUM")) {
			correctNumber = getNumber(100);
			guesses = 10;
			highestNumber = 99;
		}
		else {
			correctNumber = getNumber(1000);
			guesses = 20;
			highestNumber = 999;
		}
		
		// If game type is limited it's already declared above.
		if (gameType.equals("U")) {
			guesses = Integer.MAX_VALUE;
//			guessType = 'U';
//		}
//		else {
//			guessType = 'L';
		}
	}
	
	// Don't have accessors or mutators because the game is self contained
//	public int getNumber() {
//		return correctNumber;
//	}
//	
//	public int getGuesses() {
//		return guesses;
//	}
	
	public void playGame() {
		int guessRemaining = guesses;
		
		while (guessRemaining > 0) {
			int userGuess = readGuess();
			int direction = evaluateGuess(userGuess);
			
			if (direction > 0)
				System.out.println("Guess is too low");
			else if (direction < 0)
				System.out.println("Guess is too high");
			else {
				System.out.println("Congratulation you won!");
				System.exit(0);
			}
			
			guessRemaining--;
		}
		
		System.out.println("Sorry you lost.");
		System.out.println("The correct number was " + correctNumber);
	}
	
	/**
	 * Method determines whether user guess is correct, return 0
	 * User next guess should go down, return -1
	 * User next guess should go up, return +1
	 * @param guess
	 * @return
	 */
	private int evaluateGuess(int guess) {
		int playDirection;
		if (guess == correctNumber)
			playDirection = 0;
		else if (guess > correctNumber)
			playDirection = -1;
		else
			playDirection = 1;
		
		return playDirection;
		
	}
	
	private String readGameLevel() { 
		// Tested 
		System.out.println("Would you like to play Easy (0-9), Medium (0-99), or Hard (0-999)?");
		System.out.println("Please enter 'Easy', 'Medium', or 'Hard'.");
		String gameLevel = kb.nextLine().trim();
		
		boolean correctLevel = (gameLevel.equalsIgnoreCase("easy") 
				|| gameLevel.equalsIgnoreCase("medium")
				|| gameLevel.equalsIgnoreCase("hard"));
		
		while (!correctLevel) {
			System.out.println("Please enter the level you'd like to play.");
			gameLevel = kb.nextLine().trim();
			correctLevel = (gameLevel.equalsIgnoreCase("easy") 
					|| gameLevel.equalsIgnoreCase("medium")
					|| gameLevel.equalsIgnoreCase("hard"));
		}
		
		return gameLevel.toUpperCase();
	}
	
	private String readGameType() {
		// Tested
		System.out.print("Would you like unlimited guesses or limited guesses: ");
		System.out.println("Easy (5 attempts), Medium (10 attempts), Hard (20 attempts)");
		System.out.println("Enter either 'U' for unlimited or 'L' for limited.");
		
		String gameType = kb.nextLine();
		
		while (!((gameType.equalsIgnoreCase("L")) || gameType.equalsIgnoreCase("U"))) {
			System.out.println("Enter either 'U' for unlimited or 'L' for limited.");
			gameType = kb.nextLine();
		}
		
		return gameType.toUpperCase();
	}
	
	private int readGuess() {
		System.out.println("Please enter your guess.");
		int guess = kb.nextInt();
		
		while ((guess < 0) || (guess > highestNumber)) {
			System.out.println("Number can't be in this range. Remember your range is: ");
			System.out.println(0 + " to " + highestNumber + ", inclusive.");
			System.out.println("Please reenter your guess");
			guess = kb.nextInt();
		}
		
		return guess;	
	}
	
	private int getNumber(int max) {
		// Tested
		return (int) (Math.random() * max); 
		// The formula is Math * (max - min) + min; however min is always 0
	}
	
	
	public static void main(String[] args) {
		//System.out.println(readGameLevel());
		//System.out.println(readGameType());
//		Project3 test = new Project3("EASY","L");
//		System.out.println(test.readGuess());
		
		NumberGuessingGame test = new NumberGuessingGame();
		test.playGame();
	}
}
