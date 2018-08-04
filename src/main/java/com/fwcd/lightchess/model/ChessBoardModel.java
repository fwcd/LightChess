package com.fwcd.lightchess.model;

import java.util.Optional;
import java.util.stream.Stream;

import com.fwcd.lightchess.model.piece.ChessPieceModel;
import com.fwcd.lightchess.utils.ChessConstants;
import com.fwcd.lightchess.utils.Streams;

public class ChessBoardModel {
	private final ChessFieldModel[][] fields;
	
	public ChessBoardModel() {
		fields = new ChessFieldModel[ChessConstants.RANKS][ChessConstants.FILES];
	}
	
	public static ChessBoardModel newGame() {
		
	}
	
	public ChessFieldModel fieldAt(ChessPosition position) {
		return fields[position.getY()][position.getX()];
	}
	
	public Optional<ChessPieceModel> pieceAt(ChessPosition position) {
		return fieldAt(position).getPiece();
	}
	
	public Stream<ChessFieldModel> fields() {
		Stream.Builder<ChessFieldModel> stream = Stream.builder();
		
		return stream.build();
	}
	
	public <T extends ChessPieceModel> Stream<T> piecesOfType(Class<T> pieceType) {
		return Streams.filterPresent(fields().map(it -> it.getPieceAs(pieceType)));
	}
}
