package edu.umgc.swen656.aop1;

public class Game {

	public static void main(String[] args) {
		TicTacToe ticTacToe = new TicTacToe();
		while (!ticTacToe.isGameOver()) {
			ticTacToe.move();
		}
		System.out.println("I hope you enjoyed the game.");
	}

}
