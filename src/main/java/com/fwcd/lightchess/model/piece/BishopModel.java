package com.fwcd.lightchess.model.piece;

import java.util.ArrayList;
import java.util.List;

import com.fwcd.lightchess.model.ChessBoardModel;
import com.fwcd.lightchess.model.ChessPosition;
import com.fwcd.lightchess.model.PlayerColor;

public class BishopModel implements ChessPieceModel {
	private final PlayerColor color;
	
	public BishopModel(PlayerColor color) {
		this.color = color;
	}
	
	@Override
	public List<ChessPosition> getPossibleMoves(ChessPosition pos, ChessBoardModel board) {
		List<ChessPosition> targets = new ArrayList<>();
		// Diagonals
		PieceUtils.addPositionsUntilHit(1, 1, pos, targets, board);
		PieceUtils.addPositionsUntilHit(-1, 1, pos, targets, board);
		PieceUtils.addPositionsUntilHit(-1, -1, pos, targets, board);
		PieceUtils.addPositionsUntilHit(1, -1, pos, targets, board);
		return targets;
	}
	
	public PlayerColor getColor() { return color; }
}
