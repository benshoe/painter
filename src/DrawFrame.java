// GCS Exercise 14.17 Solution: DrawFrame.java
// Program that creates a panel for the user to draw shapes.
// Allows the user to choose the shape and color.
import javax.swing.*;
import java.awt.*;

public class DrawFrame extends JFrame {
    // Array of possible shapes
    private DrawPanel drawPanel; // panel that handles the drawing

    // constructor
   public DrawFrame()
   {
       super("Painter - New file.painter");

       setLookAndFeel();

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
       componentPanel.setOnDrawModeClicked(e -> drawPanel.setDrawmode());
       componentPanel.setOnSelectModeClicked(e -> drawPanel.setSelectmode());
       componentPanel.setOnUndoClicked(e -> drawPanel.clearLastShape());
       componentPanel.setOnClearClicked(e -> drawPanel.clearDrawing());
       componentPanel.setOnRedoClicked(e -> drawPanel.redoLastRemovedShape());
       componentPanel.setOnColorClicked(e -> drawPanel.setDrawingColor((Color) e.getSource()));

       //Save and open buttons
       Icon openIcon = new ImageIcon(getClass().getResource(Images.OPEN));
       JButton openFileButton = PainterPanel.addButtonToPanel(topPanel, openIcon, "Open File");
       openFileButton.addActionListener(e -> drawPanel.openFileDialog());

       Icon saveIcon = new ImageIcon(getClass().getResource(Images.SAVE));
       JButton saveFileButton = PainterPanel.addButtonToPanel(topPanel, saveIcon, "Save File");
       saveFileButton.addActionListener(e -> drawPanel.saveFileDialog());

       //Draw shape buttons
       Icon lineIcon = new ImageIcon(getClass().getResource(Images.LINE));
       JButton drawLineButton = PainterPanel.addButtonToPanel(topPanel, lineIcon, "Line");
       drawLineButton.addActionListener(e -> drawPanel.setShapeType(ShapeType.LINE));

       Icon squareIcon = new ImageIcon(getClass().getResource(Images.SQUARE));
       JButton drawSquareButton = PainterPanel.addButtonToPanel(topPanel, squareIcon, "Square");
       drawSquareButton.addActionListener(e -> drawPanel.setShapeType(ShapeType.SQUARE));

       Icon rectIcon = new ImageIcon(getClass().getResource(Images.RECTANGLE));
       JButton drawRectButton = PainterPanel.addButtonToPanel(topPanel, rectIcon, "Rectangle");
       drawRectButton.addActionListener(e -> drawPanel.setShapeType(ShapeType.RECTANGLE));

       Icon circleIcon = new ImageIcon(getClass().getResource(Images.CIRCLE));
       JButton drawCirleButton = PainterPanel.addButtonToPanel(topPanel, circleIcon, "Circle");
       drawCirleButton.addActionListener(e -> drawPanel.setShapeType(ShapeType.CIRCLE));

       Icon ovalIcon = new ImageIcon(getClass().getResource(Images.OVAL));
       JButton drawOvalButton = PainterPanel.addButtonToPanel(topPanel, ovalIcon, "Oval");
      drawOvalButton.addActionListener(e1 -> drawPanel.setShapeType(ShapeType.OVAL));

       Icon roundedRectIcon = new ImageIcon(getClass().getResource(Images.ROUNDED_RECTANGLE));
       JButton drawRoundedSquare = PainterPanel.addButtonToPanel(topPanel, roundedRectIcon, "Rounded rectangle");
       drawRoundedSquare.addActionListener(e -> drawPanel.setShapeType(ShapeType.ROUNDED_RECTANGLE));

       //Line thinkness Button
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
