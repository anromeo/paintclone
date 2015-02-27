/*
 * TCSS 305 Winter 2015
 * Assignment 5 - PowerPaint
 * AbstractTool.java
 */
package model;

import java.awt.Color;
import java.awt.Shape;

/**
 * The Tool Abstract that instantiates all
 * the X and Y fields for this class.
 * 
 * @author anromeo
 * @version 2015/02/19
 */
public abstract class AbstractTool implements Tool, Cloneable {

    /** The Original X Point. */
    private double myOriginalX;
    /** The Original Y Point. */
    private double myOriginalY;
    /** The Dragged X Point. */
    private double myDraggedX;
    /** The Dragged Y Point. */
    private double myDraggedY;
    /** The Title of this tool. */
    private String myTitle;
    /** The Color of this Tool. */
    private Color myColor;
    /** The thickness of this Tool. */
    private int myThickness;
    /** The Shape stored in the Tool. */
    private Shape myShape;
    
    /**
     * This is the constructor of the ToolAbstract Class
     * that sets all the coordinates to -1.
     */
    private AbstractTool() {
        myOriginalX = -1;
        myOriginalY = -1;
        myDraggedX = -1;
        myDraggedY = -1;
        myThickness = 1;
        myColor = Color.BLACK;
    }
        
    /**
     * This is the overloaded constructor that gives
     * the AbstractTool a title.
     * @param theTitle of the button
     */
    public AbstractTool(final String theTitle) {
        this();
        myTitle = theTitle;
    }
    
    /**
     * Gets myOriginalX.
     * @return myOriginalX
     */
    public double getOriginalX() {
        return myOriginalX;
    }
    /**
     * Gets myOriginalY.
     * @return myOriginalY
     */
    public double getOriginalY() {
        return myOriginalY;
    }
    /**
     * Gets myDraggedX.
     * @return myDraggedX
     */
    public double getDraggedX() {
        return myDraggedX;
    }
    /**
     * Gets myDraggedX.
     * @return myDraggedX
     */
    public double getDraggedY() {
        return myDraggedY;
    }
    /**
     * Returns myTitle.
     * @return myTitle
     */
    public String getTitle() {
        return myTitle;
    }

    /**
     * Returns the thickness of the tool.
     * @return Thickness of tool
     */
    @Override
    public int getThickness() {
        return myThickness;
    }
    /**
     * Sets the thickness of the tool.
     * @param theThickness of tool
     */
    @Override
    public void setThickness(final int theThickness) {
        myThickness = theThickness;
    }
    /**
     * Return the Color of the tool.
     * @return Color of tool
     */
    @Override
    public Color getColor() {
        return myColor;
    }
    /**
     * Return the Color of the tool.
     * @param theColor of tool
     */
    @Override
    public void setColor(final Color theColor) {
        myColor = theColor;
    }
    /**
     * Sets the Shape of this Tool.
     * @param theShape of this tool
     */
    public void setShape(final Shape theShape) {
        myShape = theShape;
    }
    
    /**
     * Sets the Original Point.
     * @param theX of the original
     * @param theY of the original
     */
    @Override
    public void setOriginalPoint(final double theX, final double theY) {
        myOriginalX = theX;
        myOriginalY = theY;
    }

    /**
     * Sets the Dragged Point.
     * @param theX of the dragged point
     * @param theY of the dragged point
     */
    @Override
    public void setDraggedPoint(final double theX, final double theY) {
        myDraggedX = theX;
        myDraggedY = theY;
    }

    /**
     * @{inheritDoc}
     * @return Shape of Tool
     */
    @Override
    public Shape getShape() {
        return myShape;
    }

    /**
     * {@inheritDoc}
     * @throws CloneNotSupportedException if thrown by a subclass
     */
    @Override
    public Tool clone() throws CloneNotSupportedException {
        final Tool copy = (Tool) super.clone();
        copy.setShape(createCopy());
        copy.setColor(new Color(myColor.getRGB()));
        myOriginalX = -1;
        myOriginalY = -1;
        myDraggedX = -1;
        myDraggedY = -1;
        return copy;
    }
    
    /**
     * Brings a copy of the current shape.
     * @return myShape
     */
    public abstract Shape createCopy(); 
}
