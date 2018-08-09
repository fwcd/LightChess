package com.fwcd.lightchess.model.piece;

import java.util.stream.Stream;

import com.fwcd.lightchess.model.ChessBoardModel;
import com.fwcd.lightchess.model.ChessMove;
import com.fwcd.lightchess.model.ChessPosition;
import com.fwcd.lightchess.model.PlayerColor;

public class BishopModel extends AbstractPieceModel {
	public BishopModel(PlayerColor color, ChessPosition position) {
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
		
		return moves.build();
	}
	
	@Override
	public void accept(ChessPieceVisitor visitor) {
		visitor.visitBishop(this);
	}
	
	@Override
	public ChessPieceType getType() { return ChessPieceType.BISHOP; }
	
	@Override
	public ChessPieceModel copy() { return new BishopModel(getColor(), getPosition()); }
	
	@Override
	public int getValue() { return 3; }
}
