package com.fwcd.lightchess.view;

import java.awt.Color;

public class ChessBoardTheme {
	public static final ChessBoardTheme WOODEN = new ChessBoardTheme(new Color(255, 194, 63), new Color(229, 171, 45));
	
	private final Color darkBG;
	private final Color brightBG;
	
	public ChessBoardTheme(Color darkBG, Color brightBG) {
		this.darkBG = darkBG;
		this.brightBG = brightBG;
	}
	
	public Color getBrightBG() { return brightBG; }
	
	public Color getDarkBG() { return darkBG; }
}
