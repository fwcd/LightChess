package com.fwcd.lightchess.model;

import com.fwcd.lightchess.model.piece.ChessPieceModel;

public class ChessMove {
	private final ChessPieceModel piece;
	private final ChessPosition origin;
	private final ChessPosition dest;
	
	public ChessMove(ChessPieceModel piece, ChessPosition origin, ChessPosition dest) {
		this.piece = piece;
		this.origin = origin;
		this.dest = dest;
	}
	
	public ChessPosition getDest() { return dest; }
	
	public ChessPosition getOrigin() { return origin; }
	
	public ChessPieceModel getPiece() { return piece; }
}
