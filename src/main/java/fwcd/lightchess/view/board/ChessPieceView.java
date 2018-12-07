package fwcd.lightchess.view.board;

import java.awt.Graphics2D;

import fwcd.fructose.geometry.Vector2D;
import fwcd.lightchess.model.piece.ChessPieceModel;
import fwcd.lightchess.view.ImageLoader;

public class ChessPieceView {
	private final ChessPieceModel model;
	private final ImageLoader imageLoader;
	
	public ChessPieceView(ChessPieceModel model, ImageLoader imageLoader) {
		this.model = model;
		this.imageLoader = imageLoader;
	}
	
	public void renderAt(Graphics2D g2d, Vector2D pos, int width, int height) {
		model.accept(new ChessPieceRenderer(imageLoader, g2d, pos, width, height));
	}
	
	public ChessPieceModel getModel() { return model; }
}
