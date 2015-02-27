/*
 * TCSS 305 Winter 2015
 * Assignment 5 - PowerPaint
 * Tool.java
 */
package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;

/**
 * The Tool interface that guarantees that all tools will
 * have the drawShape method.
 * 
 * @author anromeo
 * @version 2015/02/19
 */
public interface Tool extends Cloneable {

    /**
     * Draws the shape.
     * @param theGraphic being sent to drawShape
     * @return Tool being drawn with
     */
    Tool drawShape(final Graphics2D theGraphic);

    /**
     * Gets the title of this Tool.
     * @return String representing title of Tool
     */
    String getTitle();

    /**
     * Gets the Color of this Tool.
     * @return Color of Tool
     */
    Color getColor();

    /**
     * Gets the Thickness of this Tool.
     * @return int representing thickness of Tool
     */
    int getThickness();

    /**
     * Sets the Thickness of this Tool.
     * @param theThickness representing th thickness
     */
    void setThickness(int theThickness);

    /**
     * Sets the Color of this Tool.
     * @param theColor representing my Color
     */
    void setColor(Color theColor);

    /**
     * Sets the Original Point.
     * @param theX of the original
     * @param theY of the original
     */
    void setOriginalPoint(double theX, double theY);

    /**
     * Sets the Dragged Point.
     * @param theX of the original
     * @param theY of the original
     */
    void setDraggedPoint(double theX, double theY);

    /**
     * Gets the Shape of this Tool.
     * @return theShape of this Tool
     */
    Shape getShape();

    /**
     * Sets the Shape of this Tool.
     * @param theShape of this Tool
     */
    void setShape(Shape theShape);

    /**
     * Returns a clone of this Tool.
     * @return Tool of Clone
     * @throws CloneNotSupportedException
     */
    Tool clone() throws CloneNotSupportedException;

    /**
     * Brings a copy of the current shape.
     * @return myShape
     */
    Shape createCopy();

}
