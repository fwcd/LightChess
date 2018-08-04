package com.fwcd.lightchess;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import com.fwcd.lightchess.controller.ChessBoardController;
import com.fwcd.lightchess.model.ChessBoardModel;
import com.fwcd.lightchess.view.ChessBoardView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LightChessMain {
	private static final Logger LOG = LoggerFactory.getLogger(LightChessMain.class);
	
	public static void main(String[] args) {
		ChessBoardModel boardModel = ChessBoardModel.withInitialSetup();
		ChessBoardController boardController = new ChessBoardController(boardModel);
		
		LOG.info("Launching application");
		JFrame frame = new JFrame("LightChess");
		frame.setSize(750, 750);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(boardController.getViewComponent());
		frame.setVisible(true);
	}
}
