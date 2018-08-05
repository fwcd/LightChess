package com.fwcd.lightchess.model.piece;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import com.fwcd.lightchess.model.ChessBoardModel;
import com.fwcd.lightchess.model.ChessPosition;
import com.fwcd.lightchess.model.PlayerColor;
import com.fwcd.lightchess.utils.Streams;

public class PawnModel implements ChessPieceModel {
	private final PlayerColor color;
	
	public PawnModel(PlayerColor color) {
		this.color = color;
	}
	
	@Override
	public Set<ChessPosition> getPossibleMoves(ChessPosition pos, ChessBoardModel board) {
		// TODO: En passant and promotion
		Set<ChessPosition> targets = new HashSet<>();
		
		stepFrom(pos).ifPresent(it -> {
			if (!board.fieldAt(it).hasPiece()) {
				targets.add(it);
			}
		});
		diagonalStepsFrom(pos)
			.filter(it -> board.fieldAt(it).hasPieceOfColor(color.opponent()))
			.forEach(targets::add);
		
		return targets;
	}
	
	private Optional<ChessPosition> stepFrom(ChessPosition start) {
		switch (color) {
			case WHITE: return start.up(1);
			case BLACK: return start.down(1);
			default: throw new IllegalStateException("Invalid pawn color");
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
}
