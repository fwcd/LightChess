package com.fwcd.lightchess.view;

import java.awt.Dimension;
import java.awt.Graphics2D;

import com.fwcd.fructose.NonNull;
import com.fwcd.fructose.geometry.Rectangle2D;
import com.fwcd.fructose.swing.Rendereable;
import com.fwcd.lightchess.model.ChessFieldModel;

public class ChessFieldView implements Rendereable {
	private final ChessFieldModel model;
	private final ChessBoardTheme theme;
	private final boolean isDark;
	private NonNull<Rectangle2D> bounds = NonNull.empty();
	
	public ChessFieldView(ChessFieldModel model, ChessBoardTheme theme, boolean isDark) {
		this.model = model;
		this.theme = theme;
		this.isDark = isDark;
	}
	
	public void setBounds(Rectangle2D bounds) { this.bounds = NonNull.of(bounds); }
	
	@Override
	public void render(Graphics2D g2d, Dimension canvasSize) {
		Rectangle2D bounds = this.bounds.get();
		g2d.setColor(isDark ? theme.getDarkBG() : theme.getBrightBG());
		g2d.fillRect(
			(int) bounds.getX1(),
			(int) bounds.getY1(),
			(int) bounds.width(),
			(int) bounds.height()
		);
		model.getPiece().ifPresent(it -> it.accept(new ChessPieceRenderer(bounds, g2d)));
	}
}
