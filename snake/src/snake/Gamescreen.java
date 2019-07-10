/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snake;

import java.awt.Dimension;
import javax.swing.JPanel;

/**
 *
 * @author guilherme
 */ 
public class Gamescreen extends JPanel{
    
    public static final int WIDTH = 400, HEIGHT = 400;
    
    public  Gamescreen(){
        
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        
    }
    
}
