/*
 * TCSS 305 Winter 2015
 * Assignment 5 - PowerPaint
 * Line.java
 */
package model;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Line2D;
/**
 * The Line Tool that draws lines.
 * @author anromeo
 * @version 2015/02/21
 */
public class Line extends AbstractTool {

    /**  Constructs the Line tool. */
    public Line() {
        super("Line");
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public Tool drawShape(final Graphics2D theGraphic) {
        final Shape line = new Line2D.Double(getOriginalX(), getOriginalY(), 
                                      getDraggedX(), getDraggedY());
        theGraphic.draw(line);
        setShape(line);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Shape createCopy() {
        return (Shape) ((Line2D) getShape()).clone();
    }
}
