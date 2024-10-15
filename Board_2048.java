import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

//Daniel Song
//Program Description
//May 18, 2021

public class Board_2048
{
   //Instance Variables
   private Tile_2048[][] board;
   private int score;
   private int highestScore;
   
   
   //Constructor - giving variables a value
   public Board_2048()
   {
      board = new Tile_2048[4][4];
      //initialize all tile values to zero
      for(int r = 0; r < board.length; r++)
         for(int c = 0; c < board[r].length; c++)
               board[r][c] = new Tile_2048(0);
      
      
      
  
     spawnValue(2);
     spawnValue(2);      

   }
   
   
   
   //Methods 
   public void drawBoard(Graphics2D g2) //tiles values
   {
      int w = 100;
      int h = 100;
      for(int r = 0; r < board.length; r++)
         for(int c = 0; c < board[r].length; c++)
            board[r][c].draw2(g2, c*w, r*h, w, h); 
   
   }
   
   public void resetGame()
   {
      
     // System.out.println("OOOO");
      for(int r = 0; r < board.length; r++)
         for(int c = 0; c < board[r].length; c++)
            board[r][c].setValue(0);
      
     
  	    if (score > highestScore) {
  	        highestScore = score;
  	    }
  	
  	    score = 0;
  	  
      
      spawnValue(2);
      spawnValue(2);
   }
   
   public boolean isGameOver()
   {
      return !canMoveLeft() && !canMoveRight() && !canMoveUp() && !canMoveDown();
   }
   
   public void newScore(Graphics2D g2) {
	    g2.setColor(Color.BLACK);
	    g2.setFont(new Font("Arial", Font.BOLD, 24));
	    g2.drawString("Score: " + score, 10, 30);
	    g2.drawString("Highest Score: " + highestScore, 130, 250);
	}
   
   	
   
   public void drawGameOverMessage(Graphics2D g2)
   {
      g2.setColor(new Color(255, 255, 255, 200));
      g2.drawRect(25,170,350,85);
      g2.setColor(Color.BLUE);
      g2.setFont(new Font("Cooper Black", Font.PLAIN, 30));
      g2.drawString("GAME OVER!",100,200);
      g2.setFont(new Font("Cooper Black", Font.PLAIN, 16));
      g2.drawString("Press 'R' to reset",130,220);
	    g2.drawString("Highest Score: " + highestScore, 130, 250);

      
      
      
   }
   
   public void sideFeatures(Graphics2D g2)
   {
      g2.setColor(new Color(119, 110, 101));
      g2.setFont(new Font("Cooper Black", Font.BOLD, 50));
     // g2.drawString("2048", 410, 100);
      FontMetrics fm = g2.getFontMetrics();
      int width = fm.stringWidth(score + "");
      g2.setFont(new Font("Cooper Black", Font.BOLD, 25));
      g2.drawString("Score: " + score, 600, 750);
      g2.setFont(new Font("Cooper Black", Font.BOLD, 20));
//      g2.drawString("HOW TO PLAY: ", 410, 300);
//      g2.setFont(new Font("Cooper Black", Font.BOLD, 16));
//      g2.drawString("Use your arrow keys to ", 410, 320);
//      g2.drawString("move the tiles. Tiles ", 410, 340);
//      g2.drawString("with the same number ", 410, 360);
//      g2.drawString("can merge into one when ", 410, 380);
//      g2.drawString("they touch. ", 410, 400);
   }
   
   public void showBoard()
   {
      for(int r = 0; r < board.length; r++)
      {
         for(int c = 0; c < board[r].length; c++)
            System.out.print(board[r][c].getValue() + " ");
         System.out.println();
      }
      System.out.println();
   }
   
   public void moveLeft() {
	    boolean[][] combined = new boolean[board.length][board[0].length];
	    boolean hasMoved = false; // Track if any tiles were moved or merged

	    for (int r = 0; r < board.length; r++) {
	        for (int c = 1; c < board[0].length; c++) { // Start from the second column
	            if (board[r][c].getValue() != 0) { // If not a zero
	                //col is ... the column to the left of c with the first non-zero value to its left
	                int col = c;
	                while (col - 1 >= 0 && board[r][col - 1].getValue() == 0) {
	                    col--;  // column should decrease 
	                }

	                //if col makes it to column 0 then move to that column
	                if (col == 0) {
	                    board[r][col].setValue(board[r][c].getValue());
	                    board[r][c].setValue(0);
	                    hasMoved = true; 
	                }
	                //if col is not the first column, see if col can combine with left neighbor
	                else if (!combined[r][col - 1] && board[r][col - 1].getValue() == board[r][c].getValue()) {
	                    int addScore = board[r][col - 1].getValue() + board[r][c].getValue();
	                    board[r][col - 1].setValue(addScore);                
	                    board[r][c].setValue(0);
	                    combined[r][col - 1] = true;
	                    score += addScore; // Update score
	                    hasMoved = true; 
	                }
	                // System.out.println("Combining tiles");

	                //tile moved but cannot combine with left neighbor so place it where it stopped
	                else if (c != col) {
	                    board[r][col].setValue(board[r][c].getValue());
	                    board[r][c].setValue(0);
	                    hasMoved = true;
	                }
	            }
	        }
	    }

	    // update the highest score if any move was made
	    if (hasMoved) {
	        updateHighestScore();
	        spawnValue(2); // Spawn a new tile after the move
	    }
	}
   public void updateHighestScore() {
	    if (score > highestScore) {
	        highestScore = score; // Update highest score
	    }
	}

   public void moveRight() 
   {
      boolean[][] combined = new boolean[board.length][board[0].length];
      for(int r = 0; r < board.length; r++)
      {
         for(int c = board[r].length-2; c >= 0; c--) // if this is zero
         {
            if(board[r][c].getValue() !=0) // if not a zero
            {
               //col is ... the column to the left of c with the first non-zero value to its left
               int col = c;
               while(col+1 <= board.length-1 && board[r][col+1].getValue()==0) 
                  col++; // column should decrease         
                  
               //if col makes it to the last column then move to that column
               if (col == board.length-1)
               {
                  board[r][col].setValue(board[r][c].getValue()); 
                  board[r][c].setValue(0);
                //  System.out.println("Maximum Move");
               }
               //if col is not the last column, see if col can combine with right neighbor
               else if(!combined[r][col+1] && board[r][col+1].getValue() == board[r][c].getValue())
               {
                  int addScore = board[r][col+1].getValue()+board[r][c].getValue();
                  board[r][col+1].setValue(board[r][col+1].getValue()+board[r][c].getValue());
                  board[r][c].setValue(0);
                  combined[r][col+1] = true;
                  score+=addScore;
                  

                  }
                  
                  
                  
               //   System.out.println("Combining tiles");
               
               //tile moved but cannot combine with right neighbor so place it where it stopped
               else if(c != col)
               {
                  board[r][col].setValue(board[r][c].getValue());
                  board[r][c].setValue(0);
                //  System.out.println("Move but not combined");
               }
                 
            }
         }
      }//end of c loop
   }//end of r loop
   
   public void moveUp() 
   {
      boolean[][] combined = new boolean[board.length][board[0].length];
      for(int c = 0; c < board[0].length; c++)
      {
         for(int r = 1; r < board[0].length; r++) // if this is zero
         {
            if(board[r][c].getValue() !=0) // if not a zero
            {
               //row is ... the row above r with the first non-zero value above it
               int row = r;
               while(row-1 >= 0 && board[row-1][c].getValue()==0) 
                  row--;
               //if row makes it to row 0 then move to that row
               if (row == 0)
               {
                  board[row][c].setValue(board[r][c].getValue());
                  board[r][c].setValue(0);
                //  System.out.println("Maximum Move");
               }
               //if col is not the first column, see if col can combine with left neighbor x
               else if(!combined[row-1][c] && board[row-1][c].getValue() == board[r][c].getValue())
               {
                  int addScore = board[row-1][c].getValue()+board[r][c].getValue();
                  board[row-1][c].setValue(board[row-1][c].getValue()+board[r][c].getValue());
                  board[r][c].setValue(0);
                  combined[row-1][c] = true;
                  score+=addScore;
                  
                  
                  
                  
                 // System.out.println("Combining tiles");
               }
               //tile moved but cannot combine with top neighbor so place it where it stopped
               else if(r != row)
               {
                  board[row][c].setValue(board[r][c].getValue());
                  board[r][c].setValue(0);
                 // System.out.println("Move but not combined");
               }

            }
         }
      }//end of r loop
   }//end of c loop
   
   public void moveDown() 
   {
      boolean[][] combined = new boolean[board.length][board[0].length];
      for(int c = 0; c < board[0].length; c++)
      {
         for(int r = board.length-2; r >= 0; r--) // if this is zero
         {
            if(board[r][c].getValue() != 0) // if not a zero
            {
               //row is ... the row below r with the first non-zero value below it
               int row = r;
               while(row+1 <= board.length-1 && board[row+1][c].getValue()==0) 
                  row++;
               //if row makes it to the last row then move to that row
               if (row == board.length-1) //last row
               {
                  board[row][c].setValue(board[r][c].getValue()); 
                  board[r][c].setValue(0); ///back to the location you started in
                //  System.out.println("Maximum Move");
               }
               //if row is not the first column, see if row can combine with top neighbor 
               else if(!combined[row+1][c] && board[row+1][c].getValue() == board[r][c].getValue())
               {
                  int addScore = board[row+1][c].getValue()+board[r][c].getValue();
                  board[row+1][c].setValue(board[row+1][c].getValue()+board[r][c].getValue());
                  board[r][c].setValue(0);
                  combined[row+1][c] = true;
                  score+=addScore;
                  
//                  
                 // System.out.println("Combining tiles");
               }
               //tile moved but cannot combine with bottom neighbor so place it where it stopped
               else if(r != row)
               {
                  board[row][c].setValue(board[r][c].getValue());
                  board[r][c].setValue(0);
                 // System.out.println("Move but not combined");
               }

            }
         }
      }//end of r loop
   }//end of c loop
   
   public boolean canMoveLeft() 
   {
     
         for(int r = 0; r < board.length; r++)
         {
            for(int c = 1; c < board[0].length; c++) // if this is zero
            {
               if(board[r][c].getValue() !=0) // if not a zero
               {
                  //col is ... the column to the left of c with the first non-zero value to its left
                  int col = c;
                  while(col-1 >= 0 && board[r][col-1].getValue()==0) 
                     col--; // column should decrease           lets the column count
                     
                  //can a move be made?
                  if (col != c ||  board[r][c-1].getValue() == board[r][c].getValue())
                  {
                     
                    return true;
                  }
                
               }//end of c loop
         }//end of r loop
         
         }
         return false;
   
      }
      
   public boolean canMoveRight() 
   {
      for(int r = 0; r < board.length; r++)
      {
         for(int c = board[r].length-2; c >= 0; c--) // if this is zero
         {
            if(board[r][c].getValue() !=0) // if not a zero
            {
               //col is ... the column to the right of c with the first non-zero value to its left
               int col = c;
               while(col+1 <= board.length-1 && board[r][col+1].getValue()==0) 
                  col++;        
                  
               //can a right move be made
               if (col != c ||  board[r][c+1].getValue() == board[r][c].getValue())
               {
                 return true;
               }
               
                 
            
         }
      }//end of c loop
   }//end of r loop
      return false;
   }
   
   public boolean canMoveUp() 
   {
      for(int c = 0; c < board[0].length; c++)
      {
         for(int r = 1; r < board[0].length; r++) // if this is zero
         {
            if(board[r][c].getValue() !=0) // if not a zero
            {
               //row is ... the row above r with the first non-zero value above it
               int row = r;
               while(row-1 >= 0 && board[row-1][c].getValue()==0) 
                  row--;
               //can move up?
               if (row != r || board[r-1][c].getValue() == board[r][c].getValue())
               {
                  return true;
               }
               
            
         }
      }//end of r loop
   }//end of c loop
      return false;
    
   }
   public boolean canMoveDown() 
   {
      for(int c = 0; c < board[0].length; c++)
      {
         for(int r = board.length-2; r >= 0; r--) // if this is zero
         {
            if(board[r][c].getValue() !=0) // if not a zero
            {
               //row is ... the row below r with the first non-zero value below it
               int row = r;
               while(row+1 <= board.length-1 && board[row+1][c].getValue()==0) 
                  row++;
               //can a move down be made?
               if (row != r || board[r+1][c].getValue() == board[r][c].getValue()) 
               
                  return true;
               
               
            }
         
      }//end of r loop
   }//end of c loop
      return false; // did not find a valid move
   }
   
   
   public int getNumEmptyLocations()
   {
      int count = 0;
      for(Tile_2048[] row : board) //for every row in board
         for(Tile_2048 t : row) //take out each number one at a time --> see if it's zero.
            if(t.getValue() == 0)
               count++;
              
      return count;
         
   }
 
   public void spawnValue(int num)
   {//If I found an empty location, I want to set it to the spawned value.
      int numEmpty = getNumEmptyLocations();
      int randomLocation = (int)(Math.random()*numEmpty)+1;
      int currentEmptyCount = 0;
      for(int r = 0; r < board.length; r++)
         for(int c = 0; c < board[r].length; c++)
            if(board[r][c].getValue() == 0)
            {
               currentEmptyCount++;
               if(currentEmptyCount == randomLocation)
                  board[r][c].setValue(num);
            }    
   }
   
   public void spawnValue (int r, int c, int value)
   {
      board[r][c].setValue(value);
   }

  
   
}
