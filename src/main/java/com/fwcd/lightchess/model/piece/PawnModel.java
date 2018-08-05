package com.fwcd.lightchess.model.piece;

import java.util.stream.Stream;

import com.fwcd.lightchess.model.ChessBoardModel;
import com.fwcd.lightchess.model.ChessMove;
import com.fwcd.lightchess.model.ChessPosition;
import com.fwcd.lightchess.model.PlayerColor;
import com.fwcd.lightchess.utils.Streams;

public class PawnModel implements ChessPieceModel {
	private final PlayerColor color;
	private boolean hasMoved = false;
	
	public PawnModel(PlayerColor color) {
		this.color = color;
	}
	
	@Override
	public Stream<ChessMove> getPossibleMoves(ChessPosition origin, ChessBoardModel board) {
		// TODO: En passant and promotion
		Stream.Builder<ChessPosition> positions = Stream.builder();
		
		stepsFrom(origin)
			.filter(it -> !board.fieldAt(it).hasPiece())
			.forEach(positions::add);
		diagonalStepsFrom(origin)
			.filter(it -> board.fieldAt(it).hasPieceOfColor(color.opponent()))
			.forEach(positions::add);
		
		return positions.build()
			.map(it -> new ChessMove(this, origin, it))
			.distinct();
	}
	
	private Stream<ChessPosition> stepsFrom(ChessPosition start) {
		int step = (color == PlayerColor.BLACK) ? -1 : 1;
		// Intentionally not using a StreamBuilder, because single-element
		// streams can be constructed efficiently when calling Stream.of(...) directly.
		if (hasMoved) {
			return Streams.filterPresent(Stream.of(start.up(step)));
		} else {
			return Streams.filterPresent(Stream.of(start.up(step), start.up(step * 2)));
		}
	}
	
	private Stream<ChessPosition> diagonalStepsFrom(ChessPosition start) {
		switch (color) {
			case WHITE: return Streams.filterPresent(Stream.of(start.plus(-1, -1), start.plus(1, -1)));
			case BLACK: return Streams.filterPresent(Stream.of(start.plus(-1,  1), start.plus(1,  1)));
			default: throw new IllegalStateException("Invalid pawn color");
		}
	}
	
	@Override
	public PlayerColor getColor() { return color; }
	
	@Override
	public void accept(ChessPieceVisitor visitor) {
		visitor.visitPawn(this);
	}
	
	@Override
	public void onMove() {
		hasMoved = true;
	}
}
