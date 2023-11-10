import java.util.HashMap;
import java.util.Scanner;

/**
 * Key:
 * Un-played cell: *
 * Miss: O
 * Hit: X
 */

public class BattleShip {

	private Scanner kb = new Scanner(System.in);
	private char[][] board;
	private int[] topRow = {1,2,3,4,5,6,7,8,9,10};
	private char[] firstCol = {'A','B','C','D','E','F','G','H','I','J'};
	private HashMap<String,String> cellContents = new HashMap<>();
	private Ship carrier = new Ship("Carrier", 5,"C");
	private Ship battleship = new Ship("Battleship",4,"B");
	private Ship destroyer = new Ship("Destroyer",3,"D");
	private Ship submarine = new Ship("Submarine",3,"S");
	private Ship patrolBoat = new Ship("Patrol Boat",2,"P");
	
	public BattleShip() {
		board = new char[10][10];
		for (int row = 0; row < board.length; row++)
			for (int col = 0; col < board[row].length; col++)
				board[row][col] = '*';
		for (int row = 0; row < firstCol.length; row++)
			for (int col = 0; col < topRow.length; col++) {
				String temp = Character.toString(firstCol[row]) + Integer.toString(topRow[col]);
				cellContents.put(temp, "*");
			}
		setShip(5,"C");
		setShip(4,"B");
		setShip(3,"D");
		setShip(3,"S");
		setShip(2,"P");
		
	}
	
	public void displayBoard() {
		for (int i = 0; i <= topRow.length; i++) {
			if (i == 0)
				System.out.print("  ");
			else
				System.out.print(topRow[i-1] + " ");
		}
		System.out.println();
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col <= board[row].length; col++) {
				if (col == 0)
					System.out.print(firstCol[row] + " ");
				else
					System.out.print(board[row][col-1] + " ");
			}
			System.out.println();
		}
	}
	
	
	public boolean playTogether(BattleShip otherPlayer) {
		System.out.println("Player 1");
		otherPlayer.displayBoard();
		otherPlayer.makeMove();
		boolean player1Win = otherPlayer.carrier.shipSunk() && otherPlayer.battleship.shipSunk() 
				&& otherPlayer.destroyer.shipSunk() && otherPlayer.submarine.shipSunk() 
				&&  otherPlayer.patrolBoat.shipSunk();
		if (player1Win) {
			System.out.println("Player 1 you win! Congratulations!");
			return player1Win;
		}
		
		System.out.println("Player 2");
		displayBoard();
		makeMove();
		boolean player2Win = carrier.shipSunk() && battleship.shipSunk() && destroyer.shipSunk() 
				&& submarine.shipSunk() && patrolBoat.shipSunk();
		if (player2Win) {
			System.out.println("Player 2 you win! Congratulations!");
			return player2Win;
		}
		
		return false;

	}
	
	public void makeMove() {
		String userMove = getSelection();
		// Convert letter to row number
		int row = ((int) userMove.charAt(0)) - 65;
		int col = Integer.parseInt(userMove.substring(1)) - 1;
		
		String boardAbbr = cellContents.get(userMove);
		boolean isShipSunk = false;
		String sunkShipName = null;
		
		if (boardAbbr.equals("*")) {
			System.out.println("Miss!");
			cellContents.put(userMove, "O");
			board[row][col] = 'O';
		}
		else {
			System.out.println("Hit!");
			cellContents.put(userMove, "X");
			board[row][col] = 'X';
			if (boardAbbr.equals("C")) {
				carrier.shipHit();
				isShipSunk = carrier.shipSunk();
				sunkShipName = carrier.getName();
			}
			else if (boardAbbr.equals("B")) {
				battleship.shipHit();
				isShipSunk = battleship.shipSunk();
				sunkShipName = battleship.getName();
			}
			else if (boardAbbr.equals("D")) {
				destroyer.shipHit();
				isShipSunk = destroyer.shipSunk();
				sunkShipName = destroyer.getName();
			}
			else if (boardAbbr.equals("S")) {
				submarine.shipHit();
				isShipSunk = submarine.shipSunk();
				sunkShipName = submarine.getName();
			}
			else { // Patrol Boat
				patrolBoat.shipHit();
				isShipSunk = patrolBoat.shipSunk();
				sunkShipName = patrolBoat.getName();
			}
		}
		
		if (isShipSunk)
			System.out.println("You sunk my " + sunkShipName);
		
	}
	
	public String getSelection() {
		System.out.print("Enter your selection, e.g. D5: ");
		String userInput;
		/* Account for following issues:
		 * User entering spaces between entry
		 * Out of bounds index
		 * Space already played 
		 */
		while (true) {
			userInput = kb.nextLine().trim().toUpperCase();
			
			// Checks if user entered spaces between entry
			if (userInput.length() > 3) {
				userInput = fixSpaceBetween(userInput);
			}
			
			// Checks if user entered too few or too many entries
			if (userInput.length() < 2 || userInput.length() > 3) {
				System.out.print("Invalid entry, must enter single letter and number, e.g. D5: ");
				continue;
			}
			else {
				// Checks correct order letterNumber, in range of board
				int tempCharNum = (int) userInput.charAt(0);
				int tempInpNum;
				// Must use try-catch block b/c can have entry of 10
				try {
					tempInpNum = Integer.parseInt(userInput.substring(1));
					if (tempInpNum <= 0 || tempInpNum > 10) {
						System.out.print("The number must be between 1 and 10: ");
						continue;
					}
				}
				catch (NumberFormatException e) {
					System.out.print("The second entry must be a number between 1 and 10, e.g. D5: ");
					continue;					
				}

				if (tempCharNum < 65 || tempCharNum > 74) {
					System.out.print("The first entry must be a letter between A and J, e.g. D5: ");
					continue;
				}
			}
			
			String boardCheck = cellContents.get(userInput);
			
			if (boardCheck.equals("O") || boardCheck.equals("X")) {
				System.out.print("This square is already played, please replay: ");
				continue;
			}
			
			
			break;
		
		}
		return userInput;
	}
	
	private String fixSpaceBetween(String uI) {
		String fixed = "";
		for (int i = 0; i < uI.length(); i++) {
			if ((int) uI.charAt(i) != 32)
				fixed += uI.charAt(i);
		}
		return fixed;
	}
	
	private void setShip(int shipSize, String shipLetter) {
		
		while (true) {
			int row, col, direction, maxRow=-1, maxCol=-1;
			boolean rowClear = true, colClear = true;
			String pivotPoint,temp;
			
			row = getNumber(10);
			col = getNumber(10);
			pivotPoint = convertToLetter(row) + Integer.toString(col+1);
			
			/* There are multiple checks
			 * 1. If initial position is occupied get a new start
			 * 2. Get direction ship is placed and whether the array can hold it
			 * 3. Check if all positions are unfilled
			 */
			if (! cellContents.get(pivotPoint).equals("*"))
				continue;
			
			direction = getNumber(4);
			boolean directionIsSet = false;
			
			for (int i = 0; i < 4; i++) {
				int[] tempArr = shipBoundary(shipSize,row,col,direction);
				maxRow = tempArr[0];
				maxCol = tempArr[1];
				if (Math.min(maxRow,maxCol) >= 0 && Math.max(maxRow, maxCol) <= 9) {
					directionIsSet = true;
					break;
				}
				else
					direction = (direction + 1) % 4;
			}
			
			if (! directionIsSet)
				continue;
			
//			Second check, if direction can't fit the ship start over
//			Should be duplicative check b/c the loop above ensures the ship sits in the board 
			if (Math.min(maxRow,maxCol) < 0 || Math.max(maxRow, maxCol) > 9)
				continue;
			
			if (row != maxRow) {
				for (int i = 1; i < shipSize; i++) {
					int tempRow = row;
					if (maxRow > row)
						tempRow += i;
					else
						tempRow -= i;
					temp = convertToLetter(tempRow) + Integer.toString(col+1);
					if (! cellContents.get(temp).equals("*")) {
						rowClear = false;
						break;
					}
				}
			}
			else {
				for (int i = 1; i < shipSize; i++) {
					int tempCol = col;
					if (maxCol > col)
						tempCol += i;
					else
						tempCol -= i;
					temp = convertToLetter(row) + Integer.toString(tempCol+1);
					if (! cellContents.get(temp).equals("*")) {
						colClear = false;
						break;
					}
				}
			}
			
			// Third check, all spaces need to be clear
			if (! (rowClear && colClear))
				continue;
			
			cellContents.put(pivotPoint, shipLetter);
			
			if (row != maxRow) {
				for (int i = 1; i < shipSize; i++) {
					int tempRow = row;
					if (maxRow > row)
						tempRow += i;
					else
						tempRow -= i;
					temp = convertToLetter(tempRow) + Integer.toString(col+1);
					cellContents.put(temp, shipLetter);
				}
			}
			else {
				for (int i = 1; i < shipSize; i++) {
					int tempCol = col;
					if (maxCol > col)
						tempCol += i;
					else
						tempCol -= i;
					temp = convertToLetter(row) + Integer.toString(tempCol+1);
					cellContents.put(temp, shipLetter);
				}
			}

			
			break;
		}
		
	}
	
	private int getNumber(int max) {
		return (int) (Math.random() * max); 
		// The formula is Math * (max - min) + min; however min is always 0
	}
	
	private String convertToLetter(int num) {
		char firstConvert = Integer.toString(num).charAt(0);
		int charNum = ((int) firstConvert) + 17;
		return Character.toString((char) charNum);
	}
	
	private int[] shipBoundary(int shipSize, int row, int col, int direction) {
		int maxRow,maxCol;
		switch (direction) {
			case 0: maxRow = row - (shipSize-1);
					maxCol = col;
					break;
			case 1: maxRow = row + (shipSize-1);
					maxCol = col;
					break;
			case 2: maxRow = row;
					maxCol = col - (shipSize-1);
					break;
			case 3: maxRow = row;
					maxCol = col + (shipSize-1);
					break;
			// default will not be reached, but prevents IDE from complaining below
			default: maxRow = row;
					maxCol = col;
					break;
		}
		int[] toReturn = {maxRow,maxCol};
		return toReturn;
		
	}
	
	public static void main(String[] args) {
		BattleShip player1 = new BattleShip();
		BattleShip player2 = new BattleShip();
		
		while (! player1.playTogether(player2));

	}
}
