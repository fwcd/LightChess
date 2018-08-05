package com.fwcd.lightchess.model.piece;

import java.util.stream.Stream;

import com.fwcd.lightchess.model.ChessBoardModel;
import com.fwcd.lightchess.model.ChessMove;
import com.fwcd.lightchess.model.ChessPosition;
import com.fwcd.lightchess.model.PlayerColor;
import com.fwcd.lightchess.utils.Streams;

public class KnightModel implements ChessPieceModel {
	private final PlayerColor color;
	
	public KnightModel(PlayerColor color) {
		this.color = color;
	}
	
	@Override
	public Stream<ChessMove> getPossibleMoves(ChessPosition origin, ChessBoardModel board) {
		Stream<ChessPosition> positions = Streams.filterPresent(Stream.of(
			// Top-left
			origin.plus(-2, -1),
			origin.plus(-1, -2),
			// Top-right
			origin.plus(2, -1),
			origin.plus(1, -2),
			// Bottom-left
			origin.plus(-2, 1),
			origin.plus(-1, 2),
			// Bottom-right
			origin.plus(2, 1),
			origin.plus(1, 2)
		));
		return positions
			.filter(it -> !board.fieldAt(it).hasPieceOfColor(color))
			.map(it -> new ChessMove(this, origin, it))
			.distinct();
	}
	
	@Override
	public PlayerColor getColor() { return color; }
	
	@Override
	public void accept(ChessPieceVisitor visitor) {
		visitor.visitKnight(this);
	}
}
