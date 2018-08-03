package com.fwcd.lightchess.model;

import com.fwcd.lightchess.model.piece.ChessPieceModel;

public class ChessMove {
	private final ChessPieceModel piece;
	private final ChessPosition from;
	private final ChessPosition to;
	
	public ChessMove(ChessPieceModel piece, ChessPosition from, ChessPosition to) {
		this.piece = piece;
		this.from = from;
		this.to = to;
	}
}
