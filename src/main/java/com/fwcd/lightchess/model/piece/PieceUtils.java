package com.fwcd.lightchess.model.piece;

import java.util.Collection;

import com.fwcd.lightchess.model.ChessBoardModel;
import com.fwcd.lightchess.model.ChessFieldModel;
import com.fwcd.lightchess.model.ChessPosition;
import com.fwcd.lightchess.model.PlayerColor;

final class PieceUtils {
	private PieceUtils() {}
	
	public static void addPositionsUntilHit(
		int dx,
		int dy,
		ChessPosition start,
		Collection<ChessPosition> positions,
		ChessBoardModel board,
		PlayerColor color
	) {
		ChessPosition current = start.plus(dx, dy).orElse(null);
		while (current != null) {
			ChessFieldModel field = board.fieldAt(current);
			if (field.hasPiece()) {
				if (field.hasPieceOfColor(color.opponent())) {
					positions.add(current);
				}
				break;
			} else {
				positions.add(current);
			}
			current = current.plus(dx, dy).orElse(null);
		}
		// Hit the board bounds
	}
}
