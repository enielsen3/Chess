package Chess;

import javax.swing.ImageIcon;

public abstract class Piece {
	public enum Color {WHITE, BLACK};
	public Color color;
	public ImageIcon img;
	
	public Piece() {
		
	}
	
	public abstract Boolean isValidMove(double fromX, double fromY, double toX, double toY, Boolean capture);
	
	public Color getColor() {
		return color;
	}
	
	
}