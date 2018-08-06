package com.fwcd.lightchess.model.piece;

import java.util.stream.Stream;

import com.fwcd.lightchess.model.ChessBoardModel;
import com.fwcd.lightchess.model.ChessMove;
import com.fwcd.lightchess.model.ChessPosition;
import com.fwcd.lightchess.model.PlayerColor;

public class RookModel extends AbstractPieceModel {
	private boolean moved = false;
	
	public RookModel(PlayerColor color, ChessPosition position) {
		super(color, position);
	}
	
	private RookModel(PlayerColor color, ChessPosition position, boolean moved) {
		super(color, position);
		this.moved = moved;
	}
	
	@Override
	protected Stream<ChessMove> getIntendedMoves(ChessBoardModel board) {
		Stream.Builder<ChessMove> moves = Stream.builder();
		ChessPosition origin = getPosition();
		// Horizontals
		PieceUtils.addMovesUntilHit(this, 1, 0, origin, moves, board);
		PieceUtils.addMovesUntilHit(this, -1, 0, origin, moves, board);
		// Verticals
		PieceUtils.addMovesUntilHit(this, 0, 1, origin, moves, board);
		PieceUtils.addMovesUntilHit(this, 0, -1, origin, moves, board);
		return moves.build().distinct();
	}
	
	public boolean hasMoved() { return moved; }
	
	public boolean isInLeftCorner() {
		ChessPosition pos = getPosition();
		return pos.isTopLeftCorner() || pos.isBottomLeftCorner();
	}
	
	public boolean isInRightCorner() {
		ChessPosition pos = getPosition();
		return pos.isTopRightCorner() || pos.isBottomRightCorner();
	}
	
	@Override
	public void accept(ChessPieceVisitor visitor) {
		visitor.visitRook(this);
	}
	
	@Override
	public ChessPieceType getType() { return ChessPieceType.ROOK; }
	
	@Override
	protected void onMove() {
		moved = true;
	}
	
	@Override
	public ChessPieceModel copy() { return new RookModel(getColor(), getPosition(), moved); }
}
