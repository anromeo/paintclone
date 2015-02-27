/*
 * TCSS 305 Winter 2015
 * Assignment 5 - PowerPaint
 * Ellipse.java
 */
package model;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
/**
 * The Ellipse Tool that draws Ellipses.
 * @author anromeo
 * @version 2015/02/21
 */
public class Ellipse extends AbstractTool {

    /** Constructs the Ellipse tool. */
    public Ellipse() {
        super("Ellipse");
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Tool drawShape(final Graphics2D theGraphic) {
        final Ellipse2D ellipse;
        ellipse = new Ellipse2D.Double();
        
        final Point originalPoint = new Point();
        originalPoint.setLocation(getOriginalX(), getOriginalY());
        final Point draggedPoint = new Point();
        draggedPoint.setLocation(getDraggedX(), getDraggedY());
        ellipse.setFrameFromDiagonal(originalPoint, draggedPoint);
        
        theGraphic.draw(ellipse);
        setShape(ellipse);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Shape createCopy() {
        return (Shape) ((Ellipse2D) getShape()).clone();
    }
}
