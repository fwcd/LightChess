package com.fwcd.lightchess.view;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.Optional;

import javax.swing.JComponent;
import javax.swing.JPanel;

import com.fwcd.fructose.EventListenerList;
import com.fwcd.fructose.geometry.Rectangle2D;
import com.fwcd.fructose.geometry.Vector2D;
import com.fwcd.fructose.swing.MouseHandler;
import com.fwcd.fructose.swing.RenderPanel;
import com.fwcd.fructose.swing.Viewable;
import com.fwcd.lightchess.model.ChessBoardModel;
import com.fwcd.lightchess.model.ChessFieldModel;
import com.fwcd.lightchess.model.ChessPosition;
import com.fwcd.lightchess.model.piece.ChessPieceModel;
import com.fwcd.lightchess.utils.ChessConstants;

public class ChessBoardView implements Viewable {
	private final ChessBoardModel model;
	private final ChessFieldView[][] fields;
	private final ImageLoader imageLoader = new ImageLoader();
	private final ChessBoardTheme theme = ChessBoardTheme.WOODEN;
	private final JPanel view;
	private Optional<FloatingChessPieceView> floating = Optional.empty();
	
	private int fieldWidth;
	private int fieldHeight;
	
	private static class FloatingChessPieceView {
		ChessPieceView piece;
		ChessFieldModel origin;
		Vector2D pos;
		Vector2D offset;
		
		public FloatingChessPieceView(ChessPieceView piece, ChessFieldModel origin, Vector2D pos, Vector2D offset) {
			this.piece = piece;
			this.origin = origin;
			this.pos = pos;
			this.offset = offset;
		}
	}
	
	public ChessBoardView(ChessBoardModel model) {
		this.model = model;
		view = new RenderPanel(this::render);
		fields = new ChessFieldView[ChessConstants.RANKS][ChessConstants.FILES];
		
		setupFields();
		setupListeners();
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
	
	private void setupListeners() {
		MouseHandler handler = new MouseHandler() {
			@Override
			public void mousePressed(MouseEvent e) {
				Vector2D pos = posOf(e);
				for (ChessFieldView[] rank : fields) {
					for (ChessFieldView field : rank) {
						boolean boundsContainPos = field.getBounds().filter(it -> it.contains(pos)).isPresent();
						if (boundsContainPos) {
							field.getPiece().ifPresent(piece -> {
								Rectangle2D bounds = field.getBounds().orElseThrow(IllegalStateException::new);
								Vector2D offset = bounds.getTopLeft().sub(pos);
								ChessFieldModel fieldModel = field.getModel();
								FloatingChessPieceView dragged = new FloatingChessPieceView(piece, fieldModel, pos, offset);
								
								fieldModel.setPiece(Optional.empty());
								floating = Optional.of(dragged);
								onDragStart(dragged);
							});
							return;
						}
					}
				}
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				floating.ifPresent(floating -> {
					floating.pos = posOf(e);
					view.repaint();
				});
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				floating.ifPresent(dragged -> {
					Optional<ChessFieldModel> fieldModel = toChessPosition(posOf(e)).map(model::fieldAt);
					ChessPieceModel pieceModel = dragged.piece.getModel();
					if (fieldModel.isPresent()) {
						fieldModel.orElse(null).setPiece(pieceModel);
						onDrop(dragged);
					} else {
						dragged.origin.setPiece(pieceModel);
					}
					view.repaint();
				});
				floating = Optional.empty();
			}
		};
		handler.connect(view);
	}
	
	public void addMouseHandler(MouseHandler handler) {
		handler.connect(view);
	}
	
	private Optional<ChessPosition> toChessPosition(Vector2D pixelPos) {
		int x = (int) pixelPos.getX() / fieldWidth;
		int y = (int) pixelPos.getY() / fieldHeight;
		return ChessPosition.ifValidAt(x, y);
	}
	
	private void onDragStart(FloatingChessPieceView dragged) {
		// TODO
	}
	
	private void onDrop(FloatingChessPieceView dragged) {
		// TODO
	}
	
	private void render(Graphics2D g2d, Dimension canvasSize) {
		fieldWidth = canvasSize.width / fields[0].length;
		fieldHeight = canvasSize.height / fields.length;
		
		for (int y=0; y<fields.length; y++) {
			for (int x=0; x<fields[y].length; x++) {
				ChessFieldView field = fields[y][x];
				Vector2D pos = new Vector2D(x * fieldWidth, y * fieldHeight);
				field.setBounds(new Rectangle2D(pos, fieldWidth, fieldHeight));
				field.render(g2d, canvasSize);
			}
		}
		
		// Render a floating chess piece (if present)
		floating.ifPresent(it -> it.piece.renderAt(g2d, it.pos.add(it.offset), fieldWidth, fieldHeight));
	}
	
	@Override
	public JComponent getView() { return view; }
}
