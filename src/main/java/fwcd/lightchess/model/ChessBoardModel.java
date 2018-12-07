package fwcd.lightchess.model;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Stream;

import fwcd.fructose.Copyable;
import fwcd.lightchess.model.piece.BishopModel;
import fwcd.lightchess.model.piece.ChessPieceModel;
import fwcd.lightchess.model.piece.ChessPieceType;
import fwcd.lightchess.model.piece.KingModel;
import fwcd.lightchess.model.piece.KnightModel;
import fwcd.lightchess.model.piece.PawnModel;
import fwcd.lightchess.model.piece.QueenModel;
import fwcd.lightchess.model.piece.RookModel;
import fwcd.lightchess.utils.ChessConstants;
import fwcd.lightchess.utils.Streams;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChessBoardModel implements Copyable<ChessBoardModel> {
	private static final Logger LOG = LoggerFactory.getLogger(ChessBoardModel.class);
	private ChessFieldModel[][] fields;
	
	private ChessBoardModel() {
		fields = new ChessFieldModel[ChessConstants.RANKS][ChessConstants.FILES];
	}
	
	public static ChessBoardModel empty() {
		ChessBoardModel board = new ChessBoardModel();
		for (int y=0; y<ChessConstants.RANKS; y++) {
			for (int x=0; x<ChessConstants.FILES; x++) {
				board.fields[y][x] = new ChessFieldModel(ChessPosition.at(x, y));
			}
		}
		return board;
	}
	
	public static ChessBoardModel withInitialSetup() {
		ChessBoardModel board = empty();
		board.placeInitialPieces();
		return board;
	}
	
	public void clear() {
		for (ChessFieldModel[] rank : fields) {
			for (ChessFieldModel field : rank) {
				field.setPiece(Optional.empty());
			}
		}
	}
	
	public void resetToInitialSetup() {
		clear();
		placeInitialPieces();
	}
	
	private void placeInitialPieces() {
		for (PlayerColor color : PlayerColor.values()) {
			int pawnsY = (color == PlayerColor.BLACK)  ? 1 : 6;
			int piecesY = (color == PlayerColor.BLACK) ? 0 : 7;
			
			for (int x=0; x<ChessConstants.FILES; x++) {
				placeAt(x, pawnsY, color, PawnModel::new);
			}
			placeAt(0, piecesY, color, RookModel::new);
			placeAt(1, piecesY, color, KnightModel::new);
			placeAt(2, piecesY, color, BishopModel::new);
			placeAt(3, piecesY, color, QueenModel::new);
			placeAt(4, piecesY, color, KingModel::new);
			placeAt(5, piecesY, color, BishopModel::new);
			placeAt(6, piecesY, color, KnightModel::new);
			placeAt(7, piecesY, color, RookModel::new);
		}
	}
	
	public Optional<KingModel> getCheckedKing() {
		return kings()
			.filter(it -> it.isChecked(this))
			.findAny();
	}
	
	public Optional<PlayerColor> getWinner() {
		return getCheckmate()
			.map(KingModel::getColor)
			.map(Optional::of)
			.orElseGet(this::getStalemate)
			.map(PlayerColor::opponent);
	}
	
	public boolean isGameOver() {
		return getCheckmate().isPresent() || getStalemate().isPresent();
	}
	
	public Optional<KingModel> getCheckmate() {
		return kings()
			.filter(it -> it.isChecked(this) && !canMove(it.getColor()))
			.findAny();
	}
	
	public Optional<PlayerColor> getStalemate() {
		// TODO: Implement special stalemate rules
		return Arrays.stream(PlayerColor.values())
			.filter(it -> !canMove(it))
			.findAny();
	}
	
	public ChessBoardModel spawnChild(ChessMove move) {
		ChessBoardModel board = copy();
		board.performMoveSilently(move);
		return board;
	}
	
	private void performMoveSilently(ChessMove move) {
		ChessPosition origin = move.getOrigin();
		ChessPosition destination = move.getDestination();
		ChessFieldModel originField = fieldAt(origin);
		ChessFieldModel destField = fieldAt(destination);
		ChessPieceModel piece = originField.getPiece()
			.orElseThrow(() -> new UnsupportedOperationException("Tried to move non-existent chess piece at " + origin));
		Optional<ChessPieceModel> promotedPiece = move.getPromotedPiece()
			.map(it -> it.construct(piece.getColor(), destination));
		
		piece.moveTo(destination);
		
		originField.setPiece(Optional.empty());
		destField.setPiece(promotedPiece.orElse(piece));
		for (ChessPosition otherCapture : move.getOtherCaptures()) {
			fieldAt(otherCapture).setPiece(Optional.empty());
		}
		for (Map.Entry<ChessPosition, ChessPosition> otherRelocation : move.getOtherRelocations().entrySet()) {
			ChessFieldModel relocationOriginField = fieldAt(otherRelocation.getKey());
			ChessFieldModel relocationDestField = fieldAt(otherRelocation.getValue());
			ChessPieceModel relocatedPiece = relocationOriginField.getPiece()
				.orElseThrow(() -> new UnsupportedOperationException("Tried to relocate non-existent chess piece at " + otherRelocation.getKey()));
			relocationOriginField.setPiece(Optional.empty());
			relocationDestField.setPiece(relocatedPiece);
			relocatedPiece.moveTo(relocationDestField.getPosition());
		}
	}
	
	public Stream<ChessMove> getPossibleMovesFor(PlayerColor color) {
		return piecesOfColor(color)
			.flatMap(piece -> piece.getPossibleMoves(this));
	}
	
	public void performMove(ChessMove move) {
		performMoveSilently(move);
		LOG.debug("Committed move - stalemate: {} - check: {} - checkmate: {}", getStalemate(), getCheckedKing(), getCheckmate());
	}
	
	public ChessFieldModel fieldAt(ChessPosition position) {
		return fields[position.getY()][position.getX()];
	}
	
	public Optional<ChessPieceModel> pieceAt(ChessPosition position) {
		return fieldAt(position).getPiece();
	}
	
	public void placeAt(ChessPosition position, ChessPieceModel piece) {
		fieldAt(position).setPiece(piece);
	}
	
	public void placeAt(int x, int y, PlayerColor color, BiFunction<PlayerColor, ChessPosition, ChessPieceModel> pieceConstructor) {
		ChessPosition pos = ChessPosition.at(x, y);
		placeAt(pos, pieceConstructor.apply(color, pos));
	}
	
	public boolean canMove(PlayerColor color) {
		return piecesOfColor(color)
			.anyMatch(it -> it.canMove(this));
	}
	
	public Stream<ChessFieldModel> fields() {
		Stream.Builder<ChessFieldModel> stream = Stream.builder();
		for (ChessFieldModel[] rank : fields) {
			for (ChessFieldModel field : rank) {
				stream.accept(field);
			}
		}
		return stream.build();
	}
	
	public Stream<ChessPieceModel> pieces() {
		return Streams.filterPresent(fields().map(ChessFieldModel::getPiece));
	}
	
	public Stream<ChessPieceModel> piecesOfType(ChessPieceType pieceType) {
		return pieces().filter(it -> it.getType().equals(pieceType));
	}
	
	public Stream<ChessPieceModel> piecesOfColor(PlayerColor color) {
		return pieces().filter(it -> it.getColor().equals(color));
	}
	
	public KingModel kingOfColor(PlayerColor color) {
		return kings()
			.filter(it -> it.getColor().equals(color))
			.findAny()
			.orElseThrow(() -> new IllegalStateException("Each player needs a king"));
	}
	
	public Stream<KingModel> kings() { return piecesOfType(ChessPieceType.KING).map(it -> (KingModel) it); }
	
	public Stream<QueenModel> queens() { return piecesOfType(ChessPieceType.QUEEN).map(it -> (QueenModel) it); }
	
	public Stream<BishopModel> bishops() { return piecesOfType(ChessPieceType.BISHOP).map(it -> (BishopModel) it); }
	
	public Stream<KnightModel> knights() { return piecesOfType(ChessPieceType.QUEEN).map(it -> (KnightModel) it); }
	
	public Stream<PawnModel> pawns() { return piecesOfType(ChessPieceType.PAWN).map(it -> (PawnModel) it); }
	
	public Stream<RookModel> rooks() { return piecesOfType(ChessPieceType.ROOK).map(it -> (RookModel) it); }
	
	@Override
	public ChessBoardModel copy() {
		ChessBoardModel copy = new ChessBoardModel();
		for (int y=0; y<fields.length; y++) {
			for (int x=0; x<fields[y].length; x++) {
				copy.fields[y][x] = fields[y][x].copy();
			}
		}
		return copy;
	}
}
