import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

//Daniel Song
//Program Description: 2048 Game Runner

//This class is just calling on the code; director
public class GUI_2048 extends JPanel implements KeyListener
{
   private final int PREF_W = 440;
   private final int PREF_H = 410;
   
   private Board2048 gameboard;
   
   public GUI_2048()
   {
      System.out.println("Constructing the panel.");
      this.addKeyListener(this);
      this.setFocusable(true);
      gameboard = new Board2048();
      gameboard.showBoard();
      
   }
   
   
   public void paintComponent (Graphics g)
   {
      super.paintComponent(g);
      Graphics2D g2 = (Graphics2D) g;
      g2.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON));
     // g2.setBackground(new Color(250, 249, 238));
      gameboard.drawBoard(g2);
      
      if(gameboard.isGameOver())
         gameboard.drawGameOverMessage(g2);
      
      
      g2.setColor(Color.BLUE);
      gameboard.sideFeatures(g2);
   
   }
   
   /** Code to create the frame to hold the Panel **/
   
   public Dimension getPreferredSize()
   {
      return new Dimension(PREF_W+200, PREF_H);
   }

   public static void createAndShowGUI()
   {
      JOptionPane.showMessageDialog(null, 
            "Welcome to Daniel's 2048 Game!", 
            "2048", 
            JOptionPane.INFORMATION_MESSAGE
            );
      JOptionPane.showMessageDialog(null, 
            "Prees any arrows keys to start and play the game.\nHave Fun!", 
            "2048", 
            JOptionPane.INFORMATION_MESSAGE
            );
//      JOptionPane.showMessageDialog(null, 
//            "Welcome to Daniel's 2048 Game!", 
//            "2048", 
//            JOptionPane.CLOSED_OPTION
//            );
      JFrame frame = new JFrame("Panel Title");
      JPanel gamePanel = new GUI_2048();
      
      frame.getContentPane().add(gamePanel);
      frame.pack();
      frame.setLocationRelativeTo(null);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
   }
   

   public static void main(String[] args)
   {
     
      
      SwingUtilities.invokeLater(new Runnable()
      {
         public void run()
         {
            createAndShowGUI();
         }
      });
   }

//Key Listener Methods
   @Override
   public void keyTyped(KeyEvent e)
   {
     // System.out.println("Key Typed");
   }


   @Override
   public void keyPressed(KeyEvent e)
   {
      int key = e.getKeyCode();
      if(key == KeyEvent.VK_UP)
         if(gameboard.canMoveUp()) {
            gameboard.moveUp();
            gameboard.spawnValue(2);
         }
      if(key == KeyEvent.VK_DOWN)
         if(gameboard.canMoveDown()) {
            gameboard.moveDown();
            gameboard.spawnValue(2);
         }
      if(key == KeyEvent.VK_LEFT)
         if(gameboard.canMoveLeft()) {
            gameboard.moveLeft();
            gameboard.spawnValue(2);
         }
      if(key == KeyEvent.VK_RIGHT)
         if(gameboard.canMoveRight()) {
            gameboard.moveRight();
            gameboard.spawnValue(2);
         }
      if(key == KeyEvent.VK_R && gameboard.isGameOver())
         gameboard.resetGame();
     
      System.out.println();
      gameboard.showBoard();
      repaint();              
   }


   @Override
   public void keyReleased(KeyEvent e)
   {
     // System.out.println("Key Released");
   }
   
}
