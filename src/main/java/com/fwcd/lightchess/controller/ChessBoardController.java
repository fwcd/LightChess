package com.fwcd.lightchess.controller;

import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.JComponent;

import com.fwcd.fructose.EventListenerList;
import com.fwcd.fructose.geometry.Rectangle2D;
import com.fwcd.fructose.geometry.Vector2D;
import com.fwcd.fructose.swing.MouseHandler;
import com.fwcd.lightchess.model.ChessBoardModel;
import com.fwcd.lightchess.model.ChessMove;
import com.fwcd.lightchess.model.ChessPosition;
import com.fwcd.lightchess.model.piece.ChessPieceModel;
import com.fwcd.lightchess.view.board.ChessBoardView;
import com.fwcd.lightchess.view.board.ChessFieldView;
import com.fwcd.lightchess.view.board.FloatingChessPieceView;

public class ChessBoardController {
	private final ChessBoardModel model;
	private final ChessBoardView view;
	private EventListenerList<ChessMove> moveListeners = new EventListenerList<>();
	private boolean userInteractionsEnabled = false;
	
	public ChessBoardController(ChessBoardModel model, ChessBoardView view) {
		this.model = model;
		this.view = view;
		
		setupViewListeners();
	}
	
	public void setUserInteractionsEnabled(boolean userInteractionsEnabled) {
		this.userInteractionsEnabled = userInteractionsEnabled;
	}
	
	private void setupViewListeners() {
		MouseHandler handler = new MouseHandler() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (userInteractionsEnabled) {
					onMouseDown(posOf(e));
				}
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				if (userInteractionsEnabled) {
					onMouseDrag(posOf(e));
				}
			}
			
			@Override
			public void mouseReleased(MouseEvent e) { onMouseUp(posOf(e)); }
		};
		view.addMouseHandler(handler);
	}
	
	private void onMouseDown(Vector2D pos) {
		for (ChessFieldView[] rank : view.getFields()) {
			for (ChessFieldView field : rank) {
				boolean boundsContainPos = field.getBounds().filter(it -> it.contains(pos)).isPresent();
				if (boundsContainPos) {
					field.getPiece().ifPresent(piece -> {
						Rectangle2D bounds = field.getBounds().orElseThrow(IllegalStateException::new);
						Vector2D offset = bounds.getTopLeft().sub(pos);
						FloatingChessPieceView dragged = new FloatingChessPieceView(piece, field, pos, offset);
						
						field.setPieceFloats(true);
						view.setFloating(Optional.of(dragged));
						onDragStart(dragged);
					});
					return;
				}
			}
		}
	}
	
	private void onMouseDrag(Vector2D pos) {
		view.getFloating().ifPresent(floating -> {
			floating.setPos(pos);
			view.repaint();
		});
	}
	
	private void onMouseUp(Vector2D pos) {
		view.getFloating().ifPresent(dragged -> {
			Optional<ChessPosition> destination = view.toChessPosition(pos);
			ChessPieceModel piece = dragged.getPiece().getModel();
			Optional<ChessMove> move = piece.getPossibleMoves(model)
				.filter(it -> destination.filter(dest -> dest.equals(it.getDestination())).isPresent())
				.findAny();
			
			dragged.getOrigin().setPieceFloats(false);
			
			if (move.isPresent()) {
				moveListeners.fire(move.orElse(null));
				onDrop(dragged);
			} else {
				dragged.getOrigin().getModel().setPiece(piece);
			}
			view.repaint();
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
		return view.getView();
	}
}
