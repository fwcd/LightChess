package com.fwcd.lightchess.model;

import com.fwcd.lightchess.model.piece.ChessPieceModel;

public class ChessMove {
	private final ChessPieceModel piece;
	private final ChessPosition origin;
	private final ChessPosition destination;
	
	public ChessMove(ChessPieceModel piece, ChessPosition origin, ChessPosition destination) {
		this.piece = piece;
		this.origin = origin;
		this.destination = destination;
	}
	
	public ChessPosition getDestination() { return destination; }
	
	public ChessPosition getOrigin() { return origin; }
	
	public ChessPieceModel getPiece() { return piece; }
	
	@Override
	public boolean equals(Object obj) {
		ChessMove other = (ChessMove) obj;
		return piece.equals(other.piece) && origin.equals(other.origin) && destination.equals(other.destination);
	}
}
