package edu.umgc.swen656.aop1;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class TicTacToe {
	
	private static boolean gameOver = false;
	private static int boardSize = 3;
	private static String[][] board = new String[boardSize][boardSize];
	private static int row = 0;
	private static int col = 0;
	private static int[] rows = new int[boardSize];
	private static int[] cols = new int[boardSize];
	private static int diagonal = 0;
	private static int antiDiagonal = 0;
	private static int player = 1;
	private static int moves = 0;

	public TicTacToe() {
		printIntro();
		initGame();
	}
	
	public void move() {
		// If the game is not over, check the move.
		if (!isGameOver()) {
			Scanner sc = new Scanner(System.in);
			
			System.out.print("Player " + getPlayerName() + "'s turn. Pick a space: ");
			
			try {
				// Get and validate user's input from console.
			    int space = sc.nextInt();
			    if (space < 1 || space > 9) {
			    	System.out.println("\tInvalid input. Please enter a number from 1 to 9.");
			    	move();
			    } else if (!isEmpty(space)) {
			    	System.out.println("\tSpace is not empty. Please choose another space.");
			    	move();
			    } else {
			    	// Record the move.
			    	moves += 1;
			    	updateBoard(player, space);
			    	nextPlayer();
			    	move();
			    }
			} catch(InputMismatchException e) {
				// Catch input is not integer.
				System.out.println("\tInvalid input. Please enter a number from 1 to 9.");
				move();
			}
			
		    sc.close();
		}
	}
	
	public boolean isGameOver() {
		return gameOver;
	}
	
	private static void printIntro() {
		System.out.println("Viet Nguyen - UMGC SWEN 656 - AOP Project 1");
		System.out.println("~*~ Tic Tac Toe ~*~");
		System.out.println("Instruction:\n"
				+ "- Each player picks a space using a number from 1 to 9. Board:\n"
				+ "\t123\n"
				+ "\t456\n"
				+ "\t789\n"
				+ "- Press Enter/Return key to submit the move.\n"
				+ "- See game rules: https://en.wikipedia.org/wiki/Tic-tac-toe\n");
	}
	
	private void initGame() {
		for (String[] row : board) {
		    Arrays.fill(row, ".");
		}
		printBoard();
	}
	
	private static void printBoard() {
		System.out.println("Current board:\n");
		System.out.println(Arrays.deepToString(board)
                .replace("],","\n").replace(",","")
                .replaceAll("[\\[\\]]", " "));
		System.out.println(System.lineSeparator());
	}
	
	
	private void updateBoard(int player, int space) {
		// Get row and col value from the chosen space
		int[] coordinate = toCoordinate(space);
		int currentRow = coordinate[0];
		int currentCol = coordinate[1];
		// Update the value in each row and col
		rows[currentRow] += player;
		cols[currentCol] += player;
		// Check & update if the space can make a diagonal or anti-diagonal win
		if (currentRow == currentCol){
            diagonal += player;
        }
        if (currentRow + currentCol == boardSize - 1){
            antiDiagonal += player;
        }
		// Update the board sign at the correct position
		String sign = player == 1? "x" : "o";
		board[currentRow][currentCol] = sign;
		row = currentRow;
		col = currentCol;
	}
	
	public static void evaluateBoard() {
		printBoard();
		
		// Check if current move meets any of the winning conditions.
		// Player 1 = 1, player 2 = -1. 
		// If any of the lines has absolute value of sum = board size, that player wins.
		boolean fullRow = Math.abs(rows[row]) == boardSize;
		boolean fullCol = Math.abs(cols[col]) == boardSize;
		boolean fullDiagonal = Math.abs(diagonal) == boardSize;
		boolean fullAntiDiagonal = Math.abs(antiDiagonal) == boardSize;
		
        if (fullRow || fullCol || fullDiagonal || fullAntiDiagonal) {
        	gameOver = true;
            System.out.println("Game Over. Player " + getPlayerName() + " wins!");
            return;
        }
        
        // Check if the board is full.
 		if (moves == boardSize * boardSize) {
 			gameOver = true;
 			System.out.println("Game Over. It's a tie.");
 			return;
 		}
	}
	
	private static String getPlayerName() {
		return player == 1 ? "1" : "2";
	}
	
	// Switch to next player. Player 2 = -1 due to the check sum logic in evaluateBoard().
	private void nextPlayer() {
		player *= -1;
	}
	
	// Check if the current space is available.
	private boolean isEmpty(int space) {
		int[] coordinate = toCoordinate(space);
		return board[coordinate[0]][coordinate[1]] == ".";
	}
	
	// Convert space number to row and col values.
	private static int[] toCoordinate(int space) {
		int[] coordinate = {0, 0};
		
		if (space < 4) {
			coordinate[0] = 0;
		} else if (space < 7) {
			coordinate[0] = 1;
		} else {
			coordinate[0] = 2;
		}
		coordinate[1] = space%3 != 0 ? space%3 - 1 : 2;
		
		return coordinate;
	}

}
