package Chess;

import java.awt.geom.Point2D;

class Board {
	
	private Square[][] gameBoard = new Square[8][8];
	private static Point2D[][] pixelMap = new Point2D[8][8];
	static {
		for(int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
			pixelMap[i][j] = new Point2D.Double(20 + 100*i, 20 + 100*j);
			}
		}
	}
	public Board() {
		//set up empty spaces with squares with no piece on them
		for(int i = 2; i < 6; i++) {
			for (int j = 0; j < 8; j++) {
				gameBoard[j][i] = new Square();
			}
		}
		
		//set pieces
		
		//pawns
		for(int i = 0; i < 8; i++) {
			gameBoard[i][6] = new Square(new Pawn(Piece.Color.WHITE));
		}
		
		for(int i = 0; i < 8; i++) {
			gameBoard[i][1] = new Square(new Pawn(Piece.Color.BLACK));
		}
		
		//Rooks
		gameBoard[0][7] = new Square(new Rook(Piece.Color.WHITE));
		gameBoard[7][7] = new Square(new Rook(Piece.Color.WHITE));
		gameBoard[0][0] = new Square(new Rook(Piece.Color.BLACK));
		gameBoard[7][0] = new Square(new Rook(Piece.Color.BLACK));
		
		//Knights
		gameBoard[1][7] = new Square(new Knight(Piece.Color.WHITE));
		gameBoard[6][7] = new Square(new Knight(Piece.Color.WHITE));
		gameBoard[1][0] = new Square(new Knight(Piece.Color.BLACK));
		gameBoard[6][0] = new Square(new Knight(Piece.Color.BLACK));
		
		//Bishops
		gameBoard[2][7] = new Square(new Bishop(Piece.Color.WHITE));
		gameBoard[5][7] = new Square(new Bishop(Piece.Color.WHITE));
		gameBoard[2][0] = new Square(new Bishop(Piece.Color.BLACK));
		gameBoard[5][0] = new Square(new Bishop(Piece.Color.BLACK));
		
		//Queens
		gameBoard[3][7] = new Square(new Queen(Piece.Color.WHITE));
		gameBoard[3][0] = new Square(new Queen(Piece.Color.BLACK));
		
		//Kings
		gameBoard[4][7] = new Square(new King(Piece.Color.WHITE));
		gameBoard[4][0] = new Square(new King(Piece.Color.BLACK));
	}
	
	//Method for copying an existing board
	public Board(Board b) {
		Piece.Color c;
		for(int i = 1; i <= 8; i++) {
			for(int j = 1; j <= 8; j++) {
				if(b.getSquare(i, j).isOccupied()) {
					c = b.getSquare(i, j).getPiece().getColor();
					if(b.getSquare(i, j).getPiece() instanceof Pawn) {
						gameBoard[i-1][j-1] = new Square(new Pawn(c));
					}
					else if (b.getSquare(i, j).getPiece() instanceof Rook) {
						gameBoard[i-1][j-1] = new Square(new Rook(c));
					}
					else if (b.getSquare(i, j).getPiece() instanceof Knight) {
						gameBoard[i-1][j-1] = new Square(new Knight(c));
					}
					else if (b.getSquare(i, j).getPiece() instanceof Bishop) {
						gameBoard[i-1][j-1] = new Square(new Bishop(c));
					}
					else if (b.getSquare(i, j).getPiece() instanceof Queen) {
						gameBoard[i-1][j-1] = new Square(new Queen(c));
					}
					else if (b.getSquare(i, j).getPiece() instanceof King) {
						gameBoard[i-1][j-1] = new Square(new King(c));
					}
				}
				else {
					gameBoard[i-1][j-1] = new Square();
				}
			}
		}
	}
	public Square getSquare(int i, int j) {
		return gameBoard[i-1][j-1];
	}
	
	public static Point2D getPoint(int i, int j) {
		return pixelMap[i-1][j-1];
	}
}