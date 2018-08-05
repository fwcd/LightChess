package com.fwcd.lightchess.model;

import java.util.Optional;
import java.util.function.Consumer;

import com.fwcd.fructose.Observable;
import com.fwcd.lightchess.model.piece.ChessPieceModel;

public class ChessFieldModel {
	private final ChessPosition position;
	private Observable<Optional<ChessPieceModel>> piece = new Observable<>(Optional.empty());
	
	public ChessFieldModel(ChessPosition position) {
		this.position = position;
	}
	
	public boolean hasPiece() { return piece.get().isPresent(); }
	
	public boolean hasPieceOfColor(PlayerColor color) {
		return piece.get().filter(it -> it.getColor() == color).isPresent();
	}
	
	public boolean hasPieceOfType(Class<? extends ChessPieceModel> pieceType) {
		return piece.get().filter(pieceType::isInstance).isPresent();
	}
	
	public void observePiece(Consumer<Optional<ChessPieceModel>> observer) {
		piece.listen(observer);
		observer.accept(piece.get());
	}
	
	public ChessPosition getPosition() { return position; }
	
	public void setPiece(Optional<ChessPieceModel> piece) { this.piece.set(piece); }
	
	public void setPiece(ChessPieceModel piece) { this.piece.set(Optional.of(piece)); }
	
	public Optional<ChessPieceModel> getPiece() { return piece.get(); }
	
	@SuppressWarnings("unchecked")
	public <T> Optional<T> getPieceAs(Class<? extends ChessPieceModel> pieceType) {
		if (hasPieceOfType(pieceType)) {
			return Optional.of((T) piece.get().orElse(null));
		} else return Optional.empty();
	}
}
