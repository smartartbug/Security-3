package worms;

import java.awt.*;
import javax.swing.*;
import java.util.*;

/**
 *
 * @author smart_000
 */
public class Worms extends JFrame{
    Random random = new Random();
    Graphics2D g2;
    Graphics graphics;
    
    //array of 10000 computer objects
    Computer[] computers = new Computer[10000];
    //array of vulnerabilities (true if computer is vulnerable)
    boolean[] vulnerabilities;
    
    //original infected computer
    int root = random.nextInt(10000);
    //number of vulnerable computers
    int n = 800;
    //number of attempts to infect other computers per infected computer
    int d = 10;
    //probability a computer will be infected
    double p = .5;
    
    public static void main(String[] args) {
        //start the worms frame
        new Worms();
    }
    
    public Worms(){
        setLayout(new BorderLayout());
        //set to exit on click of "x" button
        //NOT WORKING RIGHT NOW, NOT SURE WHY
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int screenH = (int)screen.getHeight();
        //setSize of frame to screen height x screen height
        setSize(screenH, screenH);
        setVisible(true);
        setTitle("Worms");
    }
    
    @Override
    public void paint(Graphics g){
        super.paint(g);
        graphics = g;
        g2 = (Graphics2D)g;
        initComputers(g);
        setVulnerabilities(g);
        simulate(g);
    }
    
    //set up computers array
    public void initComputers(Graphics g){
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int screenH = (int)screen.getHeight();
        //set computer side length
        int side = (screenH - (99) - 40)/100;
        //set color to white
        Color hue = new Color(0,0,0);
        //number of computers so far
        int count = 0;

        //initialize and draw computers in square configuration
        for(int i = 0; i < 100; i++){
            for(int j = 0; j < 100; j++){
                //initialize computer (name, xcoor, ycoor, color, side length)
                computers[count] = new Computer(count, 20+(i*(side+1)), 40+(j*(side+1)), hue, side);
                //draw computer
                computers[count].paintComputer(g);
                //add to number of computers so far
                count++;
            }
        }
    }
    
    //set up vulnerabilities
    public void setVulnerabilities(Graphics g){
        vulnerabilities = new boolean[10000];
        boolean chosen = true;
        int vulnerable = 0;
        //set root to be vulnerable
        vulnerabilities[root] = true;
        
        //set n computers to be vulnerable
        for(int i = 0; i < n; i++)
        {
            //while the computer already vulnerable, choose a new computer
            while(chosen == true)
            {
                //chose a random int up to 10000
                vulnerable = random.nextInt(10000);
                //find out if computer is vulnerable already
                chosen = vulnerabilities[vulnerable];
                //add computer to vulnerable array
                vulnerabilities[vulnerable] = true;
            }
            //set computer to vulnerable
            computers[vulnerable].setVulnerability(g, true);
            chosen = true;
        }
    }
    
    //simulate worm
    public void simulate(Graphics g){
        boolean finished = false;
        int count = 0;
        boolean infected;
        //infect the root computer
        computers[root].infect(1, g);
        
        while(finished == false)
        {   
            int choice = 0;
            //iterate through computers
            for(int i = 0; i < 10000; i++)
            {
                //if computer is infected...
                if(computers[i].getInfected() == true){
                    //add to count
                    count++;
                    //try to infect d other computers
                    for(int j = 0; j < d; j++)
                    {
                        //choose a random computer to infect
                        choice = random.nextInt(10000);
                        //if choice computer is vulnerable...
                        if (vulnerabilities[choice] == true){
                            //pause for 1/10th of a second
                            try {
                                Thread.sleep(100);
                            } catch(InterruptedException ex) {
                                Thread.currentThread().interrupt();
                            }
                            //try to infect computer and find out if it worked
                            infected = computers[choice].infect(p, g);
                        }
                    }
                }
            }
            //if all vulnerable computers are infected end simulation
            if (count == n)
            {
                //set finished to true
                finished = true;
                System.out.println("done");
            }
        }
    }
}
