package com.fwcd.lightchess.model.piece;

import java.util.HashSet;
import java.util.Set;

import com.fwcd.lightchess.model.ChessBoardModel;
import com.fwcd.lightchess.model.ChessPosition;
import com.fwcd.lightchess.model.PlayerColor;

public class RookModel implements ChessPieceModel {
	private final PlayerColor color;
	
	public RookModel(PlayerColor color) {
		this.color = color;
	}
	
	@Override
	public Set<ChessPosition> getPossibleMoves(ChessPosition pos, ChessBoardModel board) {
		Set<ChessPosition> targets = new HashSet<>();
		// Horizontals
		PieceUtils.addPositionsUntilHit(1, 0, pos, targets, board, color);
		PieceUtils.addPositionsUntilHit(-1, 0, pos, targets, board, color);
		// Verticals
		PieceUtils.addPositionsUntilHit(0, 1, pos, targets, board, color);
		PieceUtils.addPositionsUntilHit(0, -1, pos, targets, board, color);
		return targets;
	}
	
	@Override
	public PlayerColor getColor() { return color; }
	
	@Override
	public void accept(ChessPieceVisitor visitor) {
		visitor.visitRook(this);
	}
}
