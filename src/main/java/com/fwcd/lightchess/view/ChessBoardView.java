package com.fwcd.lightchess.view;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JComponent;
import javax.swing.JPanel;

import com.fwcd.fructose.geometry.Rectangle2D;
import com.fwcd.fructose.geometry.Vector2D;
import com.fwcd.fructose.swing.RenderPanel;
import com.fwcd.fructose.swing.Viewable;
import com.fwcd.lightchess.model.ChessBoardModel;
import com.fwcd.lightchess.model.ChessPosition;
import com.fwcd.lightchess.utils.ChessConstants;

public class ChessBoardView implements Viewable {
	private final ChessBoardModel model;
	private final ChessFieldView[][] fields;
	private final ChessBoardTheme theme = ChessBoardTheme.WOODEN;
	private final JPanel view;
	
	public ChessBoardView(ChessBoardModel model) {
		this.model = model;
		view = new RenderPanel(this::render);
		fields = new ChessFieldView[ChessConstants.RANKS][ChessConstants.FILES];
		
		boolean isDark = false;
		for (int y=0; y<ChessConstants.RANKS; y++) {
			for (int x=0; x<ChessConstants.FILES; x++) {
				fields[y][x] = new ChessFieldView(model.fieldAt(ChessPosition.at(x, y)), theme, isDark);
				isDark = !isDark;
			}
			isDark = !isDark;
		}
	}
	
	private void render(Graphics2D g2d, Dimension canvasSize) {
		int fieldWidth = canvasSize.width / fields[0].length;
		int fieldHeight = canvasSize.height / fields.length;
		
		for (int y=0; y<fields.length; y++) {
			for (int x=0; x<fields[y].length; x++) {
				ChessFieldView field = fields[y][x];
				Vector2D pos = new Vector2D(x * fieldWidth, y * fieldHeight);
				field.setBounds(new Rectangle2D(pos, fieldWidth, fieldHeight));
				field.render(g2d, canvasSize);
			}
		}
	}
	
	@Override
	public JComponent getView() { return view; }
}
