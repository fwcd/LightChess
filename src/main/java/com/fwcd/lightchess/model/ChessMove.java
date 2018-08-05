package com.fwcd.lightchess.model;

import java.util.Optional;

import com.fwcd.lightchess.model.piece.ChessPieceModel;

public class ChessMove {
	private final ChessPieceModel piece;
	private final ChessPosition origin;
	private final ChessPosition destination;
	private final Optional<ChessPosition> enPassantCapturePos;
	
	public ChessMove(ChessPieceModel piece, ChessPosition origin, ChessPosition destination) {
		this(piece, origin, destination, Optional.empty());
	}
	
	public ChessMove(ChessPieceModel piece, ChessPosition origin, ChessPosition destination, Optional<ChessPosition> enPassantCapturePos) {
		this.piece = piece;
		this.origin = origin;
		this.destination = destination;
		this.enPassantCapturePos = enPassantCapturePos;
	}
	
	public ChessPosition getDestination() { return destination; }
	
	public ChessPosition getOrigin() { return origin; }
	
	public ChessPieceModel getPiece() { return piece; }
	
	public Optional<ChessPosition> getEnPassantCapturePos() { return enPassantCapturePos; }
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		ChessMove other = (ChessMove) obj;
		return piece.equals(other.piece)
			&& origin.equals(other.origin)
			&& destination.equals(other.destination)
			&& enPassantCapturePos.equals(other.enPassantCapturePos);
	}
	
	@Override
	public int hashCode() {
		return piece.hashCode()
			* origin.hashCode()
			* destination.hashCode()
			* enPassantCapturePos.hashCode()
			* 3;
	}
}
