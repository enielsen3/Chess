package Chess;

import javax.swing.JFrame;

@SuppressWarnings("serial")
class DrawFrame extends JFrame {
   
	public DrawFrame(Game game) {    
		
      add(new DrawBoard(game));
      
     // add(new MouseComponent());
      pack();
   }
	
	
}