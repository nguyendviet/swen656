package edu.umgc.swen656.aop1;

import org.aspectj.lang.annotation.After;

public aspect Referee {
	// After the board is updated (valid move), the referee evaluates it.
	@After("execution(* edu.umgc.swen656.aop1.TicTacToe.updateBoard(..))")
	public void evaluate() {
		TicTacToe.evaluateBoard();
	}
}
