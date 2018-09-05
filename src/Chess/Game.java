package Chess;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.*;


class Game {
	
	public Board board;
	private Board bCopy;
	private Boolean whiteMove;
	private Boolean blackInCheck;
	private Boolean whiteInCheck;
	private Boolean checkmate;
	private ArrayList<Piece> removedPieces;
	private Boolean legalMove;
	private Boolean capture;
	private Piece moving;
	private Piece toRemove;
	private Piece inAir;
	
	
	public Game() {
		board = new Board();
		whiteMove = true;
		blackInCheck = false;
		whiteInCheck = false;
		checkmate = false;
		removedPieces = new ArrayList<>();
	}
	
	public void move(Point2D from, Point2D to) {
		//create a copy of the current board state
		Board bCopy = new Board(board);
		
		legalMove = true;
		capture = false;
		Point2D fromS = getXY(from);
		Point2D toS = getXY(to);
		moving = null;
		toRemove = null;
		
				
		// check that the original click was on a piece, if it was --> set moving = piece; otherwise return
		if(!board.getSquare((int) fromS.getX(), (int) fromS.getY()).isOccupied()) {
			return;
		}
		else {
			moving = board.getSquare((int) fromS.getX(), (int) fromS.getY()).getPiece();
		} 
		
		
		// check if a piece is being captured, if so set it to toRemove
		if(board.getSquare((int) toS.getX(), (int) toS.getY()).isOccupied()) {
			
			//check that a player isn't capturing their own piece
			if(moving.getColor() == board.getSquare((int) toS.getX(), (int) toS.getY()).getPiece().getColor()) {
				return;
			}
			else {
				toRemove = board.getSquare((int) toS.getX(), (int) toS.getY()).getPiece();
				capture = true;
			}
			
		}
		
		//check the move logic for the specific piece -- see individual piece classes for methods
		if(!moving.isValidMove(fromS.getX(), fromS.getY(), toS.getX(), toS.getY(), capture)) {
			return;
		}
		
		//turn check
		if(whiteMove != (moving.getColor() == Piece.Color.WHITE)) {
			return;
		}
		
		//collision logic check for all pieces except for knight
		if(!(moving instanceof Knight) && isBlocked(board, fromS, toS)) {
			return;
		}
		
		//completes the move in a temporary board
		bCopy.getSquare((int) toS.getX(), (int) toS.getY()).setPiece(moving);
		bCopy.getSquare((int) fromS.getX(), (int) fromS.getY()).clearPiece();
		
		//check that resulting board state is legal (not moving into check)
		if((whiteMove && detectCheck(bCopy) == Piece.Color.WHITE) || (!whiteMove && detectCheck(bCopy) == Piece.Color.BLACK)) {
			return;
		}
		
		//if white was in check, must move out of check
		if(whiteInCheck) {
			if(detectCheck(bCopy) == Piece.Color.WHITE) {
				return;
			}
		}
		
		// if black was in check, must move out of check
		if(blackInCheck) {
			if(detectCheck(bCopy) == Piece.Color.BLACK) {
				return;
			}
		
		}
		
		//if passed all move logic checks, complete the move, put the removed piece in removedPieces 
		if(legalMove) {
			board.getSquare((int) toS.getX(), (int) toS.getY()).setPiece(moving);
			board.getSquare((int) fromS.getX(), (int) fromS.getY()).clearPiece();
			
			//check if move created check state
			if(detectCheck(board) == Piece.Color.WHITE) {
				whiteInCheck = true;
				if(isCheckMate()) {
					//endOfGame();
				}
			}
			else if (detectCheck(board) == Piece.Color.BLACK) {
				blackInCheck = true;
				if(isCheckMate()) {
					//endOfGame();
				}
			}
			else {
				whiteInCheck = false;
				blackInCheck = false;
			}
			
			//change turns
			whiteMove = !whiteMove;
			//System.out.println(checkDetection(board));
			if(toRemove instanceof Piece) {
				removedPieces.add(toRemove);
			}
		}
		else {
			//board.getSquare((int) fromS.getX(), (int) fromS.getY()).setPiece(inAir);
		}
	}
	
	public Piece.Color detectCheck(Board b) {
		
		Point2D currentPt;
		Piece currentPiece;
		Point2D blackKingLoc = null;
		Point2D whiteKingLoc = null;
		
		
		//find kings on temp board b
		for(int i = 1; i <= 8; i++) {
			for(int j = 1; j <= 8; j++) {
				currentPt = new Point2D.Double(i, j);
				if(b.getSquare((int) currentPt.getX(), (int) currentPt.getY()).isOccupied()	&& 
						(b.getSquare((int) currentPt.getX(), (int) currentPt.getY()).getPiece() instanceof King)) {
					currentPiece = b.getSquare((int) currentPt.getX(), (int) currentPt.getY()).getPiece();
					if(currentPiece.color == Piece.Color.WHITE) {
						whiteKingLoc = currentPt;
					}
					else {
						blackKingLoc = currentPt;
					}
				
				}
			}
		}
		//check that each king is not in check
		for(int i = 1; i <= 8; i++) {
			for(int j = 1; j <= 8; j++) {
				currentPt = new Point2D.Double(i, j);
				if(b.getSquare((int) currentPt.getX(), (int) currentPt.getY()).isOccupied()) {
					currentPiece = b.getSquare((int) currentPt.getX(), (int) currentPt.getY()).getPiece();
					if(currentPiece.color == Piece.Color.WHITE) {
						if(currentPiece.isValidMove(currentPt.getX(), currentPt.getY(), blackKingLoc.getX(), blackKingLoc.getY(), true)) {
							if(!(currentPiece instanceof Knight) && !isBlocked(b, currentPt, blackKingLoc)) {
								return Piece.Color.BLACK;
							}
							else if (currentPiece instanceof Knight) {
								return Piece.Color.BLACK;
							}
						}
					}
					if(currentPiece.color == Piece.Color.BLACK) {
						if(currentPiece.isValidMove(currentPt.getX(), currentPt.getY(), whiteKingLoc.getX(), whiteKingLoc.getY(), true)) {
							if(!(currentPiece instanceof Knight) && !isBlocked(b, currentPt, whiteKingLoc)) {
								return Piece.Color.WHITE;
							}
							else if (currentPiece instanceof Knight) {
								return Piece.Color.WHITE;
							}
						}
					}
				}
				
			}
		}
		
		return null;
			
	}
	
	public Boolean isCheckMate() {
		return false;
	}
	
	public Boolean isBlocked(Board b, Point2D fromS, Point2D toS) {
		
		double x = toS.getX() - fromS.getX();
		double y = toS.getY() - fromS.getY();
			
		//if vertical move
		if(x == 0) {
			for(int i = 1; i < Math.abs(y); i++) {
				if(b.getSquare((int) fromS.getX(), (int) fromS.getY() + Integer.signum((int) y)*i).isOccupied()) {
					return true;
				}
			}
		}
				
		//if horizontal move 
		else if(y == 0) {
			for(int i = 1; i < Math.abs(x); i++) {
				if(b.getSquare((int) fromS.getX() + Integer.signum((int) x)*i, (int) fromS.getY()).isOccupied()) {
					return true;
				}
			}
		}
				
		//if diagonal move
		else {
			for(int i = 1; i < Math.abs(x); i++) {
				if(b.getSquare((int) fromS.getX() + Integer.signum((int) x)*i, (int) fromS.getY() + Integer.signum((int) y)*i).isOccupied()) {
					return true;
				}
			}
		}
		
		return false;
	}
	/*
	//unused at the moment, might use for piece in air
	public Boolean eligibleToMove(Point2D from) {
		Point2D fromS = getXY(from);
		inAir = null;
		
		// check that the original click was on a piece, if it was --> set moving = piece; otherwise return false
		if(!board.getSquare((int) fromS.getX(), (int) fromS.getY()).isOccupied()) {
			return false;
		}
		else {
			inAir = board.getSquare((int) fromS.getX(), (int) fromS.getY()).getPiece();
		}
		
		//turn check
		if(whiteMove != (inAir.getColor() == Piece.Color.WHITE)) {
			return false;
		}
		return true;
	}
	
	
	//unused at the moment, might use for piece in air
	//used to "Pick up" a piece from the board and drag it with mouse
	public Piece pickUpPiece(Point2D from) {
		Point2D fromS = getXY(from);
		board.getSquare((int) fromS.getX(), (int) fromS.getY()).clearPiece();
		return inAir;
	}
	*/
	private Point2D getXY(Point2D pixels) {
		//takes input of 2D point of pixels --> (356, 478)
		//changes point to "simple" point --> (4,5)
		Point2D xy = new Point2D.Double();
		xy.setLocation(Math.ceil(pixels.getX() / 100), Math.ceil(pixels.getY() / 100));
		return xy;
	}
	
	public static void main(String[] args) {
		
		
		EventQueue.invokeLater(() ->
        {
           Game game = new Game();
           JFrame frame = new DrawFrame(game);
           frame.setTitle("Chess");
           frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
           frame.setVisible(true);
         });
	}
	
}




