package com.fwcd.lightchess.model;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Stream;

import com.fwcd.lightchess.model.piece.BishopModel;
import com.fwcd.lightchess.model.piece.ChessPieceModel;
import com.fwcd.lightchess.model.piece.ChessPieceType;
import com.fwcd.lightchess.model.piece.KingModel;
import com.fwcd.lightchess.model.piece.KnightModel;
import com.fwcd.lightchess.model.piece.PawnModel;
import com.fwcd.lightchess.model.piece.QueenModel;
import com.fwcd.lightchess.model.piece.RookModel;
import com.fwcd.lightchess.utils.ChessConstants;
import com.fwcd.lightchess.utils.Streams;

public class ChessBoardModel {
	private final ChessFieldModel[][] fields;
	
	public ChessBoardModel() {
		fields = new ChessFieldModel[ChessConstants.RANKS][ChessConstants.FILES];
		for (int y=0; y<ChessConstants.RANKS; y++) {
			for (int x=0; x<ChessConstants.FILES; x++) {
				fields[y][x] = new ChessFieldModel(ChessPosition.at(x, y));
			}
		}
	}
	
	public static ChessBoardModel withInitialSetup() {
		ChessBoardModel board = new ChessBoardModel();
		for (PlayerColor color : PlayerColor.values()) {
			int pawnsY = (color == PlayerColor.BLACK)  ? 1 : 6;
			int piecesY = (color == PlayerColor.BLACK) ? 0 : 7;
			
			for (int x=0; x<ChessConstants.FILES; x++) {
				board.placeAt(x, pawnsY, color, PawnModel::new);
			}
			board.placeAt(0, piecesY, color, RookModel::new);
			board.placeAt(1, piecesY, color, KnightModel::new);
			board.placeAt(2, piecesY, color, BishopModel::new);
			board.placeAt(3, piecesY, color, QueenModel::new);
			board.placeAt(4, piecesY, color, KingModel::new);
			board.placeAt(5, piecesY, color, BishopModel::new);
			board.placeAt(6, piecesY, color, KnightModel::new);
			board.placeAt(7, piecesY, color, RookModel::new);
		}
		return board;
	}
	
	public Optional<KingModel> getCheckedKing() {
		return kings()
			.filter(it -> it.isChecked(this))
			.findAny();
	}
	
	public boolean isGameOver() {
		return getCheckmate().isPresent() || getStalemate().isPresent();
	}
	
	public Optional<KingModel> getCheckmate() {
		return kings()
			.filter(it -> it.isCheckmate(this))
			.findAny();
	}
	
	public Optional<PlayerColor> getStalemate() {
		// TODO: Implement special stalemate rules
		return Arrays.stream(PlayerColor.values())
			.filter(it -> !canMove(it))
			.findAny();
	}
	
	public void performMove(ChessMove move) {
		ChessPieceModel piece = move.getPiece();
		ChessPosition origin = move.getOrigin();
		ChessPosition destination = move.getDestination();
		
		fieldAt(origin).setPiece(Optional.empty());
		fieldAt(destination).setPiece(piece);
		move.getEnPassantCapturePos().ifPresent(capturePos -> {
			fieldAt(capturePos).setPiece(Optional.empty());
		});
		piece.moveTo(destination);
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
			.filter(it -> it.canMove(this))
			.findAny()
			.isPresent();
	}
	
	public Stream<ChessFieldModel> fields() {
		Stream.Builder<ChessFieldModel> stream = Stream.builder();
		for (ChessFieldModel[] rank : fields) {
			for (ChessFieldModel field : rank) {
				stream.add(field);
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
	
	public Stream<KingModel> kings() { return piecesOfType(ChessPieceType.KING).map(it -> (KingModel) it); }
	
	public Stream<QueenModel> queens() { return piecesOfType(ChessPieceType.QUEEN).map(it -> (QueenModel) it); }
	
	public Stream<BishopModel> bishops() { return piecesOfType(ChessPieceType.BISHOP).map(it -> (BishopModel) it); }
	
	public Stream<KnightModel> knights() { return piecesOfType(ChessPieceType.QUEEN).map(it -> (KnightModel) it); }
	
	public Stream<PawnModel> pawns() { return piecesOfType(ChessPieceType.PAWN).map(it -> (PawnModel) it); }
	
	public Stream<RookModel> rooks() { return piecesOfType(ChessPieceType.ROOK).map(it -> (RookModel) it); }
}
