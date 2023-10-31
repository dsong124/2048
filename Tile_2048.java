import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

//Daniel Song
//Program Description: a tile for the game of 2048 


public class Tile_2048
{
   
   private final Color BORDER_COLOR = new Color(190, 175, 160);
   private final Color DARK_FONT_COLOR = new Color(34, 34, 34);
   private final Color LIGHT_FONT_COLOR = new Color(250, 246, 242);
   private Color[] colors = {new Color(206, 193, 177),
                           new Color(240, 228, 217),
                           new Color(240, 225, 197),
                           new Color(250, 175, 108),
                           new Color(255, 145, 82),
                           new Color(255, 115, 82),
                           new Color(255, 78, 16),
                           new Color(240, 210, 96),
                           new Color(240, 210, 72),
                           new Color(240, 204, 42),
                           new Color(240, 200, 0),
                           new Color(242, 198, 0),
                           new Color(60, 60, 50),
   };
   
   //instance variables (properties of a tile)
   private int value;
   private Color color; // color is determined by the value
   
   
   //constructors (how you initialize the property values)
   public Tile_2048(int value)
   {
      this.value = value;
      this.color = Color.BLACK;
      assignColor();
   }
 
   //methods for a tile object
   public void draw(Graphics2D g2, int x, int y, int w, int h)
   {
      g2.setColor(color);
      g2.fillRect(x, y, w, h);
      g2.setStroke(new BasicStroke(10));
      g2.setColor(BORDER_COLOR);
      g2.drawRect(x, y, w, h);
      
      
    if(value != 0)
    {
      g2.setFont(new Font("Monospaced Bold", Font.PLAIN, 37));
      FontMetrics fm = g2.getFontMetrics();
      int width = fm.stringWidth(value+"");
      int height = fm.getHeight();
      if(value <= 4)
         g2.setColor(DARK_FONT_COLOR);
      else
         g2.setColor(LIGHT_FONT_COLOR);
      g2.drawString(value + "", x+w/2 - width/2, y+h/2 + height/2 - 10);
    }
   }
  
   public void assignColor()
   {
      int indexForColor = 0;
      if(value != 0)
         indexForColor = (int)(Math.log(value)/Math.log(2));
      color = colors[indexForColor];
   }
   
   
   public int getValue()
   {
      return value;
   }


   public void setValue(int value)
   {
      this.value=value;
      assignColor();
   }


   public Color getColor()
   {
      return color;
   }


   public void setColor(Color color)
   {
      this.color=color;
   }

   public void draw2(Graphics2D g2,int x,int y,int w,int h)
   
   {
      g2.setColor(BORDER_COLOR);
      g2.fillRect(x, y, w, h);
    //  g2.setStroke(new BasicStroke(10));
      g2.setColor(color);
      g2.fillRoundRect(x+5, y+5, w-10, h-10, 20, 20);
      
      
      if(value != 0)
      {
         g2.setFont(new Font("Monospaced Bold", Font.PLAIN, 37));
         FontMetrics fm = g2.getFontMetrics();
         int width = fm.stringWidth(value+"");
         int height = fm.getHeight();
         if(value <= 4)
            g2.setColor(DARK_FONT_COLOR);
         else
            g2.setColor(LIGHT_FONT_COLOR);
         g2.drawString(value + "", x+w/2 - width/2, y+h/2 + height/2 - 10);
      } 
   }

   
   }
