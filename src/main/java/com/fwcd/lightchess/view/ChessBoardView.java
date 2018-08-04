package com.fwcd.lightchess.view;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.swing.JComponent;
import javax.swing.JPanel;

import com.fwcd.fructose.geometry.Rectangle2D;
import com.fwcd.fructose.geometry.Vector2D;
import com.fwcd.fructose.swing.MouseHandler;
import com.fwcd.fructose.swing.RenderPanel;
import com.fwcd.fructose.swing.Viewable;
import com.fwcd.lightchess.model.ChessBoardModel;
import com.fwcd.lightchess.model.ChessPosition;
import com.fwcd.lightchess.utils.ChessConstants;

public class ChessBoardView implements Viewable {
	private final ChessBoardModel model;
	private final ChessFieldView[][] fields;
	private final ImageLoader imageLoader = new ImageLoader();
	private final ChessBoardTheme theme = ChessBoardTheme.WOODEN;
	private final JPanel view;
	
	private Set<ChessPosition> highlightedFields = new HashSet<>();
	private Optional<FloatingChessPieceView> floating = Optional.empty();
	
	private int fieldWidth;
	private int fieldHeight;
	
	public ChessBoardView(ChessBoardModel model) {
		this.model = model;
		view = new RenderPanel(this::render);
		fields = new ChessFieldView[ChessConstants.RANKS][ChessConstants.FILES];
		
		setupFields();
	}
	
	private void setupFields() {
		boolean isDark = false;
		for (int y=0; y<ChessConstants.RANKS; y++) {
			for (int x=0; x<ChessConstants.FILES; x++) {
				fields[y][x] = new ChessFieldView(model.fieldAt(ChessPosition.at(x, y)), imageLoader, theme, isDark);
				isDark = !isDark;
			}
			isDark = !isDark;
		}
	}
	
	public void addMouseHandler(MouseHandler handler) {
		handler.connect(view);
	}
	
	public Optional<ChessPosition> toChessPosition(Vector2D pixelPos) {
		int x = (int) pixelPos.getX() / fieldWidth;
		int y = (int) pixelPos.getY() / fieldHeight;
		return ChessPosition.ifValidAt(x, y);
	}
	
	public void repaint() {
		view.repaint();
	}
	
	private void render(Graphics2D g2d, Dimension canvasSize) {
		fieldWidth = canvasSize.width / fields[0].length;
		fieldHeight = canvasSize.height / fields.length;
		
		for (int y=0; y<fields.length; y++) {
			for (int x=0; x<fields[y].length; x++) {
				ChessFieldView field = fields[y][x];
				Vector2D pixelPos = new Vector2D(x * fieldWidth, y * fieldHeight);
				ChessPosition pos = ChessPosition.at(x, y);
				
				field.setBounds(new Rectangle2D(pixelPos, fieldWidth, fieldHeight));
				field.render(g2d, canvasSize);
				
				if (highlightedFields.contains(pos)) {
					g2d.setColor(theme.getHighlightColor());
					g2d.fillRect((int) pixelPos.getX(), (int) pixelPos.getY(), fieldWidth, fieldHeight);
				}
			}
		}
		
		// Render a floating chess piece (if present)
		floating.ifPresent(it -> it.getPiece()
				.renderAt(g2d, it.getPos().add(it.getOffset()), fieldWidth, fieldHeight));
	}
	
	@Override
	public JComponent getView() { return view; }
	
	public void setFloating(Optional<FloatingChessPieceView> floating) { this.floating = floating; }
	
	public Optional<FloatingChessPieceView> getFloating() { return floating; }
	
	public ChessFieldView[][] getFields() { return fields; }
	
	public void setHighlightedFields(Set<ChessPosition> highlightedFields) { this.highlightedFields = highlightedFields; }
	
	public Set<ChessPosition> getHighlightedFields() { return highlightedFields; }
}
