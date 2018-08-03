package com.fwcd.lightchess.model.piece;

import java.util.List;

import com.fwcd.lightchess.model.ChessBoardModel;
import com.fwcd.lightchess.model.ChessPosition;

/** A chess piece */
public interface ChessPieceModel {
	List<ChessPosition> getPossibleMoves(ChessPosition pos, ChessBoardModel board);
}
