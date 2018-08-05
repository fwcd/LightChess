package com.fwcd.lightchess.model.piece;

import java.util.stream.Stream;

import com.fwcd.lightchess.model.ChessBoardModel;
import com.fwcd.lightchess.model.ChessMove;
import com.fwcd.lightchess.model.ChessPosition;
import com.fwcd.lightchess.model.PlayerColor;

/** A chess piece */
public interface ChessPieceModel {
	Stream<ChessMove> getPossibleMoves(ChessPosition origin, ChessBoardModel board);
	
	void accept(ChessPieceVisitor visitor);
	
	PlayerColor getColor();
	
	default void onMove() {}
}
