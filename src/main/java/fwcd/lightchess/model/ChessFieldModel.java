package fwcd.lightchess.model;

import fwcd.fructose.Option;
import java.util.function.Consumer;

import fwcd.fructose.Copyable;
import fwcd.fructose.Observable;
import fwcd.lightchess.model.piece.ChessPieceModel;
import fwcd.lightchess.model.piece.ChessPieceType;

public class ChessFieldModel implements Copyable<ChessFieldModel> {
	private final ChessPosition position;
	private Observable<Option<ChessPieceModel>> piece;
	
	public ChessFieldModel(ChessPosition position) {
		this.position = position;
		piece = new Observable<>(Option.empty());
	}
	
	private ChessFieldModel(ChessPosition position, Option<ChessPieceModel> piece) {
		this.position = position;
		this.piece = new Observable<>(piece);
	}
	
	public boolean hasPiece() { return piece.get().isPresent(); }
	
	public boolean hasPieceOfColor(PlayerColor color) {
		return piece.get().filter(it -> it.getColor() == color).isPresent();
	}
	
	public boolean hasPieceOfType(ChessPieceType pieceType) {
		return piece.get().filter(it -> it.getType().equals(pieceType)).isPresent();
	}
	
	public void observePiece(Consumer<Option<ChessPieceModel>> observer) {
		piece.listen(observer);
		observer.accept(piece.get());
	}
	
	public ChessPosition getPosition() { return position; }
	
	public void setPiece(Option<ChessPieceModel> piece) { this.piece.set(piece); }
	
	public void setPiece(ChessPieceModel piece) { this.piece.set(Option.of(piece)); }
	
	public Option<ChessPieceModel> getPiece() { return piece.get(); }
	
	@Override
	public ChessFieldModel copy() {
		return new ChessFieldModel(position, piece.get().map(ChessPieceModel::copy));
	}
}
