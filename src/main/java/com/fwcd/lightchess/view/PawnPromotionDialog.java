package com.fwcd.lightchess.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.Optional;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.fwcd.fructose.Safe;
import com.fwcd.fructose.geometry.Vector2D;
import com.fwcd.fructose.swing.MouseHandler;
import com.fwcd.fructose.swing.RenderPanel;
import com.fwcd.lightchess.model.ChessPosition;
import com.fwcd.lightchess.model.PlayerColor;
import com.fwcd.lightchess.model.piece.ChessPieceModel;
import com.fwcd.lightchess.model.piece.ChessPieceType;
import com.fwcd.lightchess.utils.ChessConstants;
import com.fwcd.lightchess.view.board.ChessPieceRenderer;

public class PawnPromotionDialog {
	private final JPanel view;
	private final ImageLoader imgLoader;
	private final int pieceSideLength = 100; // px
	private final int padding = 20;
	
	private final PlayerColor color;
	private Optional<ChessPieceType> selectedPiece = Optional.empty();
	
	public PawnPromotionDialog(PlayerColor color, ImageLoader imgLoader) {
		this.imgLoader = imgLoader;
		this.color = color;
		view = new RenderPanel(this::render);
		view.setPreferredSize(new Dimension((pieceSideLength + padding) * ChessConstants.PROMOTION_PIECES.length, pieceSideLength));
		view.addMouseListener(new MouseHandler() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i = e.getX() / (pieceSideLength + padding);
				selectedPiece = Safe.arrayGet(ChessConstants.PROMOTION_PIECES, i);
				view.repaint();
			}
		});
	}
	
	private void render(Graphics2D g2d, Dimension canvasSize) {
		int x = 0;
		int y = 0;
		
		for (ChessPieceType pieceType : ChessConstants.PROMOTION_PIECES) {
			ChessPieceModel piece = pieceType.construct(color, ChessPosition.at(0, 0));
			if (selectedPiece.filter(it -> it.equals(pieceType)).isPresent()) {
				// Highlight the selected piece
				g2d.setColor(Color.GRAY);
				g2d.fillRect(x, y, pieceSideLength, pieceSideLength);
			}
			piece.accept(new ChessPieceRenderer(imgLoader, g2d, new Vector2D(x, y), pieceSideLength, pieceSideLength));
			
			x += pieceSideLength + padding;
		}
	}
	
	public ChessPieceType show() {
		JOptionPane.showMessageDialog(
			null,
			view,
			"Select promotion piece",
			JOptionPane.PLAIN_MESSAGE
		);
		if (selectedPiece.isPresent()) {
			return selectedPiece.orElse(null);
		} else {
			// Re-prompt the user if no piece has been selected
			return show();
		}
	}
}
