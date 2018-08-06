package com.fwcd.lightchess.model.piece;

import java.util.stream.Stream;

import com.fwcd.fructose.Copyable;
import com.fwcd.lightchess.model.ChessBoardModel;
import com.fwcd.lightchess.model.ChessMove;
import com.fwcd.lightchess.model.ChessPosition;
import com.fwcd.lightchess.model.PlayerColor;

/** A chess piece */
public interface ChessPieceModel extends Copyable<ChessPieceModel> {
	Stream<ChessMove> getPossibleMoves(ChessBoardModel board);
	
	void accept(ChessPieceVisitor visitor);
	
	PlayerColor getColor();
	
	ChessPieceType getType();
	
	void moveTo(ChessPosition position);
	
	ChessPosition getPosition();
	
	boolean threatens(ChessPosition position, ChessBoardModel board);
	
	default boolean canBeCapturedThroughEnPassant() {
		return false;
	}
	
	default boolean canMove(ChessBoardModel board) {
		return getPossibleMoves(board).findAny().isPresent();
	}
}
