package com.fwcd.lightchess.view;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.Optional;

import com.fwcd.fructose.geometry.Rectangle2D;
import com.fwcd.fructose.geometry.Vector2D;
import com.fwcd.lightchess.model.piece.ChessPieceModel;

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
