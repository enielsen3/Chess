package Chess;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JComponent;


@SuppressWarnings("serial")
class DrawBoard extends JComponent {
   
	private static final int DEFAULT_WIDTH = 800;
	private static final int DEFAULT_HEIGHT = 800;
	private Game game;
	private Point2D point;
	private Point2D newPoint;
	private Piece pieceInAir;
	private int airX;
	private int airY;
	
	public DrawBoard(Game game) {
		this.game = game;
		addMouseListener(new MouseHandler());
	    addMouseMotionListener(new MouseMotionHandler());
	}
	
	public void paintComponent(Graphics g) {
		
		Graphics2D g2 = (Graphics2D) g;
		final double BOXDIM = 100;
				
		//Create the board background
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j ++) {
				if(j % 2 == i % 2 ) {
					g2.setPaint(new Color(255,206,158));
				}
				else {
					g2.setPaint(new Color(209,139,71));
				}
				Rectangle2D rect = new Rectangle2D.Double(BOXDIM*i, BOXDIM*j, BOXDIM, BOXDIM);
				g2.fill(rect);
			}
		}
		
		//Draw the pieces on the board
		for(int i = 1; i <= 8; i++) {
			for(int j = 1; j <= 8; j++) {
				if(game.board.getSquare(i, j).isOccupied()) {
				   g2.drawImage(game.board.getSquare(i, j).getPiece().img.getImage(), 
							(int) Board.getPoint(i, j).getX(), (int) Board.getPoint(i, j).getY(), null); 
				}
			}
		}
		
		//Draw a moving piece (if there is one)
		if(pieceInAir != null) {
			g2.drawImage(pieceInAir.img.getImage(), airX, airY, null);
		}
		
				
	}	
   
   public Dimension getPreferredSize() { return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT); }
   
   private class MouseHandler extends MouseAdapter
   {
	   public void mousePressed(MouseEvent press) {
		   // what happens when mouse is pressed 
		   point = press.getPoint();
		//   pieceInAir = null;
		          
	   }

	   public void mouseReleased(MouseEvent release) {
		   newPoint = release.getPoint();
		   if(newPoint.getX() <= 800 && newPoint.getY() <= 800) {
			   game.moveLogic(point, newPoint);
			   repaint();
		   }
		   
	   }
	   

	   public void mouseClicked(MouseEvent event) {
         /* remove the current square if double clicked
         current = find(event.getPoint());
         if (current != null && event.getClickCount() >= 2) remove(current); */
	   }
   }

   private class MouseMotionHandler implements MouseMotionListener
   {
      public void mouseMoved(MouseEvent event)
      {
         /* set the mouse cursor to cross hairs if it is inside
         // a rectangle

         if (find(event.getPoint()) == null) setCursor(Cursor.getDefaultCursor());
         else setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR)); */
      }

      public void mouseDragged(MouseEvent event)
      {
    	  //ensure the piece is eligible to move before picking it up
	/*	   if(game.eligibleToMove(point)) {
			   airX = event.getX();
			   airY = event.getY();
			   pieceInAir = game.pickUpPiece(point);
			   repaint();
		   }
         */
            
         }
      }
   
  
}