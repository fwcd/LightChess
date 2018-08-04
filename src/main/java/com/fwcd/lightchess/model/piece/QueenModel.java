package com.fwcd.lightchess.model.piece;

import java.util.HashSet;
import java.util.Set;

import com.fwcd.lightchess.model.ChessBoardModel;
import com.fwcd.lightchess.model.ChessPosition;
import com.fwcd.lightchess.model.PlayerColor;

public class QueenModel implements ChessPieceModel {
	private final PlayerColor color;
	
	public QueenModel(PlayerColor color) {
		this.color = color;
	}
	
	@Override
	public Set<ChessPosition> getPossibleMoves(ChessPosition pos, ChessBoardModel board) {
		Set<ChessPosition> targets = new HashSet<>();
		// Diagonals
		PieceUtils.addPositionsUntilHit(1, 1, pos, targets, board);
		PieceUtils.addPositionsUntilHit(-1, 1, pos, targets, board);
		PieceUtils.addPositionsUntilHit(-1, -1, pos, targets, board);
		PieceUtils.addPositionsUntilHit(1, -1, pos, targets, board);
		// Horizontals
		PieceUtils.addPositionsUntilHit(1, 0, pos, targets, board);
		PieceUtils.addPositionsUntilHit(-1, 0, pos, targets, board);
		// Verticals
		PieceUtils.addPositionsUntilHit(0, 1, pos, targets, board);
		PieceUtils.addPositionsUntilHit(0, -1, pos, targets, board);
		return targets;
	}
	
	@Override
	public PlayerColor getColor() { return color; }
	
	@Override
	public void accept(ChessPieceVisitor visitor) {
		visitor.visitQueen(this);
	}
}
