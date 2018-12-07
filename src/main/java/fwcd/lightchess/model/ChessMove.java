package fwcd.lightchess.model;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import fwcd.lightchess.model.piece.ChessPieceType;

public class ChessMove {
	private final ChessPieceType pieceType;
	private final ChessPosition origin;
	private final ChessPosition destination;
	private final List<ChessPosition> otherCaptures;
	private final Map<ChessPosition, ChessPosition> otherRelocations;
	private final Optional<ChessPieceType> promotedPiece;
	
	private ChessMove(
		ChessPieceType pieceType,
		ChessPosition origin,
		ChessPosition destination,
		List<ChessPosition> otherCaptures,
		Map<ChessPosition, ChessPosition> otherRelocations,
		Optional<ChessPieceType> promotedPiece
	) {
		this.pieceType = pieceType;
		this.origin = origin;
		this.destination = destination;
		this.otherCaptures = otherCaptures;
		this.otherRelocations = otherRelocations;
		this.promotedPiece = promotedPiece;
	}
	
	public static ChessMove create(
		ChessPieceType pieceType,
		ChessPosition origin,
		ChessPosition destination
	) {
		return new ChessMove(pieceType, origin, destination, Collections.emptyList(), Collections.emptyMap(), Optional.empty());
	}
	
	public static ChessMove createPawnPromotion(
		ChessPosition origin,
		ChessPosition destination,
		ChessPieceType promotedPiece
	) {
		return new ChessMove(ChessPieceType.PAWN, origin, destination, Collections.emptyList(), Collections.emptyMap(), Optional.of(promotedPiece));
	}
	
	public static ChessMove createEnPassant(
		ChessPieceType pieceType,
		ChessPosition origin,
		ChessPosition destination,
		ChessPosition enPassantCapturePos
	) {
		return new ChessMove(pieceType, origin, destination, Collections.singletonList(enPassantCapturePos), Collections.emptyMap(), Optional.empty());
	}
	
	public static ChessMove createCastling(
		ChessPieceType pieceType,
		ChessPosition kingOrigin,
		ChessPosition kingDestination,
		ChessPosition rookOrigin,
		ChessPosition rookDestination
	) {
		return new ChessMove(pieceType, kingOrigin, kingDestination, Collections.emptyList(), Collections.singletonMap(rookOrigin, rookDestination), Optional.empty());
	}
	
	public ChessPosition getDestination() { return destination; }
	
	public ChessPosition getOrigin() { return origin; }
	
	public ChessPieceType getPieceType() { return pieceType; }
	
	public List<ChessPosition> getOtherCaptures() { return otherCaptures; }
	
	public Map<ChessPosition, ChessPosition> getOtherRelocations() { return otherRelocations; }
	
	public Optional<ChessPieceType> getPromotedPiece() { return promotedPiece; }
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		ChessMove other = (ChessMove) obj;
		return pieceType.equals(other.pieceType)
			&& origin.equals(other.origin)
			&& destination.equals(other.destination)
			&& otherCaptures.equals(other.otherCaptures)
			&& otherRelocations.equals(other.otherRelocations)
			&& promotedPiece.equals(other.promotedPiece);
	}
	
	@Override
	public int hashCode() {
		return pieceType.hashCode()
			* origin.hashCode()
			* destination.hashCode()
			* otherCaptures.hashCode()
			* otherRelocations.hashCode()
			* promotedPiece.hashCode()
			* 3;
	}
}
