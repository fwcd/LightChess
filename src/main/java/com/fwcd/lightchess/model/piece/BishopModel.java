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
	public Stream<ChessMove> getPossibleMoves(ChessBoardModel board) {
		Stream.Builder<ChessMove> moves = Stream.builder();
		ChessPosition origin = getPosition();
		
		// Diagonals
		PieceUtils.addMovesUntilHit(this, 1, 1, origin, moves, board);
		PieceUtils.addMovesUntilHit(this, -1, 1, origin, moves, board);
		PieceUtils.addMovesUntilHit(this, -1, -1, origin, moves, board);
		PieceUtils.addMovesUntilHit(this, 1, -1, origin, moves, board);
		
		return moves.build().distinct();
	}
	
	@Override
	public void accept(ChessPieceVisitor visitor) {
		visitor.visitBishop(this);
	}
	
	@Override
	public ChessPieceType getType() { return ChessPieceType.BISHOP; }
}
