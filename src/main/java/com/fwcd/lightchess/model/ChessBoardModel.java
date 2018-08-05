package com.fwcd.lightchess.model;

import java.util.Optional;
import java.util.stream.Stream;

import com.fwcd.lightchess.model.piece.BishopModel;
import com.fwcd.lightchess.model.piece.ChessPieceModel;
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
				board.placeAt(x, pawnsY, new PawnModel(color));
			}
			board.placeAt(0, piecesY, new RookModel(color));
			board.placeAt(1, piecesY, new KnightModel(color));
			board.placeAt(2, piecesY, new BishopModel(color));
			board.placeAt(3, piecesY, new QueenModel(color));
			board.placeAt(4, piecesY, new KingModel(color));
			board.placeAt(5, piecesY, new BishopModel(color));
			board.placeAt(6, piecesY, new KnightModel(color));
			board.placeAt(7, piecesY, new RookModel(color));
		}
		return board;
	}
	
	public boolean performMove(ChessMove move) {
		boolean valid = move.getPiece()
			.getPossibleMoves(move.getOrigin(), this)
			.contains(move.getDest());
		if (valid) {
			fieldAt(move.getOrigin()).setPiece(Optional.empty());
			fieldAt(move.getDest()).setPiece(move.getPiece());;
		}
		return valid;
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
	
	public void placeAt(int x, int y, ChessPieceModel piece) {
		placeAt(ChessPosition.at(x, y), piece);
	}
	
	public Stream<ChessFieldModel> fields() {
		Stream.Builder<ChessFieldModel> stream = Stream.builder();
		
		return stream.build();
	}
	
	public <T extends ChessPieceModel> Stream<T> piecesOfType(Class<T> pieceType) {
		return Streams.filterPresent(fields().map(it -> it.getPieceAs(pieceType)));
	}
}
