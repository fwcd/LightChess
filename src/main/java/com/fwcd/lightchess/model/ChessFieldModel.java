package com.fwcd.lightchess.model;

import java.util.Optional;

import com.fwcd.lightchess.model.piece.ChessPieceModel;

public class ChessFieldModel {
	private final ChessPosition position;
	private final Optional<ChessPieceModel> piece = Optional.empty();
	
	public ChessFieldModel(ChessPosition position) {
		this.position = position;
	}
	
	public boolean hasPiece() { return piece.isPresent(); }
	
	public Optional<ChessPieceModel> getPiece() { return piece; }
}
