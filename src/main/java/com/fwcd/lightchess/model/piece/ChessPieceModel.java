package com.fwcd.lightchess.model.piece;

import java.util.Set;

import com.fwcd.lightchess.model.ChessBoardModel;
import com.fwcd.lightchess.model.ChessPosition;
import com.fwcd.lightchess.model.PlayerColor;

/** A chess piece */
public interface ChessPieceModel {
	Set<ChessPosition> getPossibleMoves(ChessPosition pos, ChessBoardModel board);
	
	void accept(ChessPieceVisitor visitor);
	
	PlayerColor getColor();
}
