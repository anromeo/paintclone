/*
 * TCSS 305 Winter 2015
 * Assignment 5 - PowerPaint
 * Pencil.java
 */
package model;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Path2D;
/**
 * The Pencil Tool that draws the Shape.
 * @author anromeo
 * @version 2015/02/21
 */
public class Pencil extends AbstractTool {
    
    /** Constructs the pencil tool. */
    public Pencil() {
        super("Pencil");
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Tool drawShape(final Graphics2D theGraphic) {

        if (getShape() == null) {
            setShape(new Path2D.Double());
        }
        if (((Path2D) getShape()).getCurrentPoint() != null) {
            ((Path2D) getShape()).lineTo(getDraggedX(), getDraggedY());
            theGraphic.draw(getShape());
        }
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Shape createCopy() {
        final Shape copy = getShape();
        setShape(null);
        return (Shape) ((Path2D) copy).clone();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setOriginalPoint(final double theX, final double theY) {
        super.setOriginalPoint(theX, theY);
        if (getShape() == null) {
            setShape(new Path2D.Double());
        }
        ((Path2D) getShape()).moveTo(theX, theY);
    }

}
