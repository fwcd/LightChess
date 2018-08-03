package com.fwcd.lightchess.model.piece;

import java.util.Collection;

import com.fwcd.lightchess.model.ChessBoardModel;
import com.fwcd.lightchess.model.ChessPosition;

final class PieceUtils {
	private PieceUtils() {}
	
	public static void addPositionsUntilHit(int dx, int dy, ChessPosition start, Collection<ChessPosition> positions, ChessBoardModel board) {
		ChessPosition current = start.plus(dx, dy).orElse(null);
		while (current != null) {
			positions.add(current);
			current = current.plus(dx, dy).orElse(null);
			if ((current != null) && board.fieldAt(current).hasPiece()) {
				// Hit a piece
				positions.add(current);
				return;
			}
		}
		// Hit the board bounds
	}
}
