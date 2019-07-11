/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snake;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author guilherme
 */ 
public class Gamescreen extends JPanel implements Runnable, KeyListener{    
     
    public static final int WIDTH = 410, HEIGHT = 410;
    
    private Thread thread;
    private boolean running;
    
    private Body b;
    private Vector<Body> snake;
    
    private Food food;
    private Vector<Food> foods;
    
    private Random r;
    
    private int x = 10, y = 10, size = 5;
    
    private boolean right = true, left = false, up = false, down = false;
    private int ticks = 0;
    
    public Gamescreen(){
        
        setFocusable(true);
        
        setPreferredSize(new Dimension(WIDTH, HEIGHT+20));
        addKeyListener(this);
        
        snake = new Vector<Body>();
        foods = new Vector<Food>();
        
        r = new Random();
        
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
            
            for (int i = 0; i < size; i++, x++) {
                b = new Body(x, y, 10);
                snake.add(b);
            }
            x--;
        }else{
            //System.out.println(x + " " + y);
            
            //Body colision
            for (int i = 0; i < snake.size()-1; i++) {
                if(x == snake.get(i).getX() && y == snake.get(i).getY())
                    gameOver();
            }

            //Border colision
            if(x < 1 || x > ((WIDTH-10*2)/10) || y < 1 || y > ((WIDTH-10*2)/10))
                gameOver();
        
            ticks++;

            if(right) x++;
            if(left) x--;
            if(up) y--;
            if(down) y++;

            b = new Body(x, y, 10);
            snake.add(b);
            
            
            if(snake.size() > size){
                snake.remove(0);//Movement
            }

            if(foods.isEmpty()){
                int x_ = r.nextInt(((WIDTH-10*3)/10));
                int y_ = r.nextInt(((WIDTH-10*3)/10));

                food = new Food(x_+1, y_+1, 10);
                foods.add(food);
            }

            for (int i = 0; i < foods.size(); i++) {
                if(x == foods.get(i).getX() && y == foods.get(i).getY()){
                    size++;
                    foods.remove(i);
                    i++;
                }
            }
            
        }
            
    }
    
    public void paint(Graphics g){
        
        g.clearRect(0, 0, WIDTH, HEIGHT);
        g.setColor(Color.gray);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        
        //each cell = 10px x 10px
        for (int i = 0; i < WIDTH/10; i++)
            g.drawLine(i*10, 0, i*10, HEIGHT);
        
        for (int i = 0; i < HEIGHT/10; i++) 
            g.drawLine(0, i*10, HEIGHT, i*10);
        
        for (int i = 0; i < snake.size(); i++) 
            snake.get(i).draw(g);   
        
        for (int i = 0; i < foods.size(); i++) 
            foods.get(i).draw(g);
        
        g.setColor(Color.black);        
        for(int i = 0; i < 10; i++){
            g.drawLine(i, i, WIDTH-i, i);
            g.drawLine(i, HEIGHT-i, WIDTH-i, HEIGHT-i);
            g.drawLine(i, i, i, HEIGHT-i);
            g.drawLine(WIDTH-i, i, WIDTH-i, HEIGHT-i);
        }
        
        for(int i = 0; i < 20; i++){
            g.drawLine(0, HEIGHT+i, WIDTH+i, HEIGHT+i);
        }
        
        g.setColor(Color.white);
        g.setFont(new Font("Courier New", Font.PLAIN, 22));
        g.drawString("  Score: " + (size-5), 0, HEIGHT+19);
        
    }

    @Override
    public void run() {
        int i = 17*6;
        while (running) {
            
            if(i == 17*6){ //Change for difficulty
               tick();
               i = 0;
            }
            try {
                Thread.sleep(1); //+-fps
                repaint();
                i++;
            } catch (InterruptedException ex) {
                Logger.getLogger(Gamescreen.class.getName()).log(Level.SEVERE, null, ex);
            }
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
