package Chess;

import javax.swing.ImageIcon;

class Knight extends Piece {

	public Knight(Color color) {
		super.color = color;
		if(color == Color.WHITE) { 
			super.img = new ImageIcon("KnightWhite.png"); 
		}
		else { 
			super.img = new ImageIcon("KnightBlack.png"); 
		}
	}

	@Override
	public Boolean isValidMove(double fromX, double fromY, double toX, double toY, Boolean capture) {
		Boolean valid = false;
		
		double x = Math.abs(fromX - toX);
		double y = Math.abs(fromY - toY);
		double total = x + y;
		
		if((x > 0) && (y > 0) && (x < 3) && (y < 3) && total == 3) {
			valid = true;
		}
				
		return valid;
		
	}
	
}