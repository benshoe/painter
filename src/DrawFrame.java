// GCS Exercise 14.17 Solution: DrawFrame.java
// Program that creates a panel for the user to draw shapes.
// Allows the user to choose the shape and color.
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class DrawFrame extends JFrame {
    // Array of possible shapes
    private DrawPanel drawPanel; // panel that handles the drawing

    // constructor
   public DrawFrame()
   {
       super("Painter - New file.painter");

       setLookAndFeel();

       addMenu();

       // create a panel to store the components at the top of the frame
       JPanel topPanel = new JPanel();
       ComponentPanel componentPanel = new ComponentPanel();

       /*
      The anonymous inner class is an object that implements the ActionListener
interface. The object’s actionPerformed() method is overridden to set the frame’s
title when the corresponding button is clicked. Because each button has its own listener,
it’s simpler than using one listener for multiple interface components.
Inner classes look more complicated than separate classes, but they can simplify and
shorten your Java code. [Lemay, L. & Cadenhead, R. 2002, Sams teach yourself Java 2 in 21 days, Sams.]
       */
       componentPanel.setOnDrawModeClicked(e -> drawPanel.setDrawMode());
       componentPanel.setOnSelectModeClicked(e -> drawPanel.setSelectMode());
       componentPanel.setOnUndoClicked(e -> drawPanel.clearLastShape());
       componentPanel.setOnClearClicked(e -> drawPanel.clearDrawing());
       componentPanel.setOnRedoClicked(e -> drawPanel.redoLastRemovedShape());
       componentPanel.setOnColorClicked(e -> {
           drawPanel.setDrawingColor((Color) e.getSource());
       });

       //Save and open buttons
       JButton openFileButton = PainterPanel.addButtonToPanel(topPanel, getClass().getResource(Images.OPEN), "Open File");
       openFileButton.addActionListener(e -> drawPanel.openFileDialog());

       JButton saveFileButton = PainterPanel.addButtonToPanel(topPanel, getClass().getResource(Images.SAVE), "Save File");
       saveFileButton.addActionListener(e -> drawPanel.saveFileDialog());

       //Draw shape buttons
       JButton drawLineButton = PainterPanel.addButtonToPanel(topPanel, getClass().getResource(Images.LINE), "Line");
       drawLineButton.addActionListener(e -> drawPanel.setShapeType(ShapeType.LINE));

       JButton drawSquareButton = PainterPanel.addButtonToPanel(topPanel, getClass().getResource(Images.SQUARE), "Square");
       drawSquareButton.addActionListener(e -> drawPanel.setShapeType(ShapeType.SQUARE));

       JButton drawRectButton = PainterPanel.addButtonToPanel(topPanel, getClass().getResource(Images.RECTANGLE), "Rectangle");
       drawRectButton.addActionListener(e -> drawPanel.setShapeType(ShapeType.RECTANGLE));

       JButton drawCirleButton = PainterPanel.addButtonToPanel(topPanel, getClass().getResource(Images.CIRCLE), "Circle");
       drawCirleButton.addActionListener(e -> drawPanel.setShapeType(ShapeType.CIRCLE));

       JButton drawOvalButton = PainterPanel.addButtonToPanel(topPanel, getClass().getResource(Images.OVAL), "Oval");
      drawOvalButton.addActionListener(e1 -> drawPanel.setShapeType(ShapeType.OVAL));

       JButton drawRoundedSquare = PainterPanel.addButtonToPanel(topPanel, getClass().getResource(Images.ROUNDED_RECTANGLE), "Rounded rectangle");
       drawRoundedSquare.addActionListener(e -> drawPanel.setShapeType(ShapeType.ROUNDED_RECTANGLE));

       //Line thickness combo box
       LineThickness[] lineThicknesses = LineThickness.values();
       JComboBox<LineThickness> basicStrokeCombo = new JComboBox<>(lineThicknesses);
       topPanel.add(basicStrokeCombo);
       basicStrokeCombo.addActionListener(e -> {
          final Object selectedItem = basicStrokeCombo.getSelectedItem();
          drawPanel.setLineThickness((LineThickness) selectedItem);
       });

      // create a checkbox to determine whether the shape is filled
      JCheckBox filledCheckBox = new JCheckBox("Filled");
      filledCheckBox.addItemListener(e -> drawPanel.setFilledShape(filledCheckBox.isSelected()));
      topPanel.add(filledCheckBox);

      // add the top panel to the frame
      add(topPanel, BorderLayout.NORTH);
       // add the componentPanel to the frame
      add(componentPanel, BorderLayout.WEST);

      // create a label for the status bar
      JLabel statusLabel = new JLabel("(0,0)");

      // add the status bar at the bottom
      add(statusLabel, BorderLayout.SOUTH);

      // create the DrawPanel with its status bar label
      drawPanel = new DrawPanel(statusLabel);

      add(drawPanel); // add the drawing area to the center
   } // end DrawFrame constructor

    private void addMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);
        fileMenu.setMnemonic(KeyEvent.VK_F);

        JMenuItem openMenuItem = new JMenuItem("Open");
        fileMenu.add(openMenuItem);
        openMenuItem.setMnemonic(KeyEvent.VK_O);
        openMenuItem.setIcon(new ImageIcon(getClass().getResource(Images.OPEN)));
        openMenuItem.addActionListener(e -> drawPanel.openFileDialog());

        JMenuItem saveMenuItem = new JMenuItem("Save");
        fileMenu.add(saveMenuItem);
        saveMenuItem.setMnemonic(KeyEvent.VK_S);
        saveMenuItem.setIcon(new ImageIcon(getClass().getResource(Images.SAVE)));
        saveMenuItem.addActionListener(e -> drawPanel.saveFileDialog());

        fileMenu.add(new JSeparator());
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        fileMenu.add(exitMenuItem);
        exitMenuItem.setMnemonic(KeyEvent.VK_X);
        exitMenuItem.addActionListener(e -> System.exit(10));

        setJMenuBar(menuBar);

    }

    private void setLookAndFeel() {
      try {
         UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
         SwingUtilities.updateComponentTreeUI(this);
      } catch (Exception e) {
         System.out.println("Could not load look and feel " + e);
      }
   }

} // end class DrawFrame


/**************************************************************************
 * (C) Copyright 1992-2014 by Deitel & Associates, Inc. and               *
 * Pearson Education, Inc. All Rights Reserved.                           *
 *                                                                        *
 * DISCLAIMER: The authors and publisher of this book have used their     *
 * best efforts in preparing the book. These efforts include the          *
 * development, research, and testing of the theories and programs        *
 * to determine their effectiveness. The authors and publisher make       *
 * no warranty of any kind, expressed or implied, with regard to these    *
 * programs or to the documentation contained in these books. The authors *
 * and publisher shall not be liable in any event for incidental or       *
 * consequential damages in connection with, or arising out of, the       *
 * furnishing, performance, or use of these programs.                     *
 **************************************************************************/
