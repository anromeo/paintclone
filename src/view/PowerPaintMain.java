/*
 * TCSS 305 Winter 2015
 * Assignment 5 - PowerPaint
 * PowerPaintMain.java
 */
package view;

import java.awt.EventQueue;
/**
 * The PowerPaint Runnable Part of the Program.
 * @author anromeo
 * @version 2015/02/21
 */
public final class PowerPaintMain {

    /**
     * Prevents class from being instantiated.
     */
    private PowerPaintMain() {
        
    }
    
    /**
     * The main method, invokes the SnapShop GUI. Command line arguments are
     * ignored.
     * 
     * @param theArgs Command line arguments.
     */
    public static void main(final String[] theArgs) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PowerPaintGui();
            }
        });
    }
}
