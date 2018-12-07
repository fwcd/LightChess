package fwcd.lightchess.model;

import fwcd.fructose.Option;

import fwcd.lightchess.utils.ChessConstants;

/** A position on the chess board */
public class ChessPosition {
	private final int x; // Any value between 0 and 8 (exclusive)
	private final int y; // Any value between 0 and 8 (exclusive)
	
	private ChessPosition(int x, int y) {
		if (!isValidX(x)) {
			throw new IllegalArgumentException("Invalid X: " + x);
		}
		if (!isValidY(y)) {
			throw new IllegalArgumentException("Invalid X: " + y);
		}
		this.y = y;
		this.x = x;
	}
	
	public static ChessPosition of(char file, int rank) {
		return at(file - 'a', 8 - rank);
	}
	
	public static ChessPosition of(String str) {
		if (str.length() == 2) {
			try {
				return of(Character.toLowerCase(str.charAt(1)), Character.getNumericValue(str.charAt(0)));
			} catch (NumberFormatException e) {}
		}
		throw new IllegalArgumentException(str + " is not a valid ChessBoardPosition");
	}
	
	public static ChessPosition at(int x, int y) {
		return new ChessPosition(x, y);
	}
	
	public static Option<ChessPosition> ifValidAt(int x, int y) {
		if (isValidX(x) && isValidY(y)) {
			return Option.of(new ChessPosition(x, y));
		} else return Option.empty();
	}
	
	public Option<ChessPosition> plus(int dx, int dy) {
		return ChessPosition.ifValidAt(x + dx, y + dy);
	}
	
	public Option<ChessPosition> minus(int dx, int dy) {
		return plus(-dx, -dy);
	}
	
	/** Equivalent to minusY */
	public Option<ChessPosition> up(int delta) { return plus(0, -delta); }
	
	/** Equivalent to plusX */
	public Option<ChessPosition> right(int delta) { return plus(delta, 0); }
	
	/** Equivalent to plusY */
	public Option<ChessPosition> down(int delta) { return plus(0, delta); }
	
	/** Equivalent to minusX */
	public Option<ChessPosition> left(int delta) { return plus(-delta, 0); }
	
	public int getX() { return x; }
	
	public int getY() { return y; }
	
	public char getFile() { return (char) ('a' + x); }
	
	public int getRank() { return ChessConstants.RANKS - y; }
	
	public int xDistanceTo(ChessPosition position) {
		return Math.abs(position.x - x);
	}
	
	public int yDistanceTo(ChessPosition position) {
		return Math.abs(position.y - y);
	}
	
	public boolean isTopLeftCorner() {
		return (x == 0) && (y == 0);
	}
	
	public boolean isBottomLeftCorner() {
		return (x == 0) && (y == (ChessConstants.RANKS - 1));
	}
	
	public boolean isTopRightCorner() {
		return (x == (ChessConstants.FILES - 1)) && (y == 0);
	}
	
	public boolean isBottomRightCorner() {
		return (x == (ChessConstants.FILES - 1)) && (y == (ChessConstants.RANKS - 1));
	}
	
	private static boolean isValidX(int x) {
		return (x >= 0) && (x < ChessConstants.FILES);
	}
	
	private static boolean isValidY(int y) {
		return (y >= 0) && (y < ChessConstants.RANKS);
	}
	
	@Override
	public String toString() {
		return new StringBuilder().append(getFile()).append(getRank()).toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		return (x == ((ChessPosition) obj).x) && (y == ((ChessPosition) obj).y);
	}
	
	@Override
	public int hashCode() {
		return x * y * 31;
	}
}
