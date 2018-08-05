package com.fwcd.lightchess.controller;

import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.Optional;

import javax.swing.JComponent;

import com.fwcd.fructose.geometry.Rectangle2D;
import com.fwcd.fructose.geometry.Vector2D;
import com.fwcd.fructose.swing.MouseHandler;
import com.fwcd.lightchess.model.ChessBoardModel;
import com.fwcd.lightchess.model.ChessFieldModel;
import com.fwcd.lightchess.model.ChessMove;
import com.fwcd.lightchess.model.ChessPosition;
import com.fwcd.lightchess.model.piece.ChessPieceModel;
import com.fwcd.lightchess.view.ChessBoardView;
import com.fwcd.lightchess.view.ChessFieldView;
import com.fwcd.lightchess.view.FloatingChessPieceView;

public class ChessBoardController {
	private final ChessBoardModel model;
	private final ChessBoardView view;
	
	public ChessBoardController(ChessBoardModel model) {
		this.model = model;
		view = new ChessBoardView(model);
		
		setupViewListeners();
	}
	
	private void setupViewListeners() {
		MouseHandler handler = new MouseHandler() {
			@Override
			public void mousePressed(MouseEvent e) { onMouseDown(posOf(e)); }
			
			@Override
			public void mouseDragged(MouseEvent e) { onMouseDrag(posOf(e)); }
			
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
						ChessFieldModel fieldModel = field.getModel();
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
			ChessPosition origin = dragged.getOrigin().getModel().getPosition();
			Optional<ChessPosition> dest = view.toChessPosition(pos);
			ChessPieceModel pieceModel = dragged.getPiece().getModel();
			Optional<ChessMove> move = dest.map(d -> new ChessMove(pieceModel, origin, d));
			
			dragged.getOrigin().setPieceFloats(false);
			
			if (move.isPresent()) {
				model.performMove(move.orElse(null));
				onDrop(dragged);
			} else {
				dragged.getOrigin().getModel().setPiece(pieceModel);
			}
			view.repaint();
		});
		view.setFloating(Optional.empty());
	}
	
	private void onDragStart(FloatingChessPieceView dragged) {
		ChessPieceModel pieceModel = dragged.getPiece().getModel();
		ChessPosition startPos = dragged.getOrigin().getModel().getPosition();
		view.setHighlightedFields(pieceModel.getPossibleMoves(startPos, model));
	}
	
	private void onDrop(FloatingChessPieceView dragged) {
		view.setHighlightedFields(Collections.emptySet());
	}
	
	public JComponent getViewComponent() {
		return view.getView();
	}
}
