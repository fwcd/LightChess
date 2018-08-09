package com.fwcd.lightchess.model.ai;

import java.util.Objects;

import com.fwcd.lightchess.model.ChessBoardModel;
import com.fwcd.lightchess.model.ChessMove;
import com.fwcd.lightchess.model.PlayerColor;
import com.fwcd.lightchess.model.match.ChessPlayer;
import com.fwcd.lightchess.model.piece.ChessPieceModel;

public class AlphaBetaPlayer implements ChessPlayer {
	private static final double LARGE_NUMBER = 100000000D;
	private final int depth;
	
	public AlphaBetaPlayer(int depth) {
		this.depth = depth;
	}
	
	@Override
	public ChessMove pickMove(PlayerColor me, ChessBoardModel board) {
		double bestRating = Double.NEGATIVE_INFINITY;
		ChessMove bestMove = null;
		PlayerColor opponent = me.opponent();
		Iterable<ChessMove> childMoves = board.getPossibleMovesFor(me)::iterator;
		
		for (ChessMove move : childMoves) {
			ChessBoardModel nextBoard = board.spawnChild(move);
			double rating = alphaBeta(me, opponent, false, nextBoard, depth, bestRating, Double.POSITIVE_INFINITY);
			if ((rating > bestRating) || (bestMove == null)) {
				bestRating = rating;
				bestMove = move;
			}
		}
		
		return Objects.requireNonNull(bestMove, "The AlphaBeta search could not find any moves");
	}
	
	private double evaluateLeaf(PlayerColor me, ChessBoardModel board) {
		int myRating = board.piecesOfColor(me)
			.mapToInt(ChessPieceModel::getValue)
			.sum();
		int opponentRating = board.piecesOfColor(me.opponent())
			.mapToInt(ChessPieceModel::getValue)
			.sum();
		return myRating - opponentRating;
	}
	
	private double alphaBeta(PlayerColor me, PlayerColor current, boolean maximizing, ChessBoardModel board, int decrementalDepth, double alpha, double beta) {
		if (decrementalDepth <= 0) {
			return evaluateLeaf(me, board);
		} else if (board.isGameOver()) {
			boolean winning = board.getWinner().filter(it -> it.equals(me)).isPresent();
			int incrementalDepth = depth - decrementalDepth;
			if (winning) {
				return LARGE_NUMBER - incrementalDepth;
			} else {
				return -LARGE_NUMBER + incrementalDepth;
			}
		} else {
			double bestRating = (maximizing ? alpha : beta);
			Iterable<ChessMove> childMoves = board.getPossibleMovesFor(current)::iterator;
			
			for (ChessMove move : childMoves) {
				ChessBoardModel nextBoard = board.spawnChild(move);
				double rating;
				if (maximizing) {
					rating = alphaBeta(me, current.opponent(), !maximizing, nextBoard, decrementalDepth - 1, bestRating, beta);
					if (rating > bestRating) {
						bestRating = rating;
						if (rating >= beta) {
							break; // Beta-cutoff
						}
					}
				} else {
					rating = alphaBeta(me, current.opponent(), !maximizing, nextBoard, decrementalDepth - 1, alpha, bestRating);
					if (rating < bestRating) {
						bestRating = rating;
						if (rating <= alpha) {
							break; // Alpha-cutoff
						}
					}
				}
			}
			
			return bestRating;
		}
	}
}
