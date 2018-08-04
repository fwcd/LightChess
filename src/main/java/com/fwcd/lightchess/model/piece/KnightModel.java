package com.fwcd.lightchess.model.piece;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fwcd.lightchess.model.ChessBoardModel;
import com.fwcd.lightchess.model.ChessPosition;
import com.fwcd.lightchess.model.PlayerColor;
import com.fwcd.lightchess.utils.Streams;

public class KnightModel implements ChessPieceModel {
	private final PlayerColor color;
	
	public KnightModel(PlayerColor color) {
		this.color = color;
	}
	
	@Override
	public List<ChessPosition> getPossibleMoves(ChessPosition pos, ChessBoardModel board) {
		return Streams.filterPresent(Stream.of(
			// Top-left
			pos.plus(-2, -1),
			pos.plus(-1, -2),
			// Top-right
			pos.plus(2, -1),
			pos.plus(1, -2),
			// Bottom-left
			pos.plus(-2, 1),
			pos.plus(-1, 2),
			// Bottom-right
			pos.plus(2, 1),
			pos.plus(1, 2)
		)).collect(Collectors.toList());
	}
	
	@Override
	public PlayerColor getColor() { return color; }
	
	@Override
	public void accept(ChessPieceVisitor visitor) {
		visitor.visitKnight(this);
	}
}
