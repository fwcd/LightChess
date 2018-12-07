package fwcd.lightchess.model.piece;

import java.util.stream.Stream;

import fwcd.lightchess.model.ChessBoardModel;
import fwcd.lightchess.model.ChessFieldModel;
import fwcd.lightchess.model.ChessMove;
import fwcd.lightchess.model.ChessPosition;

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
					moves.accept(ChessMove.create(piece.getType(), origin, current));
				}
				break;
			} else {
				moves.accept(ChessMove.create(piece.getType(), origin, current));
			}
			current = current.plus(dx, dy).orElse(null);
		}
		// Hit the board bounds
	}
}
