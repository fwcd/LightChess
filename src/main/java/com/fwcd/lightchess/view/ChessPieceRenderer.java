package com.fwcd.lightchess.view;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

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
	private final Graphics2D g2d;
	private final Vector2D pos;
	private final int width;
	private final int height;
	
	public ChessPieceRenderer(
		ImageLoader loader,
		Graphics2D g2d,
		Vector2D pos,
		int width,
		int height
	) {
		this.loader = loader;
		this.g2d = g2d;
		this.pos = pos;
		this.width = width;
		this.height = height;
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
		g2d.drawImage(
			img,
			(int) pos.getX(),
			(int) pos.getY(),
			width,
			height,
			null
		);
	}
	
	private BufferedImage loadPiece(String pieceName) {
		String relativePath = "/pieces/" + pieceName;
		return loader.load(pieceName, () -> new ResourceFile(relativePath));
	}
}
