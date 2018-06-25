package Chess;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.*;


class Game {
	
	public Board board;
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
	//private	Point2D blackKingLoc;
	//private	Point2D whiteKingLoc;
	
	public Game() {
		board = new Board();
		whiteMove = true;
		blackInCheck = false;
		whiteInCheck = false;
		checkmate = false;
		//blackKingLoc = new Point2D.Double(5, 1);
		//whiteKingLoc = new Point2D.Double(5, 8);
		removedPieces = new ArrayList<>();
	}
	
	public void moveLogic(Point2D from, Point2D to) {
		//change points to "simple" points --> (1,1) to (8,8)
		legalMove = true;
		capture = false;
		Point2D fromS = simplePoint(from);
		Point2D toS = simplePoint(to);
		moving = null;
		toRemove = null;
		
				
		// check that the original click was on a piece, if it was --> set moving = piece; otherwise return false
		if(!board.getSquare((int) fromS.getX(), (int) fromS.getY()).isOccupied()) {
			legalMove = false;
		}
		else {
			moving = board.getSquare((int) fromS.getX(), (int) fromS.getY()).getPiece();
		} 
		
		
		// check if a piece is being captured, if so set it to toRemove
		if(board.getSquare((int) toS.getX(), (int) toS.getY()).isOccupied()) {
			
			//check that a player isn't capturing their own piece
			if(moving.getColor() == board.getSquare((int) toS.getX(), (int) toS.getY()).getPiece().getColor()) {
				legalMove = false;
			}
			else {
				toRemove = board.getSquare((int) toS.getX(), (int) toS.getY()).getPiece();
				capture = true;
			}
			
		}
		
		//check the move logic for the specific piece -- see individual piece classes for methods
		if(!moving.isValidMove(fromS.getX(), fromS.getY(), toS.getX(), toS.getY(), capture)) {
			legalMove = false;
		}
		
		//turn check
		if(whiteMove != (moving.getColor() == Piece.Color.WHITE)) {
			legalMove = false;
		}
		
		//collision logic check for all pieces except for knight
		if(!(moving instanceof Knight) && piecesInBetween(fromS, toS)) {
			legalMove = false;
		}
		
		//completes the move in a temporary board, checks for illegal check state
		
		
		//if passed all move logic checks, complete the move, put the removed piece in removedPieces 
		if(legalMove) {
			board.getSquare((int) toS.getX(), (int) toS.getY()).setPiece(moving);
			board.getSquare((int) fromS.getX(), (int) fromS.getY()).clearPiece();
			//update location of King if needed
		/*	if (moving instanceof King) {
				if(whiteMove) {
					whiteKingLoc = toS;
				}
				else {
					blackKingLoc = toS;
				}
			} */
			//change turns
			whiteMove = !whiteMove;
			if(toRemove instanceof Piece) {
				removedPieces.add(toRemove);
			}
		}
		else {
			//board.getSquare((int) fromS.getX(), (int) fromS.getY()).setPiece(inAir);
		}
	}
	
	public Boolean[] checkDetection(Board b) {
		
		Point2D currentPt;
		Piece currentPiece;
		Point2D blackKingLoc = null;
		Point2D whiteKingLoc = null;
		Boolean[] result = new Boolean[2];
		
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
							if(!(currentPiece instanceof Knight) && piecesInBetween(currentPt, blackKingLoc)) {
								blackInCheck = true;
							}
						}
					}
					if(currentPiece.color == Piece.Color.BLACK) {
						if(currentPiece.isValidMove(currentPt.getX(), currentPt.getY(), whiteKingLoc.getX(), whiteKingLoc.getY(), true)) {
							if(!(currentPiece instanceof Knight) && piecesInBetween(currentPt, whiteKingLoc)) {
								whiteInCheck = true;
							}
						}
					}
				}
				
			}
		}
		
		return result;
			
	}
	public Boolean piecesInBetween(Point2D fromS, Point2D toS) {
		
		double x = toS.getX() - fromS.getX();
		double y = toS.getY() - fromS.getY();
			
		//if vertical move
		if(x == 0) {
			for(int i = 1; i < Math.abs(y); i++) {
				if(board.getSquare((int) fromS.getX(), (int) fromS.getY() + Integer.signum((int) y)*i).isOccupied()) {
					return true;
				}
			}
		}
				
		//if horizontal move 
		else if(y == 0) {
			for(int i = 1; i < Math.abs(x); i++) {
				if(board.getSquare((int) fromS.getX() + Integer.signum((int) x)*i, (int) fromS.getY()).isOccupied()) {
					return true;
				}
			}
		}
				
		//if diagonal move
		else {
			for(int i = 1; i < Math.abs(x); i++) {
				if(board.getSquare((int) fromS.getX() + Integer.signum((int) x)*i, (int) fromS.getY() + Integer.signum((int) y)*i).isOccupied()) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	public Boolean eligibleToMove(Point2D from) {
		Point2D fromS = simplePoint(from);
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
	
	//used to "Pick up" a piece from the board and drag it with mouse
	public Piece pickUpPiece(Point2D from) {
		Point2D fromS = simplePoint(from);
		board.getSquare((int) fromS.getX(), (int) fromS.getY()).clearPiece();
		return inAir;
	}
	
	private Point2D simplePoint(Point2D pixels) {
		//takes input of 2D point of pixels --> (356, 478)
		//changes point to "simple" point --> (4,5)
		Point2D simple = new Point2D.Double();
		simple.setLocation(Math.ceil(pixels.getX() / 100), Math.ceil(pixels.getY() / 100));
		return simple;
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




