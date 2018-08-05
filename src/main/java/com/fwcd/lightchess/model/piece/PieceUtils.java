package com.fwcd.lightchess.model.piece;

import java.util.stream.Stream;

import com.fwcd.lightchess.model.ChessBoardModel;
import com.fwcd.lightchess.model.ChessFieldModel;
import com.fwcd.lightchess.model.ChessMove;
import com.fwcd.lightchess.model.ChessPosition;

final class PieceUtils {
	private PieceUtils() {}
	
	public static void addMovesUntilHit(
		ChessPieceModel piece,
		int dx,
		int dy,
		ChessPosition origin,
		Stream.Builder<ChessMove> moves,
		ChessBoardModel board
	) {
		ChessPosition current = origin.plus(dx, dy).orElse(null);
		while (current != null) {
			ChessFieldModel field = board.fieldAt(current);
			if (field.hasPiece()) {
				if (field.hasPieceOfColor(piece.getColor().opponent())) {
					moves.add(new ChessMove(piece, origin, current));
				}
				break;
			} else {
				moves.add(new ChessMove(piece, origin, current));
			}
			current = current.plus(dx, dy).orElse(null);
		}
		// Hit the board bounds
	}
}
