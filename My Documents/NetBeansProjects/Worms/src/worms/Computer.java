package worms;

import java.awt.*;
import static java.awt.Color.red;
import javax.swing.*;

/**
 *
 * @author smart_000
 */
public class Computer extends JPanel{
    private int name;
    private int x;
    private int y;
    private Color color;
    private int size;
    private boolean vulnerable = false;
    private boolean infected = false;
    private int numInfections = 0;
    
    public Computer(int name, int x, int y, Color color, int size)
    {
        //set up identifying variables based on input to Computer class
        this.name = name;
        this.x = x;
        this.y = y;
        this.color = color;
        this.size = size;
    }
    
    //set to vulnerable
    public void setVulnerability(Graphics g,boolean vulnerable){
        this.vulnerable = vulnerable;
        super.paint(g);
        //change color to white;
        Color hue = new Color(255, 255, 255);
        g.setColor(hue);
        //paint the computer
        g.fillRect(x, y, size, size);
    }
    
    public boolean infect(double probability, Graphics g){
        //check if infection works based on probability
        if(Math.random() < probability)
        {
            //set to infected
            infected = true;
            //add to number of infections on this computer
            numInfections++;
            //paint the computer
            paintInfection(g);
        }
        return infected;
    }
    
    //find out if computer is infected
    public boolean getInfected(){
        return infected;
    }
    
    public void paintComputer(Graphics g){
        super.paint(g);
        //set color to gray
        Color hue = new Color(180,180,180);
        g.setColor(hue);
        //paint the computer
        g.fillRect(x, y, size, size);
    }
    
    public void paintVulnerable(Graphics g){
        super.paint(g);
        //set the color to white
        Color hue = new Color(255, 255, 255);
        g.setColor(hue);
        //paint the computer
        g.fillRect(x, y, size, size);
    }
    
    public void paintInfection(Graphics g){
        super.paint(g);
        int color = 255;
        Color hue = new Color(0,0,0);
        
        //if the computer is infected with only one copy...
        if(numInfections == 1){
            //set the color to red
            hue = new Color(255,0,0);
        }
        //if the computer is infected with more than one copy and the color is 
        //not equal to zero yet...
        else if(255 - (numInfections*15) >= 0)
        {
            //make color darker yellow for each copy of the virus
            color -= numInfections*15;
            hue = new Color(color,color,0);
        }
        g.setColor(hue);
        
        //paint the computer
        g.fillRect(x, y, size, size);
    }
}
