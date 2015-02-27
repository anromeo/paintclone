/*
 * TCSS 305 Winter 2015
 * Assignment 5 - PowerPaint
 * DrawingArea.java
 */
package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import javax.swing.JPanel;

import model.Pencil;
import model.Tool;
/**
 * The DrawingArea to be drawn on.
 * @author anromeo
 * @version 2015/02/21
 */
public class DrawingArea extends JPanel {

    /** Starting thickness value. */
    private static final int MY_THICKNESS_STARTING = 5;
    /** Starting Grid Spacing value. */
    private static final int MY_GRID_SPACING = 10;
    /** Starting width of the DrawingArea. */
    private static final int MY_ORIGINAL_WIDTH = 500;
    /** Starting height of the DrawingArea. */
    private static final int MY_ORIGINAL_HEIGHT = 350;
    
    /** The default serialVersion to make CheckStyle happy. */
    private static final long serialVersionUID = 1L;
    
    /** The thickness set in the DrawingArea. */
    private int myThickness;
    
    /** Determines whether the grid is on. */
    private boolean myGridIsOn;

    /** Determines the color of the tool. */
    private Color myColor;

    /** Determines the tool to be used. */
    private Tool myCurrentTool;

    /** All the previous tools and their shapes. */
    private final List<Tool> myStoredTools;

    /** Determine whether it is okay to draw shapes onto the panel. */
    private boolean myIsOkayToDraw;

    /** Redo stack. */
    private final Deque<Tool> myRedoDeque;
    
    /**
     * The DrawingArea constructor that initializes the DrawingArea.
     */
    public DrawingArea() {
        setPreferredSize(new Dimension(MY_ORIGINAL_WIDTH, MY_ORIGINAL_HEIGHT));
        myThickness = MY_THICKNESS_STARTING;
        addMouseListener(new MyMouseClicker());
        addMouseMotionListener(new MyMouseAdapter());
        myColor = Color.BLACK;
        myGridIsOn = false;
        myCurrentTool = new Pencil();
        setBackground(Color.WHITE);
        myStoredTools = new ArrayList<Tool>();
        myIsOkayToDraw = false;
        myRedoDeque = new ArrayDeque<Tool>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void paintComponent(final Graphics theGraphic) {
        super.paintComponent(theGraphic);
        final Graphics2D g2d = (Graphics2D) theGraphic;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                             RenderingHints.VALUE_ANTIALIAS_ON);

        drawShapes(g2d);

        g2d.setPaint(myColor);
        g2d.setStroke(new BasicStroke(myThickness));

        if (myIsOkayToDraw) {
            myCurrentTool.setColor(myColor);
            myCurrentTool.setThickness(myThickness);
            myCurrentTool.drawShape(g2d);            
        }
        if (myGridIsOn) {
            drawGrid(g2d);
        }
    }

    /**
     * Draws the grid.
     * @param theGraphic being drawn on
     */
    private void drawGrid(final Graphics2D theGraphic) {
        theGraphic.setPaint(Color.GRAY);
        theGraphic.setStroke(new BasicStroke(1));

        final double gridWidth = getWidth();
        final double gridHeight = getHeight();
        double horizontalYPoint = 0;
        double verticalXPoint = 0;

        while (horizontalYPoint <= gridHeight) {
            horizontalYPoint += MY_GRID_SPACING;
            final Line2D gridLineHorizontal = new Line2D.Double(0, horizontalYPoint,
                                                          gridWidth,
                                                          horizontalYPoint);
            theGraphic.draw(gridLineHorizontal);
        }

        while (verticalXPoint <= gridWidth) {
            theGraphic.setStroke(new BasicStroke(1));
            verticalXPoint += MY_GRID_SPACING;
            final Line2D gridLineVertical = new Line2D.Double(verticalXPoint, 0,
                                                        verticalXPoint,
                                                        gridHeight);
            theGraphic.draw(gridLineVertical);
        }
    }

    /**
     * Draw all the Shapes.
     * @param theGraphic being drawn on
     */
    private void drawShapes(final Graphics2D theGraphic) {
        for (final Tool tool : myStoredTools) {
            theGraphic.setPaint(tool.getColor());
            theGraphic.setStroke(new BasicStroke(tool.getThickness()));
            theGraphic.draw(tool.getShape());
        }
    }

    /**
     * Returns the current color being worked with.
     * @return myColor
     */
    public Color getColor() {
        return myColor;
    }

    /**
     * Empties out all the shapes.
     */
    public void clear() {
        myIsOkayToDraw = false;
        myStoredTools.clear();
        repaint();
    }

    /**
     * Toggles the grid on and off.
     */
    public void toggleGrid() {
        myGridIsOn = myGridIsOn ? false : true;
        repaint();
    }

    /**
     * Sets the Tool being held by this DrawingArea.
     * @param theTool being set
     */
    public void setTool(final Tool theTool) {
        myCurrentTool = theTool;
    }

    /**
     * Sets the Thickness for the Drawing Area.
     * @param theThickness represented by an int
     */
    public void setThickness(final int theThickness) {
        myThickness = theThickness;
    }

    /**
     * Changes the current color being worked with.
     * @param theColor being changed to.
     */
    public void setColor(final Color theColor) {
        myColor = theColor;
    }
    
    /** Undo last shape. */
    public void undo() {
        if (canUndo()) {
            final Tool lastTool = myStoredTools.remove(myStoredTools.size() - 1);
            myRedoDeque.addLast(lastTool);            
            if (canUndo()) {
                myIsOkayToDraw = false;
            }
        }
        repaint();
    }
    
    /** Empties the redo deque when a new shape is added. */
    private void emptyRedoDeque() {
        myRedoDeque.clear();
    }
    
    /**
     * Can undo?
     * @return boolean determining whether it can or can't be undone.
     */
    public boolean canUndo() {
        return !myStoredTools.isEmpty();
    }
    
    /** Redo last shape. */
    public void redo() {
        if (canRedo()) {
            myIsOkayToDraw = true;
            final Tool lastTool = myRedoDeque.removeLast();
            myStoredTools.add(lastTool);
        }
        repaint();
    }
    
    /**
     * Can redo?
     * @return boolean determing whether it can or can't be redone.
     */
    public boolean canRedo() {
        return !myRedoDeque.isEmpty();
    }
    
    /**
     * Used to listen to DrawingArea Clicks.
     */
    private class MyMouseClicker extends MouseAdapter {
        /**
         *  {@inheritDoc}
         */
        @Override
        public void mousePressed(final MouseEvent theEvent) {
            myCurrentTool.setOriginalPoint(theEvent.getX(), theEvent.getY());
            myCurrentTool.setDraggedPoint(theEvent.getX(), theEvent.getY());
            if (!myIsOkayToDraw) { myIsOkayToDraw = true; }
        }

        /**
         *  {@inheritDoc}
         */
        @Override
        public void mouseReleased(final MouseEvent theEvent) {
            try {
                myCurrentTool.setColor(myColor);
                myCurrentTool.setThickness(myThickness);
                myStoredTools.add((Tool) myCurrentTool.clone());
                emptyRedoDeque();
                myIsOkayToDraw = false;
            } catch (final CloneNotSupportedException e) {
                e.printStackTrace();
            }
            repaint();
        }

    }

    /**
     * Used to listen to when the mouse is dragged.
     */
    private class MyMouseAdapter extends MouseAdapter {
        /**
         *  {@inheritDoc}
         */
        @Override
        public void mouseDragged(final MouseEvent theEvent) {
            myCurrentTool.setDraggedPoint(theEvent.getX(), theEvent.getY());
            repaint();
        }

    }
}
