package fwcd.lightchess.model;

public enum PlayerColor {
	BLACK, WHITE;
	
	public PlayerColor opponent() {
		return (this == BLACK) ? WHITE : BLACK;
	}
}
