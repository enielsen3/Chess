package Chess;

class Square {
	
	private Piece piece;
	private Boolean isOccupied = false;
	
	public Square(Piece piece) {
		this.piece = piece;
		isOccupied = true;
	}
	
	public Square() {
		this.piece = null;
		isOccupied = false;
	}
	
	public void setPiece(Piece piece) {
		this.piece = piece;
		isOccupied = true;
	}
	
	public Piece getPiece() {
		return piece;
	}
	
	public Boolean isOccupied() {
		return isOccupied;
	}
	
	public void clearPiece() {
		piece = null;
		isOccupied = false;
	}
}