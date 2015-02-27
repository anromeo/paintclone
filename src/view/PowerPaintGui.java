/*
 * TCSS 305 Winter 2015
 * Assignment 5 - PowerPaint
 * PowerPaintGui.java
 */
package view;

import com.sun.glass.events.KeyEvent;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.ColorIcon;
import model.Ellipse;
import model.Line;
import model.Pencil;
import model.Rectangle;
import model.Tool;

/**
 * The PowerPaintGui Program.
 * @author anromeo
 * @version 2015/02/20
 */
public class PowerPaintGui extends JFrame {

    /** Default serial version to make checkstyle happy. */
    private static final long serialVersionUID = 1L;

    /** The Image icon on the MenuBar. */
    private static final String MY_IMAGE_ICON_FILE = "icon/w.gif";

    /** The main drawing area. */
    private final DrawingArea myDrawingArea;

    /** The main menu bar. */
    private final JMenuBar myMenuBar;

    /** The toolbar. */
    private final JToolBar myToolBar;

    /** The toggle buttons group. */
    private final ButtonGroup myToggleButtonsGroup;

    /** The toggle menu groups. */
    private final ButtonGroup myToggleMenuGroup;

    /** This method constructs the power paint GUI. */

    /** The Pencil Tool. */
    private final ActionListener myPencilAction;

    /** The Line Tool. */
    private final ActionListener myLineAction;

    /** The Rectangle Tool. */
    private final ActionListener myRectangleAction;

    /** The Ellipse Tool. */
    private final ActionListener myEllipseAction;

    /** The Color Changing ActionListener. */
    private final ActionListener myColorAction;

    /** The Undo Button. */
    private final JMenuItem myUndoButton;
    
    /** The Redo Button. */
    private final JMenuItem myRedoButton;
    /**
     * Instantiates the entire PowerPaintGui.
     */
    public PowerPaintGui() {
        super("TCSS 305 PowerPaint");

        myDrawingArea = new DrawingArea();
        myMenuBar = new JMenuBar();
        myToolBar = new JToolBar();
        myToggleButtonsGroup = new ButtonGroup();
        myToggleMenuGroup = new ButtonGroup();

        myPencilAction = new PowerPaintGui.ToolListener();
        myLineAction = new PowerPaintGui.ToolListener(new Line());
        myRectangleAction = new PowerPaintGui.ToolListener(new Rectangle());
        myEllipseAction = new PowerPaintGui.ToolListener(new Ellipse());
        myColorAction = new ChangeColorAction();
        myUndoButton = new JMenuItem("Undo");
        myRedoButton = new JMenuItem("Redo");
        setUpGui();
    }

    /**
     * Sets up the PowerPaintGui.
     */
    private void setUpGui() {
        createMenuToolbar();
        createToggleButtonsGroup();
        setUpMenuPanel();
        setUpFrame();
    }

    /**
     * Sets up this JFrame by setting width and height,
     * and adding an IconImage to the bar.
     */
    private void setUpFrame() {
        setIconImage(new ImageIcon(MY_IMAGE_ICON_FILE).getImage());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //Format the Gui
        myDrawingArea.addMouseListener(new MouseClicker());
        setJMenuBar(myMenuBar);
        add(myToolBar, BorderLayout.PAGE_END);
        add(myDrawingArea, BorderLayout.CENTER);
        pack();
        centerTheGui();
        setVisible(true);

    }

    /**
     * Sets up the Menu Panel.
     */
    private void setUpMenuPanel() {
        setUpFileMenu();
        setUpEditMenu();
        setUpOptionsMenu();
        setUpToolsMenu();
        setUpHelpMenu();
    }

    /**
     * Sets up the File Menu.
     */
    private void setUpFileMenu() {
        // Creating the File Menu
        final List<JMenuItem> fileMenuItemsList = new ArrayList<JMenuItem>();
        final JMenuItem exitMenuItem = new JMenuItem("Exit", KeyEvent.VK_X);
        exitMenuItem.addActionListener(new ActionListener() {       
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                System.exit(0);
            }
        });

        final JMenuItem clearMenuItem = new JMenuItem("Clear", KeyEvent.VK_C);
        clearMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                myDrawingArea.clear();
                checkUndoRedoButtons();
            }     
        });

        fileMenuItemsList.add(clearMenuItem);
        fileMenuItemsList.add(exitMenuItem);

        myMenuBar.add(createJMenu("File", fileMenuItemsList, KeyEvent.VK_F, true));

    }

    /**
     * Set up Edit Menu.
     */
    @SuppressWarnings("serial")
    private void setUpEditMenu() { 

        myUndoButton.setEnabled(false);
        myRedoButton.setEnabled(false);
        
        final ActionListener undoAction =
            new AbstractAction() {

                @Override
                public void actionPerformed(final ActionEvent theEvent) {
                    myDrawingArea.undo();
                    checkUndoRedoButtons();
                } 
            };
        myUndoButton.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z,
                                                         ActionEvent.CTRL_MASK));
        myUndoButton.addActionListener((AbstractAction) undoAction);

        final ActionListener redoAction =
            new AbstractAction() {

                @Override
                public void actionPerformed(final ActionEvent theEvent) {
                    myDrawingArea.redo();
                    checkUndoRedoButtons();
                } 
            };
            
        myRedoButton.addActionListener((AbstractAction) redoAction);
        myRedoButton.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y,
                                                         ActionEvent.CTRL_MASK));

        final List<JMenuItem> editMenu = new ArrayList<JMenuItem>();
        editMenu.add(myUndoButton);
        editMenu.add(myRedoButton);
        myMenuBar.add(createJMenu("Edit", editMenu, 
                                  KeyEvent.VK_E, false));
    }

    /** Checks undo and redo buttons. */
    private void checkUndoRedoButtons() {
        if (myDrawingArea.canUndo()) {
            myUndoButton.setEnabled(true);
        } else {
            myUndoButton.setEnabled(false);
        }
        if (myDrawingArea.canRedo()) {
            myRedoButton.setEnabled(true);
        } else {
            myRedoButton.setEnabled(false);
        }
    }

    /**
     * Set up Options Menu.
     */
    private void setUpOptionsMenu() {
        // Creating the Options Menu
        final JMenuItem gridMenuItem = new JMenuItem("Grid", KeyEvent.VK_G);
        gridMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                myDrawingArea.toggleGrid();
            }
        });

        final List<JMenuItem> optionsMenuItemsList = new ArrayList<JMenuItem>();
        optionsMenuItemsList.add(gridMenuItem);

        final JMenu optionsMenu = createJMenu("Options", optionsMenuItemsList, 
                                              KeyEvent.VK_O, false);

        optionsMenu.add(createThicknessSlider());
        myMenuBar.add(optionsMenu);

    }

    /**
     * Set up the Tools Menu.
     */
    private void setUpToolsMenu() {
        // Creates the Tools Menu
        final JMenu toolsMenu = new JMenu("Tools");
        toolsMenu.setMnemonic(KeyEvent.VK_T);

        // Create Button, Store it in the ToggleButtons, and Then Return ActionListener()
        final JMenuItem colorMenuItem = new JMenuItem((AbstractAction) myColorAction);

        toolsMenu.add(colorMenuItem);
        toolsMenu.addSeparator();

        final List<JRadioButtonMenuItem> toolsMenuItemsList =
                                        new ArrayList<JRadioButtonMenuItem>();
        toolsMenuItemsList.add(createJRadioButton(
                                                  (AbstractAction) myPencilAction,
                                                  KeyEvent.VK_P));
        toolsMenuItemsList.add(createJRadioButton(
                                                  (AbstractAction) myLineAction,
                                                  KeyEvent.VK_L));
        toolsMenuItemsList.add(createJRadioButton(
                                                  (AbstractAction) myRectangleAction,
                                                  KeyEvent.VK_R));
        toolsMenuItemsList.add(createJRadioButton(
                                                  (AbstractAction) myEllipseAction,
                                                  KeyEvent.VK_E));

        for (final JRadioButtonMenuItem toolMenu : toolsMenuItemsList) {
            myToggleMenuGroup.add(toolMenu);
            toolsMenu.add(toolMenu);
        }
        myMenuBar.add(toolsMenu);
    }

    /**
     * Creates a JRadioButton.
     * @param theAction being used to create the Object
     * @param theKeyEvent being set as a Mnemonic
     * @return JRadioButtonMenuItem being returned
     */
    private JRadioButtonMenuItem createJRadioButton(final ActionListener theAction,
                                                    final int theKeyEvent) {
        final JRadioButtonMenuItem temp = new JRadioButtonMenuItem((AbstractAction) theAction);
        temp.setMnemonic(theKeyEvent);
        return temp;
    }
    /**
     * Creates the window MenuBar.
     */
    private void createMenuToolbar() {
        final JButton colorButton = new JButton((AbstractAction) myColorAction);
        colorButton.setMnemonic(KeyEvent.VK_C);
        myToolBar.add(colorButton);
        myToolBar.addSeparator();
    }


    /**
     * Creates the ToggleButton Group.
     */
    private void createToggleButtonsGroup() {
        // Adds the Toggle Button
        createToggle(myPencilAction, KeyEvent.VK_P);
        createToggle(myLineAction, KeyEvent.VK_L);
        createToggle(myRectangleAction, KeyEvent.VK_R);
        createToggle(myEllipseAction, KeyEvent.VK_E);
    }

    /**
     * Creates the ThicknessSlider.
     * @return theJMenu Slider
     */
    private JMenu createThicknessSlider() {
        final String thickness = "Thickness";
        final int startingTick = 0;
        final int endingTick = 20;
        final int selectedTick = 5;
        final int minorTickSpacing = 1;

        final JMenu thicknessMenuItemSlider = new JMenu(thickness);
        final JMenuItem thicknessMenuItem = new JMenuItem(thickness, KeyEvent.VK_T);
        final JSlider thicknessSlider =
                                        new JSlider(startingTick, endingTick, selectedTick);
        thicknessSlider.addChangeListener(new ChangeThicknessListener());

        thicknessSlider.setMajorTickSpacing(selectedTick);
        thicknessSlider.setMinorTickSpacing(minorTickSpacing);
        thicknessSlider.setPaintTicks(true);
        thicknessSlider.setSnapToTicks(true);
        thicknessSlider.setPaintLabels(true);
        thicknessMenuItemSlider.add(thicknessSlider);
        thicknessMenuItem.add(thicknessMenuItemSlider);

        return thicknessMenuItemSlider;
    }


    /** Creates the About Menu. */
    private void setUpHelpMenu() {
        final List<JMenuItem> helpMenuItemsList = new ArrayList<JMenuItem>();
        final JMenuItem aboutMenuItem = new JMenuItem("About...", KeyEvent.VK_A);
        aboutMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                JOptionPane.showMessageDialog(PowerPaintGui.this,
                                              "TCSS 305 PowerPaint, Winter 2015",
                                              "About",
                                              JOptionPane.PLAIN_MESSAGE);
            }

        });
        helpMenuItemsList.add(aboutMenuItem);
        myMenuBar.add(createJMenu("Help", helpMenuItemsList, KeyEvent.VK_H, false));
    }

    /** Centers the frame. */
    private void centerTheGui() {

        final Toolkit kit = Toolkit.getDefaultToolkit();
        final Dimension screenSize = kit.getScreenSize();

        setLocation((int) (screenSize.width / 2.0 - getSize().width / 2.0),
                    (int) (screenSize.height / 2.0 - getSize().height / 2.0));

    }

    /**
     * For each JMenuItem, add it to a JMenu that is returned.
     * 
     * @param theHeader : string of top level in JMenu
     * @param theJMenuItemList : List of all JMenuItems to add.
     * @param theMnemonic : int of the Mnemonic
     * @param theLineSeparator : boolean representing whether to add
     * a line separator the first line or not
     * @return JMenu : to be added to.
     */
    private JMenu createJMenu(final String theHeader, final
                              List<JMenuItem> theJMenuItemList,
                              final int theMnemonic,
                              final boolean theLineSeparator) {
        final JMenu returnedJMenu = new JMenu(theHeader);
        returnedJMenu.setMnemonic(theMnemonic);
        int i = 0;

        for (final JMenuItem item : theJMenuItemList) {
            returnedJMenu.add(item);
            if (theLineSeparator && i == 0) {
                returnedJMenu.addSeparator();
            }
            i++;
        }
        return returnedJMenu;
    }

    /**
     * This creates a JToggleButton and adds it to the toggleButtonsGroup
     * field.
     * 
     * @param theAction used to create this toggle button
     * @param theMnemonic int
     */
    private void createToggle(final ActionListener theAction,
                              final int theMnemonic) {
        final JToggleButton returnToggleButton =
                                        new JToggleButton((AbstractAction) theAction);
        returnToggleButton.setMnemonic(theMnemonic);
        myToggleButtonsGroup.add(returnToggleButton);
        myToolBar.add(returnToggleButton);
    }

    /** Changes the Color. */
    private class ChangeColorAction extends AbstractAction {

        /** Default serial version to make checkstyle happy. */
        private static final long serialVersionUID = 1L;

        /** Creates the ChangeColorAction. */
        public ChangeColorAction() {
            super("Color...", new ColorIcon(myDrawingArea.getColor()));
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            @SuppressWarnings("static-access")
            final Color returnColor = new JColorChooser().showDialog(myDrawingArea,
                                                                     "Select a Color",
                                                                     myDrawingArea.getColor());
            if (returnColor != null) {
                myDrawingArea.setColor(returnColor);
            }
            putValue(Action.SMALL_ICON, new ColorIcon(myDrawingArea.getColor()));
        }
    }

    /**
     * Changes the Thickness of the Shapes.
     */
    private class ChangeThicknessListener implements ChangeListener {

        /**
         * {@inheritDoc}
         */
        @Override
        public void stateChanged(final ChangeEvent theEvent) {
            final JSlider slider = (JSlider) theEvent.getSource();
            final int thickness = slider.getValue();
            myDrawingArea.setThickness(thickness);
        }
    }

    /**
     * Creates the ActionListener for Each Tool.
     */
    private class ToolListener extends AbstractAction {
        /** Default serial version. */
        private static final long serialVersionUID = 1L;

        /** The Tool that is called on by this listener. */
        private final Tool myTool;

        /** Creates the ToolListener Class. The default tool is a Pencil. */
        private ToolListener() {
            super("Pencil");
            myTool = new Pencil();
            putValue(Action.SELECTED_KEY, true);
        }

        /**
         * Overrides the Pencil tool during construction.
         * @param theTool that is being set
         */
        public ToolListener(final Tool theTool) {
            super(theTool.getTitle());
            myTool = theTool;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            myDrawingArea.setTool(myTool);
            putValue(Action.NAME, myTool.getTitle());
            putValue(Action.SELECTED_KEY, true);
        }
    }
    
    /**
     * Used to set undo/redo buttons.
     */
    private class MouseClicker extends MouseAdapter {

        /**
         *  {@inheritDoc}
         */
        @Override
        public void mouseReleased(final MouseEvent theEvent) {
            checkUndoRedoButtons();
        }

    }

}
