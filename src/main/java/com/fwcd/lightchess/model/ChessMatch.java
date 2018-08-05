package com.fwcd.lightchess.model;

public class ChessMatch {
	private final ChessBoardModel board = ChessBoardModel.withInitialSetup();
	private final ChessPlayer white;
	private final ChessPlayer black;
	
	public ChessMatch(ChessPlayer white, ChessPlayer black) {
		this.white = white;
		this.black = black;
	}
	
	public void play() {
		// TODO
	}
}
