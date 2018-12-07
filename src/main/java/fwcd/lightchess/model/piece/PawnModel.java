package fwcd.lightchess.model.piece;

import java.util.Arrays;
import fwcd.fructose.Option;
import java.util.stream.Stream;

import fwcd.lightchess.model.ChessBoardModel;
import fwcd.lightchess.model.ChessFieldModel;
import fwcd.lightchess.model.ChessMove;
import fwcd.lightchess.model.ChessPosition;
import fwcd.lightchess.model.PlayerColor;
import fwcd.lightchess.utils.ChessConstants;
import fwcd.lightchess.utils.Streams;

public class PawnModel extends AbstractPieceModel {
	private int moves = 0;
	
	public PawnModel(PlayerColor color, ChessPosition position) {
		super(color, position);
	}
	
	private PawnModel(PlayerColor color, ChessPosition position, int moves) {
		super(color, position);
		this.moves = moves;
	}
	
	@Override
	protected Stream<ChessMove> getIntendedMoves(ChessBoardModel board) {
		Stream.Builder<ChessMove> moves = Stream.builder();
		ChessPosition origin = getPosition();
		
		stepsFrom(origin, board)
			.filter(it -> !board.fieldAt(it).hasPiece())
			.flatMap(it -> chessMovesOf(origin, it))
			.forEach(moves::accept);
		diagonalStepsFrom(origin)
			.flatMap(it -> {
				if (board.fieldAt(it).hasPieceOfColor(getColor().opponent())) {
					return chessMovesOf(origin, it);
				} else if (isEnPassantPossible(origin, it, board)) {
					return Stream.of(ChessMove.createEnPassant(getType(), origin, it, getEnPassantCapturePos(it).orElseThrow(IllegalStateException::new)));
				} else return Stream.<ChessMove>empty();
			})
			.forEach(moves::accept);
		
		return moves.build().distinct();
	}
	
	private Stream<ChessMove> chessMovesOf(ChessPosition origin, ChessPosition destination) {
		int rankY = destination.getY();
		if ((rankY == 0) || (rankY == (ChessConstants.RANKS - 1))) {
			// When moving to the final rank, promote the pawn
			return Arrays.stream(ChessConstants.PROMOTION_PIECES)
				.map(it -> ChessMove.createPawnPromotion(origin, destination, it));
		} else {
			return Stream.of(ChessMove.create(getType(), origin, destination));
		}
	}
	
	/** 
	 * Returns the y-index of a rank on which this
	 * pawn could capture an opposing pawn through "en passant"
	 */
	private int getEnPassantY() {
		switch (getColor()) {
			case WHITE: return 3;
			case BLACK: return 4;
			default: throw new IllegalStateException("Invalid pawn color");
		}
	}
	
	private int getStepY() {
		switch (getColor()) {
			case WHITE: return -1;
			case BLACK: return 1;
			default: throw new IllegalStateException("Invalid pawn color");
		}
	}
	
	private Option<ChessPosition> getEnPassantCapturePos(ChessPosition destination) {
		return destination.up(getStepY());
	}
	
	private boolean isEnPassantPossible(ChessPosition origin, ChessPosition destination, ChessBoardModel board) {
		Option<ChessPosition> capturedPos = getEnPassantCapturePos(destination);
		return origin.getY() == getEnPassantY()
			&& capturedPos
				.flatMap(it -> board.pieceAt(it))
				.filter(it -> it.canBeCapturedThroughEnPassant() && it.getColor().equals(getColor().opponent()))
				.isPresent();
	}
	
	private Stream<ChessPosition> stepsFrom(ChessPosition origin, ChessBoardModel board) {
		int step = getStepY();
		Option<ChessPosition> firstStep = origin.down(step);
		
		// Only return two steps when this is the first move and
		// no piece is directly in front of this pawn
		if ((moves == 0) && !(firstStep.map(board::fieldAt).filter(ChessFieldModel::hasPiece).isPresent())) {
			return Streams.filterPresent(Stream.of(firstStep, origin.down(step * 2)));
		} else {
			return Streams.filterPresent(Stream.of(firstStep));
		}
	}
	
	private Stream<ChessPosition> diagonalStepsFrom(ChessPosition origin) {
		int stepY = getStepY();
		return Streams.filterPresent(Stream.of(
			origin.plus(-1, stepY),
			origin.plus( 1, stepY)
		));
	}
	
	@Override
	public void accept(ChessPieceVisitor visitor) {
		visitor.visitPawn(this);
	}
	
	@Override
	public boolean canBeCapturedThroughEnPassant() {
		return moves == 1;
	}
	
	@Override
	protected void onMove() {
		moves++;
	}
	
	@Override
	public boolean threatens(ChessPosition position, ChessBoardModel board) {
		return diagonalStepsFrom(getPosition())
				.anyMatch(it -> it.equals(position));
	}
	
	@Override
	public ChessPieceType getType() { return ChessPieceType.PAWN; }
	
	@Override
	public ChessPieceModel copy() { return new PawnModel(getColor(), getPosition(), moves); }
	
	@Override
	public int getValue() { return 1; }
}
