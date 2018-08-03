package com.fwcd.lightchess.model.piece;

import java.util.ArrayList;
import java.util.List;

import com.fwcd.lightchess.model.ChessBoardModel;
import com.fwcd.lightchess.model.ChessPosition;

public class QueenModel implements ChessPieceModel {
	@Override
	public List<ChessPosition> getPossibleMoves(ChessPosition pos, ChessBoardModel board) {
		List<ChessPosition> targets = new ArrayList<>();
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
}
