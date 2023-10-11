/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package carro;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;

/**
 *
 * @author gabri
 */
public class KeyBoard implements KeyListener{
    private Cena cena;
    
    public KeyBoard(Cena cena) {
        this.cena = cena;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_RIGHT:
                cena.horizontal+=2;
                break;
            case KeyEvent.VK_LEFT:
                cena.horizontal-=2;
                break;
            case KeyEvent.VK_UP:
                cena.vertical+=2;
                break;
            case KeyEvent.VK_DOWN:
                cena.vertical-=2;
                break;
//            case KeyEvent.VK_SPACE:;
//                cena.lightOn = !cena.lightOn;
//                break;
        }
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
    
    
}
