/**
 * Play a game of Tic-Tac-Toe
 * Programming project 7 Chapter 7
 */

import java.util.Scanner;

public class TicTacToe {
	private char[][] board = new char[3][3];
	private int moves = 1;
	Scanner kb = new Scanner(System.in);

	public TicTacToe() { // Checked
	for (int row = 0; row< board.length; row++)
		for (int col = 0; col < board[row].length; col++)
			board[row][col] = '*';
	}

	public void addMove() {
		char[][] temp = new char[3][3];
		displayBoard(board);
		System.out.println("Enter your next move anywhere there's a star");
		System.out.println("Enter row (1-3) followed by column (1-3), separated by space.");
		System.out.println(getPlayerTurn() + " it's your move");
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[row].length; col++) {
				temp[row][col] = board[row][col];
			}
		}
	
		int userRow, userCol ;
		String rightMove;
		do {
//			System.out.println("IN DO LOOP");
			userRow = kb.nextInt() - 1;
			userCol = kb.nextInt() - 1;
			
			// Can add a check for nums in range
			
			while (temp[userRow][userCol] != '*') {
				System.out.println("Please enter a valid location.");
				userRow = kb.nextInt() - 1;
				userCol = kb.nextInt() - 1;
			}
			
			// Edit to display input of move to confirm it is correct input from user
			char[][] tempBoard = new char[3][3];
			for (int row = 0; row < tempBoard.length; row++) {
				for (int col = 0; col < tempBoard[row].length; col++)
					tempBoard[row][col] = board[row][col];
			}
			tempBoard[userRow][userCol] = getPlayerTurn();
			displayBoard(tempBoard);
			System.out.println("Is this the move you wanted to make (y/n) ?");
			String catchN = kb.nextLine();
			rightMove = kb.nextLine();
		} while (rightMove.equalsIgnoreCase("N"));
				
		temp[userRow][userCol] = getPlayerTurn();
		board = temp;
		
		if ((moves >= 4) && (aWinner())) {
			System.out.println("Conrats " + getPlayerTurn() + " you won");
			System.exit(0);
		}
		else {
			moves++;
			if (moves == 10){
				System.out.println("It's a draw.");
				System.exit(0);
			}
		}
	}

	private void displayBoard(char[][] inpBoard) { // Checked
		for (int row = 0; row < inpBoard.length; row++) {
			for (int col = 0; col < inpBoard[row].length; col++)
				System.out.print(inpBoard[row][col] + " ");
			System.out.println();
		}
	}

	private char getPlayerTurn() { // Checked
		if (moves % 2 == 0)
			return 'O';
		else
			return 'X';
	}

	private boolean rowWinner(char[][] inpArray) {
		boolean winner = false;
		for (int row = 0; row < inpArray.length; row++) {
			if ((inpArray[row][0] != '*') &&
					(inpArray[row][0] == inpArray[row][1]) && (inpArray[row][1] == inpArray[row][2])) {
				winner = true;
			}
		}
		return winner;
	}

	private boolean colWinner(char[][] inpArray) {
		boolean winner = false;
		for (int col = 0; col < 3; col++) {
			if ((inpArray[0][col] != '*') &&
					(inpArray[0][col] == inpArray[1][col]) && (inpArray[1][col] == inpArray[2][col])) {
				winner = true;
			}
		}
		return winner;
	}

	private boolean diagWinner(char[][] inpArray) {
		boolean leftWinner = true, rightWinner = true;
		// Left to right
		for (int i = 0; i < 2; i++) {
			if ((inpArray[i][i] != inpArray[i+1][i+1]) || (inpArray[i][i] == '*'))
				leftWinner = false;
		}
		//Right to left
		for (int i = 2; i > 0; i--) {
			if ((inpArray[i][i] != inpArray[i-1][i-1]) || (inpArray[i][i] == '*'))
					rightWinner = false;
			}
			if ((leftWinner) || (rightWinner))
				return true;
			else
				return false;
		}
	
	private boolean aWinner() {
		if ((rowWinner(board)) || (colWinner(board)) || (diagWinner(board)))
			return true;
		else
			return false;
	}
	
	public static void main(String[] args) {
		TicTacToe game1 = new TicTacToe();
		while (true)
			game1.addMove();
			
	}
}
