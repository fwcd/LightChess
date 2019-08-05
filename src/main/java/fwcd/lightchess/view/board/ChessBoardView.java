package fwcd.lightchess.view.board;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.HashSet;
import fwcd.fructose.Option;
import java.util.Set;

import javax.swing.JComponent;
import javax.swing.JPanel;

import fwcd.fructose.geometry.Rectangle2D;
import fwcd.fructose.geometry.Vector2D;
import fwcd.fructose.swing.MouseHandler;
import fwcd.fructose.swing.RenderPanel;
import fwcd.fructose.swing.Viewable;
import fwcd.lightchess.model.ChessBoardModel;
import fwcd.lightchess.model.ChessPosition;
import fwcd.lightchess.model.PlayerColor;
import fwcd.lightchess.model.piece.ChessPieceType;
import fwcd.lightchess.utils.ChessConstants;
import fwcd.lightchess.view.ImageLoader;
import fwcd.lightchess.view.PawnPromotionDialog;

public class ChessBoardView implements Viewable {
	private final ChessBoardModel model;
	private final ChessFieldView[][] fields;
	private final ImageLoader imageLoader = new ImageLoader();
	private final ChessBoardTheme theme = ChessBoardTheme.WOODEN;
	private final JPanel component;
	
	private Set<ChessPosition> highlightedFields = new HashSet<>();
	private Option<FloatingChessPieceView> floating = Option.empty();
	
	private int fieldWidth;
	private int fieldHeight;
	
	public ChessBoardView(ChessBoardModel model) {
		this.model = model;
		component = new RenderPanel(this::render);
		fields = new ChessFieldView[ChessConstants.RANKS][ChessConstants.FILES];
		
		setupFields();
	}
	
	private void setupFields() {
		boolean isDark = false;
		for (int y=0; y<ChessConstants.RANKS; y++) {
			for (int x=0; x<ChessConstants.FILES; x++) {
				fields[y][x] = new ChessFieldView(model.fieldAt(ChessPosition.at(x, y)), component::repaint, imageLoader, theme, isDark);
				isDark = !isDark;
			}
			isDark = !isDark;
		}
	}
	
	public ChessPieceType showPromotionDialog(PlayerColor color)  {
		return new PawnPromotionDialog(color, imageLoader).show();
	}
	
	public void addMouseHandler(MouseHandler handler) {
		handler.connect(component);
	}
	
	public Option<ChessPosition> toChessPosition(Vector2D pixelPos) {
		int x = (int) pixelPos.getX() / fieldWidth;
		int y = (int) pixelPos.getY() / fieldHeight;
		return ChessPosition.ifValidAt(x, y);
	}
	
	public void repaint() {
		component.repaint();
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
	public JComponent getComponent() { return component; }
	
	public void setFloating(Option<FloatingChessPieceView> floating) { this.floating = floating; }
	
	public Option<FloatingChessPieceView> getFloating() { return floating; }
	
	public ChessFieldView[][] getFields() { return fields; }
	
	public void setHighlightedFields(Set<ChessPosition> highlightedFields) { this.highlightedFields = highlightedFields; }
	
	public Set<ChessPosition> getHighlightedFields() { return highlightedFields; }
}
