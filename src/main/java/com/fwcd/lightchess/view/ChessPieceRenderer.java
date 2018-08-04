package com.fwcd.lightchess.view;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Optional;

import com.fwcd.fructose.geometry.Rectangle2D;
import com.fwcd.fructose.geometry.Vector2D;
import com.fwcd.fructose.io.ResourceFile;
import com.fwcd.lightchess.model.PlayerColor;
import com.fwcd.lightchess.model.piece.BishopModel;
import com.fwcd.lightchess.model.piece.ChessPieceVisitor;
import com.fwcd.lightchess.model.piece.KingModel;
import com.fwcd.lightchess.model.piece.KnightModel;
import com.fwcd.lightchess.model.piece.PawnModel;
import com.fwcd.lightchess.model.piece.QueenModel;
import com.fwcd.lightchess.model.piece.RookModel;

public class ChessPieceRenderer implements ChessPieceVisitor {
	private final ImageLoader loader;
	private final Rectangle2D bounds;
	private final Graphics2D g2d;
	private final Optional<Vector2D> floatingPos;
	private final Optional<Vector2D> floatingOffset;
	
	public ChessPieceRenderer(
		ImageLoader loader,
		Rectangle2D bounds,
		Optional<Vector2D> floatingPos,
		Optional<Vector2D> floatingOffset,
		Graphics2D g2d
	) {
		this.loader = loader;
		this.bounds = bounds;
		this.floatingPos = floatingPos;
		this.floatingOffset = floatingOffset;
		this.g2d = g2d;
	}
	
	@Override
	public void visitBishop(BishopModel piece) { drawPiece("Bishop", piece.getColor()); }

	@Override
	public void visitKing(KingModel piece) { drawPiece("King", piece.getColor()); }

	@Override
	public void visitQueen(QueenModel piece) { drawPiece("Queen", piece.getColor()); }

	@Override
	public void visitKnight(KnightModel piece) { drawPiece("Knight", piece.getColor()); }

	@Override
	public void visitPawn(PawnModel piece) { drawPiece("Pawn", piece.getColor()); }

	@Override
	public void visitRook(RookModel piece) { drawPiece("Rook", piece.getColor()); }
	
	private void drawPiece(String pieceName, PlayerColor color) {
		String colorName = (color == PlayerColor.BLACK) ? "black" : "white";
		BufferedImage img = loadPiece(colorName + pieceName + ".png");
		Optional<Vector2D> floatingTopLeft = floatingPos.flatMap(it -> floatingOffset.map(offs -> it.add(offs)));
		g2d.drawImage(
			img,
			floatingTopLeft.map(Vector2D::getX).orElse(bounds.getX1()).intValue(),
			floatingTopLeft.map(Vector2D::getY).orElse(bounds.getY1()).intValue(),
			(int) bounds.width(),
			(int) bounds.height(),
			null
		);
	}
	
	private BufferedImage loadPiece(String pieceName) {
		String relativePath = "/pieces/" + pieceName;
		return loader.load(pieceName, () -> new ResourceFile(relativePath));
	}
}
