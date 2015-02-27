/*
 * TCSS 305 Winter 2015
 * Assignment 5 - PowerPaint
 * Rectangle.java
 */
package model;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
/**
 * The Rectangle Tool that draws Rectangles.
 * @author anromeo
 * @version 2015/02/21
 */
public class Rectangle extends AbstractTool {
    
    /** Constructs the Rectangle tool. */
    public Rectangle() {
        super("Rectangle");
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public Tool drawShape(final Graphics2D theGraphic) {
        final Rectangle2D rectangle;
        rectangle = new Rectangle2D.Double();
        
        final Point originalPoint = new Point();
        originalPoint.setLocation(getOriginalX(), getOriginalY());
        final Point draggedPoint = new Point();
        draggedPoint.setLocation(getDraggedX(), getDraggedY());
        rectangle.setFrameFromDiagonal(originalPoint, draggedPoint);
        theGraphic.draw(rectangle);
        setShape(rectangle);
        return this;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Shape createCopy() {
        return (Shape) ((Rectangle2D) getShape()).clone();
    }

}
