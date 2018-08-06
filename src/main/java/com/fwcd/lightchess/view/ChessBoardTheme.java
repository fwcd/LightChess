package com.fwcd.lightchess.view;

import java.awt.Color;

public class ChessBoardTheme {
	public static final ChessBoardTheme WOODEN = new ChessBoardTheme(
		/* darkBG */ new Color(229, 171, 45),
		/* brightBG */ new Color(255, 194, 63),
		/* highlightColor */ new Color(255, 255, 255, 128)
	);
	
	private final Color darkBG;
	private final Color brightBG;
	private final Color highlightColor;
	
	public ChessBoardTheme(Color darkBG, Color brightBG, Color highlightColor) {
		this.darkBG = darkBG;
		this.brightBG = brightBG;
		this.highlightColor = highlightColor;
	}
	
	public Color getBrightBG() { return brightBG; }
	
	public Color getDarkBG() { return darkBG; }
	
	public Color getHighlightColor() { return highlightColor; }
}
