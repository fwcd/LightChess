package fwcd.lightchess.model.piece;

import java.util.stream.Stream;

import fwcd.lightchess.model.ChessBoardModel;
import fwcd.lightchess.model.ChessMove;
import fwcd.lightchess.model.ChessPosition;
import fwcd.lightchess.model.PlayerColor;
import fwcd.lightchess.utils.Streams;

public class KnightModel extends AbstractPieceModel {
	public KnightModel(PlayerColor color, ChessPosition position) {
		super(color, position);
	}
	
	@Override
	protected Stream<ChessMove> getIntendedMoves(ChessBoardModel board) {
		ChessPosition origin = getPosition();
		Stream<ChessPosition> positions = Streams.filterPresent(Stream.of(
			// Top-left
			origin.plus(-2, -1),
			origin.plus(-1, -2),
			// Top-right
			origin.plus(2, -1),
			origin.plus(1, -2),
			// Bottom-left
			origin.plus(-2, 1),
			origin.plus(-1, 2),
			// Bottom-right
			origin.plus(2, 1),
			origin.plus(1, 2)
		));
		return positions
			.filter(it -> !board.fieldAt(it).hasPieceOfColor(getColor()))
			.map(it -> ChessMove.create(getType(), origin, it))
			.distinct();
	}
	
	@Override
	public void accept(ChessPieceVisitor visitor) {
		visitor.visitKnight(this);
	}
	
	@Override
	public ChessPieceType getType() { return ChessPieceType.KNIGHT; }
	
	@Override
	public ChessPieceModel copy() { return new KnightModel(getColor(), getPosition()); }
	
	@Override
	public int getValue() { return 3; }
}
