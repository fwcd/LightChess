package com.fwcd.lightchess.model;

import java.util.Optional;

import com.fwcd.lightchess.model.piece.ChessPieceModel;

public class ChessFieldModel {
	private final ChessPosition position;
	private Optional<ChessPieceModel> piece = Optional.empty();
	
	public ChessFieldModel(ChessPosition position) {
		this.position = position;
	}
	
	public boolean hasPiece() { return piece.isPresent(); }
	
	public boolean hasPieceOfType(Class<? extends ChessPieceModel> pieceType) {
		return piece.map(pieceType::isInstance).orElse(false);
	}
	
	public void setPiece(Optional<ChessPieceModel> piece) { this.piece = piece; }
	
	public void setPiece(ChessPieceModel piece) { this.piece = Optional.of(piece); }
	
	public Optional<ChessPieceModel> getPiece() { return piece; }
	
	@SuppressWarnings("unchecked")
	public <T> Optional<T> getPieceAs(Class<? extends ChessPieceModel> pieceType) {
		if (hasPieceOfType(pieceType)) {
			return Optional.of((T) piece.orElse(null));
		} else return Optional.empty();
	}
}
