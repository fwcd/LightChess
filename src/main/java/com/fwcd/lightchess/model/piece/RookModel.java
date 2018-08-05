package com.fwcd.lightchess.model.piece;

import java.util.stream.Stream;

import com.fwcd.lightchess.model.ChessBoardModel;
import com.fwcd.lightchess.model.ChessMove;
import com.fwcd.lightchess.model.ChessPosition;
import com.fwcd.lightchess.model.PlayerColor;

public class RookModel implements ChessPieceModel {
	private final PlayerColor color;
	
	public RookModel(PlayerColor color) {
		this.color = color;
	}
	
	@Override
	public Stream<ChessMove> getPossibleMoves(ChessPosition pos, ChessBoardModel board) {
		Stream.Builder<ChessMove> moves = Stream.builder();
		// Horizontals
		PieceUtils.addMovesUntilHit(this, 1, 0, pos, moves, board);
		PieceUtils.addMovesUntilHit(this, -1, 0, pos, moves, board);
		// Verticals
		PieceUtils.addMovesUntilHit(this, 0, 1, pos, moves, board);
		PieceUtils.addMovesUntilHit(this, 0, -1, pos, moves, board);
		return moves.build().distinct();
	}
	
	@Override
	public PlayerColor getColor() { return color; }
	
	@Override
	public void accept(ChessPieceVisitor visitor) {
		visitor.visitRook(this);
	}
}
