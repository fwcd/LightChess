package com.fwcd.lightchess;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import com.fwcd.lightchess.model.ChessBoardModel;
import com.fwcd.lightchess.view.ChessBoardView;

public class LightChessMain {
	public static void main(String[] args) {
		JFrame frame = new JFrame("LightChess");
		frame.setSize(600, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(new ChessBoardView(ChessBoardModel.withInitialSetup()).getView());
		frame.setVisible(true);
	}
}
