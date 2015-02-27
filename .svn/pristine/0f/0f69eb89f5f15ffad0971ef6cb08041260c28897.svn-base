/*
 * TCSS 305 Winter 2015
 * Assignment 5 - PowerPaint
 * ColorIcon.java
 */
package model;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;
/**
 * The ColorIcon Class used to display the Color of the DrawingArea.
 * @author anromeo
 * @version 2015/02/22
 */
public class ColorIcon implements Icon {

    /** Height of the button. */
    private static final int MY_HEIGHT = 14;
    /** Width of the button. */
    private static final int MY_WIDTH = 14;
    /** The Color of the ColorButton. */
    private final Color myColor;
    
    /**
     * Creates the ColorIcon.
     * @param theColor selected.
     */
    public ColorIcon(final Color theColor) {
        myColor = theColor;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void paintIcon(final Component theC, final Graphics theG,
                          final int theX, final int theY) {
        theG.setColor(myColor);  
        theG.fillRect(theX, theY, MY_WIDTH - 1, MY_HEIGHT - 1);  
  
        theG.setColor(Color.black);  
        theG.drawRect(theX, theY, MY_WIDTH - 1, MY_HEIGHT - 1);        
    }

    @Override
    public int getIconWidth() {
        return MY_HEIGHT;
    }

    @Override
    public int getIconHeight() {
        return MY_WIDTH;
    }

}
