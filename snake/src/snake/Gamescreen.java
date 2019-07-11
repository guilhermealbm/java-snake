/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snake;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

/**
 *
 * @author guilherme
 */ 
public class Gamescreen extends JPanel implements Runnable{
    
     
    public static final int WIDTH = 400, HEIGHT = 400;
    
    private Thread thread;
    private boolean running;
    
    private Body b;
    private Vector<Body> snake;
    private int x = 10, y = 10, size = 5;
    
    private boolean right = true, left = false, up = false, down = false;
    
    private int ticks = 0;
    
    public  Gamescreen(){
        
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        snake = new Vector<Body>();
        start();
        
    }
    
    public void start(){
        running = true;
        thread = new Thread(this);
        thread.start();
        
    }
    
    public void gameOver(){
        running = false;
        try {
            thread.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(Gamescreen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void tick(){
        
        if(snake.size() == 0){ //Create new snake
            b = new Body(x, y, 10);
            snake.add(b);
        }
        
        ticks++;
        
        
        if(right) x++;
        if(left) x--;
        if(up) y--;
        if(down) y++;

        //ticks = 0;

        b = new Body(x, y, 10);
        snake.add(b);

        
        try {
            Thread.sleep(100);
            if(snake.size() > size){
                System.out.println(ticks);
                snake.remove(0);
            }
        } catch (InterruptedException ex) {
                Logger.getLogger(Gamescreen.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }
    
    public void paint(Graphics g){
        
        g.clearRect(0, 0, WIDTH, HEIGHT);
        g.setColor(Color.gray);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        
        for (int i = 0; i < WIDTH/10; i++) {
            g.drawLine(i*10, 0, i*10, HEIGHT);
        }
        
        for (int i = 0; i < HEIGHT/10; i++) {
            g.drawLine(0, i*10, HEIGHT, i*10);
        }
        
        for (int i = 0; i < snake.size(); i++) {
            snake.get(i).draw(g);
        }
        
    }

    @Override
    public void run() {
        while (running) {
            tick();
            repaint();
        }
        
    }
    
}
