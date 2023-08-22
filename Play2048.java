public class Play2048
{
   public static void main(String[] args) 
   {
      Board2048 gameBoard = new Board2048();
    
      gameBoard.showBoard();
      System.out.println("Left");
      gameBoard.moveLeft();  
      gameBoard.showBoard();
      System.out.println("Right");
      gameBoard.moveRight();
      gameBoard.showBoard();
      System.out.println("Down");
      System.out.println(gameBoard.canMoveDown());     
      gameBoard.moveDown();
      gameBoard.showBoard();
      System.out.println(gameBoard.canMoveDown());     
      
      
      
   }
}
