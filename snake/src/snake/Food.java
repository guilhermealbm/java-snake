/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snake;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author guilherme
 */
public class Food {

    private int x, y, width, heigth;
    
    
    public Food(int x, int y, int tile){
        this.x = x;
        this.y = y;
        width = tile;
        heigth = tile;
    }
    
    public void draw (Graphics g){
        g.setColor(Color.red);
        g.fillRect(x*width, y*heigth, width, heigth);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    
    
}