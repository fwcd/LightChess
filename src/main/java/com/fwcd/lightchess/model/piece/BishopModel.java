package com.fwcd.lightchess.model.piece;

import java.util.stream.Stream;

import com.fwcd.lightchess.model.ChessBoardModel;
import com.fwcd.lightchess.model.ChessMove;
import com.fwcd.lightchess.model.ChessPosition;
import com.fwcd.lightchess.model.PlayerColor;

public class BishopModel implements ChessPieceModel {
	private final PlayerColor color;
	
	public BishopModel(PlayerColor color) {
		this.color = color;
	}
	
	@Override
	public Stream<ChessMove> getPossibleMoves(ChessPosition origin, ChessBoardModel board) {
		Stream.Builder<ChessMove> moves = Stream.builder();
		
		// Diagonals
		PieceUtils.addMovesUntilHit(this, 1, 1, origin, moves, board);
		PieceUtils.addMovesUntilHit(this, -1, 1, origin, moves, board);
		PieceUtils.addMovesUntilHit(this, -1, -1, origin, moves, board);
		PieceUtils.addMovesUntilHit(this, 1, -1, origin, moves, board);
		
		return moves.build().distinct();
	}
	
	@Override
	public PlayerColor getColor() { return color; }
	
	@Override
	public void accept(ChessPieceVisitor visitor) {
		visitor.visitBishop(this);
	}
}
