package Chess;

import javax.swing.ImageIcon;

class Queen extends Piece {

	public Queen(Color color) {
		super.color = color;
		if(color == Color.WHITE) { 
			super.img = new ImageIcon("images/QueenWhite.png"); 
		}
		else { 
			super.img = new ImageIcon("images/QueenBlack.png"); 
		}
		
	}

	@Override
	public Boolean isValidMove(double fromX, double fromY, double toX, double toY, Boolean capture) {
		Boolean valid = false;
		double x = Math.abs(fromX-toX);
		double y = Math.abs(fromY-toY);
		if(x == y) {
			valid = true;
		}
		else if(fromX == toX || fromY == toY) {
			valid = true;
		}
				
		return valid;
		
	}
	
	
}