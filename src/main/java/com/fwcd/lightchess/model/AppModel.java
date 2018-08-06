package com.fwcd.lightchess.model;

import java.util.Optional;

import com.fwcd.lightchess.model.match.ChessMatch;

public class AppModel {
	private final ChessBoardModel chessBoard = ChessBoardModel.withInitialSetup();
	private Optional<ChessMatch> match = Optional.empty();
	
	public ChessBoardModel getChessBoard() { return chessBoard; }
	
	public Optional<ChessMatch> getMatch() { return match; }
}
