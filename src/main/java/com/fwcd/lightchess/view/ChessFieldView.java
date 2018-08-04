package com.fwcd.lightchess.view;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.fwcd.fructose.geometry.Rectangle2D;
import com.fwcd.fructose.geometry.Vector2D;
import com.fwcd.fructose.swing.Rendereable;
import com.fwcd.lightchess.model.ChessFieldModel;

public class ChessFieldView implements Rendereable {
	private final ChessFieldModel model;
	private final ChessBoardTheme theme;
	private final boolean isDark;
	private final ImageLoader imageLoader = new ImageLoader();
	private Optional<Rectangle2D> bounds = Optional.empty();
	private Optional<Vector2D> floatingPos = Optional.empty();
	private Optional<Vector2D> floatingOffset = Optional.empty();
	
	public ChessFieldView(ChessFieldModel model, ChessBoardTheme theme, boolean isDark) {
		this.model = model;
		this.theme = theme;
		this.isDark = isDark;
	}
	
	public void liftAt(Vector2D floatingPos, Vector2D floatingOffset) {
		this.floatingPos = Optional.of(floatingPos);
		this.floatingOffset = Optional.of(floatingOffset);
	}
	
	public void dragTo(Vector2D floatingPos) {
		this.floatingPos = Optional.of(floatingPos);
	}
	
	public Optional<Vector2D> drop() {
		Optional<Vector2D> pos = floatingPos;
		floatingPos = Optional.empty();
		floatingOffset = Optional.empty();
		return pos;
	}
	
	public void setBounds(Rectangle2D bounds) { this.bounds = Optional.of(bounds); }
	
	public Optional<Rectangle2D> getBounds() { return bounds; }
	
	@Override
	public void render(Graphics2D g2d, Dimension canvasSize) {
		Rectangle2D bounds = this.bounds.orElseThrow(NoSuchElementException::new);
		g2d.setColor(isDark ? theme.getDarkBG() : theme.getBrightBG());
		g2d.fillRect(
			(int) bounds.getX1(),
			(int) bounds.getY1(),
			(int) bounds.width(),
			(int) bounds.height()
		);
		model.getPiece().ifPresent(it -> it.accept(
			new ChessPieceRenderer(imageLoader, bounds, floatingPos, floatingOffset, g2d)
		));
	}
}
