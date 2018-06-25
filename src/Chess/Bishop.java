package Chess;

import javax.swing.ImageIcon;

class Bishop extends Piece {

	public Bishop(Color color) {
		super.color = color;
		if(color == Color.WHITE) { 
			super.img = new ImageIcon("BishopWhite.png"); 
		}
		else { 
			super.img = new ImageIcon("BishopBlack.png"); 
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
				
		return valid;
	}
	
	
}