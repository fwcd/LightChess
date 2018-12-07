package fwcd.lightchess.controller;

import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.JComponent;

import fwcd.fructose.EventListenerList;
import fwcd.fructose.geometry.Rectangle2D;
import fwcd.fructose.geometry.Vector2D;
import fwcd.fructose.swing.MouseHandler;
import fwcd.lightchess.model.ChessBoardModel;
import fwcd.lightchess.model.ChessMove;
import fwcd.lightchess.model.ChessPosition;
import fwcd.lightchess.model.PlayerColor;
import fwcd.lightchess.model.piece.ChessPieceModel;
import fwcd.lightchess.model.piece.ChessPieceType;
import fwcd.lightchess.utils.ChessConstants;
import fwcd.lightchess.view.board.ChessBoardView;
import fwcd.lightchess.view.board.ChessFieldView;
import fwcd.lightchess.view.board.FloatingChessPieceView;

public class ChessBoardController {
	private final ChessBoardModel model;
	private final ChessBoardView view;
	private final EventListenerList<ChessMove> moveListeners = new EventListenerList<>();
	/** Pieces with a "moveableColor" can be dragged by the user when interacting with the GUI */
	private Set<PlayerColor> moveableColors = new HashSet<>();
	
	public ChessBoardController(ChessBoardModel model, ChessBoardView view) {
		this.model = model;
		this.view = view;
		
		setupViewListeners();
	}
	
	public void setMoveableColors(PlayerColor... moveableColors) {
		this.moveableColors = Stream.of(moveableColors).collect(Collectors.toSet());
	}
	
	private void setupViewListeners() {
		MouseHandler handler = new MouseHandler() {
			@Override
			public void mousePressed(MouseEvent e) {
				onMouseDown(posOf(e));
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				onMouseDrag(posOf(e));
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				onMouseUp(posOf(e));
			}
		};
		view.addMouseHandler(handler);
	}
	
	private void onMouseDown(Vector2D pos) {
		for (ChessFieldView[] rank : view.getFields()) {
			for (ChessFieldView field : rank) {
				boolean boundsContainPos = field.getBounds().filter(it -> it.contains(pos)).isPresent();
				if (boundsContainPos) {
					field.getPiece().ifPresent(piece -> {
						if (moveableColors.contains(piece.getModel().getColor())) {
							Rectangle2D bounds = field.getBounds().orElseThrow(IllegalStateException::new);
							Vector2D offset = bounds.getTopLeft().sub(pos);
							FloatingChessPieceView dragged = new FloatingChessPieceView(piece, field, pos, offset);
							
							field.setPieceFloats(true);
							view.setFloating(Optional.of(dragged));
							onDragStart(dragged);
						}
						
					});
					return;
				}
			}
		}
	}
	
	private void onMouseDrag(Vector2D pos) {
		view.getFloating().ifPresent(dragged -> {
			dragged.setPos(pos);
			view.repaint();
		});
	}
	
	private void onMouseUp(Vector2D pos) {
		view.getFloating().ifPresent(dragged -> {
			Optional<ChessPosition> destination = view.toChessPosition(pos);
			ChessPieceModel piece = dragged.getPiece().getModel();
			boolean isPawnPromotion = (piece.getType() == ChessPieceType.PAWN)
				&& destination
					.filter(it -> (it.getY() == 0) || (it.getY() == (ChessConstants.RANKS - 1)))
					.isPresent();
			Stream<ChessMove> validMoves = piece.getPossibleMoves(model)
				.filter(it -> destination.filter(dest -> dest.equals(it.getDestination())).isPresent());
			
			if (isPawnPromotion) {
				// In case of pawn promotion, there can be multiple
				// moves leading to the same destination (these are
				// the different pieces that the pawn can promote to).
				// To disambiguate, a dialog is used.
				ChessPieceType promotionPiece = view.showPromotionDialog(piece.getColor());
				validMoves = validMoves
					.filter(it -> it.getPromotedPiece().filter(p -> p.equals(promotionPiece)).isPresent());
			}
			
			Optional<ChessMove> move = validMoves.findAny();
			
			dragged.getOrigin().setPieceFloats(false);
			
			if (move.isPresent()) {
				moveListeners.fire(move.orElse(null));
				onDrop(dragged);
			} else {
				dragged.getOrigin().getModel().setPiece(piece);
			}
		});
		view.setFloating(Optional.empty());
	}
	
	private void onDragStart(FloatingChessPieceView dragged) {
		ChessPieceModel pieceModel = dragged.getPiece().getModel();
		Set<ChessPosition> highlightedFields = pieceModel.getPossibleMoves(model)
			.map(it -> it.getDestination())
			.collect(Collectors.toSet());
		view.setHighlightedFields(highlightedFields);
	}
	
	private void onDrop(FloatingChessPieceView dragged) {
		view.setHighlightedFields(Collections.emptySet());
	}
	
	public EventListenerList<ChessMove> getMoveListeners() {
		return moveListeners;
	}
	
	public ChessBoardModel getModel() {
		return model;
	}
	
	public JComponent getViewComponent() {
		return view.getComponent();
	}
}
