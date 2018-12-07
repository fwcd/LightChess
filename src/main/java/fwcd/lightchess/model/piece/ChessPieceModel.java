package fwcd.lightchess.model.piece;

import java.util.stream.Stream;

import fwcd.fructose.Copyable;
import fwcd.lightchess.model.ChessBoardModel;
import fwcd.lightchess.model.ChessMove;
import fwcd.lightchess.model.ChessPosition;
import fwcd.lightchess.model.PlayerColor;

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
