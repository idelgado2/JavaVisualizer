package music_visualization;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author Isaac
 */
public class Circle extends JPanel{
    public int width, height;   //Size of Circle
    public int radius;
    public int x, y;            // X and Y coordinates of circle
    public Color color;         // Color of the circle
    
    public Circle(int width, int height, Color color, int x, int y){
        this.width = width;     /** set all attributes of the circle **/
        this.height = height;
        this.color = color;
        this.x = x;
        this.y = y;
        radius = height / 2;
    }
    
    public void draw(Graphics g){
        g.setColor(color);                  //set color to draw circle wil
        g.fillOval(x, y, width, height);    //draw an oval(circle) with the previously set colors
    }
    
    @Override
    public void paintComponent(Graphics g){ //painting mechanism, Uses JPanel Class
        super.paintComponent(g);
        this.draw(g);
    }
    
}
