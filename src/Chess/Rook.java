package Chess;

import javax.swing.ImageIcon;

class Rook extends Piece {

	public Rook(Color color) {
		super.color = color;
		if(color == Color.WHITE) { 
			super.img = new ImageIcon("RookWhite.png"); 
		}
		else { 
			super.img = new ImageIcon("RookBlack.png"); 
		}
	}

	@Override
	public Boolean isValidMove(double fromX, double fromY, double toX, double toY, Boolean capture) {
		Boolean valid = false;
				
		if(fromX == toX || fromY == toY) {
			valid = true;
		}
				
		return valid;
		
	}
	
}