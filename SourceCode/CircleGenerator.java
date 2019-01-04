/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package music_visualization;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JPanel;

/**
 *
 * @author Isaac
 */
public class CircleGenerator extends JPanel {
    private final int CIRCLE_WIDTH = 20;
    private final int CIRCLE_HEIGHT = 20;
    private final int CRICLE_RADIUS = CIRCLE_WIDTH / 2;

    List<Circle> Clist;
    CircleGenerator(){
        Clist = new ArrayList();
        for(int i = 25; i <= 1000; i += 25){
            Clist.add(new Circle(CIRCLE_WIDTH, CIRCLE_HEIGHT, Color.BLUE, i, 500));
        }
    }
        
    @Override
    public void paintComponent(Graphics g){ //painting mechanism, Uses JPanel Class
        super.paintComponent(g);
        for(Circle c : Clist){
            c.draw(g);
        }
    }
}
