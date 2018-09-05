package Chess;

import javax.swing.ImageIcon;

class Pawn extends Piece {

	public Pawn(Color color) {
		super.color = color;
		if(color == Color.WHITE) { 
			super.img = new ImageIcon("images/PawnWhite.png"); 
		}
		else { 
			super.img = new ImageIcon("images/PawnBlack.png"); 
		}
	}

	@Override
	public Boolean isValidMove(double fromX, double fromY, double toX, double toY, Boolean capture) {
		Boolean valid = false;
		
		double x = toX - fromX;
		double y = toY - fromY;
				
		//white
		if(super.getColor() == Color.WHITE) {
			//can move 1 or 2 spaces forward if at starting position
			if ( (fromY == 7) && (y < 0) && (x == 0) && (y > -3) && (!capture)) {
				valid = true;
			}
			//can move one space forward from any position
			if((y == -1) && (x == 0) && (!capture)) {
				valid = true;
			}
			//can move forward diagonally if capture is occurring
			if(capture && (Math.abs(x) == 1) && (y == -1)) {
				valid = true;
			}
			
		}
		
		//black
		if(super.getColor() == Color.BLACK) {
			//can move 1 or 2 spaces forward if at starting position
			if ( (fromY == 2) && (y > 0) && (x == 0) && (y < 3) && (!capture)) {
				valid = true;
			}
			//can move one space forward from any position
			if((y == 1) && (x == 0) && (!capture)) {
				valid = true;
			}
			//can move forward diagonally if capture is occurring
			if(capture && (Math.abs(x) == 1) && (y == 1)) {
				valid = true;
			}
					
		}
				
		return valid;
	}
	
}