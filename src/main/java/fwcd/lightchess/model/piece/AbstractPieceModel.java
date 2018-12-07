package fwcd.lightchess.model.piece;

import fwcd.fructose.Option;
import java.util.stream.Stream;

import fwcd.lightchess.model.ChessBoardModel;
import fwcd.lightchess.model.ChessMove;
import fwcd.lightchess.model.ChessPosition;
import fwcd.lightchess.model.PlayerColor;

public abstract class AbstractPieceModel implements ChessPieceModel {
	private final PlayerColor color;
	private ChessPosition position;
	
	public AbstractPieceModel(PlayerColor color, ChessPosition position) {
		this.color = color;
		this.position = position;
	}
	
	@Override
	public Stream<ChessMove> getPossibleMoves(ChessBoardModel board) {
		return getIntendedMoves(board)
			.filter(it -> !causesCheck(it, board));
	}
	
	private boolean causesCheck(ChessMove move, ChessBoardModel board) {
		ChessBoardModel boardAfterMove = board.spawnChild(move);
		ChessPosition kingAfterMove = boardAfterMove.kingOfColor(color).getPosition();
		return boardAfterMove
			.piecesOfColor(color.opponent())
			.anyMatch(it -> it.threatens(kingAfterMove, boardAfterMove));
	}
	
	@Override
	public boolean threatens(ChessPosition target, ChessBoardModel board) {
		ChessBoardModel boardWithoutTarget = board.copy();
		boardWithoutTarget.fieldAt(target).setPiece(Option.empty());
		return getIntendedMoves(boardWithoutTarget)
			.map(ChessMove::getDestination)
			.anyMatch(dest -> dest.equals(target));
	}
	
	@Override
	public ChessPosition getPosition() { return position; }
	
	@Override
	public PlayerColor getColor() { return color; }
	
	@Override
	public final void moveTo(ChessPosition position) {
		this.position = position;
		onMove();
	}
	
	/**
	 * Generates all possible moves without considering
	 * special situations such as a check.
	 */
	protected abstract Stream<ChessMove> getIntendedMoves(ChessBoardModel board);
	
	protected void onMove() {}
}
