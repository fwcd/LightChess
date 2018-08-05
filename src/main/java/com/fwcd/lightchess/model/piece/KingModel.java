package com.fwcd.lightchess.model.piece;

import java.util.HashSet;
import java.util.Set;

import com.fwcd.lightchess.model.ChessBoardModel;
import com.fwcd.lightchess.model.ChessPosition;
import com.fwcd.lightchess.model.PlayerColor;

public class KingModel implements ChessPieceModel {
	private final PlayerColor color;
	
	public KingModel(PlayerColor color) {
		this.color = color;
	}
	
	@Override
	public Set<ChessPosition> getPossibleMoves(ChessPosition pos, ChessBoardModel board) {
		// TODO: Castling
		Set<ChessPosition> targets = new HashSet<>();
		
		for (int dy=-1; dy<=1; dy++) {
			for (int dx=-1; dx<=1; dx++) {
				pos.plus(dx, dy)
					.filter(it -> !board.fieldAt(it).hasPieceOfColor(color))
					.ifPresent(targets::add);
			}
		}
		
		return targets;
	}
	
	@Override
	public PlayerColor getColor() { return color; }
	
	@Override
	public void accept(ChessPieceVisitor visitor) {
		visitor.visitKing(this);
	}
}
