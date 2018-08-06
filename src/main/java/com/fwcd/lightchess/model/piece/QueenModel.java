package com.fwcd.lightchess.model.piece;

import java.util.stream.Stream;

import com.fwcd.lightchess.model.ChessBoardModel;
import com.fwcd.lightchess.model.ChessMove;
import com.fwcd.lightchess.model.ChessPosition;
import com.fwcd.lightchess.model.PlayerColor;

public class QueenModel extends AbstractPieceModel {
	public QueenModel(PlayerColor color, ChessPosition position) {
		super(color, position);
	}
	
	@Override
	protected Stream<ChessMove> getIntendedMoves(ChessBoardModel board) {
		Stream.Builder<ChessMove> moves = Stream.builder();
		ChessPosition origin = getPosition();
		// Diagonals
		PieceUtils.addMovesUntilHit(this, 1, 1, origin, moves, board);
		PieceUtils.addMovesUntilHit(this, -1, 1, origin, moves, board);
		PieceUtils.addMovesUntilHit(this, -1, -1, origin, moves, board);
		PieceUtils.addMovesUntilHit(this, 1, -1, origin, moves, board);
		// Horizontals
		PieceUtils.addMovesUntilHit(this, 1, 0, origin, moves, board);
		PieceUtils.addMovesUntilHit(this, -1, 0, origin, moves, board);
		// Verticals
		PieceUtils.addMovesUntilHit(this, 0, 1, origin, moves, board);
		PieceUtils.addMovesUntilHit(this, 0, -1, origin, moves, board);
		return moves.build().distinct();
	}
	
	@Override
	public void accept(ChessPieceVisitor visitor) {
		visitor.visitQueen(this);
	}
	
	@Override
	public ChessPieceType getType() { return ChessPieceType.QUEEN; }
	
	@Override
	public ChessPieceModel copy() { return new QueenModel(getColor(), getPosition()); }
}
