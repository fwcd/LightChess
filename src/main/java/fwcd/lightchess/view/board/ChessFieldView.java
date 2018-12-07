package fwcd.lightchess.view.board;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.NoSuchElementException;

import fwcd.fructose.ListenerList;
import fwcd.fructose.Option;
import fwcd.fructose.geometry.Rectangle2D;
import fwcd.fructose.swing.Renderable;
import fwcd.lightchess.model.ChessFieldModel;
import fwcd.lightchess.model.piece.ChessPieceModel;
import fwcd.lightchess.view.ImageLoader;

public class ChessFieldView implements Renderable {
	private final ChessFieldModel model;
	private final Runnable repainter;
	private final ImageLoader imageLoader;
	private final ChessBoardTheme theme;
	private final boolean isDark;
	private Option<ChessPieceView> piece = Option.empty();
	private Option<Rectangle2D> bounds = Option.empty();
	private ListenerList changeListeners = new ListenerList();
	private boolean pieceFloats = false;
	
	public ChessFieldView(
		ChessFieldModel model,
		Runnable repainter,
		ImageLoader imageLoader,
		ChessBoardTheme theme,
		boolean isDark
	) {
		this.model = model;
		this.repainter = repainter;
		this.theme = theme;
		this.isDark = isDark;
		this.imageLoader = imageLoader;
		
		setupModelListeners();
	}
	
	private void setupModelListeners() {
		model.observePiece(this::setPiece);
	}
	
	private void setPiece(Option<ChessPieceModel> pieceModel) {
		piece = pieceModel.map(it -> new ChessPieceView(it, imageLoader));
		repainter.run();
	}
	
	public void setPieceFloats(boolean pieceFloats) {
		this.pieceFloats = pieceFloats;
	}
	
	public ChessFieldModel getModel() { return this.model; }
	
	public ListenerList getChangeListeners() { return changeListeners; }
	
	public void setBounds(Rectangle2D bounds) { this.bounds = Option.of(bounds); }
	
	public Option<Rectangle2D> getBounds() { return bounds; }
	
	public Option<ChessPieceView> getPiece() { return piece; }
	
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
		if (!pieceFloats) {
			piece.ifPresent(it -> {
				it.renderAt(g2d, bounds.getTopLeft(), (int) bounds.width(), (int) bounds.height());
			});
		}
	}
}
