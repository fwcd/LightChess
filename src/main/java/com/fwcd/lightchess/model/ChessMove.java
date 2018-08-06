package com.fwcd.lightchess.model;

import java.util.Optional;

import com.fwcd.lightchess.model.piece.ChessPieceType;

public class ChessMove {
	private final ChessPieceType pieceType;
	private final ChessPosition origin;
	private final ChessPosition destination;
	private final Optional<ChessPosition> enPassantCapturePos;
	
	public ChessMove(ChessPieceType pieceType, ChessPosition origin, ChessPosition destination) {
		this(pieceType, origin, destination, Optional.empty());
	}
	
	public ChessMove(ChessPieceType pieceType, ChessPosition origin, ChessPosition destination, Optional<ChessPosition> enPassantCapturePos) {
		this.pieceType = pieceType;
		this.origin = origin;
		this.destination = destination;
		this.enPassantCapturePos = enPassantCapturePos;
	}
	
	public ChessPosition getDestination() { return destination; }
	
	public ChessPosition getOrigin() { return origin; }
	
	public ChessPieceType getPieceType() { return pieceType; }
	
	public Optional<ChessPosition> getEnPassantCapturePos() { return enPassantCapturePos; }
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		ChessMove other = (ChessMove) obj;
		return pieceType.equals(other.pieceType)
			&& origin.equals(other.origin)
			&& destination.equals(other.destination)
			&& enPassantCapturePos.equals(other.enPassantCapturePos);
	}
	
	@Override
	public int hashCode() {
		return pieceType.hashCode()
			* origin.hashCode()
			* destination.hashCode()
			* enPassantCapturePos.hashCode()
			* 3;
	}
}
