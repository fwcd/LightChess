package com.fwcd.lightchess.model.piece;

import java.util.ArrayList;
import java.util.List;

import com.fwcd.lightchess.model.ChessBoardModel;
import com.fwcd.lightchess.model.ChessPosition;

public class KingModel implements ChessPieceModel {
	@Override
	public List<ChessPosition> getPossibleMoves(ChessPosition pos, ChessBoardModel board) {
		// TODO: Castling
		List<ChessPosition> targets = new ArrayList<>();
		
		for (int dy=-1; dy<=1; dy++) {
			for (int dx=-1; dx<=1; dx++) {
				pos.plus(dx, dy).ifPresent(targets::add);
			}
		}
		
		return targets;
	}
}
