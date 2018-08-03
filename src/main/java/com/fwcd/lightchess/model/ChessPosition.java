package com.fwcd.lightchess.model;

import java.util.Optional;

import com.fwcd.lightchess.utils.ChessConstants;

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
	
	public Optional<ChessPosition> plus(int dx, int dy) {
		int newX = x + dx;
		int newY = y + dy;
		if (isValidX(newX) && isValidY(newY)) {
			return Optional.of(new ChessPosition(newX, newY));
		} else return Optional.empty();
	}
	
	public Optional<ChessPosition> minus(int dx, int dy) {
		return plus(-dx, -dy);
	}
	
	public Optional<ChessPosition> up(int delta) { return plus(0, -delta); }
	
	public Optional<ChessPosition> right(int delta) { return plus(delta, 0); }
	
	public Optional<ChessPosition> down(int delta) { return plus(0, delta); }
	
	public Optional<ChessPosition> left(int delta) { return plus(-delta, 0); }
	
	public int getX() { return x; }
	
	public int getY() { return y; }
	
	public char getFile() { return (char) ('a' + x); }
	
	public int getRank() { return ChessConstants.RANKS - y; }
	
	private boolean isValidX(int x) {
		return (x >= 0) && (x <= ChessConstants.FILES);
	}
	
	private boolean isValidY(int y) {
		return (y >= 0) && (y <= ChessConstants.RANKS);
	}
}
