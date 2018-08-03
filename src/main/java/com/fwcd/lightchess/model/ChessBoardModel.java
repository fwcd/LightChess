package com.fwcd.lightchess.model;

import java.util.Optional;

import com.fwcd.lightchess.model.piece.ChessPieceModel;
import com.fwcd.lightchess.utils.ChessConstants;

public class ChessBoardModel {
	private final ChessFieldModel[][] fields;
	
	public ChessBoardModel() {
		fields = initializeFields();
	}
	
	private ChessFieldModel[][] initializeFields() {
		ChessFieldModel[][] newFields = new ChessFieldModel[ChessConstants.RANKS][ChessConstants.FILES];
		// TODO
		throw new RuntimeException("TODO");
	}
	
	public ChessFieldModel fieldAt(ChessPosition position) {
		return fields[position.getY()][position.getX()];
	}
	
	public Optional<ChessPieceModel> pieceAt(ChessPosition position) {
		return fieldAt(position).getPiece();
	}
}
