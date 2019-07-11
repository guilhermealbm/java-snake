/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snake;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

/**
 *
 * @author guilherme
 */ 
public class Gamescreen extends JPanel implements Runnable, KeyListener{
    
     
    public static final int WIDTH = 400, HEIGHT = 400;
    
    private Thread thread;
    private boolean running;
    
    private Body b;
    private Vector<Body> snake;
    private int x = 10, y = 10, size = 5;
    
    private boolean right = true, left = false, up = false, down = false;
    private int ticks = 0;
    
    public Gamescreen(){
        
        setFocusable(true);
        
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        addKeyListener(this);
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
        
        if(snake.isEmpty()){ //Create new snake
            b = new Body(x, y, 10);
            snake.add(b);
        }
        
        ticks++;
        
        
        if(right) x++;
        if(left) x--;
        if(up) y--;
        if(down) y++;
        
        b = new Body(x, y, 10);
        snake.add(b);

        
        try {
            Thread.sleep(100); //Change for difficulty
            if(snake.size() > size){
                snake.remove(0);//Movement
            }
        } catch (InterruptedException ex) {
                Logger.getLogger(Gamescreen.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }
    
    public void paint(Graphics g){
        
        g.clearRect(0, 0, WIDTH, HEIGHT);
        g.setColor(Color.gray);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        
        //each cell = 10px x 10px
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

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        
        if((key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) && !left){
            right = true;
            up = false;
            down = false;
        }else if((key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) && !right){
            left = true;
            up = false;
            down = false;
        }else if((key == KeyEvent.VK_UP || key == KeyEvent.VK_W) && !down){
            up = true;
            left = false;
            right = false;
        }else if((key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) && !up){
            down = true;
            left = false;
            right = false;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
    }
    
}
