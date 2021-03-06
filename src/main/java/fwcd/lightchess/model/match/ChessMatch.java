package fwcd.lightchess.model.match;

import fwcd.fructose.Option;

import fwcd.fructose.EventListenerList;
import fwcd.lightchess.model.ChessBoardModel;
import fwcd.lightchess.model.ChessMove;
import fwcd.lightchess.model.PlayerColor;
import fwcd.lightchess.model.piece.ChessPieceModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChessMatch {
	private static final Logger LOG = LoggerFactory.getLogger(ChessMatch.class);
	private final ChessBoardModel board;
	private final ChessPlayer white;
	private final ChessPlayer black;
	private EventListenerList<ChessMatchResult> resultListeners = new EventListenerList<>();
	private boolean started = false;
	private boolean gameOver = false;
	private boolean whiteHasTurn = true;
	
	public ChessMatch(ChessBoardModel board, ChessPlayer white, ChessPlayer black) {
		this.board = board;
		this.white = white;
		this.black = black;
	}
	
	public ChessMatchResult play() throws InterruptedException {
		if (started) {
			throw new IllegalStateException("Can't play an already started ChessMatch");
		}
		started = true;
		
		while (!board.isGameOver()) {
			LOG.info("{}'s turn", (whiteHasTurn ? "White" : "Black"));
			ChessMove move;
			if (whiteHasTurn) {
				move = white.pickMove(PlayerColor.WHITE, board);
			} else {
				move = black.pickMove(PlayerColor.BLACK, board);
			}
			board.performMove(move);
			whiteHasTurn = !whiteHasTurn;
		}
		
		Option<PlayerColor> checkmate = board.getCheckmate().map(ChessPieceModel::getColor);
		Option<PlayerColor> stalemate = board.getStalemate();
		PlayerColor winner;
		ChessResultType resultType;
		
		if (checkmate.isPresent()) {
			winner = checkmate.orElse(null).opponent();
			resultType = ChessResultType.CHECKMATE;
		} else if (stalemate.isPresent()) {
			winner = stalemate.orElse(null).opponent();
			resultType = ChessResultType.STALEMATE;
		} else {
			throw new IllegalStateException("Invalid match result: Neither a checkmate nor a stalemate");
		}
		
		gameOver = true;
		ChessMatchResult result = new ChessMatchResult(winner, resultType);
		resultListeners.fire(result);
		
		return result;
	}
	
	public boolean isGameOver() { return gameOver; }
	
	public void setResultListeners(EventListenerList<ChessMatchResult> resultListeners) {
		this.resultListeners = resultListeners;
	}
}
