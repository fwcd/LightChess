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
	
	/** Fetches the value of this piece (returns 0 for a king) */
	int getValue();
	
	PlayerColor getColor();
	
	ChessPieceType getType();
	
	void moveTo(ChessPosition position);
	
	ChessPosition getPosition();
	
	boolean threatens(ChessPosition target, ChessBoardModel board);
	
	default boolean canBeCapturedThroughEnPassant() {
		return false;
	}
	
	default boolean canMove(ChessBoardModel board) {
		return getPossibleMoves(board).findAny().isPresent();
	}
	
	/**
	 * Checks whether type and color of this piece
	 * matches another.
	 */
	default boolean pieceMatches(ChessPieceModel other) {
		return other.getColor().equals(getColor()) && other.getType().equals(getType());
	}
}
