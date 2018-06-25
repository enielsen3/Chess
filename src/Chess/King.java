package Chess;

import javax.swing.ImageIcon;

class King extends Piece {
		
	public King(Color color) {
		super.color = color;
		if(color == Color.WHITE) { 
			super.img = new ImageIcon("KingWhite.png"); 
		}
		else { 
			super.img = new ImageIcon("KingBlack.png"); 
		}
		
	}

	@Override
	public Boolean isValidMove(double fromX, double fromY, double toX, double toY, Boolean capture) {
		Boolean onespace = false;
		
		double x = Math.abs(fromX-toX);
		double y = Math.abs(fromY-toY);
		if(x <= 1 && y<=1) {
			onespace = true;
		}
				
		return onespace;
	}
	
}