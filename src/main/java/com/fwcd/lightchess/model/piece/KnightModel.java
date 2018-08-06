package com.fwcd.lightchess.model.piece;

import java.util.stream.Stream;

import com.fwcd.lightchess.model.ChessBoardModel;
import com.fwcd.lightchess.model.ChessMove;
import com.fwcd.lightchess.model.ChessPosition;
import com.fwcd.lightchess.model.PlayerColor;
import com.fwcd.lightchess.utils.Streams;

public class KnightModel extends AbstractPieceModel {
	public KnightModel(PlayerColor color, ChessPosition position) {
		super(color, position);
	}
	
	@Override
	public Stream<ChessMove> getPossibleMoves(ChessBoardModel board) {
		ChessPosition origin = getPosition();
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
			.filter(it -> !board.fieldAt(it).hasPieceOfColor(getColor()))
			.map(it -> new ChessMove(this, origin, it))
			.distinct();
	}
	
	@Override
	public void accept(ChessPieceVisitor visitor) {
		visitor.visitKnight(this);
	}
	
	@Override
	public ChessPieceType getType() { return ChessPieceType.KNIGHT; }
	
	@Override
	public ChessPieceModel copy() { return new KnightModel(getColor(), getPosition()); }
}
